package me.dwliu.framework.common.enums;

import lombok.Getter;

import java.util.Objects;

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
		this.desc = desc;
	}


	public static void main(String[] args) {
		System.out.println(BaseEnum.getEnum(YesOrNoEnum.class, 10).toString());
		System.out.println(BaseEnum.getJsonStr(YesOrNoEnum.class));
		System.out.println(BaseEnum.toList(YesOrNoEnum.class));
		System.out.println(BaseEnum.toList4Enum(YesOrNoEnum.class));
		System.out.println(BaseEnum.toMap(YesOrNoEnum.class));

	}

}
