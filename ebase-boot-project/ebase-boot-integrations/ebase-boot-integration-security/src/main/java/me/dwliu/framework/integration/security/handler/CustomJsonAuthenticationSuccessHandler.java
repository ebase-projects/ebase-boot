package me.dwliu.framework.integration.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.dwliu.framework.common.model.Result;
import me.dwliu.framework.core.security.cache.CacheService;
import me.dwliu.framework.core.security.constant.SecurityCoreConstant;
import me.dwliu.framework.core.security.entity.UserInfoDetails;
import me.dwliu.framework.integration.security.jwt.JwtTokenUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.Principal;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * 自定义json认证成功处理器
 * <p>登录成功处理类,登录成功后会调用里面的方法</p>
 *
 * @author liudw
 * @date 2023/4/29 22:06
 **/
//@Component
@Slf4j
@RequiredArgsConstructor
public class CustomJsonAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
	private final JwtTokenUtils jwtTokenUtils;
	private final CacheService cacheService;

	/**
	 * 登录成功后直接返回 JSON
	 *
	 * @param request        请求
	 * @param response       响应
	 * @param authentication 成功认证的用户信息
	 */
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
										Authentication authentication) throws IOException, ServletException {
		log.debug("===登录成功后直接返回 JSON===");
		response.setContentType("application/json;charset=utf-8"); // 返回JSON
		response.setStatus(HttpStatus.OK.value());  // 状态码 200
//		Map<String, Object> result = new HashMap<>(); // 返回结果
//		result.put("msg", "登录成功");
//		result.put("code", SystemResultCode.SUCCESS);
//		result.put("data", authentication);
//		response.getWriter().write(objectMapper.writeValueAsString(result));


		UserInfoDetails securityUserDetails = getCurrentUserInfo(authentication);

		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String token = jwtTokenUtils.createToken(securityUserDetails);
		Map<String, Object> model = new HashMap<>();
		model.put("token", token);
		model.put("token_expiration", dateTimeFormatter.format(
			jwtTokenUtils.getTokenExpiration(token)
				.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()));
		ObjectMapper objectMapper = new ObjectMapper();
		//加入到缓存中
		cacheService.save(SecurityCoreConstant.SECURITY_TOKEN_CACHE_KEY + securityUserDetails.getUsername(), token);

		String s = objectMapper.writeValueAsString(securityUserDetails);
		cacheService.save(SecurityCoreConstant.SECURITY_USERINFO_CACHE_KEY + securityUserDetails.getUsername(), s);

		Result<Map> result = Result.success(model);
		
		response.getWriter().write(objectMapper.writeValueAsString(result));
	}


	public UserInfoDetails getCurrentUserInfo(Authentication authentication) {
//		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Object principal = authentication.getPrincipal();
		if (principal instanceof UserDetails) {
			return ((UserInfoDetails) principal);
		}
		return null;
	}

}
