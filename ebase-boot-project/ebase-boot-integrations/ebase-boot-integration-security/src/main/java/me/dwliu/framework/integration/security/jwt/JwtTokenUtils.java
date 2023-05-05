package me.dwliu.framework.integration.security.jwt;

import io.jsonwebtoken.*;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import me.dwliu.framework.core.security.entity.UserInfoDetails;
import me.dwliu.framework.integration.security.exception.JwtTokenException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * jwt 工具类
 *
 * @author liudw
 * @date 2023/5/4 15:40
 **/
@Slf4j
public class JwtTokenUtils {

	private final String secretKey;
	private final Integer expiration;
	private final String tokenHeaderKey;
	private final String tokenParamKey;
	private final String tokenPrefix;

	public JwtTokenUtils(String secretKey, Integer expiration, String tokenHeaderKey, String tokenParamKey, String tokenPrefix) {
		this.secretKey = secretKey;
		this.expiration = expiration;
		this.tokenHeaderKey = tokenHeaderKey;
		this.tokenParamKey = tokenParamKey;
		this.tokenPrefix = tokenPrefix;
	}

	private String secretKeyBase64;

	@PostConstruct
	protected void initial() {
		secretKeyBase64 = Base64.getEncoder().encodeToString(secretKey.getBytes());
	}

	/**
	 * 根据账号、角色信息创建token
	 *
	 * @param securityUserDetails
	 * @return
	 */
	public String createToken(UserInfoDetails securityUserDetails) {
		Claims claims = Jwts.claims().setSubject(securityUserDetails.getUsername());
		// 存储业务用各种数据，保存非涉密信息，比如用户名、昵称、所属企业等
		claims.put("permissions", securityUserDetails.getAuthorities());
		claims.put("nickname", securityUserDetails.getRealName());
		return generateToken(claims);
	}

	/**
	 * 刷新token信息
	 *
	 * @param token
	 * @return
	 */
	public String refreshToken(String token) {
		Claims claims = Jwts.parser().setSigningKey(secretKeyBase64)
			.parseClaimsJws(token).getBody();
		return generateToken(claims);
	}

	private String generateToken(Claims claims) {
		Date now = new Date(System.currentTimeMillis());
		// 登陆成功生成JWT
		String token = Jwts.builder()
			// 放入用户名和用户ID
//			.setId(selfUserEntity.getUserId() + "")
			// 主题
//			.setSubject(selfUserEntity.getUsername())
			// 签发时间
			.setIssuedAt(now)
			// 签发者
			.setIssuer("ebase")
			// 自定义属性 放入用户拥有权限
//			.claim("authorities", JSON.toJSONString(selfUserEntity.getAuthorities()))
			.setClaims(claims)
			// 失效时间
			.setExpiration(new Date(now.getTime() + expiration))
			// 签名算法和密钥
			.signWith(SignatureAlgorithm.HS512, secretKeyBase64)
			.compact();
		return token;
	}

	public Authentication getAuthentication(String token) {
		// 方法一：每次接口请求时，在JwtTokenOncePerRequestFilter会调用本方法，每次读取数据库。也可以考虑增加缓存。
		//UserDetails userDetails = this.userDetailsService.loadUserByUsername(getUsername(token));
		// 方法二：token有效期内，根据token解码的信息，重新生成SecurityUserDetails信息
		Claims claims = Jwts.parser().setSigningKey(secretKeyBase64).parseClaimsJws(token).getBody();

		List<Map<String, Object>> roles = claims.get("permissions", List.class);
		List<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<>();
		if (roles != null && roles.size() > 0) {
			for (Map<String, Object> role : roles) {
				simpleGrantedAuthorities.add(new SimpleGrantedAuthority((String) role.get("authority")));
			}

		}

		UserInfoDetails userDetails = new UserInfoDetails(claims.getSubject(), claims.get("nickname", String.class), "", "", "", "", "", claims.getSubject(), "", 1, true, true, true, true, simpleGrantedAuthorities);
		return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
	}

	public String getUsername(String token) {
		return Jwts.parser().setSigningKey(secretKeyBase64).parseClaimsJws(token).getBody().getSubject();
	}

	public Date getTokenExpiration(String token) {
		return Jwts.parser().setSigningKey(secretKeyBase64).parseClaimsJws(token).getBody().getExpiration();
	}


	public String resolveToken(HttpServletRequest request) {
		String bearerToken = getToken(request);
		if (bearerToken != null) {
			if (bearerToken.startsWith(tokenPrefix + " ")) {
				return bearerToken.substring(tokenPrefix.length() + 1);
			} else {
				return bearerToken;
			}
		} else {
			return null;
		}

	}

	/**
	 * 从header或者参数中获取token
	 *
	 * @return token
	 */
	private String getToken(HttpServletRequest request) {
		String token = request.getHeader(tokenHeaderKey);
		if (!StringUtils.hasLength(token)) {
			token = request.getParameter(tokenParamKey);
		}
		return token;
	}

	public boolean validateToken(String token) {
		try {
			Claims claims = Jwts.parser().setSigningKey(secretKeyBase64).parseClaimsJws(token).getBody();
			if (claims.getExpiration().before(new Date())) {
				return false;
			}
			return true;
		} catch (ExpiredJwtException e) {
			log.warn("Expired JWT token. {}", token);
			return false;
		} catch (JwtException | IllegalArgumentException e) {
			throw new JwtTokenException("Invalid JWT token");
		}
	}

}
