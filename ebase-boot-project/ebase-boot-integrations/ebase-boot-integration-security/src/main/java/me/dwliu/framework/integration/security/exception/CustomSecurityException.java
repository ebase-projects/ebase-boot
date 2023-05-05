package me.dwliu.framework.integration.security.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * CustomSecurityException异常
 *
 * @author liudw
 * @date 2019-04-23 13:30
 **/
public class CustomSecurityException extends AuthenticationException {

	public CustomSecurityException(String msg, Throwable t) {
		super(msg, t);
	}

	public CustomSecurityException(String message) {
		super(message);
	}
}
