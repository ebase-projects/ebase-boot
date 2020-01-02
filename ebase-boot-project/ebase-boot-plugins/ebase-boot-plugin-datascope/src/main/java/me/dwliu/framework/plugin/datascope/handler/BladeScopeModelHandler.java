package me.dwliu.framework.plugin.datascope.handler;

import lombok.RequiredArgsConstructor;
import me.dwliu.framework.core.datascope.model.DataScopeModel;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;


/**
 * BladeScopeModelHandler
 *
 * @author Chill
 */
@RequiredArgsConstructor
public class BladeScopeModelHandler implements ScopeModelHandler {

	private static final String SCOPE_CACHE_CODE = "dataScope:code:";
	private static final String SCOPE_CACHE_CLASS = "dataScope:class:";
	private static final String DEPT_CACHE_ANCESTORS = "dept:ancestors:";

	private final JdbcTemplate jdbcTemplate;

	/**
	 * 获取数据权限
	 *
	 * @param mapperId 数据权限mapperId
	 * @param roleId   用户角色集合
	 * @return DataScopeModel
	 */
	@Override
	public DataScopeModel getDataScopeByMapper(String mapperId, String roleId) {
//		List<Object> args = new ArrayList<>(Collections.singletonList(mapperId));
//		List<Long> roleIds = Func.toLongList(roleId);
//		args.addAll(roleIds);
//		DataScopeModel dataScope = CacheUtil.get(SYS_CACHE, SCOPE_CACHE_CLASS, mapperId + StringPool.COLON + roleId, DataScopeModel.class);
//		if (dataScope == null) {
//			List<DataScopeModel> list = jdbcTemplate.query(DataScopeConstant.dataByMapper(roleIds.size()), args.toArray(), new BeanPropertyRowMapper<>(DataScopeModel.class));
//			if (CollectionUtil.isNotEmpty(list)) {
//				dataScope = list.iterator().next();
//				CacheUtil.put(SYS_CACHE, SCOPE_CACHE_CLASS, mapperId + StringPool.COLON + roleId, dataScope);
//			}
//		}
//		return dataScope;
		return null;
	}

	/**
	 * 获取数据权限
	 *
	 * @param code 数据权限资源编号
	 * @return DataScopeModel
	 */
	@Override
	public DataScopeModel getDataScopeByCode(String code) {
//		DataScopeModel dataScope = CacheUtil.get(SYS_CACHE, SCOPE_CACHE_CODE, code, DataScopeModel.class);
//		if (dataScope == null) {
//			List<DataScopeModel> list = jdbcTemplate.query(DataScopeConstant.DATA_BY_CODE, new Object[]{code}, new BeanPropertyRowMapper<>(DataScopeModel.class));
//			if (CollectionUtil.isNotEmpty(list)) {
//				dataScope = list.iterator().next();
//				CacheUtil.put(SYS_CACHE, SCOPE_CACHE_CODE, code, dataScope);
//			}
//		}
//		return dataScope;
		return null;
	}

	/**
	 * 获取部门子级
	 *
	 * @param deptId 部门id
	 * @return deptIds
	 */
	@Override
	public List<Long> getDeptAncestors(Long deptId) {
//		List ancestors = CacheUtil.get(SYS_CACHE, DEPT_CACHE_ANCESTORS, deptId, List.class);
//		if (CollectionUtil.isEmpty(ancestors)) {
//			ancestors = jdbcTemplate.queryForList(DataScopeConstant.DATA_BY_DEPT, new Object[]{deptId}, Long.class);
//			CacheUtil.put(SYS_CACHE, DEPT_CACHE_ANCESTORS, deptId, ancestors);
//		}
//		return ancestors;
		return null;
	}
}
