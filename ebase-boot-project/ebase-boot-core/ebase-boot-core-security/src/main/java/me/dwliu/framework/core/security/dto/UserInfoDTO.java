package me.dwliu.framework.core.security.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * Spring Security 用户信息拓展
 *
 * @author liudw
 * @date 2019-07-08 17:58
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDTO {
	/**
	 * 用户id
	 */
	private String userId;
	/**
	 * 租户ID
	 */
	private String tenantCode;
	/**
	 * 昵称
	 */
	private String realName;
	/**
	 * 账号
	 */
	private String username;
	/**
	 * 部门id
	 */
	private String deptId;
	/**
	 * 部门名称
	 */
	private String deptName;
	/**
	 * 角色id
	 */
	private Set<String> roleIds;
	/**
	 * 角色名
	 */
	private Set<String> roleNames;
	/**
	 * 头像
	 */
	private String avatar;
	/**
	 * 超级管理员   0：否   1：是
	 */
	private Integer superAdmin;

	private String password;

	private Set<String> permissions;

	private boolean accountNonExpired;

	private boolean accountNonLocked;

	private boolean credentialsNonExpired;

	private boolean enabled;


}
