package me.dwliu.framework.core.log.enums;

import me.dwliu.framework.common.enums.BaseEnum;

/**
 * 登录操作枚举
 *
 * @author liudw
 * @date 2020/7/5 12:17
 **/
public enum LoginOperationEnum implements BaseEnum {
	/**
	 * 用户登录
	 */
	LOGIN(0, "用户登录"),
	/**
	 * 用户退出
	 */
	LOGOUT(1, "用户退出");

	private final Integer value;
	private final String desc;

	LoginOperationEnum(int code, String value) {
		this.value = code;
		this.desc = value;
	}

	public Integer getValue() {
		return value;
	}

	public String getDesc() {
		return desc;
	}


}
