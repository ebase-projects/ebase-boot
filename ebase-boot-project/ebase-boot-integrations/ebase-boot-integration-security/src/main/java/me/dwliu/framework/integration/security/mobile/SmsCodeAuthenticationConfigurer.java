package me.dwliu.framework.integration.security.mobile;

import lombok.extern.slf4j.Slf4j;
import me.dwliu.framework.core.security.cache.CacheService;
import me.dwliu.framework.integration.security.validatecode.ValidateUrlsMap;
import me.dwliu.framework.integration.security.handler.CustomJsonAuthenticationFailureHandler;
import me.dwliu.framework.integration.security.handler.CustomJsonAuthenticationSuccessHandler;
import me.dwliu.framework.integration.security.jwt.JwtTokenUtils;
import me.dwliu.framework.integration.security.service.CustomUserDetailsService;
import me.dwliu.framework.integration.security.validatecode.enums.ValidateCodeTypeEnum;
import org.apache.commons.lang3.StringUtils;
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

	/**
	 * 短信登录请求路径
	 */
	private String loginSmsUrl;
	/**
	 * 手机验证码登录设置属性名，默认为"mobile"
	 */
	//private String mobileParameter;

//	@Autowired
//	private PersistentTokenRepository persistentTokenRepository;

	//public static   SmsCodeAuthenticationConfigurer smsLogin() {
	//	return new SmsCodeAuthenticationConfigurer(userDetailsService, jwtTokenUtils, cacheService);
	//}


	public SmsCodeAuthenticationConfigurer() {
	}

	/**
	 * 短信登录请求路径
	 */
	public SmsCodeAuthenticationConfigurer loginSmsUrl(String loginSmsUrl) {
		this.loginSmsUrl = loginSmsUrl;
		return this;
	}

	///**
	// * 手机验证码登录设置属性名，默认为"mobile"
	// */
	//public SmsCodeAuthenticationConfigurer mobileParameter(String mobileParameter) {
	//	this.mobileParameter = mobileParameter;
	//	return this;
	//}
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

		jwtTokenUtils = getSharedOrBean(http, JwtTokenUtils.class);
		cacheService = getSharedOrBean(http, CacheService.class);
		userDetailsService = getSharedOrBean(http, CustomUserDetailsService.class);
	}


	@Override
	public void configure(HttpSecurity http) throws Exception {
		AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
		SmsCodeAuthenticationFilter smsCodeAuthenticationFilter = new SmsCodeAuthenticationFilter();
		smsCodeAuthenticationFilter.setAuthenticationManager(authenticationManager);
		if (StringUtils.isNotBlank(this.loginSmsUrl)) {
			smsCodeAuthenticationFilter.setFilterProcessesUrl(this.loginSmsUrl);
			// 加入到验证码校验拦截器里
			ValidateUrlsMap.getValidateUrlsMap().put(this.loginSmsUrl, ValidateCodeTypeEnum.SMS);
		}

		//if (StringUtils.isNotBlank(this.mobileParameter)) {
		//	smsCodeAuthenticationFilter.setMobileParameter(this.mobileParameter);
		//}

		//if (this.authenticationSuccessHandler != null) {
		//	smsCodeAuthenticationFilter.setAuthenticationSuccessHandler(this.authenticationSuccessHandler);
		//}

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
