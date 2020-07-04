package me.dwliu.framework.core.security.constant;

/**
 * 安全相关的常量
 *
 * @author liudw
 * @date 2019-07-08 14:07
 **/
public interface SecurityConstant {
	/**
	 * {bcrypt} 加密的特征码
	 */
	String BCRYPT = "{bcrypt}";

	/**
	 * oauth_client_details 表字段
	 */
	String CLIENT_FIELDS = "client_id, CONCAT('{noop}',client_secret) as client_secret, resource_ids, scope, " +
		"authorized_grant_types, web_server_redirect_uri, authorities, access_token_validity, " +
		"refresh_token_validity, additional_information, autoapprove";

	/**
	 * oauth_client_details 查询语句
	 */
	String BASE_STATEMENT = "select " + CLIENT_FIELDS + " from oauth_client_details";

	/**
	 * oauth_client_details 查询排序
	 */
	String DEFAULT_FIND_STATEMENT = BASE_STATEMENT + " order by client_id";

	/**
	 * 查询client_id
	 */
	String DEFAULT_SELECT_STATEMENT = BASE_STATEMENT + " where client_id = ?";


	/**
	 * 客户端模式
	 */
	String CLIENT_CREDENTIALS = "client_credentials";


	/**
	 * 协议字段
	 */
	String ADDITIONAL_INFO_LICENSE = "license";

	/**
	 * 项目的license
	 */
	String ADDITIONAL_INFO_LICENSE_DESC = "made by ebase";

	/**
	 * 前缀
	 */
	String OAUTH2_TOKEN_STORE_PREFIX = "ebase_oauth:";

	/**
	 * 角色前缀
	 */
	String ROLE = "ROLE_";

}
