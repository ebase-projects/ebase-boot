package me.dwliu.framework.plugin.datascope.handler;

import me.dwliu.framework.core.datascope.model.DataScopeModel;
import org.apache.ibatis.plugin.Invocation;

/**
 * 数据权限规则
 *
 * @author Chill
 */
public interface DataScopeHandler {

	/**
	 * 获取过滤sql
	 *
	 * @param invocation  过滤器信息
	 * @param mapperId    数据查询类
	 * @param dataScope   数据权限类
	 * @param bladeUser   当前用户信息
	 * @param originalSql 原始Sql
	 * @return sql
	 */
	String sqlCondition(Invocation invocation, String mapperId, DataScopeModel dataScope, BladeUser bladeUser, String originalSql);

}
