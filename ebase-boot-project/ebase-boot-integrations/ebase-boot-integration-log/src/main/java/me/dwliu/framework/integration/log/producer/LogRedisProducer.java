package me.dwliu.framework.integration.log.producer;

import lombok.extern.slf4j.Slf4j;
import me.dwliu.framework.core.log.constant.LogConstant;
import me.dwliu.framework.core.log.dto.OperationDTO;
import me.dwliu.framework.core.log.producer.LogProducer;
import me.dwliu.framework.core.redis.RedisService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * 日志Redis生产者
 * <p>
 * 日志通过redis队列，异步保存到数据库
 *
 * @author liudw
 * @date 2020/7/5 11:50
 **/
@Slf4j
@EnableAsync
public class LogRedisProducer implements LogProducer {
	private final RedisService redisService;

	public LogRedisProducer(RedisService redisService) {
		this.redisService = redisService;
	}

	/**
	 * 保存日志
	 *
	 * @param dto
	 */
	@Override
	@Async("taskAsyncExecutor")
	public void saveLog(OperationDTO dto) {
		log.trace("通过Redis发送异步数据");
		if (dto == null) {
			return;
		}

		//保存到队列
		// pool.execute(() -> {
		// 	redisTemplate.opsForList().leftPush(LOG_KEY, dto);
		// });

		redisService.leftPush(LogConstant.LOG_KEY, dto, RedisService.NOT_EXPIRE);


	}

}
