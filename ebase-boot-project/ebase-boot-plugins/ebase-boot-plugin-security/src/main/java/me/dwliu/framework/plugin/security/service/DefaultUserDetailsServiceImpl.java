package me.dwliu.framework.plugin.security.service;

import me.dwliu.framework.common.code.SystemResultCode;
import me.dwliu.framework.common.exception.BusinessException;
import me.dwliu.framework.common.model.Result;
import me.dwliu.framework.core.security.constant.SecurityCoreConstant;
import me.dwliu.framework.core.security.entity.UserInfoDetails;
import me.dwliu.framework.plugin.security.feign.UserInfoDetailsFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * 默认的 UserDetailsServiceImpl
 *
 * @author liudw
 * @date 2020/7/4 11:04
 **/
public class DefaultUserDetailsServiceImpl implements UserDetailsService {
	@Autowired(required = false)
	private UserInfoDetailsFeignClient userInfoDetailsFeignClient;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Result<UserInfoDetails> userInfoDetailsResult = userInfoDetailsFeignClient.getByUsername(username, SecurityCoreConstant.FROM_IN);

		UserInfoDetails userInfoDetails = userInfoDetailsResult.getData();

		if (userInfoDetails == null) {
			throw new BusinessException(SystemResultCode.DATA_NOT_FOUND, "用户数据不存在");
		}

		return userInfoDetails;
	}
}
