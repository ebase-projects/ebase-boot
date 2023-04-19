package me.dwliu.framework.core.mybatis.query;

import cn.hutool.core.date.DateUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.Map;

/**
 * 参数查询工具类
 *
 * @author liudw
 * @date 2023/4/14 17:22
 **/
public class QueryParamsUtil {

	/**
	 * 参数模糊查询
	 *
	 * @param params 参数列表
	 * @param likes  需要模糊查询的字段
	 * @return Map<String, Object>
	 */
	public static Map<String, Object> paramsToLike(Map<String, Object> params, String... likes) {
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

	/**
	 * 参数封装开始结束时间
	 *
	 * @param params       参数
	 * @param beginTimeKey 开始时间key eg:beginTime
	 * @param endTimeKey   结束时间key eg:endTime
	 * @return
	 */
	public static Map<String, Object> paramsToBeginAndEndDate(Map<String, Object> params,
															  String beginTimeKey, String endTimeKey) {
		String beginTime = (String) params.get(beginTimeKey);
		String endTime = (String) params.get(endTimeKey);
		if (StringUtils.isNotBlank(beginTime) && StringUtils.isNotBlank(endTime)) {
			Date beginTime_ = DateUtil.beginOfDay(DateUtil.parse(beginTime));
			Date endTime_ = DateUtil.endOfDay(DateUtil.parseDate(endTime));
			params.put(beginTimeKey, beginTime_);
			params.put(endTimeKey, endTime_);

		}
		return params;
	}

	/**
	 * 参数封装开始结束时间
	 * <p>
	 * 默认key是  beginTime 和 endTime
	 * </p>
	 *
	 * @param params 参数
	 * @return
	 */
	public static Map<String, Object> paramsToBeginAndEndDate(Map<String, Object> params) {
		return paramsToBeginAndEndDate(params, "beginTime", "endTime");
	}

}
