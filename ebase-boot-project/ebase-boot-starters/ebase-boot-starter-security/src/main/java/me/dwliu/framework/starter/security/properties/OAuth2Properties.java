package me.dwliu.framework.starter.security.properties;

import lombok.Data;

/**
 * oauth2 配置属性
 *
 * @author liudw
 * @date 2019-04-23 13:19
 **/
@Data
public class OAuth2Properties {
	/**
	 * 使用jwt 时为token签名的密钥
	 */
	private String JwtSigningKey = "ebase";

	/**
	 * 客户端配置
	 */
	private OAuth2Properties[] clients = {};

}
