package org.xlp.javabean;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 字段描述
 * 
 * @author 徐龙平
 *         <p>
 *         2017-5-22
 *         </p>
 * @version 2.0
 * 
 */
public class FieldDescriptor {
	//字段
	private Field field;

	public FieldDescriptor(Field field){
		this.field = field;
	}
	
	/**
	 * 返回字段名
	 * 
	 * @return
	 */
	public String getFieldName(){
		return field == null ? null : field.getName();
	}

	/**
	 * 返回字段对象
	 * 
	 * @return
	 */
	public Field getField() {
		return field;
	}

	/**
	 * 得到该字段上的指定注解对象
	 * 
	 * @param annotationClass
	 * @return 假如参数为null，返回null，否则返回注解对象
	 */
	public <T extends Annotation> T getFieldAnnotation(Class<T> annotationClass){
		return field == null ? null : field.getAnnotation(annotationClass);
	}
	
	/**
	 * 返回一个 Class 对象，它标识了此 Field 对象所表示字段的声明类型。 
	 * 
	 * @return
	 */
	public Class<?> getFiledClassType(){
		return field == null ? null : field.getType();
	}
	
	/**
	 * 获取字段类型泛型信息
	 * @return 字段类型泛型信息，假如没有泛型信息，则返回空数组
	 */
	public Type[] getActualTypes(){
		Type[] types = new Type[0];
		if (field != null) {
			// 获取字段类型泛型信息
			Type type = field.getGenericType();
			if (type instanceof ParameterizedType) {
				types = ((ParameterizedType)type).getActualTypeArguments();
			}
		}
		return types;
	}
}
