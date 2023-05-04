package me.dwliu.framework.integration.security.userpwdjson;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import me.dwliu.framework.common.exception.BusinessException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;

public class CustomJsonLoginAuthenticationFilter extends AbstractAuthenticationProcessingFilter {


	private static final AntPathRequestMatcher DEFAULT_ANT_PATH_REQUEST_MATCHER =
		new AntPathRequestMatcher("/loginByJson", "POST");


	private boolean postOnly = true;

	public CustomJsonLoginAuthenticationFilter() {
		super(DEFAULT_ANT_PATH_REQUEST_MATCHER);
	}

	public CustomJsonLoginAuthenticationFilter(AuthenticationManager authenticationManager) {
		super(DEFAULT_ANT_PATH_REQUEST_MATCHER, authenticationManager);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
		throws AuthenticationException {
		if (this.postOnly && !request.getMethod().equals("POST")) {
			throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
		}
		LoginUserDTO user = null;
		try {
			user = new ObjectMapper().readValue(request.getInputStream(), LoginUserDTO.class);
		} catch (IOException e) {
			throw new BusinessException(e);
		}
		String username = user.getUsername();
		username = (username != null) ? username.trim() : "";
		String password = user.getPassword();
		password = (password != null) ? password : "";
		UsernamePasswordAuthenticationToken authRequest =
			UsernamePasswordAuthenticationToken.unauthenticated(username, password);
		// Allow subclasses to set the "details" property
		setDetails(request, authRequest);
		return this.getAuthenticationManager().authenticate(authRequest);
	}


	protected void setDetails(HttpServletRequest request, UsernamePasswordAuthenticationToken authRequest) {
		authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
	}


	public void setPostOnly(boolean postOnly) {
		this.postOnly = postOnly;
	}


}
