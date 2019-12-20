package me.dwliu.lab.core.oss.autoconfigure;

import io.minio.MinioClient;
import lombok.SneakyThrows;
import me.dwliu.lab.core.oss.properties.OssConfigProperties;
import me.dwliu.lab.core.oss.minio.MinioOssTemplate;
import me.dwliu.lab.core.oss.rule.OssRule;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * minio 自动装配
 *
 * @author liudw
 * @date 2019-11-06 10:47
 **/
@Configuration
//@AllArgsConstructor
@AutoConfigureAfter(OssAutoConfiguration.class)
@Import(OssAutoConfiguration.class)
@ConditionalOnProperty(prefix = OssConfigProperties.OSS_CONFIG_PREFIX, value = "minio", matchIfMissing = true)
//@ConditionalOnProperty(prefix = OssConfigProperties.OSS_CONFIG_PREFIX
//        + OssConfigProperties.OSS_CONFIG_TYPE_MINIO,
//        value = "enabled", havingValue = "true")
public class MinioOssAutoConfiguration {

//    private OssConfigProperties properties;
//
//    private OssRule ossRule;

	@ConditionalOnMissingBean(MinioClient.class)
	@Bean
	@SneakyThrows
	public MinioClient minioClient(OssConfigProperties properties) {
		return new MinioClient(properties.getMinio().getEndpoint(),
			properties.getMinio().getAccessKey(),
			properties.getMinio().getSecretKey());
	}

	@ConditionalOnBean({MinioClient.class, OssRule.class})
	@ConditionalOnMissingBean(MinioOssTemplate.class)
	@Bean
	public MinioOssTemplate minioOssTemplate(OssConfigProperties properties, OssRule ossRule) {
		return new MinioOssTemplate(properties.getMinio().getEndpoint(),
			properties.getMinio().getBucketName(),
			minioClient(properties), ossRule);
	}


}
