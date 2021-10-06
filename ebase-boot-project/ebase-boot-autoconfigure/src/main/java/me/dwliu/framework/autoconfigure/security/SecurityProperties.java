package me.dwliu.framework.autoconfigure.security;

import lombok.Data;
import me.dwliu.framework.autoconfigure.security.code.ValidateCodeProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 安全框架全局属性
 *
 * @author liudw
 * @date 2019-04-23 10:59
 **/
@ConfigurationProperties(prefix = SecurityProperties.SECURITY_PROPERTIES_PREFIX)
@Data
public class SecurityProperties {
	public static final String SECURITY_PROPERTIES_PREFIX = "ebase.security";


	/**
	 * 验证码配置
	 */
	private ValidateCodeProperties code = new ValidateCodeProperties();

	/**
	 * OAuth2认证服务器配置
	 */
	private OAuth2Properties oauth2 = new OAuth2Properties();

}
