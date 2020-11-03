package me.dwliu.framework.starter.mybatis.autoconfigure;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import me.dwliu.framework.core.mybatis.interceptor.QueryInterceptor;
import me.dwliu.framework.plugin.mybatis.handler.FieldMetaObjectHandler;
import me.dwliu.framework.plugin.mybatis.plugin.CustomPaginationInterceptor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;


/**
 * mybatis-plus配置
 *
 * @author liudw
 * @date 2019-04-12 18:05
 **/
@Configuration
public class CustomMybatisPlusConfig {

    /**
     * 新MybatisPlus插件配置
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor(ObjectProvider<QueryInterceptor[]> queryInterceptors) {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();

        // 配置分页拦截器
        CustomPaginationInterceptor paginationInterceptor = new CustomPaginationInterceptor();
        // 配置自定义查询拦截器
        QueryInterceptor[] queryInterceptorArray = queryInterceptors.getIfAvailable();
        if (ObjectUtil.isNotEmpty(queryInterceptorArray)) {
            AnnotationAwareOrderComparator.sort(queryInterceptorArray);
            paginationInterceptor.setQueryInterceptors(queryInterceptorArray);
        }

        //新分页
        interceptor.addInnerInterceptor(paginationInterceptor);
        return interceptor;

    }

    /**
     * 一缓和二缓遵循mybatis的规则,
     * 需要设置 MybatisConfiguration#useDeprecatedExecutor = false 避免缓存万一出现问题
     *
     * @return
     */
    @Bean
    public ConfigurationCustomizer configurationCustomizer() {
        return configuration -> configuration.setUseDeprecatedExecutor(false);
    }


    /**
     * 配置公共字段自动填充值
     *
     * @return
     */
    @Bean
    public MetaObjectHandler fieldMetaObjectHandler() {
        return new FieldMetaObjectHandler();
    }


}
