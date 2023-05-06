package me.dwliu.framework.core.security.entity;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

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
	private final String username;
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
	 * 权限集合
	 */
	private Set<String> permissions;
	/**
	 * 头像
	 */
	private String avatar;
	/**
	 * 超级管理员   0：否   1：是
	 */
	private Integer superAdmin;

	//	public UserInfoDetails(String username) {
//		super(username, "", Arrays.asList(new SimpleGrantedAuthority("1")));
//		this.username = username;
//	}

	/**
	 * 根据用户名实例化UserInfoDetails ,没有权限信息
	 *
	 * @param username
	 */
	public UserInfoDetails(String username) {
		super(username, "", Collections.EMPTY_LIST);
		this.username = username;
	}

	public UserInfoDetails(String username, Collection<? extends GrantedAuthority> authorities) {
		super(username, "", authorities);
		this.username = username;
	}

	/**
	 * 构造去除租户、部门名称、角色名称
	 *
	 * @param userId
	 * @param realName
	 * @param deptId
	 * @param roleIds
	 * @param avatar
	 * @param username
	 * @param password
	 * @param superAdmin
	 * @param enabled
	 * @param accountNonExpired
	 * @param credentialsNonExpired
	 * @param accountNonLocked
	 * @param authorities
	 */
	public UserInfoDetails(String userId, String realName, String deptId,
						   Set<String> roleIds, String avatar, String username, String password,
						   int superAdmin, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked,
						   Set<String> permissions, Collection<? extends GrantedAuthority> authorities) {
		this(userId, null, realName, deptId,
			roleIds, avatar, username, password,
			superAdmin, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked,
			permissions, authorities);

	}

	/**
	 * 构造去除租户的
	 *
	 * @param userId
	 * @param realName
	 * @param deptId
	 * @param deptName
	 * @param roleIds
	 * @param roleNames
	 * @param avatar
	 * @param username
	 * @param password
	 * @param superAdmin
	 * @param enabled
	 * @param accountNonExpired
	 * @param credentialsNonExpired
	 * @param accountNonLocked
	 * @param authorities
	 */
	public UserInfoDetails(String userId, String realName, String deptId, String deptName,
						   Set<String> roleIds, Set<String> roleNames, String avatar, String username, String password,
						   int superAdmin, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked,
						   Set<String> permissions, Collection<? extends GrantedAuthority> authorities) {
		this(userId, null, realName, deptId, deptName,
			roleIds, roleNames, avatar, username, password,
			superAdmin, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked,
			permissions, authorities);

	}

	/**
	 * 去除角色名称和部门名称
	 *
	 * @param userId
	 * @param tenantCode
	 * @param realName
	 * @param deptId
	 * @param roleIds
	 * @param avatar
	 * @param username
	 * @param password
	 * @param superAdmin
	 * @param enabled
	 * @param accountNonExpired
	 * @param credentialsNonExpired
	 * @param accountNonLocked
	 * @param authorities
	 */
	public UserInfoDetails(String userId, String tenantCode, String realName, String deptId,
						   Set<String> roleIds, String avatar, String username, String password,
						   int superAdmin, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked,
						   Set<String> permissions, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		this.userId = userId;
		this.tenantCode = tenantCode;
		this.realName = realName;
		this.username = username;
		this.deptId = deptId;
		this.roleIds = roleIds;
		this.avatar = avatar;
		this.superAdmin = superAdmin;
		this.permissions = permissions;
	}

	/**
	 * 构建全部属性（ALL）
	 *
	 * @param userId
	 * @param tenantCode
	 * @param realName
	 * @param deptId
	 * @param deptName
	 * @param roleIds
	 * @param roleNames
	 * @param avatar
	 * @param username
	 * @param password
	 * @param superAdmin
	 * @param enabled
	 * @param accountNonExpired
	 * @param credentialsNonExpired
	 * @param accountNonLocked
	 * @param authorities
	 */
	public UserInfoDetails(String userId, String tenantCode, String realName, String deptId, String deptName,
						   Set<String> roleIds, Set<String> roleNames, String avatar, String username, String password,
						   int superAdmin, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked,
						   Set<String> permissions, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		this.userId = userId;
		this.tenantCode = tenantCode;
		this.realName = realName;
		this.username = username;
		this.deptId = deptId;
		this.deptName = deptName;
		this.roleIds = roleIds;
		this.roleNames = roleNames;
		this.avatar = avatar;
		this.superAdmin = superAdmin;
		this.permissions = permissions;


	}


}
