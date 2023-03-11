package org.xlp.javabean.convert.mapandbean;

import org.xlp.javabean.config.DateFormatConfig;
import org.xlp.javabean.processer.ValueProcesser;
import org.xlp.utils.XLPBooleanUtil;
import org.xlp.utils.XLPDateUtil;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

/**
 * map值处理器
 * 
 * @author 徐龙平
 *         <p>
 *         2017-5-22
 *         </p>
 * @version 2.0
 * 
 */
public class MapValueProcesser extends ValueProcesser{
	// 字符串日期相互转换格式
	private DateFormatConfig config = new DateFormatConfig();
	
	public MapValueProcesser(){
	}
	
	/**
	 * @param format 字符串日期相互转换格式
	 */
	public MapValueProcesser(String format){
		this(new DateFormatConfig(format));
	}
	
	/**
	 * @param config 字符串日期相互转换格式
	 */
	public MapValueProcesser(DateFormatConfig config){
		if (config != null) {
			this.config = config;
		}
	}
	
	/**
	 * 对给定的Map值处理成适合bean字段的属性值
	 * 
	 * @param fieldType
	 *            字段类型
	 * @param value
	 *            给值的值
	 * @return 返回处理后的值
	 */
	@Override
	public Object processValue(Class<?> fieldType, Object value) {
		if (value == null || fieldType == null)
			return null;

		Class<?> valueType = value.getClass();
		
		//当字段类型不是数组类型时而value的值是数组时，处理成适合的值
		if (!fieldType.isArray() && valueType.isArray()) {
			int length = Array.getLength(value);//用反射获取数组的长度
			if (length == 0) return null;
			value = Array.get(value, 0);//用反射获取数组中的元素;
			if (value == null) return null;
			valueType = value.getClass();
		}
		
		if (fieldType.equals(String.class)) {
			if (value instanceof java.sql.Date) {
				value = XLPDateUtil.dateToString((Date) value, config.getDateFormat());
			}else if (value instanceof java.sql.Time) {
				value = XLPDateUtil.dateToString((Date) value, config.getTimeFormat());
			}else if (value instanceof Date) {
				value = XLPDateUtil.dateToString((Date) value, config.getDateTimeFormat());
			}else if (value instanceof Calendar) {
				value = XLPDateUtil.dateToString(((Calendar) value).getTime(), config.getDateTimeFormat());
			}else if (value instanceof LocalDateTime) {
				value = ((LocalDateTime)value).format(DateTimeFormatter.ofPattern(config.getDateTimeFormat()));
			}else if (value instanceof LocalDate) {
				value = ((LocalDate)value).format(DateTimeFormatter.ofPattern(config.getDateFormat()));
			}else if (value instanceof LocalTime) {
				value = ((LocalTime)value).format(DateTimeFormatter.ofPattern(config.getTimeFormat()));
			}else if (value instanceof BigDecimal) {
				value = ((BigDecimal)value).toPlainString();
			}else {
				value = value.toString();
			}
		} else if (fieldType.equals(Integer.TYPE)
				|| fieldType.equals(Integer.class)) {
			value = Integer.valueOf(value.toString());
		} else if (fieldType.equals(Long.TYPE) 
				|| fieldType.equals(Long.class)) {
			if (value instanceof Number) {
				value = ((Number)value).longValue();
			}else if (value instanceof Date) {
				value = ((Date)value).getTime();
			}else if (value instanceof LocalDateTime) {
				value = ((LocalDateTime)value).atZone(ZoneId.systemDefault())
						.toInstant().toEpochMilli();
			}else if (value instanceof Calendar) {
				value = ((Calendar)value).getTimeInMillis();
			}else if (value instanceof LocalDate) {
				value = XLPDateUtil.localDateToLongDate((LocalDate) value);
			}else if (value instanceof LocalTime) {
				value = XLPDateUtil.localTimeToLongDate((LocalTime) value);
			}else {
				value = Long.valueOf(value.toString());
			}
		} else if (fieldType.equals(Short.TYPE)
				|| fieldType.equals(Short.class)) {
			value = Short.valueOf(value.toString());
		} else if (fieldType.equals(Double.TYPE)
				|| fieldType.equals(Double.class)) {
			value = Double.valueOf(value.toString());
		} else if (fieldType.equals(Float.TYPE)
				|| fieldType.equals(Float.class)) {
			value = Float.valueOf(value.toString());
		} else if (fieldType.equals(Byte.class) 
				|| fieldType.equals(Byte.TYPE)) {
			value = Byte.valueOf(value.toString());
		} else if (fieldType.equals(Boolean.TYPE)
				|| fieldType.equals(Boolean.class)) {
			value = XLPBooleanUtil.valueOf(value);
		} else if (fieldType.equals(Character.TYPE)
				|| fieldType.equals(Character.class)) {
			if (valueType.equals(String.class)) {
				if (((String) value).length() == 0) {
					value = null;
				} else {
					value = Character.valueOf(((String) value).charAt(0));
				}
			}
		} else if (valueType.equals(String.class)) {
			if (fieldType.equals(Date.class)){
				value = XLPDateUtil.stringToDate((String) value, config.getDateTimeFormat());
			}else if (fieldType.equals(java.sql.Date.class)) {
				value = new java.sql.Date(XLPDateUtil.stringToDate((String) value, 
						config.getDateFormat()).getTime());
			}else if (fieldType.equals(java.sql.Time.class)) {
				value = new java.sql.Time(XLPDateUtil.stringToDate((String) value, 
						config.getTimeFormat()).getTime());
			}else if (fieldType.equals(Timestamp.class)) {
				value = new Timestamp(XLPDateUtil.stringToDate((String) value, 
						config.getDateTimeFormat()).getTime());
			}else if (Calendar.class.isAssignableFrom(fieldType)) {
				Date date = XLPDateUtil.stringToDate((String) value, 
						config.getDateTimeFormat());
				value = XLPDateUtil.dateToCalendar(date);
			}else if (fieldType.equals(LocalDateTime.class)) {
				value = LocalDateTime.parse((String) value, 
						DateTimeFormatter.ofPattern(config.getDateTimeFormat()));
			}else if (fieldType.equals(LocalDate.class)) {
				value = LocalDate.parse((String) value, 
						DateTimeFormatter.ofPattern(config.getDateFormat()));
			}else if (fieldType.equals(LocalTime.class)) {
				value = LocalTime.parse((String) value, 
						DateTimeFormatter.ofPattern(config.getTimeFormat()));
			}else if (fieldType.equals(BigDecimal.class)) { 
				value = new BigDecimal((String) value);
			}else if (fieldType.equals(BigInteger.class)) {
				value = new BigInteger((String) value);
			}
		}else if (fieldType.equals(BigDecimal.class) && !valueType.equals(BigDecimal.class)) {
			value = new BigDecimal(value.toString());
		}else if (fieldType.equals(BigInteger.class) && !valueType.equals(BigInteger.class)) {
			value = new BigInteger(value.toString());
		}else if (value instanceof Long) {
			if (Date.class.isAssignableFrom(fieldType)){
				value = new Date((Long)value);
			}else if (Calendar.class.isAssignableFrom(fieldType)) {
				value = XLPDateUtil.longDateToCalendar((Long)value);
			}else if (fieldType.equals(LocalDateTime.class)) {
				Instant instant = Instant.ofEpochMilli((Long)value);
				value = instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
			}else if (fieldType.equals(LocalDate.class)) {
				Instant instant = Instant.ofEpochMilli((Long)value);
				value = instant.atZone(ZoneId.systemDefault()).toLocalDate();
			}else if (fieldType.equals(LocalTime.class)) {
				Instant instant = Instant.ofEpochMilli((Long)value);
				value = instant.atZone(ZoneId.systemDefault()).toLocalTime();
			}
		}else if (value instanceof Date) {
			if (LocalDateTime.class.isAssignableFrom(fieldType)) {
				value = XLPDateUtil.dateToLocalDateTime((Date) value);
			}else if (Calendar.class.isAssignableFrom(fieldType)) {
				value = XLPDateUtil.dateToCalendar((Date) value);
			}
		}else if (value instanceof LocalDateTime) {
			if (Date.class.isAssignableFrom(fieldType)) {
				value = XLPDateUtil.localDateTimeToDate((LocalDateTime) value);
			}else if (Calendar.class.isAssignableFrom(fieldType)) {
				value = XLPDateUtil.localDateTimeToCalendar((LocalDateTime) value);
			}
		}else if (value instanceof Calendar) {
			if (Date.class.isAssignableFrom(fieldType)) {
				value = ((Calendar)value).getTime();
			}else if (LocalDateTime.class.isAssignableFrom(fieldType)) {
				value = XLPDateUtil.dateToLocalDateTime(((Calendar)value).getTime()); 
			}
		}
		
		if (fieldType == char[].class && CharSequence.class.isAssignableFrom(valueType)) {
			value = value.toString().toCharArray();
		}
		return value;
	}
}
