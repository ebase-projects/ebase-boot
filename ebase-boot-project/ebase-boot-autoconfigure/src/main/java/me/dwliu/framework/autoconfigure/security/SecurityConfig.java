package me.dwliu.framework.autoconfigure.security;

import lombok.extern.slf4j.Slf4j;
import me.dwliu.framework.core.security.crypto.CustomPasswordEncoderFactories;
import me.dwliu.framework.plugin.security.service.CustomUserDetailsService;
import me.dwliu.framework.plugin.security.service.DefaultUserDetailsServiceImpl;
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
@Slf4j
@ComponentScan(value = "me.dwliu.framework.plugin.security")
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

}
