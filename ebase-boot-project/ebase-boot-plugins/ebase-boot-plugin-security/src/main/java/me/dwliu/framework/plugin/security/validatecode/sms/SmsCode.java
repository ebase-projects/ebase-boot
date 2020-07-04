package me.dwliu.framework.plugin.security.validatecode.sms;


import me.dwliu.framework.plugin.security.validatecode.ValidateCode;

import java.time.LocalDateTime;

/**
 * 短信验证码
 *
 * @author liudw
 * @date 2019-04-23 14:18
 **/
public class SmsCode extends ValidateCode {
	public SmsCode(String code, LocalDateTime expireTime) {
		super(code, expireTime);
	}

	public SmsCode(String code, int expireIn) {
		super(code, expireIn);
	}
}
