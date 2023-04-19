package me.dwliu.framework.core.mybatis.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import me.dwliu.framework.common.model.PageData;
import me.dwliu.framework.core.mybatis.dao.BaseDAO;
import me.dwliu.framework.core.mybatis.query.QueryPageUtil;
import me.dwliu.framework.core.mybatis.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * 基础服务类，所有Service都要继承
 *
 * @param <M> DAO
 * @param <T> DO
 * @author liudw
 * @date 2019-12-29 09:18
 **/
public abstract class BaseServiceImpl<M extends BaseDAO<T>, T> extends ServiceImpl<M, T> implements BaseService<T> {

	@Autowired
	protected M baseDAO;

	/**
	 * 查询条件封装
	 *
	 * @param params
	 * @return
	 */
	public abstract Wrapper<T> getWrapper(Map<String, Object> params);

	/**
	 * 分页查询
	 *
	 * @param params 查询封装参数
	 * @return PageData
	 */
	@Override
	public PageData<T> listByPage(Map<String, Object> params) {
		return listByPage(params, null, false);
	}

	/**
	 * @param params            查询封装参数
	 * @param defaultOrderField 默认排序字段
	 * @param isAsc             升级or降序 true asc false desc
	 * @return PageData
	 */
	@Override
	public PageData<T> listByPage(Map<String, Object> params, String defaultOrderField, boolean isAsc) {
		IPage<T> page = baseDAO.selectPage(getPage(params, defaultOrderField, isAsc), getWrapper(params));
		return getPageData(page);
	}


	/**
	 * 获取分页对象
	 *
	 * @param params            分页查询参数
	 * @param defaultOrderField 默认排序字段
	 * @param isAsc             排序方式
	 */
	protected IPage<T> getPage(Map<String, Object> params, String defaultOrderField, boolean isAsc) {
		return new QueryPageUtil<T>().getPage(params, defaultOrderField, isAsc);
	}


	/**
	 * 获取分页对象
	 *
	 * @param params 分页查询参数
	 */
	protected IPage<T> getPage(Map<String, Object> params) {
		return new QueryPageUtil<T>().getPage(params, null, null);
	}


	/**
	 * 组装分页数据
	 *
	 * @param list  查询数据集合
	 * @param total 总条数
	 * @return PageData
	 */
	protected <T> PageData<T> getPageData(List<T> list, long total) {
		return new PageData<>(list, total);
	}

	/**
	 * 组装分页数据
	 *
	 * @param page
	 * @return PageData
	 */
	protected <T> PageData<T> getPageData(IPage page) {
		return getPageData(page.getRecords(), page.getTotal());
	}
}
