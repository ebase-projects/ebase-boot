

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

   

