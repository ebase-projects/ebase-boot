package me.dwliu.framework.autoconfigure.security;

import me.dwliu.framework.integration.security.validatecode.DefaultValidateCodeRepository;
import me.dwliu.framework.integration.security.validatecode.ValidateCodeGenerator;
import me.dwliu.framework.integration.security.validatecode.ValidateCodeProcessorHolder;
import me.dwliu.framework.integration.security.validatecode.ValidateCodeRepository;
import me.dwliu.framework.integration.security.validatecode.filter.ValidateCodeFilter;
import me.dwliu.framework.integration.security.validatecode.image.ImageCodeGenerator;
import me.dwliu.framework.integration.security.validatecode.sms.DemoSmsCodeSender;
import me.dwliu.framework.integration.security.validatecode.sms.SmsCodeGenerator;
import me.dwliu.framework.integration.security.validatecode.sms.SmsCodeSender;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 验证码相关的扩展点配置。
 *
 * @author liudw
 * @date 2019-04-23 20:39
 **/
@Configuration
@EnableConfigurationProperties(value = SecurityProperties.class)
@ConditionalOnClass({ValidateCodeGenerator.class})
public class ValidateCodeBeanConfig {

	/**
	 * 默认的图形验证码生成实现类
	 *
	 * @return
	 */
	@Bean(value = "imageValidateCodeGenerator")
	@ConditionalOnMissingBean(name = "imageValidateCodeGenerator")
	public ValidateCodeGenerator validateCodeGenerator(SecurityProperties securityProperties) {
		return new ImageCodeGenerator(
			securityProperties.getCode().getImage().getWidth(),
			securityProperties.getCode().getImage().getHeight(),
			securityProperties.getCode().getImage().getLength(),
			securityProperties.getCode().getImage().getExpireIn());

	}

	/**
	 * 短信验证码生成实现类
	 *
	 * @return
	 */
	@Bean(value = "smsValidateCodeGenerator")
	public SmsCodeGenerator smsCodeGenerator(SecurityProperties securityProperties) {
		return new SmsCodeGenerator(securityProperties.getCode().getSms().getLength(), securityProperties.getCode().getSms().getExpireIn());
	}

	@Bean
	public ValidateCodeFilter validateCodeFilter(SecurityProperties securityProperties, ValidateCodeProcessorHolder validateCodeProcessorHolder) {
		ValidateCodeFilter validateCodeFilter = new ValidateCodeFilter();
		validateCodeFilter.setValidateCodeProcessorHolder(validateCodeProcessorHolder);
		validateCodeFilter.setImageUrl(securityProperties.getCode().getImage().getUrl());
		validateCodeFilter.setSmsUrl(securityProperties.getCode().getSms().getUrl());

		return validateCodeFilter;
		// securityProperties.getCode().getImage().getUrl(),
		// 	securityProperties.getCode().getSms().getUrl(),
		// 	validateCodeProcessorHolder,
		// 	authenticationFailureHandler);
	}


	/**
	 * 默认的验证码持久化类
	 *
	 * @return
	 */
	@Bean
	@ConditionalOnMissingBean(ValidateCodeRepository.class)
	public ValidateCodeRepository validateCodeRepository() {
		return new DefaultValidateCodeRepository();
	}

	/**
	 * 模拟短信验证码发送器
	 *
	 * @return
	 */
	@Bean
	@ConditionalOnMissingBean(SmsCodeSender.class)
	public SmsCodeSender smsCodeSender() {
		return new DemoSmsCodeSender();
	}
}
