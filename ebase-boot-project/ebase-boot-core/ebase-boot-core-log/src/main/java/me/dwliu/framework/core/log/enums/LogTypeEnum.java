package me.dwliu.framework.core.log.enums;

/**
 * 日志类型枚举
 *
 * @author liudw
 * @date 2020/7/5 12:19
 **/
public enum LogTypeEnum {
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

	private int value;
	private String desc;

	LogTypeEnum(int code, String value) {
		this.value = code;
		this.desc = value;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}
