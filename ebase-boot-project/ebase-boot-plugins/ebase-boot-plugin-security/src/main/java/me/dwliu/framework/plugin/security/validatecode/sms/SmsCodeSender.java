package me.dwliu.framework.plugin.security.validatecode.sms;

/**
 * 短信发送
 *
 * @author liudw
 * @date 2019-04-23 19:52
 **/
public interface SmsCodeSender {
	/**
	 * 发送短信
	 *
	 * @param mobile 手机号
	 * @param code   验证码
	 */
	void send(String mobile, String code);
}
