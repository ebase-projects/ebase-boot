package me.dwliu.framework.starter.oss.autoconfigure;

import me.dwliu.framework.plugin.redis.serializer.JsonRedisSerializer;
import me.dwliu.framework.plugin.redis.RedisService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.Resource;

/**
 * Redis自动配置
 *
 * @author liudw
 * @date 2020/7/5 22:01
 **/
@Configuration
public class RedisAutoConfiguration {
	@Resource
	private RedisConnectionFactory factory;

	@Bean
	public RedisTemplate<String, Object> redisTemplate() {
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
		// redisTemplate.setKeySerializer(new StringRedisSerializer());
		// redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class));
		// redisTemplate.setHashKeySerializer(new StringRedisSerializer());
		// redisTemplate.setHashValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class));
		// redisTemplate.setConnectionFactory(factory);

		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setValueSerializer(new JsonRedisSerializer<>(Object.class));
		redisTemplate.setHashKeySerializer(new StringRedisSerializer());
		redisTemplate.setHashValueSerializer(new JsonRedisSerializer<>(Object.class));
		redisTemplate.setConnectionFactory(factory);

		return redisTemplate;
	}

	@Bean
	public RedisService redisService() {
		RedisService redisService = new RedisService();
		redisService.setRedisTemplate(redisTemplate());
		return redisService;

	}
}
