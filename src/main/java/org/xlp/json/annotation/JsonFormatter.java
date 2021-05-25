package org.xlp.json.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>创建时间：2021年5月21日 下午11:06:33</p>
 * @author xlp
 * @version 1.0 
 * @Description json数据格式化 
*/
@Retention(RetentionPolicy.RUNTIME) // 注解会在class字节码文件中存在，在运行时可以通过反射获取到  
@Target({ElementType.TYPE})//定义注解的作用目标**作用范围字段 
@Documented//说明该注解将被包含在javadoc中 
public @interface JsonFormatter {
	/**
	 * json格式化模式
	 * 
	 * @return
	 */
	public String formatter();
}
