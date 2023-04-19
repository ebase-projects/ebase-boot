package me.dwliu.framework.core.mybatis.query;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import me.dwliu.framework.common.model.PageData;
import me.dwliu.framework.core.mybatis.constant.Constant;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * 分页工具
 * <p>
 * 针对特殊场景，比如自定义VO 多表查询的分页
 * <br>
 * IPage<VO> page = new QueryPageUtil<VO>().getPage(params, "id", false);
 * </p>
 *
 * @author liudw
 * @date 2019-12-27 15:41
 **/
public class QueryPageUtil<T> {

	/**
	 * 获取分页对象
	 *
	 * @param params            分页查询参数
	 * @param defaultOrderField 默认排序字段
	 * @param isAsc             排序方式
	 */
	public IPage<T> getPage(Map<String, Object> params, String defaultOrderField, Boolean isAsc) {
		//分页参数
		long curPage = 1;
		long limit = 10;

		if (params.get(Constant.PAGE) != null) {
			curPage = Long.parseLong((String) params.get(Constant.PAGE));
		}
		if (params.get(Constant.LIMIT) != null) {
			limit = Long.parseLong((String) params.get(Constant.LIMIT));
		}

		//分页对象
		Page<T> page = new Page<>(curPage, limit);

		//分页参数
		params.put(Constant.PAGE, page);

		//排序字段
		String orderField = (String) params.get(Constant.ORDER_FIELD);

		String order = (String) params.get(Constant.ORDER);

		//前端字段排序
		if (StringUtils.isNotEmpty(orderField)) {
			//转为下划线
//			String orderFieldLowerCamel = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, orderField);
			String orderFieldLowerCamel = StrUtil.toUnderlineCase(orderField);
			if (StringUtils.isNotEmpty(order) && Constant.ASC.equalsIgnoreCase(order)) {
				page.addOrder(OrderItem.asc(orderFieldLowerCamel));
			} else if (StringUtils.isNotEmpty(order) && Constant.DESC.equalsIgnoreCase(order)) {
				page.addOrder(OrderItem.desc(orderFieldLowerCamel));
			} else {
				//默认升序
				page.addOrder(OrderItem.asc(orderFieldLowerCamel));
			}
		} else {
			//没有排序字段，则不排序
			if (StringUtils.isEmpty(defaultOrderField)) {
				return page;
			}
			//默认排序
			if (isAsc) {
				//page.setAsc(defaultOrderField);
				page.addOrder(OrderItem.asc(defaultOrderField));
			} else {
				//page.setDesc(defaultOrderField);
				page.addOrder(OrderItem.desc(defaultOrderField));
			}

		}


		return page;
	}




}
