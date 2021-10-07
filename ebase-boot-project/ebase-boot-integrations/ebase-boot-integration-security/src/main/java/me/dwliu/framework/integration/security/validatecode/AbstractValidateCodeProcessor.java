package me.dwliu.framework.integration.security.validatecode;

import lombok.extern.slf4j.Slf4j;
import me.dwliu.framework.integration.security.validatecode.enums.ValidateCodeTypeEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.Map;

/**
 * 验证码处理器抽象类
 *
 * @author liudw
 * @date 2019-04-23 14:40
 **/
@Slf4j
public abstract class AbstractValidateCodeProcessor<C extends ValidateCode> implements ValidateCodeProcessor {
	/**
	 * 默认实现类后缀
	 */
	private static final String DEFAULT_CODE_PROCESSOR_CLASS_SUFFIX = "CodeProcessor";
	/**
	 * 默认的验证码生成器后缀
	 */
	private static final String DEFAULT_VALIDATE_CODE_GENERATOR_SUFFIX = "CodeGenerator";


	/**
	 * 收集系统中所有的 {@link ValidateCodeGenerator} 接口的实现。
	 */
	@Autowired
	private Map<String, ValidateCodeGenerator> validateCodeGenerators;


	@Autowired
	private ValidateCodeRepository validateCodeRepository;


	/**
	 * 创建验证码
	 *
	 * @param request spring 封装的request
	 * @throws Exception
	 */
	@Override

	public void create(ServletWebRequest request) {
		// 1 生成验证码
		C validateCode = generate(request);
		// 2 保存验证码
		save(request, validateCode);
		// 3 发送验证码
		send(request, validateCode);
	}

	/**
	 * 校验验证码
	 *
	 * @param request spring 封装的request
	 * @throws Exception
	 */
	@Override
	public void validate(ServletWebRequest request) {
		ValidateCodeTypeEnum validateCodeType = getValidateCodeType();

		C codeInRepository = (C) validateCodeRepository.get(request, validateCodeType);

		String codeInRequest = null;
		try {
			codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(),
				validateCodeType.getParamNameOnValidate());
		} catch (ServletRequestBindingException e) {
			log.error("获取参数异常", e.fillInStackTrace());
		}

		if (StringUtils.isBlank(codeInRequest)) {
			throw new ValidateCodeException(String.format("%s 验证码的值为空", validateCodeType));
		}


		if (null == codeInRepository) {
			throw new ValidateCodeException(String.format("%s 验证码不存在", validateCodeType));
		}

		if (codeInRepository.isExpired()) {
			validateCodeRepository.remove(request, validateCodeType);
			throw new ValidateCodeException(String.format("%s 验证码已过期", validateCodeType));
		}

		if (!StringUtils.equalsIgnoreCase(codeInRepository.getCode(), codeInRequest)) {
			//短信验证码 有重试机会
			if (validateCodeType == ValidateCodeTypeEnum.SMS) {
				throw new ValidateCodeException(String.format("%s 验证码不匹配", validateCodeType));
			} else {
				validateCodeRepository.remove(request, validateCodeType);
				throw new ValidateCodeException(String.format("%s 验证码不匹配", validateCodeType));
			}


		} else {
			validateCodeRepository.remove(request, validateCodeType);
		}


	}

	/**
	 * 根据请求的url获取校验码的类型
	 *
	 * @return
	 */
	private ValidateCodeTypeEnum getValidateCodeType() {
		// 获取实现类前缀
		// 比如：实现类 abstractValidateCodeProcessor  后缀 CodeProcessor
		// 得到前缀 type= abstractValidate
		String type = StringUtils.substringBefore(this.getClass().getSimpleName(),
			DEFAULT_CODE_PROCESSOR_CLASS_SUFFIX);

		return ValidateCodeTypeEnum.valueOf(type.toUpperCase());


	}


	/**
	 * 生成验证码
	 *
	 * @param request spring 封装的request
	 * @return 验证码实体
	 */
	private C generate(ServletWebRequest request) {
		//eg: sms或者 image
		String type = getValidateCodeType().toString().toLowerCase();
		log.info("获取验证码类型:{}", type);

		String validateCodeGeneratorName = type + ValidateCodeGenerator.class.getSimpleName();
		log.debug("获取 validateCodeGeneratorName:{}", validateCodeGeneratorName);

		ValidateCodeGenerator validateCodeGenerator = validateCodeGenerators.get(validateCodeGeneratorName);

		if (null == validateCodeGenerator) {
			log.error("验证码生成器{}不存在", validateCodeGeneratorName);
			throw new ValidateCodeException(String.format("验证码生成器 %s 不存在", validateCodeGeneratorName));
		}

		ValidateCode generate = validateCodeGenerator.generate(request);
		return (C) generate;

	}

	/**
	 * 保存验证码
	 *
	 * @param request      spring 封装的request
	 * @param validateCode 验证码
	 */
	private void save(ServletWebRequest request, C validateCode) {
		ValidateCode code = new ValidateCode(validateCode.getCode(), validateCode.getExpireTime());
		validateCodeRepository.save(request, code, getValidateCodeType());
	}

	/**
	 * 发送验证码
	 *
	 * @param request      spring 封装的request
	 * @param validateCode 验证码
	 */
	protected abstract void send(ServletWebRequest request, C validateCode);


}
