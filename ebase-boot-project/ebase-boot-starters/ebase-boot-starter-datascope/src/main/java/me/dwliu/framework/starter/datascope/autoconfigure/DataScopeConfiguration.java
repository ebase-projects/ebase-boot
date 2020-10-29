package me.dwliu.framework.starter.datascope.autoconfigure;

import lombok.AllArgsConstructor;
import me.dwliu.framework.plugin.datascope.aspect.DataScopeFilterAspect;
import me.dwliu.framework.plugin.datascope.interceptor.DataScopeFilterInterceptor;
import me.dwliu.framework.starter.datascope.properties.DataScopeProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * 数据权限配置类
 *
 * @author Chill
 */
@Configuration
@AllArgsConstructor
@EnableConfigurationProperties(DataScopeProperties.class)
public class DataScopeConfiguration {

//	private JdbcTemplate jdbcTemplate;
//
//	@Bean
//	@ConditionalOnMissingBean(ScopeModelHandler.class)
//	public ScopeModelHandler scopeModelHandler() {
//		return new BladeScopeModelHandler(jdbcTemplate);
//	}
//
//	@Bean
//	@ConditionalOnBean(ScopeModelHandler.class)
//	@ConditionalOnMissingBean(DataScopeHandler.class)
//	public DataScopeHandler dataScopeHandler(ScopeModelHandler scopeModelHandler) {
//		return new BladeDataScopeHandler(scopeModelHandler);
//	}
//
//	@Bean
//	@ConditionalOnBean(DataScopeHandler.class)
//	@ConditionalOnMissingBean(DataScopeInterceptor.class)
//	public DataScopeInterceptor interceptor(DataScopeHandler dataScopeHandler, DataScopeProperties dataScopeProperties) {
//		return new DataScopeInterceptor(dataScopeHandler, dataScopeProperties);
//	}


    /**
     * 配切面
     */
    @Bean
    public DataScopeFilterAspect dataFilterAspect() {
        return new DataScopeFilterAspect();
    }

    /**
     * 配置数据权限
     */
    @Bean
    @Order(100)
    public DataScopeFilterInterceptor dataScopeFilterInterceptor() {
        return new DataScopeFilterInterceptor();
    }

}
