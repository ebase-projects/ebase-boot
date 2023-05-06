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

	/**
	 * 登陆异常
	 */
	LOGIN_ERROR(SecurityResultCode.LOGIN_ERROR_CODE, "登陆异常"),
	/* 用户错误 */
	USER_NOT_LOGIN(SecurityResultCode.USER_NOT_LOGIN_CODE, "用户未登录"),
	USER_ACCOUNT_EXPIRED(SecurityResultCode.USER_ACCOUNT_EXPIRED_CODE, "账号已过期"),
	USER_CREDENTIALS_ERROR(SecurityResultCode.USER_CREDENTIALS_ERROR_CODE, "密码错误"),
	USER_CREDENTIALS_EXPIRED(SecurityResultCode.USER_CREDENTIALS_EXPIRED_CODE, "密码过期"),
	USER_ACCOUNT_DISABLE(SecurityResultCode.USER_ACCOUNT_DISABLE_CODE, "账号不可用"),
	USER_ACCOUNT_LOCKED(SecurityResultCode.USER_ACCOUNT_LOCKED_CODE, "账号被锁定"),
	USER_ACCOUNT_NOT_EXIST(SecurityResultCode.USER_ACCOUNT_NOT_EXIST_CODE, "账号不存在"),
	USER_ACCOUNT_ALREADY_EXIST(SecurityResultCode.USER_ACCOUNT_ALREADY_EXIST_CODE, "账号已存在"),
	USER_ACCOUNT_USE_BY_OTHERS(SecurityResultCode.USER_ACCOUNT_USE_BY_OTHERS_CODE, "账号下线"),
	TOKEN_UNAUTHORIZED_CODE_ERROR(SecurityResultCode.TOKEN_UNAUTHORIZED_CODE, "TOKEN未验证通过"),
	VALIDATE_CODE_ERROR(SecurityResultCode.VALIDATE_CODE_ERROR_CODE, "验证码验证异常"),

	FORBIDDEN(SecurityResultCode.FORBIDDEN_CODE, "没有权限");


	public static final int LOGIN_ERROR_CODE = 200000;

	public static final int USER_NOT_LOGIN_CODE = 200001;
	public static final int USER_ACCOUNT_EXPIRED_CODE = 200002;
	public static final int USER_CREDENTIALS_ERROR_CODE = 200003;
	public static final int USER_CREDENTIALS_EXPIRED_CODE = 200004;
	public static final int USER_ACCOUNT_DISABLE_CODE = 200005;
	public static final int USER_ACCOUNT_LOCKED_CODE = 200006;
	public static final int USER_ACCOUNT_NOT_EXIST_CODE = 200007;
	public static final int USER_ACCOUNT_ALREADY_EXIST_CODE = 200008;
	public static final int USER_ACCOUNT_USE_BY_OTHERS_CODE = 200009;
	public static final int VALIDATE_CODE_ERROR_CODE = 200010;
	public static final int TOKEN_UNAUTHORIZED_CODE = 200011;

	public static final int FORBIDDEN_CODE = 200999;


	/**
	 * 消息码
	 */
	private final Integer code;

	/**
	 * 消息描述
	 */
	private final String msg;


}
