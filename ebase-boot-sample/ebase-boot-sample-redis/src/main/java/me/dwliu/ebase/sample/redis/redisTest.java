package me.dwliu.ebase.sample.redis;

import me.dwliu.framework.integration.redis.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class redisTest {
	@Autowired
	private RedisService redisService;

	@PostConstruct
	public void test() {
		redisService.set("wwwwww", "sssssssssssssssss");
    }
}
