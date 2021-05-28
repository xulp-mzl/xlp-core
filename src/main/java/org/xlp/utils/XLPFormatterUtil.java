package org.xlp.utils;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

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
	 * @param toClassObject 转换的类型对象（可为数字类型和时间相关对象）
	 * @return 假如参数为null，则返回null, 否则返回解析后的数字或时间对象
	 * @throws RuntimeException 假如解析异常，则抛出该异常
	 * @throws IllegalArgumentException 假如 转换的类型对象不是数字类型和时间相关对象，则抛出该异常
	 */
	public static Object parse(String pattern, CharSequence value, Class<?> toClassObject) {
		if (XLPStringUtil.isEmpty(pattern) || value == null || toClassObject == null) {
			return null;
		}
		String parseStr = value.toString();
		if ((Long.class == toClassObject || Long.TYPE == toClassObject) 
				&& XLPStringUtil.containSubString(pattern, "[yMmdHhs]")) {
			return XLPDateUtil.stringDateToLong(parseStr, pattern);
		}
		if (Number.class.isAssignableFrom(toClassObject)) {
			if (toClassObject.equals(Integer.TYPE)
					|| toClassObject.equals(Integer.class)) {
				return parse(pattern, value).intValue();
			} 
			if (toClassObject.equals(Long.TYPE) 
					|| toClassObject.equals(Long.class)) {
				return parse(pattern, value).longValue();
			} 
			if (toClassObject.equals(Short.TYPE)
					|| toClassObject.equals(Short.class)) {
				return parse(pattern, value).shortValue();
			} 
			if (toClassObject.equals(Double.TYPE)
					|| toClassObject.equals(Double.class)) {
				return parse(pattern, value).doubleValue();
			} 
			if (toClassObject.equals(Float.TYPE)
					|| toClassObject.equals(Float.class)) {
				return parse(pattern, value).floatValue();
			} 
			if (toClassObject.equals(Byte.class) 
					|| toClassObject.equals(Byte.TYPE)) {
				return parse(pattern, value).byteValue();
			}
			return parse(pattern, value);
		}
		
		//以下普通字符串解析成时间对象
		if (toClassObject.equals(Date.class)){
			return XLPDateUtil.stringToDate(parseStr, pattern); 
		} 
		if (toClassObject.equals(java.sql.Date.class)) {
			return new java.sql.Date(XLPDateUtil.stringToDate(parseStr, pattern).getTime());
		} 
		if (toClassObject.equals(java.sql.Time.class)) {
			return new Time(XLPDateUtil.stringToDate(parseStr, pattern).getTime());
		} 
		if (toClassObject.equals(Timestamp.class)) {
			return new Timestamp(XLPDateUtil.stringToDate(parseStr, pattern).getTime());
		} 
		if (toClassObject.isAssignableFrom(Calendar.class)) {
			Calendar calendar = Calendar.getInstance();
			Date date = XLPDateUtil.stringToDate(parseStr, pattern);
			calendar.setTime(date);
			return calendar;
		} 
		if (toClassObject.equals(LocalDateTime.class)){
			return LocalDateTime.parse(parseStr, DateTimeFormatter.ofPattern(pattern));
		} 
		if (toClassObject.equals(LocalDate.class)) {
			return LocalDate.parse(parseStr, DateTimeFormatter.ofPattern(pattern));
		} 
		if (toClassObject.equals(LocalTime.class)) {
			return LocalTime.parse(parseStr, DateTimeFormatter.ofPattern(pattern));
		}
		
		throw new IllegalArgumentException(toClassObject.getName() + "不是数字或时间对象，转换失败");
	}
}
