package org.xlp.javabean.convert.mapandbean.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用来标记map与javabean之间转换时，map中key的值
 * <p>作用是javabean中字段以别名存储在map的key中
 * 
 * @author 徐龙平
 *         <p>
 *         2017-1-14
 *         </p>
 * @version 1.0
 * 
 */
@Retention(RetentionPolicy.RUNTIME) // 注解会在class字节码文件中存在，在运行时可以通过反射获取到  
@Target({ElementType.FIELD})//定义注解的作用目标**作用范围字段 
@Documented//说明该注解将被包含在javadoc中 
public @interface MapKey {
	public String name() default "";
	public String description() default "";
}
