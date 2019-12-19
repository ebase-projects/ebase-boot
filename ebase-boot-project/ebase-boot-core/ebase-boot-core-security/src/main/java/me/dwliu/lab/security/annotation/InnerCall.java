package me.dwliu.lab.security.annotation;

import java.lang.annotation.*;

/**
 * 服务调用鉴权注解
 *
 * @author liudw
 * @date 2019-08-14 11:05
 **/
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface InnerCall {
    /**
     * AOP 是否切面拦截处理
     *
     * @return true or false
     */
    boolean value() default true;

    /**
     * 需要特殊判空的字段（预留）
     *
     * @return {}
     */
    String[] field() default {};

}




