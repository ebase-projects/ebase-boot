package me.dwliu.framework.integration.security.mobile;

import lombok.extern.slf4j.Slf4j;
import me.dwliu.framework.core.security.cache.CacheService;
import me.dwliu.framework.integration.security.handler.CustomJsonAuthenticationFailureHandler;
import me.dwliu.framework.integration.security.handler.CustomJsonAuthenticationSuccessHandler;
import me.dwliu.framework.integration.security.jwt.JwtTokenUtils;
import me.dwliu.framework.integration.security.service.CustomUserDetailsService;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * 短信登陆配置
 *
 * @author liudw
 * @date 2019-04-28 15:59
 **/
@Slf4j
//@Component
//@RequiredArgsConstructor
public class SmsCodeAuthenticationConfigurer
	extends AbstractHttpConfigurer<SmsCodeAuthenticationConfigurer, HttpSecurity> {


	//private AuthenticationSuccessHandler authenticationSuccessHandler;
	//private AuthenticationFailureHandler authenticationFailureHandler;
	private CustomUserDetailsService userDetailsService;
	private JwtTokenUtils jwtTokenUtils;
	private CacheService cacheService;

//	@Autowired
//	private PersistentTokenRepository persistentTokenRepository;

	//public static   SmsCodeAuthenticationConfigurer smsLogin() {
	//	return new SmsCodeAuthenticationConfigurer(userDetailsService, jwtTokenUtils, cacheService);
	//}


	public SmsCodeAuthenticationConfigurer() {
	}

	//public SmsCodeAuthenticationConfigurer authenticationSuccessHandler(AuthenticationSuccessHandler authenticationSuccessHandler) {
	//	this.authenticationSuccessHandler = authenticationSuccessHandler;
	//	return this;
	//}
	//
	//public SmsCodeAuthenticationConfigurer authenticationFailureHandler(AuthenticationFailureHandler authenticationFailureHandler) {
	//	this.authenticationFailureHandler = authenticationFailureHandler;
	//	return this;
	//}

	//public SmsCodeAuthenticationConfigurer userDetailsService(CustomUserDetailsService userDetailsService) {
	//	this.userDetailsService = userDetailsService;
	//	return this;
	//}
	//
	//public SmsCodeAuthenticationConfigurer cacheService(CacheService cacheService) {
	//	this.cacheService = cacheService;
	//	return this;
	//}
	//
	//public SmsCodeAuthenticationConfigurer jwtToken(JwtTokenUtils jwtTokenUtils) {
	//	this.jwtTokenUtils = jwtTokenUtils;
	//	return this;
	//}


	//private CustomUserDetailsService getUserDetailsService(HttpSecurity http) {
	//
	//
	//	return getService(http);
	//
	//
	//	//Assert.state(this.userDetailsService != null,
	//	//	() -> "userDetailsService cannot be null. Invoke " + RememberMeConfigurer.class.getSimpleName()
	//	//		+ "#userDetailsService(UserDetailsService) or see its javadoc for alternative approaches.");
	//	//return this.userDetailsService;
	//}

	//private CustomUserDetailsService getBean(HttpSecurity http) {
	//	if (this.userDetailsService == null) {
	//		CustomUserDetailsService shared = http.getSharedObject(CustomUserDetailsService.class);
	//		if (shared != null) {
	//			return this.userDetailsService = shared;
	//		}
	//	}
	//
	//	ApplicationContext context = getBuilder().getSharedObject(ApplicationContext.class);
	//	if (context == null) {
	//		return null;
	//	}
	//	try {
	//		return this.userDetailsService = context.getBean(CustomUserDetailsService.class);
	//	} catch (NoSuchBeanDefinitionException ex) {
	//		return null;
	//	}
	//}

	@Override
	public void init(HttpSecurity http) throws Exception {
		log.debug("===init SmsCodeAuthenticationConfigurer===");
		//validateInput();
		//String key = getKey();
		//RememberMeServices rememberMeServices = getRememberMeServices(http, key);
		//http.setSharedObject(RememberMeServices.class, rememberMeServices);
		//LogoutConfigurer<H> logoutConfigurer = http.getConfigurer(LogoutConfigurer.class);
		//if (logoutConfigurer != null && this.logoutHandler != null) {
		//	logoutConfigurer.addLogoutHandler(this.logoutHandler);
		//}
		//RememberMeAuthenticationProvider authenticationProvider = new RememberMeAuthenticationProvider(key);
		//authenticationProvider = postProcess(authenticationProvider);
		//http.authenticationProvider(authenticationProvider);
		//initDefaultLoginFilter(http);

		jwtTokenUtils = getSharedOrBean(http, JwtTokenUtils.class);
		cacheService = getSharedOrBean(http, CacheService.class);
		userDetailsService = getSharedOrBean(http, CustomUserDetailsService.class);
	}


	@Override
	public void configure(HttpSecurity http) throws Exception {
		AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
		SmsCodeAuthenticationFilter smsCodeAuthenticationFilter = new SmsCodeAuthenticationFilter();
		smsCodeAuthenticationFilter.setAuthenticationManager(authenticationManager);

		//if (this.authenticationSuccessHandler != null) {
		//	smsCodeAuthenticationFilter.setAuthenticationSuccessHandler(this.authenticationSuccessHandler);
		//}

		//JwtTokenUtils jwtTokenUtils = getSharedOrBean(http, JwtTokenUtils.class);
		//CacheService cacheService = getSharedOrBean(http, CacheService.class);
		//CustomUserDetailsService userDetailsService = getSharedOrBean(http, CustomUserDetailsService.class);

		smsCodeAuthenticationFilter.setAuthenticationSuccessHandler(new CustomJsonAuthenticationSuccessHandler(jwtTokenUtils, cacheService));
		smsCodeAuthenticationFilter.setAuthenticationFailureHandler(new CustomJsonAuthenticationFailureHandler());

		SmsCodeAuthenticationProvider smsCodeAuthenticationProvider = new SmsCodeAuthenticationProvider();
		smsCodeAuthenticationProvider.setUserDetailsService(userDetailsService);
		http.authenticationProvider(smsCodeAuthenticationProvider);

//		String key = UUID.randomUUID().toString();
//		smsCodeAuthenticationFilter.setRememberMeServices(
//			new PersistentTokenBasedRememberMeServices(key, userDetailsService, persistentTokenRepository));
//

		//自定义的filter放到UsernamePasswordAuthenticationFilter之后执行
		http.addFilterBefore(smsCodeAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

	}


	public static SmsCodeAuthenticationConfigurer smsLogin() {
		return new SmsCodeAuthenticationConfigurer();
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
