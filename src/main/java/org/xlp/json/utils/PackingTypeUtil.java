package org.xlp.json.utils;

/**
 * 包装类型与基本类型简化操作工具类
 * 
 * @author 徐龙平
 * 
 * @version 1.0
 */
public class PackingTypeUtil {
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
