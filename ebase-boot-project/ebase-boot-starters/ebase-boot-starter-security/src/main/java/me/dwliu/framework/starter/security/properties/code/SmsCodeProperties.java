package me.dwliu.framework.starter.security.properties.code;

import lombok.Data;

/**
 * 短信验证码配置属性
 *
 * @author liudw
 * @date 2019-04-23 11:13
 **/
@Data
public class SmsCodeProperties {

	/**
	 * 验证码长度
	 */
	private int length = 6;
	/**
	 * 过期时间，默认 60 秒
	 */
	private int expireIn = 60;
	/**
	 * 要拦截的url,多个用逗号(,)隔开，ant pattern
	 */
	private String url;


}
