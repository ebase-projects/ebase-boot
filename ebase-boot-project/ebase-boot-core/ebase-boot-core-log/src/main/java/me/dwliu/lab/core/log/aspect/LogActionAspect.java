package me.dwliu.lab.core.log.aspect;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import me.dwliu.lab.core.log.annotation.LogAction;
import me.dwliu.lab.core.log.event.LogActionPublisher;
import me.dwliu.lab.core.log.api.dto.SysLogOperationDTO;
import me.dwliu.lab.core.tool.util.HttpContextUtils;
import me.dwliu.lab.core.tool.util.IPUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.HttpHeaders;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * 操作日志，异步入库
 *
 * @author liudw
 * @date 2019-03-08 13:28
 **/
@Slf4j
@Aspect
//@Component
public class LogActionAspect {

    @Pointcut("@annotation(me.dwliu.lab.core.log.annotation.LogAction)")
    public void logPointCut() {
    }

    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        log.debug("操作日志，异步入库开始");
        Object result = null;

        try {
            //获取开始时间毫秒数
            long startTime = System.currentTimeMillis();
            //执行方法
            result = point.proceed();
            //获取执行时间（执行完业务后的时间毫秒数-开始时间）
            long time = System.currentTimeMillis() - startTime;

            // 记录日志  发布消息日志
            saveLog(point, time, 0);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }


    }

    /**
     * 记录日志  发布消息日志
     *
     * @param joinPoint
     * @param time      执行时间
     * @param status
     */
    private void saveLog(ProceedingJoinPoint joinPoint, long time, Integer status) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        SysLogOperationDTO log = new SysLogOperationDTO();
        LogAction annotation = method.getAnnotation(LogAction.class);
        if (annotation != null) {
            //注解上的描述
            log.setOperation(annotation.value());
        }

//		//登录用户信息
//		UserDetail user = SecurityUser.getUser();
//		if (user != null) {
//			log.setCreatorName(user.getUsername());
//		}

        //log.setStatus(status);
        log.setRequestTime((int) time);


        //请求相关信息
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        log.setRemoteIp(IPUtil.getIpAddr(request));
        log.setUserAgent(request.getHeader(HttpHeaders.USER_AGENT));
        log.setRequestUri(request.getRequestURI());
        log.setRequestMethod(request.getMethod());

        //请求参数
        Object[] args = joinPoint.getArgs();
        try {
            String params = JSON.toJSONString(args[0]);
            log.setRequestParams(params);
        } catch (Exception e) {

        }

        //保存到DB
        LogActionPublisher.publishEvent(log);

    }
}
