package me.dwliu.framework.autoconfigure.security.jwt;

import lombok.Data;

/**
 * JWT配置类
 *
 * @Author Sans
 * @CreateTime 2019/10/1 22:56
 */
@Data
public class JwtConfigProperties {
	/**
	 * 密钥KEY
	 */
	private String secret = "rJ1lN9tP1rR2iX7hX1iO1wF9mW8aS2mAmV0";
	/**
	 * TokenKey 默认 "Authorization"
	 */
	private String tokenHeaderKey = "Authorization";
	/**
	 * 请求参数的 key
	 */
	private String tokenParamsKey = "token";
	/**
	 * Token前缀字符 默认 "Bearer"
	 */
	private String tokenPrefix = "Bearer";
	/**
	 * 过期时间（毫秒）默认 24H
	 */
	private Integer expiration = 24 * 60 * 60 * 1000;

}
