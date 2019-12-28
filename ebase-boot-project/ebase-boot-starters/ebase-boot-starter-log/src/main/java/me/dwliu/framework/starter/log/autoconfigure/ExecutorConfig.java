package me.dwliu.framework.starter.log.autoconfigure;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.dwliu.framework.starter.log.properties.AsyncProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 异步处理自动装配
 * <br/>
 * 加载装配公共属性，详看  {@link AsyncProperties} 中配置的属性参数
 *
 * @author liudw
 * @date 2019-03-11 13:39
 **/
@Configuration
@EnableAsync
@AllArgsConstructor
@EnableConfigurationProperties({AsyncProperties.class})
@Slf4j
public class ExecutorConfig {

	private final AsyncProperties asyncProperties;

	@Bean(name = "taskAsyncExecutor")
	public Executor taskAsyncExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(asyncProperties.getCorePoolSize());
		executor.setMaxPoolSize(asyncProperties.getMaxPoolSize());
		executor.setQueueCapacity(asyncProperties.getQueueCapacity());
		executor.setKeepAliveSeconds(asyncProperties.getKeepAliveSeconds());
		executor.setThreadNamePrefix("ebase-async-");
		executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
		return executor;
	}

}
