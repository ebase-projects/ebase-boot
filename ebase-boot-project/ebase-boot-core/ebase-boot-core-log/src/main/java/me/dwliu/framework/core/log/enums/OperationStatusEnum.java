package me.dwliu.framework.core.log.enums;

import me.dwliu.framework.common.enums.BaseEnum;

/**
 * 操作状态枚举
 *
 * @author liudw
 * @date 2020/7/5 12:20
 **/
public enum OperationStatusEnum implements BaseEnum {
	/**
	 * 失败
	 */
	FAIL(0, "失败"),
	/**
	 * 成功
	 */
	SUCCESS(1, "成功");

	private final Integer value;
	private final String desc;

	OperationStatusEnum(int code, String value) {
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
