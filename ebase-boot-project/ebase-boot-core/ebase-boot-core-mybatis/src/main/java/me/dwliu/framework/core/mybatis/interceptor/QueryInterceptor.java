package me.dwliu.framework.core.mybatis.interceptor;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.core.Ordered;

/**
 * 自定义 mybatis plus 查询拦截器
 *
 * @author liudw
 * @date 2020/11/3 12:39
 **/
public interface QueryInterceptor extends Ordered {

    /**
     * 拦截处理
     *
     * @param executor
     * @param mappedStatement
     * @param parameter
     * @param rowBounds
     * @param resultHandler
     * @param boundSql
     */
    void intercept(Executor executor, MappedStatement mappedStatement, Object parameter,
                   RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql);

    /**
     * 排序
     *
     * @return int 排序，默认最低优先级
     */
    @Override
    default int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}
