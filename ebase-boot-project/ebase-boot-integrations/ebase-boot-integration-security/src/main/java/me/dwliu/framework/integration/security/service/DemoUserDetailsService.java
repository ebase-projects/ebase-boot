package me.dwliu.framework.integration.security.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

/**
 * 实例用户服务
 *
 * <p>
 * 仅限演示用，必须实现
 * </p>
 *
 * @author liudw
 * @date 2019-04-29 16:36
 **/
@Slf4j
@AllArgsConstructor
public class DemoUserDetailsService implements CustomUserDetailsService {
	private final PasswordEncoder passwordEncoder;

	/**
	 * Locates the user based on the username. In the actual implementation, the search
	 * may possibly be case sensitive, or case insensitive depending on how the
	 * implementation instance is configured. In this case, the <code>UserDetails</code>
	 * object that comes back may have a username that is of a different case than what
	 * was actually requested..
	 *
	 * @param username the username identifying the user whose data is required.
	 * @return a fully populated user record (never <code>null</code>)
	 * @throws UsernameNotFoundException if the user could not be found or the user has no
	 *                                   GrantedAuthority
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.warn("===演示用的加载用户信息服务，必须实现 UserDetailsService 重写 loadUserByUsername 方法===");
		//TODO
		//throw new UsernameNotFoundException(username);

		List<SimpleGrantedAuthority> rols = new ArrayList<>();
		rols.add(new SimpleGrantedAuthority("demo"));

		return new User(username, passwordEncoder.encode("123456"), true, true, true, true, rols);
	}

	@Override
	public UserDetails loadUserBySocial(String code) throws UsernameNotFoundException {
		return null;
	}

	@Override
	public UserDetails loadUserByMobile(String username) throws UsernameNotFoundException {
		log.warn("===演示用的加载用户信息服务，必须实现 UserDetailsService 重写 loadUserByMobile 方法===");

		List<SimpleGrantedAuthority> rols = new ArrayList<>();
		rols.add(new SimpleGrantedAuthority("demo"));

		return new User(username, passwordEncoder.encode("123456"), true, true, true, true, rols);
	}
}
