package me.dwliu.framework.core.datascope.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据权限类型
 *
 * @author liudw
 * @date 2020-01-01 22:27
 **/
public enum DataScopeEnum {
	/**
	 * 全部数据
	 */
	ALL("全部", 1),

	/**
	 * 本人可见
	 */
	OWN("本人可见", 2),

	/**
	 * 所在机构可见
	 */
	OWN_DEPT("所在机构可见", 3),

	/**
	 * 所在机构及子级可见
	 */
	OWN_DEPT_CHILD("所在机构及子级可见", 4),

	/**
	 * 自定义
	 */
	CUSTOM("自定义", 5);

	/**
	 * 枚举值
	 */
	private int value;
	/**
	 * 描述
	 */
	private String desc;

	DataScopeEnum(String desc, int value) {
		this.desc = desc;
		this.value = value;
	}

	public static DataScopeEnum getEnum(int value) {
		DataScopeEnum resultEnum = null;
		DataScopeEnum[] enumAry = DataScopeEnum.values();
		for (int i = 0; i < enumAry.length; i++) {
			if (enumAry[i].getValue() == value) {
				resultEnum = enumAry[i];
				break;
			}
		}
		return resultEnum;
	}

	public static Map<String, Map<String, Object>> toMap() {
		DataScopeEnum[] ary = DataScopeEnum.values();
		Map<String, Map<String, Object>> enumMap = new HashMap<String, Map<String, Object>>();
		for (int num = 0; num < ary.length; num++) {
			Map<String, Object> map = new HashMap<String, Object>();
			String key = String.valueOf(getEnum(ary[num].getValue()));
			map.put("value", String.valueOf(ary[num].getValue()));
			map.put("desc", ary[num].getDesc());
			enumMap.put(key, map);
		}
		return enumMap;
	}

	/**
	 * 获取枚举和描述
	 *
	 * @return
	 */
	@SuppressWarnings({"rawtypes", "unchecked"})
	public static List toList4Enum() {
		DataScopeEnum[] ary = DataScopeEnum.values();
		List list = new ArrayList();
		for (int num = 0; num < ary.length; num++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("value", DataScopeEnum.getEnum(ary[num].getValue()).name());
			map.put("desc", ary[num].getDesc());
			list.add(map);
		}
		return list;
	}


	@SuppressWarnings({"rawtypes", "unchecked"})
	public static List toList() {
		DataScopeEnum[] ary = DataScopeEnum.values();
		List list = new ArrayList();
		for (int i = 0; i < ary.length; i++) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("value", String.valueOf(ary[i].getValue()));
			map.put("desc", ary[i].getDesc());
			list.add(map);
		}
		return list;
	}

	/**
	 * 取枚举的json字符串
	 *
	 * @return
	 */
	public static String getJsonStr() {
		DataScopeEnum[] enums = DataScopeEnum.values();
		StringBuffer jsonStr = new StringBuffer("[");
		for (DataScopeEnum senum : enums) {
			if (!"[".equals(jsonStr.toString())) {
				jsonStr.append(",");
			}
			jsonStr.append("{id:'").append(senum).append("',desc:'").append(senum.getDesc()).append("',value:'")
				.append(senum.getValue()).append("'}");
		}
		jsonStr.append("]");
		return jsonStr.toString();
	}


	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public static void main(String[] args) {
		System.out.println(DataScopeEnum.getEnum(1));
		System.out.println(DataScopeEnum.getJsonStr());
		System.out.println(DataScopeEnum.toList());
		System.out.println(DataScopeEnum.toMap());
		System.out.println(DataScopeEnum.toList4Enum());

	}
}
