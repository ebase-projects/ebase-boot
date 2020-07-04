package me.dwliu.framework.plugin.security.validatecode;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * 验证码处理器
 *
 * @author liudw
 * @date 2019-04-23 14:08
 **/
public interface ValidateCodeProcessor {

	/**
	 * 创建验证码
	 *
	 * @param request spring 封装的request
	 * @throws Exception
	 */
	void create(ServletWebRequest request);

	/**
	 * 校验验证码
	 *
	 * @param request spring 封装的request
	 * @throws Exception
	 */
	void validate(ServletWebRequest request);
}
