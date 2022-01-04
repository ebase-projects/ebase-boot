package me.dwliu.framework.autoconfigure.swagger;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.documentation.swagger.web.UiConfiguration;

/**
 * @author 翟永超
 * Create date：2017/8/7.
 * My blog： http://blog.didispace.com
 */
@Configuration
@EnableConfigurationProperties(SwaggerProperties.class)
@ConditionalOnClass({UiConfiguration.class})
@ConditionalOnProperty(value = "springfox.documentation.enabled", havingValue = "true", matchIfMissing = true)
@Import({SwaggerUiConfiguration.class, SwaggerAuthorizationConfiguration.class, DocketConfiguration.class})
public class SwaggerAutoConfiguration {

	@Bean
	public DocketBeanFactoryPostProcessor docketBeanFactoryPostProcessor() {
		return new DocketBeanFactoryPostProcessor();
	}

}
