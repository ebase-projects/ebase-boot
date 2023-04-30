package me.dwliu.framework.core.security.enums;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import me.dwliu.framework.common.code.IResultCode;

/**
 * 认证授权返回码
 * <pre>
 * 错误编码，由6位数字组成，前3位为模块编码，后3位为业务编码
 * <pre>
 * 如：100001（100代表系统模块，001代表业务代码）
 * </pre>
 *
 * @author liudw
 * @date 2019-03-21 16:15
 **/
@Getter
@AllArgsConstructor
@Schema(description = "系统返回码 由6位数字组成，前3位为模块编码，后3位为业务编码," +
	"如：100001（100代表系统模块，001代表业务代码")
public enum SecurityResultCode implements IResultCode {


	//-----------------------------通用数据层--------------------------------//
	/**
	 * 登陆未授权
	 */
	LOGIN_UNAUTHORIZED(SecurityResultCode.LOGIN_UNAUTHORIZED_CODE, "登陆未授权");


	public static final int LOGIN_UNAUTHORIZED_CODE = 200001;


	/**
	 * 消息码
	 */
	private final Integer code;

	/**
	 * 消息描述
	 */
	private final String msg;


}
