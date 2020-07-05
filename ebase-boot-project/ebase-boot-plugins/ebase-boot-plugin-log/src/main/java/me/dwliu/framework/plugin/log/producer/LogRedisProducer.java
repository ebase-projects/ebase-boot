package me.dwliu.framework.plugin.log.producer;

import lombok.extern.slf4j.Slf4j;
import me.dwliu.framework.core.log.constant.LogConstant;
import me.dwliu.framework.core.log.dto.AbstractLogOperationDTO;
import me.dwliu.framework.core.log.producer.LogProducer;
import org.springframework.data.redis.core.RedisTemplate;
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


	private final RedisTemplate redisTemplate;

	// ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("log-producer-pool-%d").build();
	// ExecutorService pool = new ThreadPoolExecutor(5, 200, 0L, TimeUnit.MILLISECONDS,
	// 	new LinkedBlockingQueue<>(1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());

	public LogRedisProducer(RedisTemplate redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	/**
	 * 保存日志
	 *
	 * @param dto
	 */
	@Override
	@Async("taskAsyncExecutor")
	public void saveLog(AbstractLogOperationDTO dto) {
		log.debug("通过Redis发送异步数据");
		if (dto == null) {
			return;
		}

		//保存到队列
		// pool.execute(() -> {
		// 	redisTemplate.opsForList().leftPush(LOG_KEY, dto);
		// });

		redisTemplate.opsForList().leftPush(LogConstant.LOG_KEY, dto);


	}
}
