package me.dwliu.framework.integration.security.service;

import me.dwliu.framework.core.security.entity.UserInfoDetails;

/**
 * 用户接口
 *
 * @author liudw
 * @date 2020/7/3 22:36
 **/

public interface UserInfoDetailsService {

	/**
	 * 根据用户ID 获取用户信息
	 *
	 * @param userId 用户ID
	 * @return
	 */
	UserInfoDetails getUserById(Long userId);

	/**
	 * 根据用户手机号码获取用户信息
	 *
	 * @param mobile 手机号码
	 * @return
	 */
	UserInfoDetails getUserByMobile(String mobile);

	/**
	 * 根据用户名获取用户信息
	 *
	 * @param username 用户名
	 * @return
	 */
	UserInfoDetails getUserInfo(String username);

}
