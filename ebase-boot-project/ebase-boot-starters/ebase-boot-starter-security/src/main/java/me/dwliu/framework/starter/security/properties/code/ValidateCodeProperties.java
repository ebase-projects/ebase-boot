package me.dwliu.framework.starter.security.properties.code;

import lombok.Data;

/**
 * 验证码配置属性
 *
 * @author liudw
 * @date 2019-04-23 11:11
 **/
@Data
public class ValidateCodeProperties {
	/**
	 * 短信验证码
	 */
	private SmsCodeProperties sms = new SmsCodeProperties();

	/**
	 * 图形验证码
	 */
	private ImageCodeProperties image = new ImageCodeProperties();

}
