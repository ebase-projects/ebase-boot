package me.dwliu.lab.security.exception;

import lombok.extern.slf4j.Slf4j;
import me.dwliu.lab.common.model.Result;
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
public class SecurityExceptionHandler {

	@ResponseStatus(HttpStatus.FORBIDDEN)
	@ExceptionHandler(AccessDeniedException.class)
	public Result accessDeniedException(Exception e) {
		log.error("没有权限不允许访问", e);
		return Result.fail("没有权限不允许访问");
	}


}
