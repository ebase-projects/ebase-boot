package me.dwliu.ebase.sample;

import me.dwliu.framework.plugin.mybatis.handler.DefaultGlobalExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler extends DefaultGlobalExceptionHandler {
}
