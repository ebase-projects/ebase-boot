package me.dwliu.framework.core.mybatis.service;

import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import com.baomidou.mybatisplus.extension.service.IService;
import me.dwliu.framework.common.model.PageData;

import java.util.Map;

/**
 * 基础服务接口，所有Service接口都要继承
 * <p>
 * T do
 *
 * @param <T> DO
 * @author liudw
 * @date 2019-08-23 11:36
 **/

public interface BaseService<T> extends IService<T> {

//	/**
//	 * 获取DO 实体
//	 *
//	 * @return DO
//	 */
//	default Class<T> currentDOClass() {
//		return (Class<T>) ReflectionKit.getSuperClassGenericType(getClass(), BaseService.class, 0);
//	}

	/**
	 * 分页查询
	 *
	 * @param params 查询封装参数
	 * @return PageData<T>  分页数据 D(dto)
	 */
	PageData<T> listByPage(Map<String, Object> params);

	/**
	 * 分页查询
	 *
	 * @param params     查询封装参数
	 * @param orderField 排序字段
	 * @param isAsc      升级or降序 true asc false desc
	 * @return PageData<T>
	 */
	PageData<T> listByPage(Map<String, Object> params, String orderField, boolean isAsc);

}
