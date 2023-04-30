package me.dwliu.framework.integration.security.validatecode.sms;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import me.dwliu.framework.integration.security.validatecode.DefaultValidateCode;

import java.time.LocalDateTime;

/**
 * 短信验证码
 *
 * @author liudw
 * @date 2019-04-23 14:18
 **/
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class SmsCode extends DefaultValidateCode {
	private String phone;

	public SmsCode(String phone, String code, LocalDateTime expireTime) {
		super(code, expireTime);
		this.phone = phone;
	}

	public SmsCode(String phone, String code, int expireIn) {
		super(code, expireIn);
		this.phone = phone;
	}
}
