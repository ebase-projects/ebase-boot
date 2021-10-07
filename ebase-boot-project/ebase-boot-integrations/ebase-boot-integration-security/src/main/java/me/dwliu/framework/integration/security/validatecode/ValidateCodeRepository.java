package me.dwliu.framework.integration.security.validatecode;

import me.dwliu.framework.integration.security.validatecode.enums.ValidateCodeTypeEnum;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * 验证码持久化
 *
 * @author liudw
 * @date 2019-04-23 16:15
 **/
public interface ValidateCodeRepository {
	/**
	 * 保存
	 *
	 * @param request  spring 封装
	 * @param code     验证码
	 * @param codeType 验证码类型枚举
	 */
	void save(ServletWebRequest request, ValidateCode code, ValidateCodeTypeEnum codeType);


	/**
	 * 获取验证码
	 *
	 * @param request  spring 封装
	 * @param codeType 验证码类型枚举
	 * @return
	 */
	ValidateCode get(ServletWebRequest request, ValidateCodeTypeEnum codeType);


	/**
	 * @param request  spring 封装
	 * @param codeType 验证码类型枚举
	 */
	void remove(ServletWebRequest request, ValidateCodeTypeEnum codeType);

}
