package me.dwliu.framework.integration.security.userpwdjson;

import lombok.extern.slf4j.Slf4j;
import me.dwliu.framework.core.security.cache.CacheService;
import me.dwliu.framework.integration.security.handler.CustomJsonAccessDeniedHandler;
import me.dwliu.framework.integration.security.handler.CustomJsonAuthenticationEntryPoint;
import me.dwliu.framework.integration.security.handler.CustomJsonAuthenticationFailureHandler;
import me.dwliu.framework.integration.security.handler.CustomJsonAuthenticationSuccessHandler;
import me.dwliu.framework.integration.security.jwt.JwtTokenUtils;
import me.dwliu.framework.integration.security.mobile.SmsCodeAuthenticationConfigurer;
import me.dwliu.framework.integration.security.service.CustomUserDetailsService;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;

/**
 * 登录过滤器的配置类
 *
 * @author liudw
 * @date 2019-04-28 15:59
 **/
@Slf4j
public class CustomJsonLoginAuthenticationConfigurer
	extends AbstractHttpConfigurer<CustomJsonLoginAuthenticationConfigurer, HttpSecurity> {

	private CustomUserDetailsService userDetailsService;
	private JwtTokenUtils jwtTokenUtils;
	private CacheService cacheService;
//	private final AuthenticationEntryPoint authenticationEntryPoint;

	public CustomJsonLoginAuthenticationConfigurer() {
	}

	/**
	 * JWT 登陆配置入口
	 *
	 * @return
	 */
	//public static CustomJsonLoginAuthenticationConfigurer jwtLogin() {
	//	return new CustomJsonLoginAuthenticationConfigurer();
	//}





	@Override
	public void init(HttpSecurity http) throws Exception {
		log.debug("===init CustomJsonLoginAuthenticationConfigurer===");

		jwtTokenUtils = getSharedOrBean(http, JwtTokenUtils.class);
		cacheService = getSharedOrBean(http, CacheService.class);
		userDetailsService = getSharedOrBean(http, CustomUserDetailsService.class);
	}

	/**
	 * 将登录接口的过滤器配置到过滤器链中
	 * 1. 配置登录成功、失败处理器
	 * 2. 配置自定义的userDetailService（从数据库中获取用户数据）
	 * 3. 将自定义的过滤器配置到spring security的过滤器链中，配置在UsernamePasswordAuthenticationFilter之前
	 *
	 * @param http
	 * @throws Exception
	 */
	@Override
	public void configure(HttpSecurity http) throws Exception {

		/**
		 * 替换内置实现类Http403ForbiddenEntryPoint
		 * ExceptionTranslationFilter throw new AccessDeniedException("Access Denied") 并且为匿名用户
		 */
		http.exceptionHandling().authenticationEntryPoint(new CustomJsonAuthenticationEntryPoint());
		http.exceptionHandling().accessDeniedHandler(new CustomJsonAccessDeniedHandler());

		CustomJsonLoginAuthenticationFilter customJsonLoginAuthenticationFilter = new CustomJsonLoginAuthenticationFilter();
		AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
		customJsonLoginAuthenticationFilter.setAuthenticationManager(authenticationManager);

		customJsonLoginAuthenticationFilter.setAuthenticationSuccessHandler(new CustomJsonAuthenticationSuccessHandler(jwtTokenUtils, cacheService));
		customJsonLoginAuthenticationFilter.setAuthenticationFailureHandler(new CustomJsonAuthenticationFailureHandler());

		//直接使用DaoAuthenticationProvider
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		//设置userDetailService
		provider.setUserDetailsService(userDetailsService);
		http.authenticationProvider(provider);


		//自定义的filter放到UsernamePasswordAuthenticationFilter之后执行
		http.addFilterBefore(customJsonLoginAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

		CustomJsonValidateLoginTokenFilter customJsonValidateLoginTokenFilter = new CustomJsonValidateLoginTokenFilter(jwtTokenUtils, cacheService);
		http.addFilterBefore(customJsonValidateLoginTokenFilter, LogoutFilter.class);

	}


	private <C> C getSharedOrBean(HttpSecurity http, Class<C> type) {
		C shared = http.getSharedObject(type);
		if (shared != null) {
			return shared;
		}
		return getBeanOrNull(type);
	}

	private <T> T getBeanOrNull(Class<T> type) {
		ApplicationContext context = getBuilder().getSharedObject(ApplicationContext.class);
		if (context == null) {
			return null;
		}
		try {
			return context.getBean(type);
		} catch (NoSuchBeanDefinitionException ex) {
			return null;
		}
	}
}
