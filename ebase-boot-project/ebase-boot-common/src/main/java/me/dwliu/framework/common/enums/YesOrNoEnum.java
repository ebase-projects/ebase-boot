package me.dwliu.framework.common.enums;

import lombok.Getter;

/**
 * Yes Or No 枚举定义
 *
 * @author liudw
 * @date 2019-12-20 20:03
 **/
@Getter
public enum YesOrNoEnum {
	YES(1),
	NO(0);
	private Integer value;

	YesOrNoEnum(Integer value) {
		this.value = value;
	}
}
