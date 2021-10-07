package me.dwliu.framework.integration.security.validatecode;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * 验证码生成器
 *
 * @author liudw
 * @date 2019-04-23 14:06
 **/
public interface ValidateCodeGenerator {
	/**
	 * 生成验证码
	 *
	 * @param request spring 封装的request
	 * @return 验证码封装类
	 */
	ValidateCode generate(ServletWebRequest request);
}
