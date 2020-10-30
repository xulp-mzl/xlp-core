package org.xlp.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

import org.xlp.assertion.AssertUtils;

/**
 * 时间格式转换类
 * 
 * @author 徐龙平
 *         <p>
 *         2016-11-20
 *         </p>
 * @version 2.0
 * 
 */
public class XLPDateUtil {
	// 默认转换格式
	public final static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	// 默认时间转换格式
	public final static String TIME_DEFAULT_FORMAT = "HH:mm:ss";
	// 默认日期转换格式
	public final static String DATE_DEFAULT_FORMAT = "yyyy-MM-dd";

	/**
	 * 计算两个日期之间相差的月数
	 * 
	 * @param date1
	 * @param date2
	 * @return 假如参数为null，返回0
	 */
	public static int calculateResultMonths(Date date1, Date date2) {
		if (date1 == null || date2 == null) {
			return 0;
		}
		Calendar calendar1 = Calendar.getInstance();
		calendar1.setTime(date1);
		Calendar calendar2 = Calendar.getInstance();
		calendar2.setTime(date2);
		return calculateResultMonths(calendar1, calendar2);
	}
	
	/**
	 * 计算两个日期之间相差的月数
	 * 
	 * @param calendar1
	 * @param calendar2
	 * @return 假如参数为null，返回0
	 */
	public static int calculateResultMonths(Calendar calendar1, Calendar calendar2) {
		if (calendar1 == null || calendar2 == null) {
			return 0;
		}
		if (calendar1.compareTo(calendar2) == 0) {
			return 0;
		} else if (calendar1.compareTo(calendar2) == -1) {
			Calendar temp = calendar1;
			calendar1 = calendar2;
			calendar2 = temp;
		}

		int year1 = calendar1.get(Calendar.YEAR);
		int year2 = calendar2.get(Calendar.YEAR);

		return (year1 - year2) * 12 + calendar1.get(Calendar.MONTH)
				- calendar2.get(Calendar.MONTH);
	}
	
	/**
	 * 计算两个日期之间相差的日数
	 * 
	 * @param date1
	 * @param date2
	 * @return 假如参数为null，返回0
	 */
	public static long calculateResultDays(Date date1, Date date2) {
		if (date1 == null || date2 == null) {
			return 0;
		}
		Calendar calendar1 = Calendar.getInstance();
		calendar1.setTime(date1);
		Calendar calendar2 = Calendar.getInstance();
		calendar2.setTime(date2);
		return calculateResultDays(calendar1, calendar2);
	}
	
	/**
	 * 计算两个日期之间相差的日数
	 * 
	 * @param calendar1
	 * @param calendar2
	 * @return 假如参数为null，返回0
	 */
	public static long calculateResultDays(Calendar calendar1, Calendar calendar2) {
		if (calendar1 == null || calendar2 == null) {
			return 0;
		}
		int year1 = calendar1.get(Calendar.YEAR);
		int month1 = calendar1.get(Calendar.MONTH);
		int day1 = calendar1.get(Calendar.DAY_OF_MONTH);
		int year2 = calendar2.get(Calendar.YEAR);
		int month2 = calendar2.get(Calendar.MONTH);
		int day2 = calendar2.get(Calendar.DAY_OF_MONTH);
		calendar1.set(year1, month1, day1, 0, 0, 0);
		calendar2.setTime(calendar1.getTime()); 
		calendar2.set(year2, month2, day2); 
		long millis =calendar1.getTimeInMillis() - calendar2.getTimeInMillis();
		if (millis < 0) {
			millis = -millis;
		}
		return millis / 1000 / 60 / 60 / 24; 
	}

