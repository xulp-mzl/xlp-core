package org.xlp.utils;

/**
 * 简化对象操作工具类
 * 
 * @author xlp
 * @date 2020-05-10
 */
public class XLPObjectUtil {
	/**
	 * 给定的object对象转换成字符串
	 * 
	 * @param object
	 * @return 假如对象为空则返回""，否则返回object.toString().trim()
	 */
	public static String object2Str(Object object){
		if (object == null) {
			return XLPStringUtil.EMPTY;
		}
		return object.toString().trim();
	}
	
	/**
	 * 比价两个对象是否相等
	 * 
	 * @param param1
	 * @param param2
	 * @return
	 */
	public static boolean equals(Object param1, Object param2){
		if (param1 == null && param2 == null) {
			return true;
		}
		return param1 == null ? param2.equals(param1) : param1.equals(param2);
	}
}