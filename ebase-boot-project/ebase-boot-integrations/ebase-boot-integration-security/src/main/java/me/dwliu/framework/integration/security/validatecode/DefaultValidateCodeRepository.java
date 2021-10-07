package me.dwliu.framework.integration.security.validatecode;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.extern.slf4j.Slf4j;
import me.dwliu.framework.integration.security.validatecode.enums.ValidateCodeTypeEnum;
import org.apache.commons.lang3.StringUtils;
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
	Cache<String, ValidateCode> localCache = CacheBuilder.newBuilder()
		.maximumSize(1000).expireAfterAccess(5, TimeUnit.MINUTES).build();


	/**
	 * 保存
	 *
	 * @param request  spring 封装
	 * @param code     验证码
	 * @param codeType 验证码类型枚举
	 */
	@Override
	public void save(ServletWebRequest request, ValidateCode code, ValidateCodeTypeEnum codeType) {
		localCache.put(buildKey(request, codeType), code);
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
		return localCache.getIfPresent(buildKey(request, codeType));
	}

	/**
	 * @param request  spring 封装
	 * @param codeType 验证码类型枚举
	 */
	@Override
	public void remove(ServletWebRequest request, ValidateCodeTypeEnum codeType) {
		localCache.invalidate(buildKey(request, codeType));
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
