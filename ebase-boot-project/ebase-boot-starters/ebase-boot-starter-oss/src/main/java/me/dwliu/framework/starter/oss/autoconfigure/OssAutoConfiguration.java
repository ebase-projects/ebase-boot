package me.dwliu.framework.starter.oss.autoconfigure;

import me.dwliu.framework.core.oss.rule.DefaultOssRule;
import me.dwliu.framework.core.oss.rule.OssRule;
import me.dwliu.framework.starter.oss.properties.OssConfigProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 存储自动配置类
 *
 * @author liudw
 * @date 2019-03-13 17:35
 **/
@Configuration
@EnableConfigurationProperties(OssConfigProperties.class)
//@ConditionalOnClass(StorageService.class)
//@ConditionalOnProperty(prefix = OssConfigProperties.OSS_CONFIG_PREFIX, value = "enabled", havingValue = "true")
public class OssAutoConfiguration {

    @ConditionalOnMissingBean(OssRule.class)
    @Bean
    public OssRule ossRule(OssConfigProperties properties) {
        return new DefaultOssRule(properties.getPrefix());
    }

//	/**
//	 * 装载 local
//	 *
//	 * @param properties
//	 * @return
//	 */
//	@Bean
//	@ConditionalOnProperty(prefix = OssConfigProperties.STORAGE_CONFIG_PREFIX
//		+ OssConfigProperties.STORAGE_CONFIG_TYPE_LOCAL,
//		value = "enabled", havingValue = "true")
//	//@ConditionalOnBean(LocalStorageService.class)
//	public StorageService localStorageService(OssConfigProperties properties) {
//		return new LocalStorageService(properties);
//	}
//
//	/**
//	 * 装载 fastDFS
//	 *
//	 * @param properties
//	 * @return
//	 */
//	@Bean
//	@ConditionalOnProperty(prefix = OssConfigProperties.STORAGE_CONFIG_PREFIX
//		+ OssConfigProperties.STORAGE_CONFIG_TYPE_FASTDFS,
//		value = "enabled", havingValue = "true")
//	public StorageService fastDfsStorageService(OssConfigProperties properties) {
//		return new FastDfsStorageService(properties);
//	}
//
//
//	@Bean
//	@ConditionalOnProperty(prefix = OssConfigProperties.STORAGE_CONFIG_PREFIX
//		+ OssConfigProperties.STORAGE_CONFIG_TYPE_QINIU,
//		value = "enabled", havingValue = "true")
//	public StorageService qiniuCloudStorageService(OssConfigProperties properties) {
//		QiniuCloudStorageService qiniuCloudStorageService = new QiniuCloudStorageService(properties);
//
//		return qiniuCloudStorageService;
//	}
}
