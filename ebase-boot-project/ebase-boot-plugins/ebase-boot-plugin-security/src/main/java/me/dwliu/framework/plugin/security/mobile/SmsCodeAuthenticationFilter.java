package me.dwliu.framework.plugin.security.mobile;

import me.dwliu.framework.core.security.constant.ValidateCodeConstants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 短信登陆过滤器
 * 参考实现{@link org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter}
 *
 * <pre>
 *     https://www.jianshu.com/p/a65f883de0c1
 * </pre>
 *
 * @author liudw
 * @date 2019-04-28 14:27
 **/
public class SmsCodeAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

	public static final String SPRING_SECURITY_FORM_MOBILE_KEY = ValidateCodeConstants.DEFAULT_PARAMETER_NAME_MOBILE;

	private String mobileParameter = SPRING_SECURITY_FORM_MOBILE_KEY;

	private boolean postOnly = true;


	public SmsCodeAuthenticationFilter() {
		super(new AntPathRequestMatcher(ValidateCodeConstants.DEFAULT_SIGN_IN_PROCESSING_URL_MOBILE, "POST"));
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request,
												HttpServletResponse response) throws AuthenticationException {
		if (postOnly && !request.getMethod().equals("POST")) {
			throw new AuthenticationServiceException(
				"Authentication method not supported: " + request.getMethod());
		}

		String mobile = obtainMobile(request);

		if (StringUtils.isBlank(mobile)) {
			mobile = "";
		}

		mobile = mobile.trim();

		SmsCodeAuthenticationToken authRequest = new SmsCodeAuthenticationToken(mobile);

		// Allow subclasses to set the "details" property
		setDetails(request, authRequest);

		return this.getAuthenticationManager().authenticate(authRequest);
	}

	protected String obtainMobile(HttpServletRequest request) {
		return request.getParameter(mobileParameter);
	}

	protected void setDetails(HttpServletRequest request,
							  SmsCodeAuthenticationToken authRequest) {
		authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
	}

	public String getMobileParameter() {
		return mobileParameter;
	}

	public void setMobileParameter(String mobileParameter) {
		Assert.hasText(mobileParameter, "手机号不能为空");
		this.mobileParameter = mobileParameter;
	}

	public void setPostOnly(boolean postOnly) {
		this.postOnly = postOnly;
	}
}
