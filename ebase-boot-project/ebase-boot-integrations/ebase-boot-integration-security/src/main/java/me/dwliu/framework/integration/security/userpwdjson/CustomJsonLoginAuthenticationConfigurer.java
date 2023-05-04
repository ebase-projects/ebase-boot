package me.dwliu.framework.integration.security.userpwdjson;

import lombok.RequiredArgsConstructor;
import me.dwliu.framework.integration.security.handler.CustomJsonAuthenticationFailureHandler;
import me.dwliu.framework.integration.security.handler.CustomJsonAuthenticationSuccessHandler;
import me.dwliu.framework.integration.security.jwt.JwtTokenUtils;
import me.dwliu.framework.integration.security.service.CustomUserDetailsService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

/**
 * 登录过滤器的配置类
 *
 * @author liudw
 * @date 2019-04-28 15:59
 **/
@Component
@RequiredArgsConstructor
public class CustomJsonLoginAuthenticationConfigurer
	extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

	private final CustomUserDetailsService userDetailsService;
	private final JwtTokenUtils jwtTokenUtils;
//	private final AuthenticationEntryPoint authenticationEntryPoint;

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
		CustomJsonLoginAuthenticationFilter filter = new CustomJsonLoginAuthenticationFilter();
		AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
		filter.setAuthenticationManager(authenticationManager);

		filter.setAuthenticationSuccessHandler(new CustomJsonAuthenticationSuccessHandler(jwtTokenUtils));
		filter.setAuthenticationFailureHandler(new CustomJsonAuthenticationFailureHandler());

		//直接使用DaoAuthenticationProvider
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		//设置userDetailService
		provider.setUserDetailsService(userDetailsService);
		http.authenticationProvider(provider);


		//自定义的filter放到UsernamePasswordAuthenticationFilter之后执行
		http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);


		CustomJsonValidateLoginTokenFilter filter1 = new CustomJsonValidateLoginTokenFilter(jwtTokenUtils);

		http.addFilterBefore(filter1, UsernamePasswordAuthenticationFilter.class);

	}
}
