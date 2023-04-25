//package me.dwliu.framework.integration.security.handler;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Lazy;
//import org.springframework.http.MediaType;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
//import org.springframework.stereotype.Component;
//
//
//import java.io.IOException;
//import java.nio.charset.StandardCharsets;
//import java.util.Base64;
//import java.util.Collections;
//
///**
// * 认证成功处理器
// *
// * @author liudw
// * @date 2019-04-30 13:05
// **/
//@Component("ebaseAuthenticationSuccessHandler")
//@Slf4j
//public class EbaseAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
//	@Autowired
//	private ObjectMapper objectMapper;
//
//	@Autowired(required = false)
//	private ClientDetailsService clientDetailsService;
//
//	@Autowired
//	private PasswordEncoder passwordEncoder;
//
//	@Lazy
//	@Autowired
//	private AuthorizationServerTokenServices authorizationServerTokenServices;
//
//	@Override
//	public void onAuthenticationSuccess(HttpServletRequest request,
//										HttpServletResponse response,
//										Authentication authentication) throws ServletException, IOException {
//		//super.onAuthenticationSuccess(request, response, authentication);
//
//		String header = request.getHeader("Authorization");
//
//		if (header == null || !header.toLowerCase().startsWith("basic ")) {
//			throw new BadCredentialsException("Failed to decode basic authentication token");
//		}
//
//
//		String[] tokens = extractAndDecodeHeader(header, request);
//		assert tokens.length == 2;
//
//		String clientId = tokens[0];
//		String clientSecret = tokens[1];
//
//		clientSecret = "{bcrypt}" + new BCryptPasswordEncoder().encode(clientSecret);
//
//		log.debug("Basic Authentication Authorization header found for user '" + clientId + "'");
//
//		ClientDetails clientDetails = clientDetailsService.loadClientByClientId(clientId);
//
//		if (clientDetails == null) {
//			throw new UnapprovedClientAuthenticationException("clientId对应的配置信息不存在:" + clientId);
//			//} else if (!StringUtils.equals(clientDetails.getClientSecret(), clientSecret)) {
//		} else if (passwordEncoder.matches(clientDetails.getClientSecret(), clientSecret)) {
//			throw new UnapprovedClientAuthenticationException("clientSecret不匹配:" + clientId);
//		}
//
//		TokenRequest tokenRequest = new TokenRequest(Collections.emptyMap(),
//			clientId, clientDetails.getScope(), "custom");
//
//		OAuth2Request oAuth2Request = tokenRequest.createOAuth2Request(clientDetails);
//
//		OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request, authentication);
//
//		OAuth2AccessToken token = authorizationServerTokenServices.createAccessToken(oAuth2Authentication);
//
//		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//		response.getWriter().write(objectMapper.writeValueAsString(token));
//
//	}
//
//
//	/**
//	 * Decodes the header into a username and password.
//	 *
//	 * @throws BadCredentialsException if the Basic header is not present or is not valid
//	 *                                 Base64
//	 */
//	private String[] extractAndDecodeHeader(String header, HttpServletRequest request)
//		throws IOException {
//
//		//Basic YXBwaWQ6YXBwc2VjcmV0 截取Basic后的
//		byte[] base64Token = header.substring(6).getBytes(StandardCharsets.UTF_8);
//		byte[] decoded;
//		try {
//			//appid:appsecret
//			decoded = Base64.getDecoder().decode(base64Token);
//		} catch (IllegalArgumentException e) {
//			throw new BadCredentialsException(
//				"Failed to decode basic authentication token");
//		}
//
//		String token = new String(decoded, StandardCharsets.UTF_8);
//
//		int delim = token.indexOf(":");
//
//		if (delim == -1) {
//			throw new BadCredentialsException("Invalid basic authentication token");
//		}
//		return new String[]{token.substring(0, delim), token.substring(delim + 1)};
//	}
//}
