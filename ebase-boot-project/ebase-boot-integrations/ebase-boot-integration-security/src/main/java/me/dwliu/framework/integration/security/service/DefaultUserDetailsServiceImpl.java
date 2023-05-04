package me.dwliu.framework.integration.security.service;

import lombok.extern.slf4j.Slf4j;
import me.dwliu.framework.core.security.entity.UserInfoDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * 自定义用户详细信息实现类
 *
 * @author liudw
 * @date 2020-03-04 22:42
 **/
@Slf4j
public class DefaultUserDetailsServiceImpl implements CustomUserDetailsService {


	private UserInfoDetailsService userInfoDetailsService;


	@Override
	public UserDetails loadUserBySocial(String code) throws UsernameNotFoundException {
		return null;
	}

	@Override
	public UserDetails loadUserByMobile(String mobile) throws UsernameNotFoundException {
		UserInfoDetails userByMobile = userInfoDetailsService.getUserByMobile(mobile);
		if (userByMobile == null) {
			throw new UsernameNotFoundException("用户手机号不存在");
		}
		return userByMobile;
	}


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserInfoDetails userInfoDetails = userInfoDetailsService.getUserInfo(username);
		return userInfoDetails;
	}


	/**
	 * 封装用户信息
	 *
	 * @param userInfoDTO
	 * @return
	 */
	// private UserDetails getUserDetails(UserInfoDTO userInfoDTO) {
	// 	if (userInfoDTO == null) {
	// 		throw new UsernameNotFoundException("用户不存在");
	// 	}
	//
	// 	SysUserDTO sysUserDTO = userInfoDTO.getSysUserDTO();
	//
	// 	Set<SimpleGrantedAuthority> authorities = new HashSet<>();
	// 	// 获取角色
	// 	if (userInfoDTO.getRoleIds() != null && userInfoDTO.getRoleIds().size() > 0) {
	// 		userInfoDTO.getRoleIds()
	// 			.stream()
	// 			.forEach(roleId -> authorities.add(new SimpleGrantedAuthority(SecurityConstant.ROLE + roleId)));
	// 	}
	//
	// 	// 获取资源
	// 	if (userInfoDTO.getPermissions() != null && userInfoDTO.getPermissions().size() > 0) {
	// 		userInfoDTO.getPermissions()
	// 			.stream()
	// 			.forEach(permission -> authorities.add(new SimpleGrantedAuthority(permission)));
	// 	}
	//
	// 	return new UserInfoDetails(sysUserDTO.getId() + "", sysUserDTO.getTenantCode(), sysUserDTO.getRealName(),
	// 		sysUserDTO.getDeptId() + "", sysUserDTO.getDeptName(),
	// 		"", "", sysUserDTO.getAvatar(),
	// 		sysUserDTO.getUsername(), sysUserDTO.getPassword(),
	// 		sysUserDTO.getStatus() == 1, true, true, true, authorities);
	// }
}
