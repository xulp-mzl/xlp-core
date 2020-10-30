package org.xlp.javabean.processer;

import java.util.HashMap;
import java.util.Map;

/**
 * 值处理器（把对应的值赋值给指定的类型的字段）
 * 
 * @author 徐龙平
 *         <p>
 *         2017-5-22
 *         </p>
 * @version 2.0
 * 
 */
public abstract class ValueProcesser {
	/**
	 * 基本类型默认值
	 */
	public static final Map<Class<?>, Object> PRIMITIVE_DEFAULTS = new HashMap<Class<?>, Object>();

	static {
		PRIMITIVE_DEFAULTS.put(Integer.TYPE, Integer.valueOf(0));
		PRIMITIVE_DEFAULTS.put(Short.TYPE, Short.valueOf((short) 0));
		PRIMITIVE_DEFAULTS.put(Byte.TYPE, Byte.valueOf((byte) 0));
		PRIMITIVE_DEFAULTS.put(Float.TYPE, Float.valueOf(0f));
		PRIMITIVE_DEFAULTS.put(Double.TYPE, Double.valueOf(0d));
		PRIMITIVE_DEFAULTS.put(Long.TYPE, Long.valueOf(0L));
		PRIMITIVE_DEFAULTS.put(Boolean.TYPE, Boolean.FALSE);
		PRIMITIVE_DEFAULTS.put(Character.TYPE, Character.valueOf((char) 0));
	}

	/**
	 * 判断一个对象是否是数字类型或其对应的包装类型
	 * 
	 * @param value
	 * @return
	 */
	public static final boolean isNumberClass(Object value){
		boolean isNumber = false;
		if (value != null) {
			Class<?> type = value.getClass();
			if (type.equals(Byte.TYPE) || type.equals(Byte.class)) {
				isNumber = true;
			}else if (type.equals(Integer.TYPE) || type.equals(Integer.class)) {
				isNumber = true;
			}else if (type.equals(Short.TYPE) || type.equals(Short.class)) {
				isNumber = true;
			}else if (type.equals(Long.TYPE) || type.equals(Long.class)) {
				isNumber = true;
			}else if (type.equals(Double.TYPE) || type.equals(Double.class)) {
				isNumber = true;
			}else if (type.equals(Float.TYPE) || type.equals(Float.class)) {
				isNumber = true;
			}
		}
		return isNumber;
	}
	
	/**
	 * @param fieldType
	 *            字段类型
	 * @param value
	 *            给定的值
	 * @return 返回处理后的值
	 */
	public abstract Object processValue(Class<?> fieldType, Object value);
	
}
