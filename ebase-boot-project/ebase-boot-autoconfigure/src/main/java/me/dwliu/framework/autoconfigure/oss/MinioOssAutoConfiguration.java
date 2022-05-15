package me.dwliu.framework.autoconfigure.oss;

import io.minio.MinioClient;
import lombok.SneakyThrows;
import me.dwliu.framework.core.oss.rule.OssRule;
import me.dwliu.framework.integration.oss.minio.MinioOssTemplate;
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
@ConditionalOnProperty(prefix = OssConfigProperties.OSS_CONFIG_PREFIX + OssConfigProperties.OSS_CONFIG_TYPE_MINIO,
	value = "enabled", havingValue = "true")
public class MinioOssAutoConfiguration {

//    private OssConfigProperties properties;
//
//    private OssRule ossRule;

	@ConditionalOnMissingBean(MinioClient.class)
	@Bean
	@SneakyThrows
	public MinioClient minioClient(OssConfigProperties properties) {
		//return new MinioClient(properties.getMinio().getEndpoint(),
		//	properties.getMinio().getAccessKey(),
		//	properties.getMinio().getSecretKey());

		MinioClient minioClient = MinioClient.builder()
			.endpoint(properties.getMinio().getEndpoint())
			.credentials(properties.getMinio().getAccessKey(), properties.getMinio().getSecretKey())
			.build();
		return minioClient;
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
