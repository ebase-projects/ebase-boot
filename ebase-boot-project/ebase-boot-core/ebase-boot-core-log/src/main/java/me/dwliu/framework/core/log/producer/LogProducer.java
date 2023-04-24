package me.dwliu.framework.core.log.producer;

import me.dwliu.framework.core.log.dto.AbstractLogOperationDTO;
import me.dwliu.framework.core.log.dto.OperationDTO;

/**
 * 日志生产者
 *
 * @author liudw
 * @date 2020/7/5 11:40
 **/
public interface LogProducer {
	/**
	 * 保存日志
	 *
	 * @param dto
	 */
	void saveLog(OperationDTO dto);
}
