package me.dwliu.framework.integration.log.event;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.dwliu.framework.core.log.dto.LogOperationDTO;
import me.dwliu.framework.core.log.producer.LogProducer;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;

import java.util.Map;

/**
 * 日志操作监听器
 *
 * @author liudw
 * @date 2019-02-25 17:03
 **/

@Slf4j
@AllArgsConstructor
//@EnableAsync
public class LogActionListener {
	private final LogProducer logProducer;

	@Async("taskAsyncExecutor")
	@EventListener(LogActionEvent.class)
	public void saveLog(LogActionEvent event) {
		log.info("日志监听器保存操作");

		Map<String, Object> logAction = (Map<String, Object>) event.getSource();

		//logAction.get("logAction");

		LogOperationDTO dto = (LogOperationDTO) logAction.get("logAction");

		log.info("{}", logAction);

		// 保存到DB
		logProducer.saveLog(dto);



	}
}
