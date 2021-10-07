package me.dwliu.framework.integration.security.mobile;

import me.dwliu.framework.integration.security.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

/**
 * 短信登陆配置
 *
 * @author liudw
 * @date 2019-04-28 15:59
 **/
@Component
public class SmsCodeAuthenticationSecurityConfig
	extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

	@Autowired
	private AuthenticationSuccessHandler ebaseAuthenticationSuccessHandler;

	@Autowired
	private AuthenticationFailureHandler ebaseAuthenticationFailureHandler;

	@Autowired
	private CustomUserDetailsService userDetailsService;

//	@Autowired
//	private PersistentTokenRepository persistentTokenRepository;

	@Override
	public void configure(HttpSecurity http) throws Exception {
		SmsCodeAuthenticationFilter smsCodeAuthenticationFilter = new SmsCodeAuthenticationFilter();
		smsCodeAuthenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
		smsCodeAuthenticationFilter.setAuthenticationSuccessHandler(ebaseAuthenticationSuccessHandler);
		smsCodeAuthenticationFilter.setAuthenticationFailureHandler(ebaseAuthenticationFailureHandler);

//		String key = UUID.randomUUID().toString();
//		smsCodeAuthenticationFilter.setRememberMeServices(
//			new PersistentTokenBasedRememberMeServices(key, userDetailsService, persistentTokenRepository));
//

		SmsCodeAuthenticationProvider smsCodeAuthenticationProvider = new SmsCodeAuthenticationProvider();
		smsCodeAuthenticationProvider.setUserDetailsService(userDetailsService);

		//自定义的filter放到UsernamePasswordAuthenticationFilter之后执行
		http.authenticationProvider(smsCodeAuthenticationProvider)
			.addFilterAfter(smsCodeAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

//		http.authenticationProvider(smsCodeAuthenticationProvider)
//			.addFilterAfter(smsCodeAuthenticationFilter, LogoutFilter.class);

	}
}
