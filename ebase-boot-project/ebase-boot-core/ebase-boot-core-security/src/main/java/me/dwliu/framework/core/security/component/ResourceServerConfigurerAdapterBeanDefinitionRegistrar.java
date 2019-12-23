package me.dwliu.framework.core.security.component;

import lombok.extern.slf4j.Slf4j;
import me.dwliu.framework.core.security.constant.SecurityCoreConstant;
import me.dwliu.framework.core.security.annotation.EnableBaseResourceServer;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * 根据注解值动态注入资源服务器的相关属性
 * <p>
 * 加载入口{@link EnableBaseResourceServer}
 *
 * @author liudw
 * @date 2019-08-14 16:44
 **/
@Slf4j
public class ResourceServerConfigurerAdapterBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        if (registry.isBeanNameInUse(SecurityCoreConstant.RESOURCE_SERVER_CONFIGURER)) {
            log.warn("已经存在资源服务器，覆盖默认资源配置:{}", SecurityCoreConstant.RESOURCE_SERVER_CONFIGURER);
            return;
        }

        log.debug("加载默认资源服务器:{}", SecurityCoreConstant.RESOURCE_SERVER_CONFIGURER);
        GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
        beanDefinition.setBeanClass(DefaultResourceServerConfig.class);
        registry.registerBeanDefinition(SecurityCoreConstant.RESOURCE_SERVER_CONFIGURER, beanDefinition);


    }
}
