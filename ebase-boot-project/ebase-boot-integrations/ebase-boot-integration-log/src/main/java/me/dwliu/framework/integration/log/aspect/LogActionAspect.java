package me.dwliu.framework.integration.log.aspect;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.dwliu.framework.core.log.annotation.LogAction;
import me.dwliu.framework.core.log.dto.LogOperationDTO;
import me.dwliu.framework.core.log.enums.LogTypeEnum;
import me.dwliu.framework.core.log.producer.LogProducer;
import me.dwliu.framework.core.security.entity.UserInfoDetails;
import me.dwliu.framework.core.security.utils.SecurityUtils;
import me.dwliu.framework.core.tool.util.HttpContextUtils;
import me.dwliu.framework.core.tool.util.IPUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.HttpHeaders;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.time.LocalDateTime;

/**
 * 操作日志，异步入库
 *
 * @author liudw
 * @date 2019-03-08 13:28
 **/
@Slf4j
@Aspect
//@Component
@AllArgsConstructor
public class LogActionAspect {

	private final LogProducer logProducer;

	@Pointcut("@annotation(me.dwliu.framework.core.log.annotation.LogAction)")
	public void logPointCut() {
	}

	@Around("logPointCut()")
	public Object around(ProceedingJoinPoint point) throws Throwable {
		log.trace("操作日志，异步入库开始");
		Object result = null;
		//获取开始时间毫秒数
		long startTime = System.currentTimeMillis();

		try {

			//执行方法
			result = point.proceed();
			//获取执行时间（执行完业务后的时间毫秒数-开始时间）
			long time = System.currentTimeMillis() - startTime;

			// 记录日志  发布消息日志
			saveLog(point, time, 0);
			return result;
		} catch (Exception e) {
			//获取执行时间（执行完业务后的时间毫秒数-开始时间）
			long time = System.currentTimeMillis() - startTime;
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

		LogOperationDTO log = new LogOperationDTO();
		LogAction annotation = method.getAnnotation(LogAction.class);
		if (annotation != null) {
			//注解上的描述
			log.setOperation(annotation.value());
		}

		log.setType(LogTypeEnum.OPERATION.getValue());

		//登录用户信息
		UserInfoDetails user = SecurityUtils.getUser();
		if (user != null) {
			log.setCreatorName(user.getUsername());
			log.setCreateBy(user.getUserId());
		}

		log.setStatus(status);
		log.setCreateTime(LocalDateTime.now());
		log.setRequestTime((int) time);


		//请求相关信息
		HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
		log.setIp(IPUtil.getIpAddr(request));
		log.setUserAgent(request.getHeader(HttpHeaders.USER_AGENT));
		log.setRequestUri(request.getRequestURI());
		log.setRequestMethod(request.getMethod());

		//请求参数
		Object[] args = joinPoint.getArgs();
		try {
			String params = JSON.toJSONString(args[0]);
			log.setRequestParams(params);
		} catch (Exception e) {
			//丢弃
		}

		//保存到DB
		// LogActionPublisher.publishEvent(log);
		logProducer.saveLog(log);
	}
}
