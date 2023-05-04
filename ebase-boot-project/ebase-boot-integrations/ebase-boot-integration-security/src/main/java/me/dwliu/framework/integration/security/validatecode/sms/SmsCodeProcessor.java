package me.dwliu.framework.integration.security.validatecode.sms;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.dwliu.framework.core.security.constant.ValidateCodeConstants;
import me.dwliu.framework.integration.security.validatecode.AbstractValidateCodeProcessor;
import me.dwliu.framework.integration.security.validatecode.ValidateCodeException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

@Slf4j
@Component(value = "smsValidateCodeProcessor")
@RequiredArgsConstructor
public class SmsCodeProcessor extends AbstractValidateCodeProcessor<SmsCode> {

	private final SmsCodeSender smsCodeSender;


	/**
	 * 发送验证码
	 *
	 * @param request spring 封装的request
	 * @param smsCode 验证码
	 */
	@Override
	protected void send(ServletWebRequest request, SmsCode smsCode) {
		String mobileParameter = "";
		try {
			mobileParameter = ServletRequestUtils.getStringParameter(request.getRequest(),
				ValidateCodeConstants.DEFAULT_PARAMETER_NAME_MOBILE);

			if (StringUtils.isBlank(mobileParameter)) {
				throw new ValidateCodeException("手机号为空");
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e.fillInStackTrace());
			if (e instanceof ServletRequestBindingException) {
				throw new ValidateCodeException(String.format("%s 参数缺失", ValidateCodeConstants.DEFAULT_PARAMETER_NAME_MOBILE));
			} else if (e instanceof ValidateCodeException) {
				throw new ValidateCodeException(e.getMessage());
			}
		}

		smsCodeSender.send(mobileParameter, smsCode.getCode());

	}
}
