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
    String SQL_FILTER = "sqlFilter";

    String SQL_ROLE_DATA_SCOPE = "select role_id ,scope_type ,dept_ids from sys_role_data_scope where role_id in (:roleIds)";
    String SQL_SYS_DEPT = "select id from sys_dept where pid =? and status =1 and del_flag =0";

    static String dataByMapper(int size) {
        return "select role_id ,scope_type ,dept_ids from sys_role_data_scope where role_id in (" + buildHolder(size) + ")";

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

        }

        return builder.toString();

    }


}
