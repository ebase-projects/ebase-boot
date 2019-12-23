package me.dwliu.framework.starter.swagger2;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 使能Swagger2
 *
 * @author liudw
 * @date 2019-08-18 11:22
 **/
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({SwaggerAutoConfiguration.class, SwaggerWebConfiguration.class})
public @interface EnableSwagger2Doc {


}
