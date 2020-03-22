package me.dwliu.ebase.sample.feign.config;

import feign.Feign;
import feign.Logger;
import okhttp3.ConnectionPool;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
@ConditionalOnClass(Feign.class)
@AutoConfigureBefore(FeignAutoConfiguration.class)
public class FeignConfig {

	@Bean
	public okhttp3.OkHttpClient okHttpClient() {
		return new okhttp3.OkHttpClient.Builder()
			.readTimeout(60, TimeUnit.SECONDS)
			.connectTimeout(60, TimeUnit.SECONDS)
			.writeTimeout(120, TimeUnit.SECONDS)
			.connectionPool(new ConnectionPool())
			.build();
	}


	@Bean
	public Logger.Level feignLoggerLevel() {
		return Logger.Level.FULL;
	}
}
