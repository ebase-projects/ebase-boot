package me.dwliu.framework.plugin.datascope.handler;

import me.dwliu.framework.core.datascope.model.DataScopeModel;

import java.util.List;

/**
 * 获取数据权限模型统一接口
 *
 * @author liudw
 * @date 2020-01-02 09:39
 **/
public interface ScopeModelHandler {

	/**
	 * 获取数据权限
	 *
	 * @param mapperId 数据权限mapperId
	 * @param roleId   用户角色集合
	 * @return DataScopeModel
	 */
	DataScopeModel getDataScopeByMapper(String mapperId, String roleId);

	/**
	 * 获取数据权限
	 *
	 * @param code 数据权限资源编号
	 * @return DataScopeModel
	 */
	DataScopeModel getDataScopeByCode(String code);

	/**
	 * 获取部门子级
	 *
	 * @param deptId 部门id
	 * @return deptIds
	 */
	List<Long> getDeptAncestors(Long deptId);

}
