package me.dwliu.framework.integration.security.userpwdjson;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import me.dwliu.framework.integration.security.jwt.JwtTokenException;
import me.dwliu.framework.integration.security.jwt.JwtTokenUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * 进行token的认证拦截
 *
 * @author liudw
 * @date 2023/5/3 23:00
 **/
@RequiredArgsConstructor
public class CustomJsonValidateLoginTokenFilter extends OncePerRequestFilter {

	private final JwtTokenUtils jwtTokenUtils;
//	private final AuthenticationEntryPoint authenticationEntryPoint;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

//		try {
		String token = jwtTokenUtils.resolveToken(request);
		if (token != null && jwtTokenUtils.validateToken(token)) {
			Authentication authentication = jwtTokenUtils.getAuthentication(token);
			if (authentication != null) {
//				SecurityContextHolder.getContext().setAuthentication(authentication);
				// SecurityContextHolder 权限验证上下文
				SecurityContext context = SecurityContextHolder.getContext();
				// 指示用户已通过身份验证
				context.setAuthentication(authentication);
			}
		}
//		} catch (AuthenticationException e) {
//			onUnsuccessfulAuthentication(request, response, e);
//			this.authenticationEntryPoint.commence(request, response, e);
//		}
		// 继续下一个过滤器
		filterChain.doFilter(request, response);
	}


}
