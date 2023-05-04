package me.dwliu.framework.autoconfigure.security;

import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.dwliu.framework.autoconfigure.security.jwt.JwtConfigProperties;
import me.dwliu.framework.core.security.crypto.CustomPasswordEncoderFactories;
import me.dwliu.framework.integration.security.feign.UserInfoDetailsWebClient;
import me.dwliu.framework.integration.security.jwt.JwtTokenUtils;
import me.dwliu.framework.integration.security.service.CustomUserDetailsService;
import me.dwliu.framework.integration.security.service.DefaultUserDetailsServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

/**
 * 安全配置类
 *
 * @author liudw
 * @date 2020/7/4 11:39
 **/
@Configuration
@ConditionalOnClass({PasswordEncoder.class})
@Slf4j
@ComponentScan(value = "me.dwliu.framework.integration.security")
public class SecurityConfig {

	/**
	 * 声明密码加密器
	 *
	 * @return
	 */
	@Bean
	@ConditionalOnMissingBean(value = PasswordEncoder.class)
	public PasswordEncoder passwordEncoder() {
		log.debug("初始化passwordEncoder: PasswordEncoderFactories.createDelegatingPasswordEncoder()");
		return CustomPasswordEncoderFactories.createDelegatingPasswordEncoder();
	}


	/**
	 * 加载默认查询用户信息服务
	 *
	 * @return
	 */
	@Bean
	@ConditionalOnMissingBean(CustomUserDetailsService.class)
	public CustomUserDetailsService customUserDetailsService() {
		log.debug("使用默认的获取用户信息service: DefaultUserDetailsServiceImpl");
		return new DefaultUserDetailsServiceImpl();
	}

	@Bean
	@ConditionalOnMissingBean(CustomUserDetailsService.class)
	public WebClient employeeWebClient() {
		return WebClient.builder()
//			.baseUrl("http://employee-service")
			.build();
	}

	@Bean
	@ConditionalOnMissingBean(CustomUserDetailsService.class)
	public UserInfoDetailsWebClient userInfoDetailsWebClient() {
		HttpServiceProxyFactory httpServiceProxyFactory
			= HttpServiceProxyFactory
			.builder(WebClientAdapter.forClient(employeeWebClient()))
			.build();
		return httpServiceProxyFactory.createClient(UserInfoDetailsWebClient.class);
	}


	@Bean
	@ConditionalOnClass(Jwts.class)
	public JwtTokenUtils jwtTokenUtils(SecurityProperties securityProperties) {
		JwtConfigProperties jwt = securityProperties.getJwt();
		return new JwtTokenUtils(jwt.getSecret(),
			jwt.getExpiration(),
			jwt.getTokenHeaderKey(),
			jwt.getTokenParamsKey(),
			jwt.getTokenPrefix());
	}


}
