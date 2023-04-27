package me.dwliu.framework.core.mybatis.datascope.enums;

import me.dwliu.framework.common.enums.BaseEnum;

/**
 * 数据权限查询类型枚举
 *
 * @author liudw
 * @date 2020-01-01 22:27
 **/
public enum DataScopeViewEnum implements BaseEnum {

	/**
	 * 本人可见
	 */
	OWN("本人可见", 10),

	/**
	 * 所在机构可见
	 */
	DEPT("所在部门可见", 20),

	/**
	 * 所在机构及子级可见
	 */
	DEPT_CHILD("所在部门及子部门可见", 30),

	/**
	 * 自定义
	 */
	CUSTOM("自定义", -10),

	/**
	 * 全部数据
	 */
	ALL("全部", 99);

	/**
	 * 枚举值
	 */
	private Integer value;
	/**
	 * 描述
	 */
	private String desc;

	DataScopeViewEnum(String desc, int value) {
		this.desc = desc;
		this.value = value;
	}

	public String getDesc() {
		return desc;
	}

	public Integer getValue() {
		return value;
	}


	public static void main(String[] args) {
		System.out.println(BaseEnum.getEnum(DataScopeViewEnum.class, 10).toString());
		System.out.println(BaseEnum.getJsonStr(DataScopeViewEnum.class));
		System.out.println(BaseEnum.toList(DataScopeViewEnum.class));
		System.out.println(BaseEnum.toList4Enum(DataScopeViewEnum.class));
		System.out.println(BaseEnum.toMap(DataScopeViewEnum.class));

	}
}
