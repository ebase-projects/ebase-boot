package me.dwliu.framework.autoconfigure.log;

import me.dwliu.framework.core.log.producer.LogProducer;
import me.dwliu.framework.core.redis.RedisService;
import me.dwliu.framework.integration.log.aspect.LogActionAspect;
import me.dwliu.framework.integration.log.producer.LogRedisProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * 日志自动装载
 *
 * @author liudw
 * @date 2019-03-11 13:22
 **/
@Configuration
@ConditionalOnClass({RedisTemplate.class})
@ComponentScan("me.dwliu.framework.*.log")
@ConditionalOnWebApplication
public class LogActionAutoConfiguration {

	@Autowired(required = false)
	private RedisService redisService;

	/**
	 * 实例化日志监听
	 *
	 * @return
	 */
//	@Bean
//	public LogActionListener logActionListener() {
//		return new LogActionListener(remoteSysLogOperationService);
//	}

	/**
	 * redis 实例化才实例化这个日志提供者
	 *
	 * @return
	 */
	@Bean
	//@ConditionalOnBean(value = {RedisConnectionFactory.class})
	public LogProducer logProducer() {
		return new LogRedisProducer(redisService);
	}

	/**
	 * 实例化操作日志AOP
	 *
	 * @return
	 */
	@Bean
	public LogActionAspect logActionAspect() {
		return new LogActionAspect();
	}


}
