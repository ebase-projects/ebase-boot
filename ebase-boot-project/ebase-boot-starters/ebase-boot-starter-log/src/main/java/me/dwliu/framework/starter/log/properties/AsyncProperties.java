package me.dwliu.framework.starter.log.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 异步配置
 *
 * @author liudw
 * @date 2019-03-11 13:46
 **/
@Getter
@Setter
@ConfigurationProperties("ebase.log.async")
public class AsyncProperties {
	/**
	 * 异步核心线程数，默认：2
	 */
	private int corePoolSize = 2;
	/**
	 * 异步最大线程数，默认：50
	 */
	private int maxPoolSize = 50;
	/**
	 * 队列容量，默认：10000
	 */
	private int queueCapacity = 10000;
	/**
	 * 线程存活时间，默认：300
	 */
	private int keepAliveSeconds = 300;
}
