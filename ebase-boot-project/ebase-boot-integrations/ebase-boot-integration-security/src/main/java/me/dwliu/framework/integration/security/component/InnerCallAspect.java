//package me.dwliu.framework.integration.security.component;
//
//import jakarta.servlet.http.HttpServletRequest;
//import lombok.extern.slf4j.Slf4j;
//import me.dwliu.framework.core.security.constant.SecurityCoreConstant;
//import me.dwliu.framework.core.tool.util.HttpContextUtils;
//import org.apache.commons.lang3.StringUtils;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.springframework.security.access.AccessDeniedException;
//import org.springframework.stereotype.Component;
//
//
///**
// * 服务间接口不鉴权处理
// *
// * @author liudw
// * @date 2019-08-15 14:38
// **/
//@Aspect
//@Component
//@Slf4j
//public class InnerCallAspect {
//
////    @Pointcut("@annotation(me.dwliu.framework.security.annotation.InnerCall)")
////    public void innerCallCut() {
////    }
//
//	@Around("@annotation(innerCall)")
//	public Object around(ProceedingJoinPoint point, InnerCall innerCall) throws Throwable {
//		HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
//		String header = request.getHeader(SecurityCoreConstant.FROM);
//		if (innerCall.value() && !StringUtils.equals(header, SecurityCoreConstant.FROM_IN)) {
//			log.warn("访问接口没有权限：{}", point.getSignature().getName());
//			throw new AccessDeniedException(String.format("访问接口没有权限:%s", point.getSignature().getName()));
//		}
//
//		return point.proceed();
//
//	}
//
//}
