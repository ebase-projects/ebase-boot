package me.dwliu.framework.integration.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import me.dwliu.framework.common.model.Result;
import me.dwliu.framework.core.security.enums.SecurityResultCode;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

/**
 * 没有权限,被拒绝访问时的调用类
 *
 * @author liudw
 * @date 2023/5/3 20:02
 **/
@Slf4j
public class CustomJsonAccessDeniedHandler implements AccessDeniedHandler {
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
		log.debug("===登录账号密码错误等===");
		response.setContentType("application/json;charset=utf-8"); // 返回JSON
		response.setStatus(HttpStatus.FORBIDDEN.value());  // 状态码 401
		ObjectMapper objectMapper = new ObjectMapper();
		response.getWriter()
			.write(objectMapper.writeValueAsString(Result
				.fail(SecurityResultCode.FORBIDDEN, "没有权限", accessDeniedException.getMessage())));
	}
}
