//package me.dwliu.framework.integration.security.handler;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import me.dwliu.framework.common.code.SystemResultCode;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * 认证失败处理器
// *
// * @author liudw
// * @date 2019-04-25 14:13
// **/
//@Slf4j
////@Component("ebaseAuthenticationFailureHandler")
//public class EbaseAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
//
//
//	/**
//	 * Performs the redirect or forward to the {@code defaultFailureUrl} if set, otherwise
//	 * returns a 401 error code.
//	 * <p>
//	 * If redirecting or forwarding, {@code saveException} will be called to cache the
//	 * exception for use in the target view.
//	 *
//	 * @param request
//	 * @param response
//	 * @param exception
//	 */
//	@Override
//	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
//										AuthenticationException exception) throws IOException, ServletException {
//
//		log.trace("登陆失败");
//		response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
//		response.setContentType("application/json;charset=utf-8");
//		ObjectMapper objectMapper = new ObjectMapper();
//
//		Map<String, Object> result = new HashMap<>(); // 返回结果
//		result.put("msg", "登陆失败");
//		result.put("code", SystemResultCode.SUCCESS);
//		result.put("data", exception.getMessage());
//		response.getWriter().write(objectMapper.writeValueAsString(result));
//
//	}
//
//}
