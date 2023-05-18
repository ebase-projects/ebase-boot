package me.dwliu.framework.autoconfigure.security;

import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import me.dwliu.framework.autoconfigure.security.jwt.JwtConfigProperties;
import me.dwliu.framework.core.security.cache.CacheService;
import me.dwliu.framework.core.security.cache.DefaultCacheService;
import me.dwliu.framework.core.security.crypto.CustomPasswordEncoderFactories;
import me.dwliu.framework.integration.security.jwt.JwtTokenUtils;
import me.dwliu.framework.integration.security.service.CustomUserDetailsService;
import me.dwliu.framework.integration.security.service.DefaultUserDetailsServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

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
		log.trace("初始化passwordEncoder: PasswordEncoderFactories.createDelegatingPasswordEncoder()");
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
		log.trace("===使用默认的获取用户信息service: DefaultUserDetailsServiceImpl===");
		return new DefaultUserDetailsServiceImpl();
	}

	@Bean
	@ConditionalOnMissingBean(CacheService.class)
	public CacheService cacheService(SecurityProperties securityProperties) {
		log.trace("===使用默认的cacheservice缓存security信息===");
		return new DefaultCacheService(securityProperties.getJwt().getExpiration());
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
