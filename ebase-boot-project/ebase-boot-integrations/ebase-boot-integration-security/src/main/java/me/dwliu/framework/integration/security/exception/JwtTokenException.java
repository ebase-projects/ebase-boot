package me.dwliu.framework.integration.security.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * jwt异常
 *
 * @author liudw
 * @date 2019-04-23 13:30
 **/
public class JwtTokenException extends AuthenticationException {

	public JwtTokenException(String msg, Throwable t) {
		super(msg, t);
	}

	public JwtTokenException(String message) {
		super(message);
	}
}
