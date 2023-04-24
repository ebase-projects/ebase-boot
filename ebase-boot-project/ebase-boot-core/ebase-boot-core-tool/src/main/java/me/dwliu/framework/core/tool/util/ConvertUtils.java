package me.dwliu.framework.core.tool.util;

import lombok.extern.slf4j.Slf4j;
import me.dwliu.framework.core.tool.bean.BeanMapper;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 转换工具类
 *
 * @author eric
 */
@Slf4j
public class ConvertUtils {

	/**
	 * 单个实体复制 不支持List T->D
	 *
	 * @param source 源实体
	 * @param target 目标实体
	 * @param <T>
	 * @return
	 */
	public static <T> T sourceToTarget(Object source, Class<T> target) {
//		if (source == null) {
//			return null;
//		}
//		T targetObject = null;
//		try {
//			targetObject = target.newInstance();
//			BeanUtils.copyProperties(source, targetObject);
//		} catch (Exception e) {
//			logger.error("convert error ", e);
//		}
//
//		return targetObject;

		return sourceToTarget(source, target, false);
	}

	/**
	 * 单个实体复制 支持List T->D
	 *
	 * @param source            源实体
	 * @param target            目标实体
	 * @param isSupportDeepCopy 是否支持List T->D
	 * @param <T>
	 * @return
	 */
	public static <T> T sourceToTarget(Object source, Class<T> target, boolean isSupportDeepCopy) {
		if (source == null) {
			return null;
		}
		T targetObject = null;
		if (isSupportDeepCopy) {
			targetObject = BeanMapper.map(source, target);
		} else {
			try {
				targetObject = target.newInstance();

				BeanUtils.copyProperties(source, targetObject);
			} catch (Exception e) {
				log.error("convert error ", e);
			}

		}


		return targetObject;
	}

	/**
	 * 多个实体复制 不支持List T->D
	 *
	 * @param sourceList 源实体
	 * @param target     目标实体
	 * @param <T>
	 * @return
	 */
	public static <T> List<T> sourceToTarget(Collection<?> sourceList, Class<T> target) {
//		if (sourceList == null) {
//			return null;
//		}
//
//		List targetList = new ArrayList<>(sourceList.size());
//		try {
//			for (Object source : sourceList) {
//				T targetObject = target.newInstance();
//				BeanUtils.copyProperties(source, targetObject);
//				targetList.add(targetObject);
//			}
//		} catch (Exception e) {
//			logger.error("convert error ", e);
//		}
//
//		return targetList;
		return sourceToTarget(sourceList, target, false);
	}

	/**
	 * 多个实体复制 支持List T->D
	 *
	 * @param sourceList        源实体
	 * @param target            目标实体
	 * @param isSupportDeepCopy 是否支持List T->D
	 * @param <T>
	 * @return
	 */
	public static <T> List<T> sourceToTarget(Collection<?> sourceList, Class<T> target, boolean isSupportDeepCopy) {
		if (sourceList == null) {
			return null;
		}

		List targetList = new ArrayList<>(sourceList.size());
		if (isSupportDeepCopy) {
			for (Object source : sourceList) {
				T t = BeanMapper.map(source, target);
				targetList.add(t);
			}
		} else {
			try {
				for (Object source : sourceList) {
					T targetObject = target.newInstance();
					BeanUtils.copyProperties(source, targetObject);
					targetList.add(targetObject);
				}
			} catch (Exception e) {
				log.error("convert error ", e);
			}
		}


		return targetList;
	}
}
