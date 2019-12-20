package me.dwliu.lab.core.log.annotation;

import java.lang.annotation.*;

/**
 * 日志操作注解
 *
 * @author liudw
 * @date 2019-02-24 17:28
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogAction {

	/**
	 * 日志描述
	 *
	 * @return ""
	 */
	String value() default "操作日志";
}
