package me.dwliu.framework.plugin.security.validatecode.sms;

import me.dwliu.framework.plugin.security.validatecode.ValidateCode;
import me.dwliu.framework.plugin.security.validatecode.ValidateCodeGenerator;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * 短信验证码生成器
 *
 * @author liudw
 * @date 2019-04-23 19:35
 **/
//@Component("smsValidateCodeGenerator")
public class SmsCodeGenerator implements ValidateCodeGenerator {

	private final int smsLength;
	private final int smsExpireIn;

	public SmsCodeGenerator(int smsLength, int smsExpireIn) {
		this.smsLength = smsLength;
		this.smsExpireIn = smsExpireIn;
	}

	/**
	 * 生成验证码
	 *
	 * @param request spring 封装的request
	 * @return 验证码封装类
	 */
	@Override
	public ValidateCode generate(ServletWebRequest request) {

		String code = RandomStringUtils.randomNumeric(smsLength);
		return new SmsCode(code, smsExpireIn);
	}
}
