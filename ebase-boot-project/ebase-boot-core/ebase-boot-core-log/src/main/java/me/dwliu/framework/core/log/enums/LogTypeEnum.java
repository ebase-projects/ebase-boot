package me.dwliu.framework.core.log.enums;

import me.dwliu.framework.common.enums.BaseEnum;

/**
 * 日志类型枚举
 *
 * @author liudw
 * @date 2020/7/5 12:19
 **/
public enum LogTypeEnum implements BaseEnum {
	/**
	 * 登录日志
	 */
	LOGIN(0, "登录日志"),
	/**
	 * 操作日志
	 */
	OPERATION(1, "操作日志"),
	/**
	 * 异常日志
	 */
	ERROR(2, "异常日志");

	private final Integer value;
	private final String desc;

	LogTypeEnum(int code, String value) {
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
