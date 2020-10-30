package org.xlp.utils;

/**
 * 工具类: 主要功能是判断是否是基本类型的包装类型，把字符串数组转换成指定类型的数组，针对于基本类型的包装类型
 * 
 * @author 徐龙平
 *         <p>
 *         2017-5-19
 *         </p>
 * @version 1.0
 * 
 */
public class XLPPackingTypeUtil {
	/**
	 * 把字符串数组转换成指定类型的数组针对于基本类型的包装类型
	 * 
	 * @param values
	 * @param type
	 * @return 如果type类型为包装类型，则把string数组转换成对应的包装类型数组，否则不转换
	 */
	@SuppressWarnings("unchecked")
	public static <T> T[] convert(String[] values, Class<T> type) {
		Object object = null;
		if (values != null) {
			int len = values.length;
			if (Integer.class.equals(type)) {
				Integer[] vs = new Integer[len];
				for (int i = 0; i < len; i++) {
					vs[i] = Integer.valueOf(values[i]);
				}
				object = vs;
			} else if (Short.class.equals(type)) {
				Short[] vs = new Short[len];
				for (int i = 0; i < len; i++) {
					vs[i] = Short.valueOf(values[i]);
				}
				object = vs;
			} else if (Long.class.equals(type)) {
				Long[] vs = new Long[len];
				for (int i = 0; i < len; i++) {
					vs[i] = Long.valueOf(values[i]);
				}
				object = vs;
			} else if (Byte.class.equals(type)) {
				Byte[] vs = new Byte[len];
				for (int i = 0; i < len; i++) {
					vs[i] = Byte.valueOf(values[i]);
				}
				object = vs;
			} else if (Double.class.equals(type)) {
				Double[] vs = new Double[len];
				for (int i = 0; i < len; i++) {
					vs[i] = Double.valueOf(values[i]);
				}
				object = vs;
			} else if (Float.class.equals(type)) {
				Float[] vs = new Float[len];
				for (int i = 0; i < len; i++) {
					vs[i] = Float.valueOf(values[i]);
				}
				object = vs;
			} else if (Boolean.class.equals(type)) {
				Boolean[] vs = new Boolean[len];
				for (int i = 0; i < len; i++) {
					vs[i] = Boolean.valueOf(values[i]);
				}
				object = vs;
			} else if (Character.class.equals(type)) {
				Character[] vs = new Character[len];
				for (int i = 0; i < len; i++) {
					if (values[i] == null || values[i].length() == 0)
						vs[i] = null;
					else
						vs[i] = Character.valueOf(values[i].charAt(0));
				}
				object = vs;
			} else {
				object = values;
			}
		} else {
			object = values;
		}
		return (T[]) object;
	}

	/**
	 * 判断是否是基本类型的包装类
	 * 
	 * @param type
	 * @return 假如是返回true，否则返回false
	 */
	public static boolean isPackingType(Class<?> type) {
		return (Integer.class.equals(type) || Short.class.equals(type)
				|| Long.class.equals(type) || Byte.class.equals(type)
				|| Double.class.equals(type) || Float.class.equals(type)
				|| Boolean.class.equals(type) || Character.class.equals(type));
	}
	
	/**
	 * 判断是否是数字类型的基本类型
	 * 
	 * @param cs
	 * @return 假如是返回true，否则返回false
	 */
	public static boolean isRawNumberType(Class<?> cs){
		return Byte.TYPE == cs || Short.TYPE == cs || Integer.TYPE == cs
				|| Long.TYPE == cs || Double.TYPE == cs || Float.TYPE == cs;
	}
	
	/**
	 * 判断是否是数字类型的包装类型
	 * 
	 * @param cs
	 * @return 假如是返回true，否则返回false
	 */
	public static boolean isPackingNumberType(Class<?> cs){
		return Integer.class.equals(cs) || Byte.class.equals(cs)
				|| Long.class.equals(cs) || Short.class.equals(cs)
				|| Double.class.equals(cs) || Float.class.equals(cs);
	}
	
	/**
	 * 判断是否是数字类型
	 * 
	 * @param cs
	 * @return 假如是返回true，否则返回false
	 */
	public static boolean isNumberType(Class<?> cs){
		return isPackingNumberType(cs) || isRawNumberType(cs);
	}
	
	/**
	 * 判断是否是原生浮点型
	 * 
	 * @param cs
	 * @return 假如是返回true，否则返回false
	 */
	public static boolean isRawDecimalType(Class<?> cs){
		return Double.TYPE == cs || Float.TYPE == cs;
	}
	
	/**
	 * 判断是否是包装浮点型
	 * 
	 * @param cs
	 * @return 假如是返回true，否则返回false
	 */
	public static boolean isPackingDecimalType(Class<?> cs){
		return Double.class.equals(cs) || Float.class.equals(cs);
	}
	
	/**
	 * 判断是否是浮点型
	 * 
	 * @param cs
	 * @return 假如是返回true，否则返回false
	 */
	public static boolean isDecimalType(Class<?> cs){
		return isRawDecimalType(cs) || isPackingDecimalType(cs);
	}
	
	/**
	 * 判断给定的对象是否是数字
	 * 
	 * @param cs
	 * @return 假如是返回true，否则返回false
	 */
	public static boolean isNumber(Object num){
		return num instanceof Number;
	}
	
	/**
	 * 判断是否是boolean或char基本类型或其对应的包装类型
	 * 
	 * @param cs
	 * @return 假如是返回true，否则返回false
	 */
	public static boolean isOtherRawOrPackingType(Class<?> cs){
		return Character.TYPE == cs || Boolean.TYPE == cs
			|| Character.class.equals(cs) || Boolean.class.equals(cs);
 	}
}
