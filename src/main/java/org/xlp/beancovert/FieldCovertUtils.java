package org.xlp.beancovert;

import org.xlp.javabean.PropertyDescriptor;

/**
 * <p>创建时间：2022年12月4日 上午12:09:10</p>
 * @author xlp
 * @version 1.0 
 * @Description bean字段转换工具类
*/
class FieldCovertUtils {
	/**
	 * bean字段转换
	 * 
	 * @param sourceValue
	 * @param tp
	 * @param sp
	 * @return 转换后的字段类型值
	 */
	public static <T, S> Object covert(Object sourceValue, PropertyDescriptor<T> tp, PropertyDescriptor<S> sp){
		Class<?> sc = sp.getFiledClassType();
		Class<?> tc = tp.getFiledClassType();
		
		if (sourceValue == null) return sourceValue;
		
		if (String.class.equals(tc) && !String.class.equals(sc)) {
			return String.valueOf(sourceValue);
		}
		
		if (String.class.equals(sc)) {
			if (Integer.TYPE.equals(tc) || Integer.class.equals(tc)) {
				return Integer.parseInt((String)sourceValue);
			}
			if (Double.TYPE.equals(tc) || Double.class.equals(tc)) {
				return Double.parseDouble((String)sourceValue);
			}
			if (Float.TYPE.equals(tc) || Float.class.equals(tc)) {
				return Float.parseFloat((String)sourceValue);
			}
			if (Long.TYPE.equals(tc) || Long.class.equals(tc)) {
				return Long.parseLong((String)sourceValue);
			}
			if (Short.TYPE.equals(tc) || Short.class.equals(tc)) {
				return Short.parseShort((String)sourceValue);
			}
			if (Byte.TYPE.equals(tc) || Byte.class.equals(tc)) {
				return Byte.parseByte((String)sourceValue);
			}
			if (Boolean.TYPE.equals(tc) || Boolean.class.equals(tc)) {
				return Boolean.parseBoolean((String)sourceValue);
			}
			if (Character.TYPE.equals(tc) || Character.class.equals(tc)) {
				String s = (String)sourceValue;
				if(!s.isEmpty()){
					return s.charAt(0);
				}
			}
		}
		return sourceValue;
	}
}
