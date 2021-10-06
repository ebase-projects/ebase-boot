package me.dwliu.framework.autoconfigure.security;

import lombok.Data;

/**
 * 认证服务器注册的第三方应用配置
 *
 * @author liudw
 * @date 2019-04-23 11:45
 **/
@Data
public class OAuth2ClientProperties {
	/**
	 * 第三方应用appId
	 */
	private String clientId;
	/**
	 * 第三方应用appSecret
	 */
	private String clientSecret;

	/**
	 * 针对应用发出的token的有效时间
	 * <br/> 默认 7200 秒
	 */
	private int accessTokenValidateSeconds = 7200;
}
