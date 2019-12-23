package me.dwliu.framework.core.log.event;

import lombok.extern.slf4j.Slf4j;
import me.dwliu.framework.core.log.api.dto.SysLogOperationDTO;
import me.dwliu.framework.core.tool.util.SpringContextUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 操作日志信息事件发布
 *
 * @author liudw
 * @date 2019-03-04 13:41
 **/
@Slf4j
public class LogActionPublisher {
	public static void publishEvent(SysLogOperationDTO logAction) {
		log.info("操作日志信息事件发布");

		Map<String, Object> event = new HashMap<>();

		event.put("logAction", logAction);

		SpringContextUtil.publishEvent(new LogActionEvent(event));

	}
}
