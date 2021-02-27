

### 开启日志：
1. application.yml 中配置日志输出
```yml
logging:
  level:
    me.dwliu.ebase.sample.feign.feign.K12UserFeign: debug
```

2. 配置类中(@Configuration)或者主程序入口配置日志bean

```java
@Bean
public Logger.Level feignLoggerLevel() {
    return Logger.Level.FULL;
}

```



### 引入okhttp

> [SpringBoot2.x直接整合Feign实现远程接口调用[尝鲜]](http://wuwenliang.net/2019/07/24/SpringBoot2.x直接整合Feign实现远程接口调用[尝鲜]/)

1. pom.xml 引入依赖

   ```xml
   <dependency>
     <groupId>org.springframework.cloud</groupId>
     <artifactId>spring-cloud-starter-openfeign</artifactId>
   </dependency>
   <dependency>
     <groupId>io.github.openfeign</groupId>
     <artifactId>feign-okhttp</artifactId>
     <version>10.8</version>
   </dependency>
   ```

   

2. 配置开关开启

   ```yml
   feign:
     httpclient:
       enabled: false
     okhttp:
       enabled: true
   ```

   

3. 配置类配置

   ```java
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
   }
   
   ```

   

### Spring Cloud OpenFeign支持POJO提作为GET参数映射

当我们在SpringCloud项目中引入spring-cloud-starter-openfeign时，如果我们用Feign发送Get请求时，采用POJO对象传递参数，那么会可能会出现异常。那么如果你又不想用@RequestParam一个个参数写在调用方法内，有什么好的解决方案吗？

这个方案是Spring Cloud OpenFeign官方提供的，我是在看[官方文档](https://cloud.spring.io/spring-cloud-static/spring-cloud-openfeign/2.1.3.RELEASE/single/spring-cloud-openfeign.html)看到的，于是在github上找查看了一下。

在github上有这样一个[Issue](https://github.com/spring-cloud/spring-cloud-openfeign/pull/79/files)——`Add support for feign's QueryMap annotation for Object mapping #79`,这个Issue已经是closed，看日期是解决是在2018-12-07号。方法也很简单。保持原来的不用改，不需要添加额外的依赖，加一个注解`@SpringQueryMap`就搞定。

> 注意，要用该注解，需要升级你的Spring Cloud OpenFeign到新的版本（`2.1.0.RC1`以及之后的版本）。

```java
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "hello", url = "http://localhost:8080")
public interface HelloFeignService {

	@GetMapping("/test")
	Hello hello(@SpringQueryMap Hello hello);
}
```

### Spring Cloud Feign 异常处理

- com.netflix.hystrix.exception.HystrixRuntimeException: SecurityUserFeign#currentUser(String) failed and no fallback available.

  对于`failed and no fallback available.`这种异常信息，是因为项目开启了熔断：

  ```
  feign.hystrix.enabled: true
  ```

   

  当调用服务时抛出了异常，却没有定义`fallback`方法，就会抛出上述异常。由此引出了第一个解决方式。
