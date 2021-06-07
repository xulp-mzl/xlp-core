package org.xlp.json.utils;

import org.xlp.utils.XLPPackingTypeUtil;

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
		return XLPPackingTypeUtil.isRawNumberType(cs);
	}
	
	/**
	 * 判断是否是数字类型的包装类型
	 * 
	 * @param cs
	 * @return 假如是返回true，否则返回false
	 */
	public static boolean isPackingNumberType(Class<?> cs){
		return XLPPackingTypeUtil.isPackingNumberType(cs);
	}
	
	/**
	 * 判断是否是数字类型
	 * 
	 * @param cs
	 * @return 假如是返回true，否则返回false
	 */
	public static boolean isNumberType(Class<?> cs){
		return XLPPackingTypeUtil.isNumberType(cs);
	}
	
	/**
	 * 判断是否是原生浮点型
	 * 
	 * @param cs
	 * @return 假如是返回true，否则返回false
	 */
	public static boolean isRawDecimalType(Class<?> cs){
		return XLPPackingTypeUtil.isRawDecimalType(cs);
	}

	/**
	 * 判断是否是包装浮点型
	 * 
	 * @param cs
	 * @return 假如是返回true，否则返回false
	 */
	public static boolean isPackingDecimalType(Class<?> cs){
		return XLPPackingTypeUtil.isPackingDecimalType(cs);
	}
	
	/**
	 * 判断是否是浮点型
	 * 
	 * @param cs
	 * @return 假如是返回true，否则返回false
	 */
	public static boolean isDecimalType(Class<?> cs){
		return XLPPackingTypeUtil.isDecimalType(cs);
	}
	
	/**
	 * 判断给定的对象是否是数字
	 * 
	 * @param num
	 * @return 假如是返回true，否则返回false
	 */
	public static boolean isNumber(Object num){
		return XLPPackingTypeUtil.isNumber(num);
	}
	
	/**
	 * 判断是否是boolean或char基本类型或其对应的包装类型
	 * 
	 * @param cs
	 * @return 假如是返回true，否则返回false
	 */
	public static boolean isOtherRawOrPackingType(Class<?> cs){
		return XLPPackingTypeUtil.isOtherRawOrPackingType(cs);
 	}
}
