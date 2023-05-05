package me.dwliu.framework.integration.security.mobile;

import lombok.RequiredArgsConstructor;
import me.dwliu.framework.core.security.cache.CacheService;
import me.dwliu.framework.integration.security.handler.CustomJsonAuthenticationFailureHandler;
import me.dwliu.framework.integration.security.handler.CustomJsonAuthenticationSuccessHandler;
import me.dwliu.framework.integration.security.jwt.JwtTokenUtils;
import me.dwliu.framework.integration.security.service.CustomUserDetailsService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

/**
 * 短信登陆配置
 *
 * @author liudw
 * @date 2019-04-28 15:59
 **/
@Component
@RequiredArgsConstructor
public class SmsCodeAuthenticationConfigurer
	extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

	private final CustomUserDetailsService userDetailsService;
	private final JwtTokenUtils jwtTokenUtils;
	private final CacheService cacheService;

//	@Autowired
//	private PersistentTokenRepository persistentTokenRepository;

	@Override
	public void configure(HttpSecurity http) throws Exception {
		AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
		SmsCodeAuthenticationFilter filter = new SmsCodeAuthenticationFilter();
		filter.setAuthenticationManager(authenticationManager);

		filter.setAuthenticationSuccessHandler(new CustomJsonAuthenticationSuccessHandler(jwtTokenUtils, cacheService));
		filter.setAuthenticationFailureHandler(new CustomJsonAuthenticationFailureHandler());

		SmsCodeAuthenticationProvider smsCodeAuthenticationProvider = new SmsCodeAuthenticationProvider();
		smsCodeAuthenticationProvider.setUserDetailsService(userDetailsService);
		http.authenticationProvider(smsCodeAuthenticationProvider);

//		String key = UUID.randomUUID().toString();
//		filter.setRememberMeServices(
//			new PersistentTokenBasedRememberMeServices(key, userDetailsService, persistentTokenRepository));
//

		//自定义的filter放到UsernamePasswordAuthenticationFilter之后执行
		http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);

	}
}
