package me.dwliu.lab.core.log.event;

import org.springframework.context.ApplicationEvent;

import java.util.Map;

/**
 * 日志事件
 *
 * @author liudw
 * @date 2019-02-25 16:40
 **/
public class LogActionEvent extends ApplicationEvent {

	public LogActionEvent(Map<String, Object> source) {
		super(source);
	}
}
