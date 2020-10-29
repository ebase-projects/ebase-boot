package me.dwliu.framework.starter.datascope.autoconfigure;

import lombok.AllArgsConstructor;
import me.dwliu.framework.core.datascope.annotation.DataScopeFilter;
import me.dwliu.framework.plugin.datascope.interceptor.DataScopeFilterInterceptor;
import me.dwliu.framework.starter.datascope.properties.DataScopeProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 数据权限配置类
 *
 * @author liudw
 * @date 2020/10/29 18:02
 **/
@Configuration
@AllArgsConstructor
@EnableConfigurationProperties(DataScopeProperties.class)
public class DataScopeConfiguration {

    private JdbcTemplate jdbcTemplate;
    private DataScopeProperties dataScopeProperties;

    /**
     * 配置数据权限
     */
    @Bean
    @Order(100)
    @ConditionalOnClass(DataScopeFilter.class)
    public DataScopeFilterInterceptor dataScopeFilterInterceptor() {
        DataScopeFilterInterceptor interceptor = new DataScopeFilterInterceptor();
        interceptor.setJdbcTemplate(jdbcTemplate);
        interceptor.setDataScopeLevel(dataScopeProperties.getDataScopeLevel());
        return interceptor;
    }

}
