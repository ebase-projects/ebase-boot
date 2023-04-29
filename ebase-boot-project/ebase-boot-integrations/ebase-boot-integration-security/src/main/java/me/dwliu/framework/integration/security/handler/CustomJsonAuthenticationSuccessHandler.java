package me.dwliu.framework.integration.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.dwliu.framework.common.model.Result;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 自定义json认证成功处理器
 *
 * @author liudw
 * @date 2023/4/29 22:06
 **/
@Component("customJsonAuthenticationSuccessHandler")
@Slf4j
public class CustomJsonAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	/**
	 * 登录成功后直接返回 JSON
	 *
	 * @param request        请求
	 * @param response       响应
	 * @param authentication 成功认证的用户信息
	 */
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		response.setContentType("application/json;charset=utf-8"); // 返回JSON
		response.setStatus(HttpStatus.OK.value());  // 状态码 200
//		Map<String, Object> result = new HashMap<>(); // 返回结果
//		result.put("msg", "登录成功");
//		result.put("code", SystemResultCode.SUCCESS);
//		result.put("data", authentication);
//		response.getWriter().write(objectMapper.writeValueAsString(result));
		ObjectMapper objectMapper = new ObjectMapper();
		response.getWriter().write(objectMapper.writeValueAsString(Result.success("登陆成功")));
	}

}
