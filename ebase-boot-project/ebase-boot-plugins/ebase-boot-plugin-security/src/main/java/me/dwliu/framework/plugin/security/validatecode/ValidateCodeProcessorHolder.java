package me.dwliu.framework.plugin.security.validatecode;

import me.dwliu.framework.plugin.security.validatecode.enums.ValidateCodeTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 验证码处理管理器
 *
 * @author liudw
 * @date 2019-04-23 21:33
 **/
@Component
public class ValidateCodeProcessorHolder {

	@Autowired
	private Map<String, ValidateCodeProcessor> validateCodeProcessors;


	public ValidateCodeProcessor getValidateCodeProcessor(ValidateCodeTypeEnum validateCodeTypeEnum) {
		return getValidateCodeProcessor(validateCodeTypeEnum.toString().toLowerCase());
	}

	public ValidateCodeProcessor getValidateCodeProcessor(String codeType) {
		String type = codeType.toLowerCase() + ValidateCodeProcessor.class.getSimpleName();
		ValidateCodeProcessor validateCodeProcessor = validateCodeProcessors.get(type);
		if (null == validateCodeProcessor) {
			throw new ValidateCodeException(String.format("验证码处理器 %s 不存在", type));
		}
		return validateCodeProcessor;
	}
}
