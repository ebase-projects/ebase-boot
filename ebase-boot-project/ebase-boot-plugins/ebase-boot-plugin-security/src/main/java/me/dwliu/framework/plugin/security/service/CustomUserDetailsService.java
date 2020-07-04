package me.dwliu.framework.plugin.security.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * 自定义用户详细信息
 *
 * @author liudw
 * @date 2020-03-04 22:43
 **/
public interface CustomUserDetailsService extends UserDetailsService {

	/**
	 * 根据社交登录code 登录
	 *
	 * @param code
	 * @return
	 * @throws UsernameNotFoundException
	 */
	UserDetails loadUserBySocial(String code) throws UsernameNotFoundException;

	/**
	 * 根据手机号登陆
	 *
	 * @param mobile
	 * @return
	 * @throws UsernameNotFoundException
	 */
	UserDetails loadUserByMobile(String mobile) throws UsernameNotFoundException;
}
