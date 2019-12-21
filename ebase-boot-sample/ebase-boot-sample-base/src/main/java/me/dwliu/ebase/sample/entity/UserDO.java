package me.dwliu.ebase.sample.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import me.dwliu.lab.core.base.entity.BaseTenantDO;

/**
 * 系统用户表
 *
 * @author liudw ldw4033@163.com
 * @since 1.0.0 2019-08-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_user")
public class UserDO extends BaseTenantDO {

	/**
	 * 用户名
	 */
	private String username;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 姓名
	 */
	private String realName;
	/**
	 * 头像
	 */
	private String avatar;
	/**
	 * 性别   0：男   1：女    2：保密
	 */
	private Integer gender;
	/**
	 * 邮箱
	 */
	private String email;
	/**
	 * 手机号
	 */
	private String mobile;
	/**
	 * 部门ID
	 */
	private Long deptId;
	/**
	 * 超级管理员   0：否   1：是
	 */
	private Integer superAdmin;

	/**
	 * 状态  0：停用   1：正常
	 */
	private Integer status;

	/**
	 * 部门名称
	 */
	@TableField(exist = false)
	private String deptName;
}
