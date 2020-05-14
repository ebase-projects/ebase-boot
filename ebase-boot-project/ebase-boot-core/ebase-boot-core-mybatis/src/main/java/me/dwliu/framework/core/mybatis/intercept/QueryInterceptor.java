package me.dwliu.framework.core.mybatis.intercept;

import org.apache.ibatis.plugin.Invocation;
import org.springframework.core.Ordered;

/**
 * 自定义 mybatis plus 查询拦截器
 *
 * @author L.cm
 */
public interface QueryInterceptor extends Ordered {

	Object intercept(Invocation invocation) throws Throwable;

	@Override
	default int getOrder() {
		return Ordered.LOWEST_PRECEDENCE;
	}
}
