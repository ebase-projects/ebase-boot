package me.dwliu.framework.integration.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.dwliu.framework.common.model.Result;
import me.dwliu.framework.core.security.enums.SecurityResultCode;
import me.dwliu.framework.integration.security.validatecode.ValidateCodeException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 自定义json认证失败处理器
 *
 * <p>登录账号密码错误等情况下,会调用的处理类</p>
 *
 * @author liudw
 * @date 2023/4/29 21:58
 **/
@Slf4j
//@Component
public class CustomJsonAuthenticationFailureHandler implements AuthenticationFailureHandler {


	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
										AuthenticationException exception) throws IOException, ServletException {
		log.debug("===登录账号密码错误等===");
		response.setContentType("application/json;charset=utf-8"); // 返回JSON
		response.setStatus(HttpStatus.UNAUTHORIZED.value());  // 状态码 401
		ObjectMapper objectMapper = new ObjectMapper();
//		response.getWriter().write(objectMapper.writeValueAsString(Result.fail(SecurityResultCode.LOGIN_ERROR, "登录失败", exception.getMessage())));

		Result result;

		String username = " UserUtil.loginUsername(request)";
		if (exception instanceof ValidateCodeException) {
			// 账号过期
			log.info("[登录失败] - 验证码错误");
			result = Result.fail(SecurityResultCode.VALIDATE_CODE_ERROR, exception.getMessage());

		} else if (exception instanceof AccountExpiredException) {
			// 账号过期
			log.info("[登录失败] - 用户[{}]账号过期", username);
			result = Result.fail(SecurityResultCode.USER_ACCOUNT_EXPIRED);

		} else if (exception instanceof BadCredentialsException) {
			// 密码错误
			log.info("[登录失败] - 用户[{}]密码错误", username);
			result = Result.fail(SecurityResultCode.USER_CREDENTIALS_ERROR);

		} else if (exception instanceof CredentialsExpiredException) {
			// 密码过期
			log.info("[登录失败] - 用户[{}]密码过期", username);
			result = Result.fail(SecurityResultCode.USER_CREDENTIALS_EXPIRED);

		} else if (exception instanceof DisabledException) {
			// 用户被禁用
			log.info("[登录失败] - 用户[{}]被禁用", username);
			result = Result.fail(SecurityResultCode.USER_ACCOUNT_DISABLE);

		} else if (exception instanceof LockedException) {
			// 用户被锁定
			log.info("[登录失败] - 用户[{}]被锁定", username);
			result = Result.fail(SecurityResultCode.USER_ACCOUNT_LOCKED);

		} else if (exception instanceof InternalAuthenticationServiceException) {
			// 内部错误
			log.error(String.format("[登录失败] - [%s]内部错误", username), exception);
			result = Result.fail(SecurityResultCode.LOGIN_ERROR, "登录失败", exception.getMessage());

		} else {
			// 其他错误
			log.error(String.format("[登录失败] - [%s]其他错误", username), exception);
			result = Result.fail(SecurityResultCode.LOGIN_ERROR, "登录失败", exception.getMessage());
		}

		response.getWriter().write(objectMapper.writeValueAsString(result));

		// TODO 登录失败 记录日志
		//ServletUtils.render(request, response, result);

	}
}
