package me.dwliu.framework.integration.security.userpwdjson;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import me.dwliu.framework.core.security.cache.CacheService;
import me.dwliu.framework.core.security.constant.SecurityCoreConstant;
import me.dwliu.framework.integration.security.jwt.JwtTokenUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
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
	private final CacheService cacheService;
//	private final AuthenticationEntryPoint authenticationEntryPoint;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

//		try {
		String token = jwtTokenUtils.resolveToken(request);
		if (StringUtils.isNotBlank(token)) {
			String username = jwtTokenUtils.getUsername(token);
			String token4Server = (String) cacheService.get(SecurityCoreConstant.SECURITY_TOKEN_CACHE_KEY + username);
			if (StringUtils.equals(token, token4Server)) {
				if (jwtTokenUtils.validateToken(token)) {
					Authentication authentication = jwtTokenUtils.getAuthentication(token);
					if (authentication != null) {
//				SecurityContextHolder.getContext().setAuthentication(authentication);
						// SecurityContextHolder 权限验证上下文
						SecurityContext context = SecurityContextHolder.getContext();
						// 指示用户已通过身份验证
						context.setAuthentication(authentication);
					}
				}
			}
//				} else {
//					throw new JwtTokenException("TOKEN不存在，请重新登陆");
//				}

		}

//		} catch (
//			AuthenticationException e) {
////			onUnsuccessfulAuthentication(request, response, e);
//			this.authenticationEntryPoint.commence(request, response, e);
//		}
		// 继续下一个过滤器
		filterChain.doFilter(request, response);
	}


}
