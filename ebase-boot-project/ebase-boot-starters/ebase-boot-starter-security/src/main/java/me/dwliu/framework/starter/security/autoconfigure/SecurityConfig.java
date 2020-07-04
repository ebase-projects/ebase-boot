package me.dwliu.framework.starter.security.autoconfigure;

import lombok.extern.slf4j.Slf4j;
import me.dwliu.framework.plugin.security.service.DefaultUserDetailsServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * 安全配置类
 *
 * @author liudw
 * @date 2020/7/4 11:39
 **/
@Configuration
@Slf4j
public class SecurityConfig {

	/**
	 * 获取用户信息service
	 *
	 * @return
	 */
	@Bean
	@ConditionalOnMissingBean(UserDetailsService.class)
	public UserDetailsService userDetailsService() {
		log.debug("使用默认的获取用户信息service: userDetailsService");
		return new DefaultUserDetailsServiceImpl();
	}

}
