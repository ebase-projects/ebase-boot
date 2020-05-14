package me.dwliu.framework.core.mongodb.service.impl;

import me.dwliu.framework.common.model.PageData;
import me.dwliu.framework.core.mongodb.dao.BaseDAO;
import me.dwliu.framework.core.mongodb.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * 基础服务类，所有Service都要继承
 *
 * @param <T> DO
 * @param <D> DTO
 * @author liudw
 * @date 2019-12-29 09:18
 **/
public abstract class BaseServiceImpl<T, D> implements BaseService<T, D> {

	@Autowired
	private BaseDAO baseDAO;

	@Override
	public PageData<D> listEntityByPage(Map<String, Object> params) {
		return null;
	}

	@Override
	public PageData<D> listEntityByPage(Map<String, Object> params, String orderField, boolean isAsc) {
		return null;
	}

	@Override
	public List<D> listEntity(Map<String, Object> params) {
		return null;
	}

	@Override
	public D getEntity(Long id) {
		return null;
	}

	@Override
	public boolean insertEntity(D dto) {
		return false;
	}

	@Override
	public boolean insertBachEntity(List<D> dtos) {
		return false;
	}

	@Override
	public boolean insertBachEntity(List<D> dtos, int batchSize) {
		return false;
	}

	@Override
	public List<D> insertBachEntityReturnId(List<D> dtos) {
		return null;
	}

	@Override
	public List<D> insertBachEntityReturnId(List<D> dtos, boolean isSupportDeepCopy) {
		return null;
	}

	@Override
	public List<D> insertBachEntityReturnId(List<D> dtos, int batchSize) {
		return null;
	}

	@Override
	public List<D> insertBachEntityReturnId(List<D> dtos, int batchSize, boolean isSupportDeepCopy) {
		return null;
	}

	@Override
	public boolean updateEntity(D dto) {
		return false;
	}

	@Override
	public boolean deleteEntity(Long id) {
		return false;
	}

	@Override
	public boolean deleteBachEntity(Long[] ids) {
		return false;
	}

//	/**
//	 * 查询条件封装
//	 *
//	 * @param params
//	 * @return
//	 */
////	public abstract QueryWrapper<T> getWrapper(Map<String, Object> params);
//
//	/**
//	 * 分页查询
//	 *
//	 * @param params 查询封装参数
//	 * @return PageData
//	 */
//	@Override
//	public PageData<D> listEntityByPage(Map<String, Object> params) {
//		return listEntityByPage(params, null, false);
//	}
//
//	/**
//	 * @param params            查询封装参数
//	 * @param defaultOrderField 默认排序字段
//	 * @param isAsc             升级or降序 true asc false desc
//	 * @return PageData
//	 */
//	@Override
//	public PageData<D> listEntityByPage(Map<String, Object> params, String defaultOrderField, boolean isAsc) {
//		IPage<T> page = baseDAO.selectPage(getPage(params, defaultOrderField, isAsc), getWrapper(params));
//		return getPageData(page, currentDTOClass());
//	}
//
//	/**
//	 * 根据参数查询所有集合
//	 *
//	 * @param params 查询封装参数
//	 * @return
//	 */
//	@Override
//	public List<D> listEntity(Map<String, Object> params) {
//		List<T> entityList = baseDAO.selectList(getWrapper(params));
//
//		return ConvertUtils.sourceToTarget(entityList, currentDTOClass());
//	}
//
//	/**
//	 * 获取分页对象
//	 *
//	 * @param params            分页查询参数
//	 * @param defaultOrderField 默认排序字段
//	 * @param isAsc             排序方式
//	 */
//	protected IPage<T> getPage(Map<String, Object> params, String defaultOrderField, boolean isAsc) {
//		return new QueryPageUtil<T>().getPage(params, defaultOrderField, isAsc);
//	}
//
//	/**
//	 * 组装分页数据
//	 *
//	 * @param list   查询数据集合
//	 * @param total  总条数
//	 * @param target 目标实体对象
//	 * @return PageData
//	 */
//	protected <T> PageData<T> getPageData(List<?> list, long total, Class<T> target) {
//		List<T> targetList = ConvertUtils.sourceToTarget(list, target);
//		return new PageData<>(targetList, total);
//	}
//
//	/**
//	 * 组装分页数据
//	 *
//	 * @param page
//	 * @param target
//	 * @return PageData
//	 */
//	protected <T> PageData<T> getPageData(IPage page, Class<T> target) {
//		return getPageData(page.getRecords(), page.getTotal(), target);
//	}
//
//	/**
//	 * 参数模糊查询
//	 *
//	 * @param params 参数列表
//	 * @param likes  需要模糊查询的字段
//	 * @return Map<String, Object>
//	 */
//	protected Map<String, Object> paramsToLike(Map<String, Object> params, String... likes) {
//		for (String like : likes) {
//			String val = (String) params.get(like);
//			if (StringUtils.isNotEmpty(val)) {
//				params.put(like, "%" + val + "%");
//			} else {
//				params.put(like, null);
//			}
//		}
//		return params;
//	}

}
