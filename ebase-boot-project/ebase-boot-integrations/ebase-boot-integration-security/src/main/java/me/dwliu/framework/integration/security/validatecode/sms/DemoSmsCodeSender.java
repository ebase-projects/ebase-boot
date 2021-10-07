package me.dwliu.framework.integration.security.validatecode.sms;

import lombok.extern.slf4j.Slf4j;

/**
 * 模拟短信发送
 * <br>
 * <b>
 * 生产环境禁用
 * </b>
 *
 * @author liudw
 * @date 2019-04-23 19:53
 **/
@Slf4j
public class DemoSmsCodeSender implements SmsCodeSender {
	/**
	 * 发送短信
	 *
	 * @param mobile 手机号
	 * @param code   验证码
	 */
	@Override
	public void send(String mobile, String code) {
		log.warn("======请配置真实的短信验证码发送器(SmsCodeSender)=======");
		log.info("模拟短信发送请求，手机号：{},验证码是：{}", mobile, code);

	}
}
