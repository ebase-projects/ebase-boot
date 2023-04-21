package me.dwliu.framework.autoconfigure.mybatis;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.support.AllEncompassingFormHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;

/**
 * 自定义通用配置
 *
 * @author liudw
 * @date 2019-08-22 14:33
 **/
@Configuration
public class CustomWebMvcConfig implements WebMvcConfigurer {


	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.add(new ByteArrayHttpMessageConverter());
		converters.add(new StringHttpMessageConverter());
		converters.add(new ResourceHttpMessageConverter());
		converters.add(new AllEncompassingFormHttpMessageConverter());
		converters.add(new StringHttpMessageConverter());

		//将我们定义的时间格式转换器添加到转换器列表中
		converters.add(jackson2HttpMessageConverter());
		//converters.add(fastJsonHttpMessageConverter());
	}

	/**
	 * jackson 配置
	 *
	 * @return
	 */
	@Bean
	public MappingJackson2HttpMessageConverter jackson2HttpMessageConverter() {
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		ObjectMapper mapper = new ObjectMapper();

		//日期格式转换
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm"));
		mapper.setTimeZone(TimeZone.getTimeZone("GMT+8"));

		//Long类型转String类型
		SimpleModule simpleModule = new SimpleModule();
		simpleModule.addSerializer(Long.class, com.fasterxml.jackson.databind.ser.std.ToStringSerializer.instance);
		simpleModule.addSerializer(Long.TYPE, com.fasterxml.jackson.databind.ser.std.ToStringSerializer.instance);
		mapper.registerModule(simpleModule);


		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		//驼峰转下划线
//        mapper.setPropertyNamingStrategy(com.fasterxml.jackson.databind.PropertyNamingStrategy.SNAKE_CASE);

		converter.setObjectMapper(mapper);
		return converter;


	}


}
