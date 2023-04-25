package me.dwliu.framework.integration.security.validatecode;

import jakarta.servlet.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.stereotype.Component;


/**
 * 校验码相关安全配置
 *
 * @author liudw
 * @date 2019-04-24 17:04
 **/
@Component("validateCodeSecurityConfig")
public class ValidateCodeSecurityConfig
	extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

	@Autowired
	private Filter validateCodeFilter;

	/**
	 * 将验证码拦截器添加到 {@code AbstractPreAuthenticatedProcessingFilter} 之前
	 * <br/>
	 * {@code validateCodeFilter}  是验证码嘛拦截器的入口
	 *
	 * @param http
	 * @throws Exception
	 */
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.addFilterBefore(validateCodeFilter, AbstractPreAuthenticatedProcessingFilter.class);
	}

}
