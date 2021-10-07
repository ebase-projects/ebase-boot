package me.dwliu.framework.integration.mybatis.plugin;

import cn.hutool.core.util.ObjectUtil;
import me.dwliu.framework.core.mybatis.interceptor.QueryInterceptor;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;


/**
 * 查询拦截器执行器
 *
 * <p>
 * 目的：抽取此方法是为了后期方便同步更新
 * </p>
 *
 * @author L.cm
 */
public class QueryInterceptorExecutor {

	/**
	 * 执行查询拦截器
	 */
	static void exec(QueryInterceptor[] interceptors, Executor executor, MappedStatement ms,
	                 Object parameter, RowBounds rowBounds, ResultHandler
		                 resultHandler, BoundSql boundSql) throws Throwable {
		if (ObjectUtil.isEmpty(interceptors)) {
			return;
		}
		for (QueryInterceptor interceptor : interceptors) {
			interceptor.intercept(executor, ms, parameter, rowBounds, resultHandler, boundSql);
		}
	}

}
