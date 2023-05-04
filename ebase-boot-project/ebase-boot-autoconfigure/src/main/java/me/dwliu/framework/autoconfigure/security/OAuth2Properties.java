package me.dwliu.framework.autoconfigure.security;

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
	 * 客户端配置
	 */
	private OAuth2Properties[] clients = {};

}
