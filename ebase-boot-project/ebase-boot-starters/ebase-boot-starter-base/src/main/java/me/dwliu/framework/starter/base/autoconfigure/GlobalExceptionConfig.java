package me.dwliu.framework.starter.base.autoconfigure;

import lombok.extern.slf4j.Slf4j;
import me.dwliu.framework.core.base.handler.GlobalExceptionHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 全局异常捕获配置类
 *
 * @author liudw
 * @date 2020-03-24 13:48
 **/
@Configuration
@Slf4j
public class GlobalExceptionConfig {

	@Bean
	@ConditionalOnMissingBean(GlobalExceptionHandler.class)
	public void globalExceptionHandler() {
		log.debug("需要全局捕获异常，请继承 GlobalExceptionHandler 类，并添加 注解 @RestControllerAdvice ");
	}
}
