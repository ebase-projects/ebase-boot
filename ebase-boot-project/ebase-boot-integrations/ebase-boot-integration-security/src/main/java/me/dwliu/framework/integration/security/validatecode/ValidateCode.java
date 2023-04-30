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
public interface ValidateCode extends Serializable {

	String getCode();

	LocalDateTime getExpireTime();

	/**
	 * 验证码是否失效
	 *
	 * @return
	 */
	boolean isExpired();


}
