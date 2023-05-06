package me.dwliu.framework.integration.security.userpwdjson;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import me.dwliu.framework.core.security.cache.CacheService;
import me.dwliu.framework.core.security.constant.SecurityCoreConstant;
import me.dwliu.framework.core.security.dto.UserInfoDTO;
import me.dwliu.framework.core.security.entity.UserInfoDetails;
import me.dwliu.framework.integration.security.jwt.JwtTokenUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

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
					String userInfoDetailStr = (String) cacheService.get(SecurityCoreConstant.SECURITY_USERINFO_CACHE_KEY + username);

					if (StringUtils.isNotBlank(userInfoDetailStr)) {
						ObjectMapper objectMapper = new ObjectMapper();
						objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
						UserInfoDTO user = objectMapper.readValue(userInfoDetailStr, UserInfoDTO.class);

						Set<String> permissions = user.getPermissions();
						Set<GrantedAuthority> grantedAuthoritySet = new HashSet<>();
						if (permissions != null) {
							for (String p : permissions) {
								grantedAuthoritySet.add(new SimpleGrantedAuthority(p));
							}
						}

						// 必须加入UserInfoDetails，后期SecurityUtils.getUser() 才能获取到完整信息
						UserInfoDetails userInfoDetails = new UserInfoDetails(user.getUserId(), user.getTenantCode(), user.getRealName(), user.getDeptId()
							, user.getRoleIds(), user.getAvatar(), user.getUsername(), "[PROTECTED]",
							user.getSuperAdmin(), user.isEnabled(), user.isAccountNonExpired(), user.isCredentialsNonExpired(), user.isAccountNonLocked(),
							user.getPermissions(), grantedAuthoritySet);
//					Authentication authentication = jwtTokenUtils.getAuthentication(token, userInfoDetails);

						// TODO 这里可以续签

						//UsernamePasswordAuthenticationToken result = UsernamePasswordAuthenticationToken.authenticated(userInfoDetails,
						//	"[PROTECTED]", grantedAuthoritySet);

						UsernamePasswordAuthenticationToken authentication
							= new UsernamePasswordAuthenticationToken(userInfoDetails, "[PROTECTED]", grantedAuthoritySet);
						authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
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
