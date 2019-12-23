package me.dwliu.framework.starter.swagger2;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.swagger2.configuration.Swagger2DocumentationConfiguration;

/**
 * swagger 配置类
 *
 * @author liudw
 * @date 2019-07-03 10:58
 **/
@Configuration
@ConditionalOnProperty(name = SwaggerProperties.SWAGGER2_PROPERTIES_ENABLED, matchIfMissing = true)
@Import({
        Swagger2DocumentationConfiguration.class,
        BeanValidatorPluginsConfiguration.class
})
public class SwaggerConfiguration {
}
