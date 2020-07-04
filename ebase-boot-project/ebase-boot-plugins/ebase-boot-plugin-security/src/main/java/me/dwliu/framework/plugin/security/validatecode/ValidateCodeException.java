package me.dwliu.framework.plugin.security.validatecode;

import org.springframework.security.core.AuthenticationException;

/**
 * 验证码异常
 *
 * @author liudw
 * @date 2019-04-23 13:30
 **/
public class ValidateCodeException extends AuthenticationException {

	public ValidateCodeException(String msg, Throwable t) {
		super(msg, t);
	}

	public ValidateCodeException(String message) {
		super(message);
	}
}
