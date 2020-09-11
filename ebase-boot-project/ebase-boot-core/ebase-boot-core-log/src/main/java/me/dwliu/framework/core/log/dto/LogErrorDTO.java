package me.dwliu.framework.core.log.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 异常日志
 *
 * @author liudw
 * @date 2020/7/23 14:52
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class LogErrorDTO extends AbstractLogOperationDTO {

	/**
	 * id
	 */
	private Long id;
	/**
	 * 请求URI
	 */
	private String requestUri;
	/**
	 * 请求方式
	 */
	private String requestMethod;
	/**
	 * 请求参数
	 */
	private String requestParams;
	/**
	 * 用户代理
	 */
	private String userAgent;
	/**
	 * 操作IP
	 */
	private String ip;
	/**
	 * 异常信息
	 */
	private String errorInfo;


}
