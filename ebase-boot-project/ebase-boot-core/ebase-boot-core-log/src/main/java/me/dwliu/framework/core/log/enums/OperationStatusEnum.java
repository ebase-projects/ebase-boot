package me.dwliu.framework.core.log.enums;

/**
 * 操作状态枚举
 *
 * @author liudw
 * @date 2020/7/5 12:20
 **/
public enum OperationStatusEnum {
	/**
	 * 失败
	 */
	FAIL(0, "失败"),
	/**
	 * 成功
	 */
	SUCCESS(1, "成功");

	private int value;
	private String desc;


	OperationStatusEnum(int code, String value) {
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
