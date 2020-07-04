package me.dwliu.framework.plugin.security.mobile;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import me.dwliu.framework.plugin.security.service.CustomUserDetailsService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * 短信登陆验证逻辑
 * <p>由于短信验证码在过滤器里以实现，这里直接读取用户信息即可</p>
 *
 * @author liudw
 * @date 2019-04-28 15:02
 **/
@Slf4j
public class SmsCodeAuthenticationProvider implements AuthenticationProvider {

	@Getter
	@Setter
	private CustomUserDetailsService userDetailsService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		SmsCodeAuthenticationToken authenticationToken = (SmsCodeAuthenticationToken) authentication;
		UserDetails userDetails = this.getUserDetailsService()
			.loadUserByMobile((String) authenticationToken.getPrincipal());

		if (null == userDetails) {
			throw new UsernameNotFoundException("无法获取用户信息");
		}

		SmsCodeAuthenticationToken result = new SmsCodeAuthenticationToken(userDetails, userDetails.getAuthorities());
		result.setDetails(authentication.getDetails());

		return result;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return SmsCodeAuthenticationToken.class.isAssignableFrom(authentication);
	}


}
