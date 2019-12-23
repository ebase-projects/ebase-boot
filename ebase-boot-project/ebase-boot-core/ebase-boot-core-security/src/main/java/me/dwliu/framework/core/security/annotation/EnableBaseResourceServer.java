package me.dwliu.framework.core.security.annotation;

import me.dwliu.framework.core.security.component.ResourceServerConfigurerAdapterBeanDefinitionRegistrar;
import me.dwliu.framework.core.security.component.ResourceServerAutoConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

import java.lang.annotation.*;

/**
 * 增强资源服务器注解
 * <ul>
 *     <li>加入原有的资源服务器注解</li>
 *     <li>加入{@link EnableGlobalMethodSecurity}注解，来判断用户对某个控制层的方法是否具有访问权限</li>
 *     <li>加入 {@link Inherited} 继承</li>
 *     <li> {@link ResourceServerConfigurerAdapterBeanDefinitionRegistrar} 配置默认的资源服务器</li>
 * </ul>
 *
 * @author liudw
 * @date 2019-08-14 11:19
 **/
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Import({ResourceServerAutoConfiguration.class, ResourceServerConfigurerAdapterBeanDefinitionRegistrar.class})
public @interface EnableBaseResourceServer {
}
