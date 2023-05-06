package me.dwliu.framework.integration.security.exception;

import lombok.extern.slf4j.Slf4j;
import me.dwliu.framework.common.model.Result;
import me.dwliu.framework.core.security.enums.SecurityResultCode;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * 安全模块的异常处理器
 *
 * @author liudw
 * @date 2019-05-30 11:43
 **/
@RestControllerAdvice
@Slf4j
public class CustomSecurityExceptionHandler {

	@ResponseStatus(HttpStatus.FORBIDDEN)
	@ExceptionHandler(AccessDeniedException.class)
	public Result accessDeniedException(Exception e) {
		log.error("没有权限不允许访问", e);
		return Result.fail(SecurityResultCode.FORBIDDEN, "没有权限不允许访问");
	}


}
