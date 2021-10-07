package me.dwliu.ebase.sample;

import me.dwliu.framework.integration.mybatis.handler.DefaultGlobalExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler extends DefaultGlobalExceptionHandler {
}
