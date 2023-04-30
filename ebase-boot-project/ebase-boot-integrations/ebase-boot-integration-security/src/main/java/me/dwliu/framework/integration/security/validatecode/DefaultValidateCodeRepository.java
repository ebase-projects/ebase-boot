package me.dwliu.framework.integration.security.validatecode;

import cn.hutool.cache.Cache;
import cn.hutool.cache.CacheUtil;

import lombok.extern.slf4j.Slf4j;
import me.dwliu.framework.core.security.constant.ValidateCodeConstants;
import me.dwliu.framework.integration.security.validatecode.enums.ValidateCodeTypeEnum;
import me.dwliu.framework.integration.security.validatecode.sms.SmsCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.concurrent.TimeUnit;

/**
 * 默认的验证码持久实现类
 *
 * @author liudw
 * @date 2019-04-24 10:44
 **/
@Slf4j
public class DefaultValidateCodeRepository implements ValidateCodeRepository {
	/**
	 * Local Cache  5分钟过期
	 */
//	Cache<String, ValidateCode> localCache = CacheBuilder.newBuilder()
//		.maximumSize(1000).expireAfterAccess(5, TimeUnit.MINUTES).build();

	Cache<String, ValidateCode> localCache = CacheUtil.newFIFOCache(5000, 5 * 60 * 1000);


	/**
	 * 保存
	 *
	 * @param request  spring 封装
	 * @param code     验证码
	 * @param codeType 验证码类型枚举
	 */
	@Override
	public void save(ServletWebRequest request, ValidateCode code, ValidateCodeTypeEnum codeType) {
		// 短信要加入手机号
		SmsCode smsCode = null;
		if (codeType == ValidateCodeTypeEnum.SMS) {
			String mobileParameter;
			try {
				mobileParameter = ServletRequestUtils.getStringParameter(request.getRequest(),
					ValidateCodeConstants.DEFAULT_PARAMETER_NAME_MOBILE);
			} catch (ServletRequestBindingException e) {
				throw new ValidateCodeException("获取手机号为空");
			}

			if (StringUtils.isBlank(mobileParameter)) {
				throw new ValidateCodeException("手机号为空");
			}


			smsCode = new SmsCode(mobileParameter, code.getCode(), code.getExpireTime());


		}

		localCache.put(buildKey(request, codeType), smsCode);
	}

	/**
	 * 获取验证码
	 *
	 * @param request  spring 封装
	 * @param codeType 验证码类型枚举
	 * @return
	 */
	@Override
	public ValidateCode get(ServletWebRequest request, ValidateCodeTypeEnum codeType) {
		return localCache.get(buildKey(request, codeType));
	}

	/**
	 * @param request  spring 封装
	 * @param codeType 验证码类型枚举
	 */
	@Override
	public void remove(ServletWebRequest request, ValidateCodeTypeEnum codeType) {
		localCache.remove(buildKey(request, codeType));
	}

	private String buildKey(ServletWebRequest request, ValidateCodeTypeEnum codeTypeEnum) {
		String deviceId = request.getHeader("deviceId");

		if (StringUtils.isBlank(deviceId)) {
			deviceId = request.getRequest().getParameter("deviceId");
		}

		if (log.isDebugEnabled()) {
			log.info("终端传输过来的deviceId：{}", deviceId);

		}

		if (StringUtils.isBlank(deviceId)) {
			throw new ValidateCodeException("设备号不存在");
		}

		return String.format("code:%s:%s", codeTypeEnum.toString().toLowerCase(), deviceId);


	}
}
