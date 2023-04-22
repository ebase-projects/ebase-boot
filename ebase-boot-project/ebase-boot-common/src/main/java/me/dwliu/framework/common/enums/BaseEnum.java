package me.dwliu.framework.common.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 基础枚举基类
 *
 * @author liudw
 * @date 2020/8/11 15:12
 * <p>
 * <a href="https://snakey.blog.csdn.net/article/details/109336047?spm=1001.2101.3001.6650.15&utm_medium=distribute.pc_relevant.none-task-blog-2%7Edefault%7EBlogCommendFromBaidu%7ERate-15-109336047-blog-78840521.235%5Ev31%5Epc_relevant_default_base3&depth_1-utm_source=distribute.pc_relevant.none-task-blog-2%7Edefault%7EBlogCommendFromBaidu%7ERate-15-109336047-blog-78840521.235%5Ev31%5Epc_relevant_default_base3&utm_relevant_index=16">参考</a>
 * </p>
 **/
public interface BaseEnum {
	/**
	 * 获取key值
	 *
	 * @return
	 */
	Integer getValue();

	/**
	 * 获取描述
	 *
	 * @return
	 */
	String getDesc();


	/**
	 * 通过枚举类型和code值获取对应的枚举类型
	 *
	 * @param enumType
	 * @param value
	 * @param <T>
	 * @return
	 */
	static <T extends BaseEnum> T getEnum(Class<? extends BaseEnum> enumType, Integer value) {
		T[] enumConstants = getEnumConstants(enumType);
		if (enumConstants == null) return null;
		for (T enumConstant : enumConstants) {
			int enumValue = enumConstant.getValue();
			if (value.equals(enumValue)) {
				return enumConstant;
			}
		}
		return null;
	}

	/**
	 * 将enum转换为list
	 *
	 * <p>
	 * *     [{value=value, desc=desc}]
	 * * </p>
	 *
	 * @param enumType
	 * @param <T>
	 * @return
	 */
	static <T extends BaseEnum> List<Map<String, Object>> toList(Class<? extends BaseEnum> enumType) {
		T[] enumConstants = getEnumConstants(enumType);
		if (enumConstants == null) return null;
		ArrayList<Map<String, Object>> results = new ArrayList<>();
		for (T bean : enumConstants) {
			String desc = bean.getDesc();
			Integer value = bean.getValue();
			HashMap<String, Object> map = new HashMap<>();
			map.put("value", value);
			map.put("desc", desc);
			results.add(map);
		}
		return results;
	}

	/**
	 * 将enum装换为List
	 * <p>
	 * [{value=toString, desc=desc}]
	 * </p>
	 *
	 * @param enumType
	 * @param <T>
	 * @return
	 */
	static <T extends BaseEnum> List<Map<String, Object>> toList4Enum(Class<? extends BaseEnum> enumType) {
		T[] enumConstants = getEnumConstants(enumType);
		if (enumConstants == null) return null;
		ArrayList<Map<String, Object>> results = new ArrayList<>();
		for (T bean : enumConstants) {
			String desc = bean.getDesc();
			Integer value = bean.getValue();
			HashMap<String, Object> map = new HashMap<>();
			map.put("value", bean.toString());
			map.put("desc", desc);
			results.add(map);
		}
		return results;
	}


	/**
	 * 将enum转为map
	 *
	 * <p>
	 * {'toString'={value='value', desc='desc'}
	 * </p>
	 *
	 * @param enumType
	 * @param <T>
	 * @return
	 */
	static <T extends BaseEnum> Map<String, Map<String, Object>> toMap(Class<? extends BaseEnum> enumType) {
		T[] enumConstants = getEnumConstants(enumType);
		if (enumConstants == null) return null;

		Map<String, Map<String, Object>> enumMap = new HashMap<String, Map<String, Object>>();
		for (T bean : enumConstants) {
			Map<String, Object> map = new HashMap<String, Object>();
			String key = String.valueOf(bean.toString());
			map.put("value", bean.getValue());
			map.put("desc", bean.getDesc());
			enumMap.put(key, map);
		}
		return enumMap;
	}


	/**
	 * 将enum 转换为json
	 * <p>
	 * [{id:'toString',desc:'desc',value:'value'}
	 * </p>
	 *
	 * @param enumType
	 * @param <T>
	 * @return
	 */
	static <T extends BaseEnum> String getJsonStr(Class<? extends BaseEnum> enumType) {
		T[] enumConstants = getEnumConstants(enumType);
		if (enumConstants == null) return null;

		StringBuffer jsonStr = new StringBuffer("[");
		for (T senum : enumConstants) {
			if (!"[".equals(jsonStr.toString())) {
				jsonStr.append(",");
			}
			jsonStr.append("{id:'").append(senum).append("',desc:'").append(senum.getDesc()).append("',value:'").append(senum.getValue()).append("'}");
		}
		jsonStr.append("]");
		return jsonStr.toString();
	}


	private static <T extends BaseEnum> T[] getEnumConstants(Class<? extends BaseEnum> enumType) {
		if (enumType == null) {
			return null;
		}
		T[] enumConstants = (T[]) enumType.getEnumConstants();
		if (enumConstants == null) {
			return null;
		}
		return enumConstants;
	}

//	public static void main(String[] args) {
//		System.out.println(BaseEnum.getEnum(BaseEnum.class, 10).toString());
//		System.out.println(BaseEnum.getJsonStr(BaseEnum.class));
//		System.out.println(BaseEnum.toList(BaseEnum.class));
//		System.out.println(BaseEnum.toList4Enum(BaseEnum.class));
//		System.out.println(BaseEnum.toMap(BaseEnum.class));
//
//	}

}
