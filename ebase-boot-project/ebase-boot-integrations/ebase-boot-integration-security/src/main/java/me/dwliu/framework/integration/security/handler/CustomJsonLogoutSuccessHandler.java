package me.dwliu.framework.integration.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import me.dwliu.framework.common.model.Result;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import java.io.IOException;

@Slf4j
public class CustomJsonLogoutSuccessHandler implements LogoutSuccessHandler {

	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

		log.error("====自定义退出登录====");
		response.setContentType("application/json;charset=utf-8"); // 返回JSON
		response.setStatus(HttpStatus.OK.value());  // 状态码 200

		ObjectMapper objectMapper = new ObjectMapper();
		response.setCharacterEncoding("UTF-8");
		//response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
		response.setContentType("application/json;charset=UTF-8");
		Result<String> result = Result.success("退出成功");
		response.getWriter().append(objectMapper.writeValueAsString(result));


	}

}
