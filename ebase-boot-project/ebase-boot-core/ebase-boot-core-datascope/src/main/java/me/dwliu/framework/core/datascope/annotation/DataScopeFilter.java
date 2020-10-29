package me.dwliu.framework.core.datascope.annotation;

import java.lang.annotation.*;

/**
 * 数据权限定义
 *
 * @author liudw
 * @date 2020-01-02 09:35
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataScopeFilter {

    /**
     * 表的别名
     */
    String tableAlias() default "";

    /**
     * 用户ID
     */
    String createBy() default "create_by";

    /**
     * 部门ID
     */
    String createDept() default "create_dept";

}

