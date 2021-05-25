package org.xlp.utils;

import java.text.DecimalFormat;
import java.text.ParseException;

/**
 * <p>
 * 创建时间：2021年5月23日 上午9:40:00
 * </p>
 * 
 * @author xlp
 * @version 1.0
 * @Description 字符串格式化工具类
 */
public class XLPFormatterUtil {
	/**
	 * 用指定的格式，格式化给定的对象(针对于对象为数字类型或时间类型)
	 * 
	 * @param pattern
	 *            格式化模式
	 * @param value
	 *            要格式化的对象
	 * @return 假如给定的对象不是数字类型或时间类型时，直接返回value.toString(),否则返回格式化后的字符串，假如格式化失败或参数为null，则返回null
	 */
	public static String format(String pattern, Object value) {
		String formatValue = null;
		if (XLPStringUtil.isEmpty(pattern) || value == null) {
			return formatValue;
		}
		try {
			if (value instanceof Number) {
				DecimalFormat decimalFormat = new DecimalFormat(pattern);
				formatValue = decimalFormat.format(((Number) value).doubleValue());
			} else {
				formatValue = XLPDateUtil.dateToString(value, pattern);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			formatValue = value.toString();
		}
		return formatValue;
	}
	
	/**
	 * 用指定的格式，格式化给定的对象(针对于对象为数字类型)
	 * 
	 * @param pattern
	 *            格式化模式
	 * @param value
	 *            要格式化的对象
	 * @return 格式化后的字符串，假如参数为null，返回null
	 */
	public static String format(String pattern, Number value) {
		String formatValue = null;
		if (XLPStringUtil.isEmpty(pattern) || value == null) {
			return formatValue;
		}
		DecimalFormat decimalFormat = new DecimalFormat(pattern);
		return decimalFormat.format(((Number) value).doubleValue());
	}
	
	/**
	 * 用指定的模式把字符串转换成相应的类型对象
	 * 
	 * @param pattern 格式化模式
	 * @param value 要转换的字符串
	 * @return 假如参数为null，则返回null,否则返回解析后的对象
	 * @throws RuntimeException 假如解析出错，则抛出该异常
	 */
	public static Number parse(String pattern, CharSequence value) {
		if (XLPStringUtil.isEmpty(pattern) || value == null) {
			return null;
		}
		try {
			DecimalFormat decimalFormat = new DecimalFormat(pattern);
			return decimalFormat.parse(value.toString());
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 用指定的模式把字符串转换成相应的类型对象
	 * 
	 * @param pattern 格式化模式
	 * @param value 要转换的字符串
	 * @param toClassObject 转换的类型对象
	 * @return 假如参数为null或解析异常，则返回null
	 */
	public static <T> T parse(String pattern, CharSequence value, Class<T> toClassObject) {
		if (XLPStringUtil.isEmpty(pattern) || value == null || toClassObject == null) {
			return null;
		}
		
		return null;
	}
}
