package org.xlp.javabean.utils;

import java.util.Locale;

import org.xlp.utils.XLPStringUtil;

/**
 * javaBean方法名形成工具类
 * 
 * @author 徐龙平
 *         <p>
 *         2017-5-22
 *         </p>
 * @version 2.0
 * 
 */
public final class MethodNameUtil {
	// 字段对应方法的前缀
	public final static String SET_PREFIX = "set";
	public final static String GET_PREFIX = "get";
	public final static String IS_PREFIX = "is";

	/**
	 * 得到指定字段名的写方法名
	 * 
	 * @param fieldName
	 * @return
	 * @throws NullPointerException  假如指定字段名为空，将抛出该异常
	 */
	public static String createSetterMethodName(String fieldName) {
		String menthodName = firstCharToUpCase(fieldName);
		menthodName = SET_PREFIX + menthodName;
		return menthodName;
	}
	
	/**
	 * 得到指定字段名的写方法名
	 * 
	 * @param fieldName
	 * @param isBooleanType 标记字段是否是boolean类型 值位true则表示是，false则表示不是
	 * @return
	 * @throws NullPointerException  假如指定字段名为空，将抛出该异常
	 */
	public static String createSetterMethodName(String fieldName, 
			boolean isBooleanType) {
		if (isBooleanType && fieldName.length() > IS_PREFIX.length()
				&& fieldName.startsWith(IS_PREFIX)) { 
			fieldName = fieldName.substring(IS_PREFIX.length());
		}
		return createSetterMethodName(fieldName);
	}

	/**
	 * 字符串的首字母大写
	 * 
	 * @param fieldName
	 * @return
	 * @throws NullPointerException  假如指定字段名为空，将抛出该异常
	 */
	private static String firstCharToUpCase(String fieldName) {
		fieldName = XLPStringUtil.emptyTrim(fieldName);
		if (XLPStringUtil.isEmpty(fieldName)) {
			throw new NullPointerException("fieldName param is null or empty");
		}
		int len = fieldName.length();
		
		if (len == 1) {
			fieldName = fieldName.toUpperCase(Locale.ENGLISH);
		}else {
			fieldName = fieldName.substring(0, 1).toUpperCase(Locale.ENGLISH)
					+ fieldName.substring(1);
		}
		return fieldName;
	}
	
	/**
	 * 得到指定字段名的读方法名
	 * 
	 * @param fieldName
	 * @return
	 * @throws NullPointerException  假如指定字段名为空，将抛出该异常
	 */
	public static String createGetterMethodName(String fieldName) {
		String menthodName = firstCharToUpCase(fieldName);
		menthodName = GET_PREFIX + menthodName;
		return menthodName;
	}
	
	/**
	 * 返回指定字段名的读方法名
	 * 
	 * @param fieldName
	 * @return
	 * @throws NullPointerException  假如指定字段名为空，将抛出该异常
	 */
	public static String createIsMethodName(String fieldName) {
		if (fieldName.length() > IS_PREFIX.length()
				&& fieldName.startsWith(IS_PREFIX)) { 
			String temp = fieldName.substring(IS_PREFIX.length());
			if (!XLPStringUtil.startsWith(temp, "[a-z]")) {  
				fieldName = temp;
			}
		}
		String menthodName = firstCharToUpCase(fieldName);
		menthodName = IS_PREFIX + menthodName;
		return menthodName;
	}
}
