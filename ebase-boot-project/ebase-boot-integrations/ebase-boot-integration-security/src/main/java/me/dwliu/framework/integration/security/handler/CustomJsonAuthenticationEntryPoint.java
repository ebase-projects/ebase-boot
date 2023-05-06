package me.dwliu.framework.integration.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import me.dwliu.framework.common.model.Result;
import me.dwliu.framework.core.security.enums.SecurityResultCode;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.time.LocalDateTime;

/**
 * 匿名未登录的时候访问,需要登录的资源的调用类
 *
 * @author liudw
 * @date 2019-08-16 11:05
 **/
@Slf4j
//@Component
public class CustomJsonAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Override
	@SneakyThrows
	public void commence(HttpServletRequest request, HttpServletResponse response,
						 AuthenticationException authException) {
		log.error("检验token未通过，没有权限不允许访问:{}", authException.getMessage());
		ObjectMapper objectMapper = new ObjectMapper();
		response.setCharacterEncoding("UTF-8");
		// response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
		response.setContentType("application/json;charset=UTF-8");
		Result<String> result = new Result<>();

		result.setCode(SecurityResultCode.FORBIDDEN_CODE);
		result.setMsg("检验token未通过，不允许访问");
		result.setTimestamp(LocalDateTime.now());
		result.setData(authException.getMessage());

		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		response.getWriter().append(objectMapper.writeValueAsString(result));
	}
}
