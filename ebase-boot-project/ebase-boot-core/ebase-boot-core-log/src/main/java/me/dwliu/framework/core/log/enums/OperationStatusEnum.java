package me.dwliu.framework.core.log.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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


	public static OperationStatusEnum getEnum(int value) {
		OperationStatusEnum resultEnum = null;
		OperationStatusEnum[] enumAry = OperationStatusEnum.values();
		for (int i = 0; i < enumAry.length; i++) {
			if (enumAry[i].getValue() == value) {
				resultEnum = enumAry[i];
				break;
			}
		}
		return resultEnum;
	}

	public static Map<String, Map<String, Object>> toMap() {
		OperationStatusEnum[] ary = OperationStatusEnum.values();
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
		OperationStatusEnum[] ary = OperationStatusEnum.values();
		List list = new ArrayList();
		for (int num = 0; num < ary.length; num++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("value", OperationStatusEnum.getEnum(ary[num].getValue()).name());
			map.put("desc", ary[num].getDesc());
			list.add(map);
		}
		return list;
	}


	@SuppressWarnings({"rawtypes", "unchecked"})
	public static List toList() {
		OperationStatusEnum[] ary = OperationStatusEnum.values();
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
		OperationStatusEnum[] enums = OperationStatusEnum.values();
		StringBuffer jsonStr = new StringBuffer("[");
		for (OperationStatusEnum senum : enums) {
			if (!"[".equals(jsonStr.toString())) {
				jsonStr.append(",");
			}
			jsonStr.append("{id:'").append(senum).append("',desc:'").append(senum.getDesc()).append("',value:'")
				.append(senum.getValue()).append("'}");
		}
		jsonStr.append("]");
		return jsonStr.toString();
	}

}
