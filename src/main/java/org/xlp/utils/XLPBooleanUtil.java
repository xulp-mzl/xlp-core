package org.xlp.utils;

import org.xlp.utils.exception.ValueCastException;

/**
 * boolean值处理工具类
 * 
 * @author 徐龙平
 *         <p>
 *         2017-6-8
 *         </p>
 * @version 1.0
 *          <p>
 *          主要功能是把int， long， byte等装换成Boolean对象
 */
public class XLPBooleanUtil {
	/**
	 * 把byte转换成Boolean对象
	 * 
	 * @param v
	 *            转换值
	 * @return 假如值为0，返回FALSE，假如值为1，返回true，否则抛出ValueCastException异常
	 * @throws ValueCastException
	 */
	public static Boolean valueOf(byte v) {
		if (v == 0)
			return false;
		else if (v == 1)
			return true;
		else
			throw new ValueCastException("传进来的参数值必须为0或1");
	}

	/**
	 * 把int转换成Boolean对象
	 * 
	 * @param v
	 *            转换值
	 * @return 假如值为0，返回FALSE，假如值为1，返回true，否则抛出ValueCastException异常
	 * @throws ValueCastException
	 */
	public static Boolean valueOf(int v) {
		if (v == 0)
			return false;
		else if (v == 1)
			return true;
		else
			throw new ValueCastException("传进来的参数值必须为0或1");
	}

	/**
	 * 把long转换成Boolean对象
	 * 
	 * @param v
	 *            转换值
	 * @return 假如值为0，返回FALSE，假如值为1，返回true，否则抛出ValueCastException异常
	 * @throws ValueCastException
	 */
	public static Boolean valueOf(long v) {
		if (v == 0)
			return false;
		else if (v == 1)
			return true;
		else
			throw new ValueCastException("传进来的参数值必须为0或1");
	}

	/**
	 * 把short转换成Boolean对象
	 * 
	 * @param v
	 *            转换值
	 * @return 假如值为0，返回FALSE，假如值为1，返回true，否则抛出ValueCastException异常
	 * @throws ValueCastException
	 */
	public static Boolean valueOf(short v) {
		if (v == 0)
			return false;
		else if (v == 1)
			return true;
		else
			throw new ValueCastException("传进来的参数值必须为0或1");
	}

	/**
	 * 把String转换成Boolean对象
	 * 
	 * @param v
	 *            转换值
	 * @return 假如值为0或false，返回FALSE，假如值为1或true，返回true，否则抛出ValueCastException异常
	 * @throws ValueCastException
	 */
	public static Boolean valueOf(String v) {
		if (v == null)
			return null;
		if (v.equals("0") || v.equalsIgnoreCase("false"))
			return false;
		else if (v.equals("1") || v.equalsIgnoreCase("true"))
			return true;
		else
			throw new ValueCastException(
					"传进来的参数值必须为0或1或false(大小写都可)或true(大小写都可)");
	}

	/**
	 * 把T转换成Boolean对象
	 * 
	 * @param v
	 *            转换值
	 * @return 假如值为0或false，返回FALSE，假如值为1或true，返回true，否则抛出ValueCastException异常
	 * @throws ValueCastException
	 */
	public static <T> Boolean valueOf(T v) {
		if (v == null)
			return null;
		return valueOf(v.toString());
	}
	
	/**
	 * 判断给定的类是否是boolean 或Boolean
	 * 
	 * @param cs
	 * @return
	 */
	public static boolean isBoolean(Class<?> cs){
		return cs == Boolean.TYPE || cs == Boolean.class;
	}
}
