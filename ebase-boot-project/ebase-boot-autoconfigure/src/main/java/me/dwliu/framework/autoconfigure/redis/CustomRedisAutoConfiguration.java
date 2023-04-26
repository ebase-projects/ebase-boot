package me.dwliu.framework.autoconfigure.redis;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@AllArgsConstructor
public class CustomRedisAutoConfiguration {
	private final RedisConnectionFactory factory;

	@Bean
	public RedisTemplate<String, Object> redisTemplate() {
		log.debug("===配置默认的 RedisTemplate===");
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
		log.debug("===配置默认的 RedisService===");
		RedisService redisService = new RedisService();
		redisService.setRedisTemplate(redisTemplate());
		return redisService;

	}
}
