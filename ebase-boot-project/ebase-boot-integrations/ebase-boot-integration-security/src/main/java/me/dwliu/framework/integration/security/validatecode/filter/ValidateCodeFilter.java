package me.dwliu.framework.integration.security.validatecode.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import me.dwliu.framework.core.security.constant.ValidateCodeConstants;
import me.dwliu.framework.integration.security.handler.CustomJsonAuthenticationFailureHandler;
import me.dwliu.framework.integration.security.validatecode.ValidateCodeException;
import me.dwliu.framework.integration.security.validatecode.ValidateCodeProcessor;
import me.dwliu.framework.integration.security.validatecode.ValidateCodeProcessorHolder;
import me.dwliu.framework.integration.security.validatecode.enums.ValidateCodeTypeEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 验证码拦截器进行拦截验证
 *
 * @author liudw
 * @date 2019-04-24 17:35
 **/
//@Component
@Slf4j
@Data
public class ValidateCodeFilter extends OncePerRequestFilter {

	private String imageUrl;
	private String smsUrl;

	private String[] imageUrls;
	private String[] smsUrls;

	private String loginImageUrl;
	private String loginSmsUrl;

	//@Autowired
	private ValidateCodeProcessorHolder validateCodeProcessorHolder;
	/**
	 * 验证码校验失败处理器
	 */
	//@Autowired
	private AuthenticationFailureHandler authenticationFailureHandler = new CustomJsonAuthenticationFailureHandler();
	/**
	 * 存放所有需要校验验证码的url
	 */
	private Map<String, ValidateCodeTypeEnum> urlMap = new HashMap<>();
	/**
	 * 验证请求url与配置的url是否匹配的工具类
	 */
	private AntPathMatcher pathMatcher = new AntPathMatcher();

	// public ValidateCodeFilter(String imageUrl, String smsUrl) {
	// 	this.imageUrl = imageUrl;
	// 	this.smsUrl = smsUrl;
	// }


	@Override
	public void afterPropertiesSet() throws ServletException {
		super.afterPropertiesSet();

		//加入用户名密码登陆和短信登陆链接
		urlMap.put(ValidateCodeConstants.DEFAULT_SIGN_IN_PROCESSING_URL_MOBILE, ValidateCodeTypeEnum.SMS);
//		urlMap.put("/oauth/token", ValidateCodeTypeEnum.IMAGE);
		addUrlToMap(loginImageUrl, ValidateCodeTypeEnum.IMAGE);
		addUrlToMap(loginSmsUrl, ValidateCodeTypeEnum.SMS);

		// 将图片验证码配置文件 url 属性加入到map
		addUrlToMap(imageUrl, ValidateCodeTypeEnum.IMAGE);
		addUrlsToMap(imageUrls, ValidateCodeTypeEnum.IMAGE);

		// 将短信验证码配置文件 url 属性加入到map
		addUrlToMap(smsUrl, ValidateCodeTypeEnum.SMS);
		addUrlsToMap(smsUrls, ValidateCodeTypeEnum.SMS);


	}

	/**
	 * 系统中配置的需要校验验证码的URL根据校验的类型放入map
	 *
	 * @param urlsStr      url集合字符串 eg:/a,/aa/b,/aaaa/aaa/aa
	 * @param codeTypeEnum 验证码枚举类型
	 */
	private void addUrlToMap(String urlsStr, ValidateCodeTypeEnum codeTypeEnum) {
		if (StringUtils.isNotBlank(urlsStr)) {
			//将以逗号分隔的url字符串拆分成url 集合
			String[] urls = StringUtils.splitByWholeSeparatorPreserveAllTokens(urlsStr, ",");
			for (String url : urls) {
				urlMap.put(url, codeTypeEnum);
			}
		}
	}

	private void addUrlsToMap(String[] urls, ValidateCodeTypeEnum codeTypeEnum) {
		if (urls != null && urls.length > 0) {
			//将以逗号分隔的url字符串拆分成url 集合

			for (String url : urls) {
				urlMap.put(url, codeTypeEnum);
			}
		}
	}


	@Override
	protected void doFilterInternal(HttpServletRequest httpServletRequest,
									HttpServletResponse httpServletResponse,
									FilterChain filterChain) throws ServletException, IOException {

		String requestURI = httpServletRequest.getRequestURI();
		log.debug("===验证码校验器 ValidateCodeFilter start : {}===", requestURI);
		//忽略 refresh_token 模式
		if (requestURI.contains("/oauth/token")) {
			String grantType = httpServletRequest.getParameter("grant_type");
			if (StringUtils.equals(grantType, "refresh_token")) {
				filterChain.doFilter(httpServletRequest, httpServletResponse);
				return;
			}
		}


		ValidateCodeTypeEnum validateCodeType = getValidateCodeType(httpServletRequest);
		if (null != validateCodeType) {
			log.debug("===校验请求({})中的验证码类型是:{}===",
				httpServletRequest.getRequestURI(), validateCodeType);

			try {
				ValidateCodeProcessor validateCodeProcessor = validateCodeProcessorHolder.getValidateCodeProcessor(validateCodeType);
				validateCodeProcessor.validate(new ServletWebRequest(httpServletRequest, httpServletResponse));
			} catch (ValidateCodeException exception) {
				log.error(exception.getMessage(), exception.fillInStackTrace());
				authenticationFailureHandler.onAuthenticationFailure(httpServletRequest, httpServletResponse, exception);
				return;
			}
		}
		filterChain.doFilter(httpServletRequest, httpServletResponse);

	}


	/**
	 * 获取校验码的类型，如果当前请求不需要校验，则返回null
	 *
	 * @param httpServletRequest
	 * @return
	 */
	private ValidateCodeTypeEnum getValidateCodeType(HttpServletRequest httpServletRequest) {
		if (!StringUtils.equalsIgnoreCase(httpServletRequest.getMethod(), "GET")) {
			for (Map.Entry<String, ValidateCodeTypeEnum> next : urlMap.entrySet()) {
				if (pathMatcher.match(next.getKey(), httpServletRequest.getRequestURI())) {
					return next.getValue();
				}

			}
		}

		return null;

	}
}
