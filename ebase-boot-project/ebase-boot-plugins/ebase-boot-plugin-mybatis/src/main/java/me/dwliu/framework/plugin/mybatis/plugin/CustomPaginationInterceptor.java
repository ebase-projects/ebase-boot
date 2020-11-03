package me.dwliu.framework.plugin.mybatis.plugin;

import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import lombok.Setter;
import lombok.SneakyThrows;
import me.dwliu.framework.core.mybatis.interceptor.QueryInterceptor;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

/**
 * 拓展分页拦截器
 *
 * @author Chill
 */
@Setter
public class CustomPaginationInterceptor extends PaginationInnerInterceptor {

    /**
     * 查询拦截器
     */
    private QueryInterceptor[] queryInterceptors;

    @SneakyThrows
    @Override
    public boolean willDoQuery(Executor executor, MappedStatement ms,
                               Object parameter, RowBounds rowBounds,
                               ResultHandler resultHandler, BoundSql boundSql) {
        QueryInterceptorExecutor.exec(queryInterceptors, executor, ms,
                parameter, rowBounds, resultHandler, boundSql);

        return super.willDoQuery(executor, ms, parameter,
                rowBounds, resultHandler, boundSql);
    }

}
