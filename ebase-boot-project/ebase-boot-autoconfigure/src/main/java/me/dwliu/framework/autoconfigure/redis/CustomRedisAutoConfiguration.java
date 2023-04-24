package me.dwliu.framework.autoconfigure.redis;

import jakarta.annotation.Resource;
import me.dwliu.framework.core.redis.RedisService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Redis自动配置
 *
 * @author liudw
 * @date 2020/7/5 22:01
 **/
@Configuration
@ConditionalOnClass({RedisTemplate.class})
public class CustomRedisAutoConfiguration {
	@Resource
	private RedisConnectionFactory factory;

	@Bean
	public RedisTemplate<String, Object> redisTemplate() {
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class));
		redisTemplate.setHashKeySerializer(new StringRedisSerializer());
		redisTemplate.setHashValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class));
		redisTemplate.setConnectionFactory(factory);

		return redisTemplate;
	}

	@Bean
	@ConditionalOnMissingBean(RedisService.class)
	public RedisService redisService() {
		RedisService redisService = new RedisService();
		redisService.setRedisTemplate(redisTemplate());
		return redisService;

	}
}
