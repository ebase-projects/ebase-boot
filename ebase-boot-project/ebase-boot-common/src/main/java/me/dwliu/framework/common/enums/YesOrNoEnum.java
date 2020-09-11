package me.dwliu.framework.common.enums;

import lombok.Getter;

/**
 * Yes Or No 枚举定义
 *
 * @author liudw
 * @date 2019-12-20 20:03
 **/
@Getter
public enum YesOrNoEnum implements BaseEnum {
	YES(1, "是"),
	NO(0, "否");

	private Integer value;
	private String desc;

	YesOrNoEnum(Integer value, String desc) {
		this.value = value;
	}

}
