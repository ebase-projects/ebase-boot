package me.dwliu.framework.starter.mybatis.autoconfigure;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import me.dwliu.framework.core.mybatis.handler.FieldMetaObjectHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

/**
 * mybatis-plus配置
 *
 * @author liudw
 * @date 2019-04-12 18:05
 **/
@Configuration
public class CustomMybatisPlusConfig {

    /**
     * 配置分页
     */
    @Bean
    @Order(value = Ordered.HIGHEST_PRECEDENCE)
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

    /**
     * 乐观锁配置
     */
    @Bean
    public OptimisticLockerInterceptor optimisticLockerInterceptor() {
        return new OptimisticLockerInterceptor();
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
