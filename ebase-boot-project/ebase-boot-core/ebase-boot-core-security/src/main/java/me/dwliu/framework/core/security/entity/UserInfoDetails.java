package me.dwliu.framework.core.security.entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * Spring Security 用户信息拓展
 *
 * @author liudw
 * @date 2019-07-08 17:58
 **/
@Getter
public class UserInfoDetails extends User {
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
	private String roleId;
	/**
	 * 角色名
	 */
	private String roleName;
	/**
	 * 头像
	 */
	private String avatar;

	/**
	 * 超级管理员   0：否   1：是
	 */
	private Integer superAdmin;


	public UserInfoDetails(String username) {
		super(username, "", null);
		this.username = username;
	}

	public UserInfoDetails(String userId, String tenantCode, String realName, String deptId,
	                       String deptName, String roleId, String roleName, String avatar,
	                       String username, String password, int superAdmin, boolean enabled, boolean accountNonExpired,
	                       boolean credentialsNonExpired, boolean accountNonLocked,
	                       Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		this.userId = userId;
		this.tenantCode = tenantCode;
		this.realName = realName;
		this.username = username;
		this.deptId = deptId;
		this.deptName = deptName;
		this.roleId = roleId;
		this.roleName = roleName;
		this.avatar = avatar;
		this.superAdmin = superAdmin;

	}
}
