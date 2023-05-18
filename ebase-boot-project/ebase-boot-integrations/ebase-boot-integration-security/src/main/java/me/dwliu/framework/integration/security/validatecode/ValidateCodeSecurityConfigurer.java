package me.dwliu.framework.integration.security.validatecode;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import me.dwliu.framework.integration.security.mobile.SmsCodeAuthenticationConfigurer;
import me.dwliu.framework.integration.security.validatecode.filter.ValidateCodeFilter;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;


/**
 * 校验码相关安全配置
 *
 * @author liudw
 * @date 2019-04-24 17:04
 **/
@Slf4j
//@Component("validateCodeSecurityConfigurer")
//@RequiredArgsConstructor
@Data
public class ValidateCodeSecurityConfigurer
	extends AbstractHttpConfigurer<ValidateCodeSecurityConfigurer, HttpSecurity> {

	private ValidateCodeFilter validateCodeFilter;
	//private String loginImageUrl;
	//private String loginSmsUrl;

	public ValidateCodeSecurityConfigurer() {
	}

	//public ValidateCodeSecurityConfigurer loginImageUrl(String loginImageUrl) {
	//	this.loginImageUrl = loginImageUrl;
	//	return this;
	//}
	//
	//public ValidateCodeSecurityConfigurer loginSmsUrl(String loginSmsUrl) {
	//	this.loginSmsUrl = loginSmsUrl;
	//	return this;
	//}


	/**
	 * 校验码 配置入口
	 *
	 * @return
	 */
	//public ValidateCodeSecurityConfigurer validateCode() {
	//	return new ValidateCodeSecurityConfigurer();
	//}


	@Override
	public void init(HttpSecurity http) throws Exception {
		log.trace("===init ValidateCodeSecurityConfigurer===");
		validateCodeFilter = getSharedOrBean(http, ValidateCodeFilter.class);
		//validateCodeFilter.setLoginSmsUrl(this.getLoginSmsUrl());
		//validateCodeFilter.setLoginImageUrl(this.getLoginImageUrl());


	}

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
