package me.dwliu.framework.core.mybatis.service;

import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import com.baomidou.mybatisplus.extension.service.IService;
import me.dwliu.framework.common.model.PageData;
import me.dwliu.framework.core.tool.util.ConvertUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 基础服务接口，所有Service接口都要继承
 * <p>
 * T do  D dto
 *
 * @param <T> DO
 * @param <D> DTO
 * @author liudw
 * @date 2019-08-23 11:36
 **/

public interface BaseService<T, D> extends IService<T> {

	/**
	 * 获取DO 实体
	 *
	 * @return DO
	 */
	default Class<T> currentDOClass() {
		return (Class<T>) ReflectionKit.getSuperClassGenericType(getClass(), BaseService.class, 1);
	}

	/**
	 * 获取DTO 实体
	 *
	 * @return DTO
	 */
	default Class<D> currentDTOClass() {
		return (Class<D>) ReflectionKit.getSuperClassGenericType(getClass(), BaseService.class, 2);
	}

	/**
	 * 分页查询
	 *
	 * @param params 查询封装参数
	 * @return PageData<D> 分页数据 D(dto)
	 */
	PageData<D> listEntityByPage(Map<String, Object> params);

	/**
	 * 分页查询
	 *
	 * @param params     查询封装参数
	 * @param orderField 排序字段
	 * @param isAsc      升级or降序 true asc false desc
	 * @return PageData<D>
	 */
	PageData<D> listEntityByPage(Map<String, Object> params, String orderField, boolean isAsc);

	/**
	 * 根据参数查询所有集合
	 *
	 * @param params 查询封装参数
	 * @return List<D> 集合数据 D(dto)
	 */
	List<D> listEntity(Map<String, Object> params);

	/**
	 * 查找单个实体
	 *
	 * @param id ID
	 * @return D  D(DTO)
	 */
	default D getEntity(Long id) {
		if (id == null) {
			return null;
		}
		T entity = getById(id);
		return ConvertUtils.sourceToTarget(entity, currentDTOClass());
	}

	/**
	 * 插入单个实体
	 *
	 * @param dto D(DTO)
	 */
	@Transactional(rollbackFor = Exception.class)
	default boolean insertEntity(D dto) {
		T entity = ConvertUtils.sourceToTarget(dto, currentDOClass());
		boolean b = save(entity);
		//copy主键值到dto
		BeanUtils.copyProperties(entity, dto);
		return b;
	}

	/**
	 * 批量插入实体
	 *
	 * @param dtos D(DTO)
	 */
	@Transactional(rollbackFor = Exception.class)
	default boolean insertBachEntity(List<D> dtos) {
		List<T> ts = ConvertUtils.sourceToTarget(dtos, currentDOClass());
		return saveBatch(ts);
	}

	/**
	 * 批量插入实体
	 *
	 * @param dtos D(DTO)
	 */
	@Transactional(rollbackFor = Exception.class)
	default boolean insertBachEntity(List<D> dtos, int batchSize) {
		List<T> ts = ConvertUtils.sourceToTarget(dtos, currentDOClass());
		return saveBatch(ts, batchSize);
	}


	/**
	 * 批量插入实体返回ID
	 *
	 * @param dtos 入参实体
	 * @return List<D>
	 */
	@Transactional(rollbackFor = Exception.class)
	default List<D> insertBachEntityReturnId(List<D> dtos) {
//		List<T> ts = ConvertUtils.sourceToTarget(dtos, currentDOClass());
//		saveBatch(ts);
//		return ConvertUtils.sourceToTarget(ts, currentDTOClass());
		return insertBachEntityReturnId(dtos, false);
	}

	/**
	 * 批量插入实体返回ID
	 *
	 * @param dtos              入参实体
	 * @param isSupportDeepCopy 是否将source的List强制给了target的List
	 * @return List<D>
	 */
	@Transactional(rollbackFor = Exception.class)
	default List<D> insertBachEntityReturnId(List<D> dtos, boolean isSupportDeepCopy) {
		List<T> ts = ConvertUtils.sourceToTarget(dtos, currentDOClass(), isSupportDeepCopy);
		saveBatch(ts);
		return ConvertUtils.sourceToTarget(ts, currentDTOClass(), isSupportDeepCopy);
	}

	/**
	 * 批量插入实体返回ID
	 *
	 * @param dtos      入参实体
	 * @param batchSize 批量的条数
	 * @return List<D>
	 */
	@Transactional(rollbackFor = Exception.class)
	default List<D> insertBachEntityReturnId(List<D> dtos, int batchSize) {
//		List<T> ts = ConvertUtils.sourceToTarget(dtos, currentDOClass());
//		saveBatch(ts, batchSize);
//		return ConvertUtils.sourceToTarget(ts, currentDTOClass());

		return insertBachEntityReturnId(dtos, batchSize, false);
	}

	/**
	 * 批量插入实体返回ID
	 *
	 * @param dtos              入参实体
	 * @param batchSize         批量的条数
	 * @param isSupportDeepCopy 是否将source的List强制给了target的List
	 * @return List<D>
	 */
	@Transactional(rollbackFor = Exception.class)
	default List<D> insertBachEntityReturnId(List<D> dtos, int batchSize, boolean isSupportDeepCopy) {
		List<T> ts = ConvertUtils.sourceToTarget(dtos, currentDOClass(), isSupportDeepCopy);
		saveBatch(ts, batchSize);
		return ConvertUtils.sourceToTarget(ts, currentDTOClass(), isSupportDeepCopy);
	}

	/**
	 * 更新单个实体
	 *
	 * @param dto D(DTO)
	 */
	@Transactional(rollbackFor = Exception.class)
	default boolean updateEntity(D dto) {
		T entity = ConvertUtils.sourceToTarget(dto, currentDOClass());
		return updateById(entity);
	}

	/**
	 * 删除单个实体
	 *
	 * @param id ID
	 */
	@Transactional(rollbackFor = Exception.class)
	default boolean deleteEntity(Long id) {
		return removeById(id);
	}

	/**
	 * 批量删除实体
	 *
	 * @param ids ID
	 */
	@Transactional(rollbackFor = Exception.class)
	default boolean deleteBachEntity(Long[] ids) {
		return removeByIds(Arrays.asList(ids));
	}


}
