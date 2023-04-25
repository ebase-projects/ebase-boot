//package me.dwliu.framework.autoconfigure.security;
//
//import cn.hutool.core.util.ReUtil;
//import lombok.Getter;
//import lombok.Setter;
//import me.dwliu.framework.core.security.annotation.InnerCall;
//import org.springframework.beans.factory.InitializingBean;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.annotation.AnnotationUtils;
//import org.springframework.web.context.WebApplicationContext;
//import org.springframework.web.method.HandlerMethod;
//import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
//import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
//
//import java.util.LinkedList;
//import java.util.List;
//import java.util.Map;
//import java.util.Optional;
//import java.util.regex.Pattern;
//
///**
// * 资源服务器过滤不受权限拦截的url
// *
// * @author liudw
// * @date 2019-08-14 15:23
// **/
//@Configuration
//@ConditionalOnExpression("'${security.oauth2.client.ignore-urls}'.length() > 0")
//@ConfigurationProperties(prefix = "security.oauth2.client")
//public class OAuth2Client4IgnoreUrlProperties implements InitializingBean {
//	private static final Pattern PATTERN = Pattern.compile("\\{(.*?)\\}");
//
//	/**
//	 * 需过滤的URl
//	 */
//	@Getter
//	@Setter
//	private List<String> ignoreUrls = new LinkedList<>();
//
//
//	@Autowired
//	private WebApplicationContext applicationContext;
//
//	@Override
//	public void afterPropertiesSet() {
//		RequestMappingHandlerMapping mapping = applicationContext.getBean(RequestMappingHandlerMapping.class);
//		Map<RequestMappingInfo, HandlerMethod> map = mapping.getHandlerMethods();
//
//		map.keySet().forEach(info -> {
//			HandlerMethod handlerMethod = map.get(info);
//
//			// 获取方法上边的注解 替代path variable 为 *
//			InnerCall method = AnnotationUtils.findAnnotation(handlerMethod.getMethod(), InnerCall.class);
//			Optional.ofNullable(method)
//				.ifPresent(InnerCall -> info.getPatternsCondition().getPatterns()
//					.forEach(url -> ignoreUrls.add(ReUtil.replaceAll(url, PATTERN, "*"))));
//
//			// 获取类上边的注解, 替代path variable 为 *
//			InnerCall controller = AnnotationUtils.findAnnotation(handlerMethod.getBeanType(), InnerCall.class);
//			Optional.ofNullable(controller)
//				.ifPresent(inner -> info.getPatternsCondition().getPatterns()
//					.forEach(url -> ignoreUrls.add(ReUtil.replaceAll(url, PATTERN, "*"))));
//		});
//
//	}
//}
