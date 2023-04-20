package me.dwliu.framework.core.mybatis.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import me.dwliu.framework.common.model.PageData;
import me.dwliu.framework.core.mybatis.dao.BaseDAO;
import me.dwliu.framework.core.mybatis.query.QueryPageUtil;
import me.dwliu.framework.core.mybatis.service.Base4DTOService;
import me.dwliu.framework.core.tool.util.ConvertUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * 基础服务类，所有Service都要继承
 *
 * @param <M> DAO
 * @param <T> DO
 * @param <D> DTO
 * @author liudw
 * @date 2019-12-29 09:18
 **/
public abstract class Base4DTOServiceImpl<M extends BaseDAO<T>, T, D> extends BaseServiceImpl<M, T> implements Base4DTOService<T, D> {

	@Autowired
	protected M baseDAO;

	/**
	 * 分页查询
	 *
	 * @param params 查询封装参数
	 * @return PageData
	 */
	@Override
	public PageData<D> listDTOByPage(Map<String, Object> params) {
		return listDTOByPage(params, null, false);
	}

	/**
	 * @param params            查询封装参数
	 * @param defaultOrderField 默认排序字段
	 * @param isAsc             升级or降序 true asc false desc
	 * @return PageData
	 */
	@Override
	public PageData<D> listDTOByPage(Map<String, Object> params, String defaultOrderField, boolean isAsc) {
		IPage<T> page = baseDAO.selectPage(getPage(params, defaultOrderField, isAsc), getWrapper(params));
		return getPageData(page, currentDTOClass());
	}

	/**
	 * 根据参数查询所有集合
	 *
	 * @param params 查询封装参数
	 * @return
	 */
	@Override
	public List<D> listDTO(Map<String, Object> params) {
		List<T> entityList = baseDAO.selectList(getWrapper(params));

		return ConvertUtils.sourceToTarget(entityList, currentDTOClass());
	}


}
