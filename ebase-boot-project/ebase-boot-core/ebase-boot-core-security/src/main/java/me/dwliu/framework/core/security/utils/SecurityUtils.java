package me.dwliu.framework.core.security.utils;

import cn.hutool.core.util.StrUtil;
import lombok.experimental.UtilityClass;
import me.dwliu.framework.core.security.constant.SecurityCoreConstant;
import me.dwliu.framework.core.security.entity.UserInfoDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 安全工具类
 *
 * @author liudw
 * @date 2019-08-22 16:55
 **/
@UtilityClass
public class SecurityUtils {
	/**
	 * 获取Authentication
	 */
	public Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

	/**
	 * 获取用户
	 *
	 * @param authentication
	 * @return UserInfoDetails
	 */
	public UserInfoDetails getUser(Authentication authentication) {
		if (authentication == null) {
			return null;
		}
		Object principal = authentication.getPrincipal();
		if (principal instanceof UserInfoDetails) {
			return (UserInfoDetails) principal;
		} else if (principal instanceof String) {
			UserInfoDetails userInfoDetails = new UserInfoDetails((String) principal);
			return userInfoDetails;
		}
		return null;
	}

	/**
	 * 获取用户
	 *
	 * @return UserInfoDetails
	 */
	public UserInfoDetails getUser() {
		Authentication authentication = getAuthentication();
		return getUser(authentication);
	}

	/**
	 * 获取用户角色信息
	 *
	 * @return 角色集合
	 */
	public List<Integer> getRoles() {
		Authentication authentication = getAuthentication();
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

		List<Integer> roleIds = new ArrayList<>();
		authorities.stream()
			.filter(granted -> StrUtil.startWith(granted.getAuthority(), SecurityCoreConstant.ROLE))
			.forEach(granted -> {
				String id = StrUtil.removePrefix(granted.getAuthority(), SecurityCoreConstant.ROLE);
				roleIds.add(Integer.parseInt(id));
			});
		return roleIds;
	}

}
