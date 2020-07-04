package me.dwliu.framework.core.security.constant;

/**
 * 常量列表
 *
 * @author liudw
 * @date 2019-04-23 15:06
 **/
public interface ValidateCodeConstants {
	/**
	 * 发送短信验证码 或 验证短信验证码时，传递手机号的参数的名称
	 */
	String DEFAULT_PARAMETER_NAME_MOBILE = "mobile";

	/**
	 * 默认的手机验证码登录请求处理url
	 */
	//String DEFAULT_SIGN_IN_PROCESSING_URL_MOBILE = "/oauth/mobile";
	String DEFAULT_SIGN_IN_PROCESSING_URL_MOBILE = "/auth/mobile";

	/**
	 * 验证图片验证码时，http请求中默认的携带图片验证码信息的参数的名称
	 */
	String DEFAULT_PARAMETER_NAME_CODE_IMAGE = "imageCode";

	/**
	 * 验证短信验证码时，http请求中默认的携带短信验证码信息的参数的名称
	 */
	String DEFAULT_PARAMETER_NAME_CODE_SMS = "smsCode";
}
