package me.dwliu.framework.integration.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.dwliu.framework.common.model.Result;
import me.dwliu.framework.core.security.enums.SecurityResultCode;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 自定义json认证失败处理器
 *
 * @author liudw
 * @date 2023/4/29 21:58
 **/
@Slf4j
@Component("customJsonAuthenticationFailureHandler")
public class CustomJsonAuthenticationFailureHandler implements AuthenticationFailureHandler {


	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

		log.debug("登陆失败");
		response.setContentType("application/json;charset=utf-8"); // 返回JSON
		response.setStatus(HttpStatus.UNAUTHORIZED.value());  // 状态码 401
//		Map<String, Object> result = new HashMap<>(); // 返回结果
//		result.put("msg", "登录失败");
//		result.put("code", HttpStatus.UNAUTHORIZED.value());
//		result.put("data", exception.getMessage());
//		response.getWriter().write(objectMapper.writeValueAsString(result));
		ObjectMapper objectMapper = new ObjectMapper();
		response.getWriter().write(objectMapper.writeValueAsString(Result.fail(SecurityResultCode.LOGIN_UNAUTHORIZED, "登录失败", exception.getMessage())));
	}
}
