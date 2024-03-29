package me.dwliu.framework.core.security.constant;

/**
 * 安全相关的常量
 *
 * @author liudw
 * @date 2019-07-08 14:07
 **/
public interface SecurityCoreConstant {
    /**
     * 资源服务器默认bean名称
     */
    String RESOURCE_SERVER_CONFIGURER = "resourceServerConfigurerAdapter";

    /**
     * 内部
     */
    String FROM_IN = "Y";

    /**
     * 标志
     */
    String FROM = "from";

    /**
     * 角色前缀
     */
    String ROLE = "ROLE_";


    /**
     * 用户ID字段
     */
    String DETAILS_USER_ID = "user_id";

    /**
     * 用户名字段
     */
    String DETAILS_USERNAME = "username";

    /**
     * 用户部门字段
     */
    String DETAILS_DEPT_ID = "create_dept";

    /**
     * 租户ID 字段
     */
    String DETAILS_TENANT_ID = "tenant_code";


    /**
     * 不进行认证的URL
     */
    String[] IGNORING_URLS = {
            "/actuator/**",
            "/doc.html",
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/v2/api-docs/**",
            "/v3/api-docs/**",
	        "/swagger-resources/**",
            "/webjars/**",
            "/service-worker.js",
            "/favicon.ico",
    };
}
