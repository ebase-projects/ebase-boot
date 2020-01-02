package me.dwliu.framework.core.datascope.constant;

import org.apache.commons.lang3.StringUtils;

/**
 * 数据权限常量
 *
 * @author liudw
 * @date 2020-01-01 22:35
 **/
public interface DataScopeConstant {

	String DEFAULT_COLUMN = "create_dept";

	/**
	 * 获取部门数据
	 */
	String DATA_BY_DEPT = "select id from sys_dept where ancestors like concat('%', ?, '%')";

	/**
	 * 根据resourceCode获取数据权限配置
	 */
	String DATA_BY_CODE = "select resource_code, scope_column, scope_field, scope_type, scope_value from blade_scope_data where resource_code = ?";

	/**
	 * 根据mapperId获取数据权限配置
	 *
	 * @param size 数量
	 * @return String
	 */
	static String dataByMapper(int size) {
		return "select resource_code, scope_column, scope_field, scope_type, scope_value from blade_scope_data where scope_class = ? and id in (select scope_id from blade_role_scope where scope_category = 1 and role_id in (" + buildHolder(size) + "))";
	}

	/**
	 * 获取Sql占位符
	 *
	 * @param size 数量
	 * @return String
	 */
	static String buildHolder(int size) {
		StringBuilder builder = new StringBuilder();
		if (size > 0) {
			for (int i = 0; i < size; i++) {
				builder.append("?,");
			}

			//去除最后的 ，
			return StringUtils.substringBeforeLast(builder.toString(), ",");
			//TODO return StringUtil.removeSuffix(builder.toString(), ",");

		}

		return builder.toString();

	}


}
