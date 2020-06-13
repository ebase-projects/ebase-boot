package me.dwliu.framework.core.mybatis.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import me.dwliu.framework.common.model.PageData;
import me.dwliu.framework.core.mybatis.dao.BaseDAO;
import me.dwliu.framework.core.mybatis.page.QueryPageUtil;
import me.dwliu.framework.core.mybatis.service.BaseService;
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
public abstract class BaseServiceImpl<M extends BaseDAO<T>, T, D> extends ServiceImpl<M, T>
	implements BaseService<T, D> {

	@Autowired
	protected M baseDAO;

	/**
	 * 查询条件封装
	 *
	 * @param params
	 * @return
	 */
	public abstract QueryWrapper<T> getWrapper(Map<String, Object> params);

	/**
	 * 分页查询
	 *
	 * @param params 查询封装参数
	 * @return PageData
	 */
	@Override
	public PageData<D> listEntityByPage(Map<String, Object> params) {
		return listEntityByPage(params, null, false);
	}

	/**
	 * @param params            查询封装参数
	 * @param defaultOrderField 默认排序字段
	 * @param isAsc             升级or降序 true asc false desc
	 * @return PageData
	 */
	@Override
	public PageData<D> listEntityByPage(Map<String, Object> params, String defaultOrderField, boolean isAsc) {
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
	public List<D> listEntity(Map<String, Object> params) {
		List<T> entityList = baseDAO.selectList(getWrapper(params));

		return ConvertUtils.sourceToTarget(entityList, currentDTOClass());
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
	 * 组装分页数据
	 *
	 * @param list   查询数据集合
	 * @param total  总条数
	 * @param target 目标实体对象
	 * @return PageData
	 */
	protected <T> PageData<T> getPageData(List<?> list, long total, Class<T> target) {
		List<T> targetList = ConvertUtils.sourceToTarget(list, target);
		return new PageData<>(targetList, total);
	}

	/**
	 * 组装分页数据
	 *
	 * @param page
	 * @param target
	 * @return PageData
	 */
	protected <T> PageData<T> getPageData(IPage page, Class<T> target) {
		return getPageData(page.getRecords(), page.getTotal(), target);
	}

	/**
	 * 参数模糊查询
	 *
	 * @param params 参数列表
	 * @param likes  需要模糊查询的字段
	 * @return Map<String, Object>
	 */
	protected Map<String, Object> paramsToLike(Map<String, Object> params, String... likes) {
		for (String like : likes) {
			String val = (String) params.get(like);
			if (StringUtils.isNotEmpty(val)) {
				params.put(like, "%" + val + "%");
			} else {
				params.put(like, null);
			}
		}
		return params;
	}


//    /**
//     * 逻辑删除
//     *
//     * @param ids id集合(逗号分隔)
//     * @return
//     */
//    @Override
//    @Transactional
//    public boolean deleteEntityLogic(@NotEmpty List<Long> ids) {
////        T entity = BeanUtils.instantiateClass(modelClass);
//////        entity.setUpdateUser(user.getUserId());
////        //entity.setUpdateTime(LocalDateTime.now());
////        entity.setcreateBy(new Date());
////
////        //boolean update = super.update(entity, Wrappers.<T>update().lambda().in(T::getId, ids));
////        boolean update = super.update(entity, new QueryWrapper<T>().in("id", ids));
////
////        //boolean update = super.update(entity, new QueryWrapper<T>().lambda().in(T::getId, ids));
////        boolean remove = super.removeByIds(ids);
////        return update && remove;
//
//        return false;
//
//    }
}
