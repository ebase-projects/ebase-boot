package me.dwliu.framework.autoconfigure.mybatis;

import me.dwliu.framework.integration.mybatis.handler.DefaultGlobalExceptionHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * GlobalExceptionHandlerConfig配置
 *
 * @author liudw
 * @date 2022/1/4 15:01
 **/
@Configuration
@ConditionalOnClass({DefaultGlobalExceptionHandler.class})
public class GlobalExceptionHandlerConfig {


	@Bean
	public DefaultGlobalExceptionHandler defaultGlobalExceptionHandler() {
		return new DefaultGlobalExceptionHandler();
	}


}
