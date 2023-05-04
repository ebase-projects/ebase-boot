package me.dwliu.framework.integration.security.component;

import cn.hutool.http.HttpStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import me.dwliu.framework.common.code.SystemResultCode;
import me.dwliu.framework.common.model.Result;
import me.dwliu.framework.core.security.enums.SecurityResultCode;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.PrintWriter;

/**
 * 匿名未登录的时候访问,需要登录的资源的调用类
 *
 * @author liudw
 * @date 2019-08-16 11:05
 **/
@Slf4j
@Component
public class CustomJsonAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Override
	@SneakyThrows
	public void commence(HttpServletRequest request, HttpServletResponse response,
						 AuthenticationException authException) {
		ObjectMapper objectMapper = new ObjectMapper();
		response.setCharacterEncoding("UTF-8");
		// response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
		response.setContentType("application/json;charset=UTF-8");
		Result<String> result = new Result<>();
		result.setCode(SecurityResultCode.LOGIN_ERROR_CODE);
		if (authException != null) {
			result.setMsg(authException.getMessage());
			result.setData(authException.getMessage());
		}
		response.setStatus(HttpStatus.HTTP_UNAUTHORIZED);
		response.getWriter().append(objectMapper.writeValueAsString(result));
	}
}
