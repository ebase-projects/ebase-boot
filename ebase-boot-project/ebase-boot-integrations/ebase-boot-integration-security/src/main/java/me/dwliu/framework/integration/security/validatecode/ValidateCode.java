package me.dwliu.framework.integration.security.validatecode;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 验证码实体
 *
 * @author liudw
 * @date 2019-04-23 13:33
 **/
@Data
public class ValidateCode implements Serializable {
	/**
	 * 验证码内容
	 */
	private String code;

	/**
	 * 失效时间
	 */
	private LocalDateTime expireTime;

	/**
	 * @param code       验证码
	 * @param expireTime 失效时间
	 */
	public ValidateCode(String code, LocalDateTime expireTime) {
		this.code = code;
		this.expireTime = expireTime;
	}

	/**
	 * @param code     验证码
	 * @param expireIn 失效时间，单位：秒
	 */
	public ValidateCode(String code, int expireIn) {
		this.code = code;
		this.expireTime = LocalDateTime.now().plusSeconds(expireIn);
	}


	/**
	 * 验证码是否失效
	 *
	 * @return
	 */
	public boolean isExpired() {
		return LocalDateTime.now().isAfter(this.expireTime);
	}


}
