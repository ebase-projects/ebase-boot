package me.dwliu.ebase.sample.es.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ElasticSearchClientConfig {

	@Bean
	// 硬编码的值可以设置到配置文件，通过@Value读取
	public RestHighLevelClient restHighLevelClient() {
		return new RestHighLevelClient(RestClient.builder(new HttpHost("192.168.1.15", 29200, "http")));
		// return new RestHighLevelClient(RestClient.builder(new HttpHost("scjg008", 29200, "http")));
	}
}
