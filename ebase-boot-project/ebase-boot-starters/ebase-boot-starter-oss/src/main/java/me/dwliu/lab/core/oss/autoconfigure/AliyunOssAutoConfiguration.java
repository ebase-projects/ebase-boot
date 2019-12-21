package me.dwliu.lab.core.oss.autoconfigure;

import com.aliyun.oss.ClientBuilderConfiguration;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.auth.CredentialsProvider;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;
import me.dwliu.lab.core.oss.aliyun.AliyunOssTemplate;
import me.dwliu.lab.core.oss.properties.OssConfigProperties;
import me.dwliu.lab.core.oss.rule.OssRule;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * aliyun 存储
 *
 * @author liudw
 * @date 2019-11-08 21:30
 **/
@Configuration
@AutoConfigureAfter(OssAutoConfiguration.class)
//@ConditionalOnProperty(prefix = OssConfigProperties.OSS_CONFIG_PREFIX, value = "aliyun", matchIfMissing = false)
@Import(OssAutoConfiguration.class)
@ConditionalOnProperty(prefix = OssConfigProperties.OSS_CONFIG_PREFIX
	+ OssConfigProperties.OSS_CONFIG_TYPE_ALIYUN,
	value = "enabled", havingValue = "true")
public class AliyunOssAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean(OSS.class)
	public OSS ossClient(OssConfigProperties properties) {

		// 创建ClientConfiguration。ClientConfiguration是OSSClient的配置类，可配置代理、连接超时、最大连接数等参数。
		ClientBuilderConfiguration conf = new ClientBuilderConfiguration();

		// 设置OSSClient允许打开的最大HTTP连接数，默认为1024个。
		conf.setMaxConnections(1024);
		// 设置Socket层传输数据的超时时间，默认为50000毫秒。
		conf.setSocketTimeout(10000);
		// 设置建立连接的超时时间，默认为50000毫秒。
		conf.setConnectionTimeout(50000);
		// 设置从连接池中获取连接的超时时间（单位：毫秒），默认不超时。
		conf.setConnectionRequestTimeout(50000);
		// 设置连接空闲超时时间。超时则关闭连接，默认为60000毫秒。
		conf.setIdleConnectionTime(10000);
		// 设置失败请求重试次数，默认为3次。
		conf.setMaxErrorRetry(5);
		// 设置是否支持将自定义域名作为Endpoint，默认支持。
		//conf.setSupportCname(true);
		// 设置是否开启二级域名的访问方式，默认不开启。
		//conf.setSLDEnabled(true);
		// 设置连接OSS所使用的协议（HTTP/HTTPS），默认为HTTP。
		//conf.setProtocol(Protocol.HTTP);
		// 设置用户代理，指HTTP的User-Agent头，默认为aliyun-sdk-java。
		//conf.setUserAgent("aliyun-sdk-java");
		// 设置代理服务器端口。
		//conf.setProxyHost("<yourProxyHost>");
		// 设置代理服务器验证的用户名。
		//conf.setProxyUsername("<yourProxyUserName>");
		// 设置代理服务器验证的密码。
		//conf.setProxyPassword("<yourProxyPassword>");

		CredentialsProvider credentialsProvider =
			new DefaultCredentialProvider(properties.getAliyun().getAccessKey(),
				properties.getAliyun().getSecretKey());

		// 创建OSSClient实例
		OSS ossClient = new OSSClientBuilder()
			.build(properties.getAliyun().getEndpoint(), credentialsProvider, conf);

		return ossClient;

	}

	@Bean
	@ConditionalOnMissingBean(AliyunOssTemplate.class)
	@ConditionalOnBean({OSS.class, OssRule.class})
	public AliyunOssTemplate aliyunOssTemplate(OSS ossClient, OssConfigProperties properties, OssRule ossRule) {
		return new AliyunOssTemplate(properties.getAliyun().getEndpoint(),
			properties.getAliyun().getBucketName(), ossClient, ossRule);
	}
}