	/**
	 * 计算n天前后的日期
	 * 
	 * @param date
	 *            原日期
	 * @param days
	 *            天数
	 * @return 新时间
	 */
	public static Date calculateAfterDays(Date date, int days) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_YEAR, days);
		return calendar.getTime();
	}

	/**
	 * 计算n月前后的日期
	 * 
	 * @param date
	 *            原日期
	 * @param months
	 *            月数
	 * @return 新时间
	 */
	public static Date getAfterMonths(Date date, int months) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, months);
		return calendar.getTime();
	}

	/**
	 * 判断是否是闰年
	 * 
	 * @param year
	 * @return 假如是返回true，否则返回false
	 */
	public static boolean isLeapYear(int year) {
		if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
			return true;
		}
		return false;
	}

	/**
	 * 将时间转换成yyyy-MM-dd HH:mm:ss格式的字符串
	 * 
	 * @param date
	 *            时间
	 * @return
	 */
	public static String dateToString(Date date) {
		return dateToString(date, DATE_FORMAT);
	}

	/**
	 * 将时间转换成dateFormat格式的字符串
	 * 
	 * @param date
	 * @param dateFormat
	 *            转换格式
	 * @return 返回格式化时间，假如第一个参数为null返回"",假如第二个参数为空，返回用默认格式转换（yyyy-MM-dd HH:mm:ss）
	 */
	public static String dateToString(Date date, String dateFormat) {
		if (date == null) {
			return "";
		}
		if (XLPStringUtil.isBlankSpace(dateFormat)) {
			dateFormat = DATE_FORMAT;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		return sdf.format(date);
	}

	/**
	 * 将yyyy-MM-dd HH:mm:ss格式的字符串转换成Date
	 * 
	 * @param dateString
	 *            要转换的字符串
	 * @return date
	 */
	public static Date stringToDate(String dateString) {
		return stringToDate(dateString, DATE_FORMAT);
	}

	/**
	 * 将dateFormat格式的字符串转换成Date
	 * 
	 * @param dateString
	 *            要转换的字符串
	 * @param dateFormat
	 *            转换格式
	 * @return date
	 */
	public static Date stringToDate(String dateString, String dateFormat) {
		if (XLPStringUtil.isBlankSpace(dateFormat)) {
			dateFormat = DATE_FORMAT;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		try {
			return sdf.parse(dateString);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 将长整形时间格式化指定格式的字符串时间
	 * 
	 * @param longDate
	 * @param format
	 *            转换格式
	 * @return 返回格式化时间，假如第一个参数为null返回"",假如第二个参数为空，返回用默认格式转换（yyyy-MM-dd HH:mm:ss）
	 */
	public static String longDateFormat(Long longDate, String format) {
		if (longDate == null) {
			return "";
		}
		return dateToString(new Date(longDate.longValue()), format);
	}

	/**
	 * 将长整形时间格式化指定格式的字符串时间(yyyy-MM-dd HH:mm:ss)
	 * 
	 * @param longDate
	 * @return 返回格式化时间，假如第一个参数为null返回"",假如第二个参数为空，返回用默认格式转换（yyyy-MM-dd HH:mm:ss）
	 */
	public static String longDateFormat(Long longDate) {
		return longDateFormat(longDate, DATE_FORMAT);
	}

	/**
	 * 将字符串时间转换成长整形
	 * 
	 * @param strDate
	 *            字符串时间
	 * @param format
	 *            转换格式
	 * @return 假如参数为null，返回null，否则返回长整形时间。
	 */
	public static Long stringDateToLong(String strDate, String format) {
		if (XLPStringUtil.isBlankSpace(strDate)
				|| XLPStringUtil.isBlankSpace(format)) {
			return null;
		}
		return stringToDate(strDate, format).getTime();
	}

	/**
	 * 将字符串时间转换成长整形
	 * 
	 * @param strDate
	 *            字符串时间
	 * @return 假如参数为null，返回null，否则返回长整形时间。
	 */
	public static Long stringDateToLong(String strDate) {
		return stringDateToLong(strDate, DATE_FORMAT);
	}
	
	/**
	 * 将LocalDateTime转换Date
	 * 
	 * @param localDateTime
	 * @return 假如参数为null，返回null。
	 */
	public static Date localDateTimeToDate(LocalDateTime localDateTime){
		if (localDateTime == null) {
			return null;
		}
		ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = localDateTime.atZone(zoneId);
        Date date = Date.from(zdt.toInstant());
        return date;
	}
	
	/**
	 * 将Date转换LocalDateTime
	 * 
	 * @param date
	 * @return 假如参数为null，返回null。
	 */
	public static LocalDateTime dateToLocalDateTime(Date date){
		if (date == null) {
			return null;
		}
		Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime localDateTime = instant.atZone(zoneId).toLocalDateTime();
        return localDateTime;
	}
	
	/**
	 * 将long转换LocalDateTime
	 * 
	 * @param date
	 * @return 假如参数为null，返回null。
	 */
	public static LocalDateTime longDateToLocalDateTime(long date){
		Instant instant = Instant.ofEpochMilli(date);
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime localDateTime = instant.atZone(zoneId).toLocalDateTime();
        return localDateTime;
	}
	
	/**
	 * 将LocalDateTime转换long
	 * 
	 * @param localDateTime
	 * @throws NullPointerException 假如参数为空，抛出该异常
	 * @return 
	 */
	public static long localDateTimeToLongDate(LocalDateTime localDateTime){
		return localDateTime.atZone(ZoneId.systemDefault()).toInstant()
				.toEpochMilli();
	}
	
	/**
	 * 将LocalDateTime转换Calendar
	 * 
	 * @param localDateTime
	 * @return 假如参数为null，返回null。
	 */
	public static Calendar localDateTimeToCalendar(LocalDateTime localDateTime){
		if (localDateTime == null) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(localDateTimeToDate(localDateTime));
		return calendar;
	}
	
	/**
	 * 将Date转换Calendar
	 * 
	 * @param date
	 * @return 假如参数为null，返回null。
	 */
	public static Calendar dateToCalendar(Date date){
		if (date == null) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar;
	}
	
	/**
	 * 将long转换Calendar
	 * 
	 * @param date
	 */
	public static Calendar longDateToCalendar(long date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(date);
		return calendar;
	}
	
	/**
	 * 将时间对象转换成指定格式的字符串
	 * 
	 * @param date
	 * @throws NullPointerException 假如参数为空，抛出该异常
	 * @throws IllegalArgumentException 假如dateObject对象不是时间对象，则抛出该异常
	 */
	public static String dateToString(Object dateObject, String dateFormat){
		AssertUtils.isNotNull(dateObject, "dateObject param is null!");
		AssertUtils.isNotNull(dateFormat, "dateFormat param is null or empty"); 
		if (dateObject instanceof Date) {
			return dateToString((Date)dateObject, dateFormat);
		}
		if (dateObject instanceof LocalDateTime) {  
			return ((LocalDateTime)dateObject).format(DateTimeFormatter.ofPattern(dateFormat));
		}
		if (dateObject instanceof LocalDate) {  
			return ((LocalDate)dateObject).format(DateTimeFormatter.ofPattern(dateFormat));
		}
		if (dateObject instanceof LocalTime) {  
			return ((LocalTime)dateObject).format(DateTimeFormatter.ofPattern(dateFormat));
		}
		if (dateObject instanceof Calendar) {
			return dateToString(((Calendar)dateObject).getTime(), dateFormat);
		}
		if (dateObject instanceof Long) {
			return dateToString(new Date((Long) dateObject), dateFormat);
		}
		throw new IllegalArgumentException("给的时间参数对象不是时间对象");
	}
	
	/**
	 * 判断给定的对象是否是时间对象(即为:Date, LocalDateTime, LocalDate, LocalTime, Long, Calendar)
	 * 
	 * @param dateObject
	 * @return 假如参数为null或不是时间对象返回false，否则返回true
	 */
	public static boolean isDate(Object dateObject){
		if (dateObject == null) {
			return false;
		}
		return (dateObject instanceof Date || dateObject instanceof LocalDateTime
				|| dateObject instanceof LocalDate || dateObject instanceof LocalTime
				|| dateObject instanceof Long || dateObject instanceof Calendar);
	}
}
