//package me.dwliu.framework.integration.security.component;
//
//import me.dwliu.framework.common.code.SystemResultCode;
//import me.dwliu.framework.common.model.Result;
//import me.dwliu.framework.integration.security.exception.CustomOAuth2Exception;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.AccessDeniedException;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.oauth2.common.DefaultThrowableAnalyzer;
//import org.springframework.security.oauth2.common.OAuth2AccessToken;
//import org.springframework.security.oauth2.common.exceptions.ClientAuthenticationException;
//import org.springframework.security.oauth2.common.exceptions.InsufficientScopeException;
//import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
//import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
//import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
//import org.springframework.security.web.util.ThrowableAnalyzer;
//import org.springframework.stereotype.Component;
//import org.springframework.web.HttpRequestMethodNotSupportedException;
//
///**
// * 异常处理,重写oauth 默认实现 自定义登录或者鉴权失败时的返回信息
// *
// * @author liudw
// * @date 2020/8/22 11:48
// **/
//@Component
//public class CustomWebResponseExceptionTranslator implements WebResponseExceptionTranslator<OAuth2Exception> {
//	private ThrowableAnalyzer throwableAnalyzer = new DefaultThrowableAnalyzer();
//
//	@Override
//	public ResponseEntity<OAuth2Exception> translate(Exception e) {
//		Throwable[] causeChain = throwableAnalyzer.determineCauseChain(e);
//
//		Exception exception = (AuthenticationException) throwableAnalyzer.getFirstThrowableOfType(AuthenticationException.class, causeChain);
//		if (exception != null) {
//			//return handleOAuth2Exception(new CustomOAuth2Exception(e.getMessage(), e));
//
//			OAuth2Exception ex = new CustomOAuth2Exception(e.getMessage(), e);
//			int status = ex.getHttpErrorCode();
//			HttpHeaders headers = new HttpHeaders();
//			headers.set(HttpHeaders.CACHE_CONTROL, "no-store");
//			headers.set(HttpHeaders.PRAGMA, "no-cache");
//			if (status == HttpStatus.UNAUTHORIZED.value() || (e instanceof InsufficientScopeException)) {
//				headers.set(HttpHeaders.WWW_AUTHENTICATE, String.format("%s %s", OAuth2AccessToken.BEARER_TYPE, ex.getSummary()));
//			}
//
//			if (e instanceof ClientAuthenticationException) {
//				return new ResponseEntity<>(ex, headers, HttpStatus.valueOf(status));
//			}
//
//			Result<String> result = Result.fail(SystemResultCode.INVALID_ACCESS_TOKEN, ex.getMessage());
//
//			return new ResponseEntity(result, headers, HttpStatus.valueOf(status));
//
//
//		}
//
//		exception = (AccessDeniedException) throwableAnalyzer.getFirstThrowableOfType(AccessDeniedException.class, causeChain);
//		if (exception != null) {
//			return handleOAuth2Exception(new CustomOAuth2Exception(exception.getMessage(), exception));
//		}
//
//		exception = (InvalidGrantException) throwableAnalyzer.getFirstThrowableOfType(InvalidGrantException.class, causeChain);
//		if (exception != null) {
//			return handleOAuth2Exception(new CustomOAuth2Exception(exception.getMessage(), exception));
//		}
//
//		exception = (HttpRequestMethodNotSupportedException) throwableAnalyzer.getFirstThrowableOfType(HttpRequestMethodNotSupportedException.class, causeChain);
//		if (exception != null) {
//			return handleOAuth2Exception(new CustomOAuth2Exception(exception.getMessage(), exception));
//		}
//
//		exception = (OAuth2Exception) throwableAnalyzer.getFirstThrowableOfType(OAuth2Exception.class, causeChain);
//		if (exception != null) {
//			return handleOAuth2Exception((OAuth2Exception) exception);
//		}
//
//		return handleOAuth2Exception(new CustomOAuth2Exception(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), e));
//	}
//
//	private ResponseEntity<OAuth2Exception> handleOAuth2Exception(OAuth2Exception e) {
//		int status = e.getHttpErrorCode();
//		HttpHeaders headers = new HttpHeaders();
//		headers.set(HttpHeaders.CACHE_CONTROL, "no-store");
//		headers.set(HttpHeaders.PRAGMA, "no-cache");
//		if (status == HttpStatus.UNAUTHORIZED.value() || (e instanceof InsufficientScopeException)) {
//			headers.set(HttpHeaders.WWW_AUTHENTICATE, String.format("%s %s", OAuth2AccessToken.BEARER_TYPE, e.getSummary()));
//		}
//
//		if (e instanceof ClientAuthenticationException) {
//			return new ResponseEntity<>(e, headers, HttpStatus.valueOf(status));
//		}
//
//		Result<String> result = Result.fail(SystemResultCode.FAILURE, e.getMessage());
//
//		return new ResponseEntity(result, headers, HttpStatus.valueOf(status));
//
//	}
//}
