package me.dwliu.lab.core.oss.qiniu;

import com.qiniu.common.Zone;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import lombok.AllArgsConstructor;
import me.dwliu.lab.core.oss.OssAutoConfiguration;
import me.dwliu.lab.core.oss.OssConfigProperties;
import me.dwliu.lab.core.oss.rule.OssRule;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * 七牛存储配置
 *
 * @author liudw
 * @date 2019-11-07 15:48
 **/
@Configuration
@AllArgsConstructor
@AutoConfigureAfter(OssAutoConfiguration.class)
@Import(OssAutoConfiguration.class)
@ConditionalOnProperty(prefix = OssConfigProperties.OSS_CONFIG_PREFIX, value = "qiniu", matchIfMissing = true)
//@ConditionalOnProperty(prefix = OssConfigProperties.OSS_CONFIG_PREFIX +
//        OssConfigProperties.OSS_CONFIG_TYPE_QINIU,
//        value = "enabled", havingValue = "true")
public class QiniuOssAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(com.qiniu.storage.Configuration.class)
    public com.qiniu.storage.Configuration qiniuCfg() {
        //构造一个带指定Region对象的配置类
        return new com.qiniu.storage.Configuration(Zone.autoZone());
    }

    @Bean
    @ConditionalOnMissingBean(Auth.class)
    public Auth auth(OssConfigProperties properties) {
        return Auth.create(properties.getQiniu().getAccessKey(), properties.getQiniu().getSecretKey());
    }

    @Bean
    @ConditionalOnBean(com.qiniu.storage.Configuration.class)
    public UploadManager uploadManager() {
        return new UploadManager(qiniuCfg());
    }

    @Bean
    @ConditionalOnBean(com.qiniu.storage.Configuration.class)
    public BucketManager bucketManager(OssConfigProperties properties) {
        return new BucketManager(auth(properties), qiniuCfg());
    }

    @Bean
    @ConditionalOnMissingBean(QiniuOssTemplate.class)
    @ConditionalOnBean({Auth.class, UploadManager.class, BucketManager.class, OssRule.class})
    public QiniuOssTemplate qiniuOssTemplate(OssConfigProperties properties, OssRule ossRule) {
        return new QiniuOssTemplate(auth(properties), bucketManager(properties), uploadManager(), ossRule, properties);
    }


}
