package me.dwliu.framework.autoconfigure.datascope;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.dwliu.framework.autoconfigure.mybatis.CustomMybatisPlusConfig;
import me.dwliu.framework.core.datascope.annotation.DataScopeFilter;
import me.dwliu.framework.integration.mybatis.interceptor.DataScopeFilterInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 数据权限配置类
 *
 * @author liudw
 * @date 2020/10/29 18:02
 **/
@Configuration
@AllArgsConstructor
@ConditionalOnClass({JdbcTemplate.class, DataScopeFilterInterceptor.class})
@EnableConfigurationProperties(DataScopeProperties.class)
@Import(CustomMybatisPlusConfig.class)
@Slf4j
public class DataScopeConfiguration {

	private JdbcTemplate jdbcTemplate;
	private DataScopeProperties dataScopeProperties;

	/**
	 * 配置数据权限
	 */
	@Bean
	@ConditionalOnClass(DataScopeFilter.class)
	@ConditionalOnMissingBean(DataScopeFilterInterceptor.class)
	public DataScopeFilterInterceptor dataScopeFilterInterceptor() {
		log.debug("===配置数据权限 dataScopeFilterInterceptor ===");
		DataScopeFilterInterceptor interceptor = new DataScopeFilterInterceptor();
		interceptor.setJdbcTemplate(jdbcTemplate);
		interceptor.setDataScopeLevel(dataScopeProperties.getDataScopeLevel());
		return interceptor;
	}

}
