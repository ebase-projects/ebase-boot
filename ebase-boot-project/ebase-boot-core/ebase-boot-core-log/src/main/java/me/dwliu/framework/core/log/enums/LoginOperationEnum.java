package me.dwliu.framework.core.log.enums;

/**
 * 登录操作枚举
 *
 * @author liudw
 * @date 2020/7/5 12:17
 **/
public enum LoginOperationEnum {
	/**
	 * 用户登录
	 */
	LOGIN(0, "用户登录"),
	/**
	 * 用户退出
	 */
	LOGOUT(1, "用户退出");

	private int value;
	private String desc;


	LoginOperationEnum(int code, String value) {
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
