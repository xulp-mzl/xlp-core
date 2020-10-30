package org.xlp.json.config;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

import org.xlp.utils.XLPDateUtil;
import org.xlp.utils.XLPStringUtil;

/**
 * 时间格式转换基本实现类
 * 
 * @author 徐龙平
 *         <p>
 *         2017-6-12
 *         </p>
 * @version 1.0
 */
public class DateFormatConfig{
	private String dateTimeFormat = XLPDateUtil.DATE_FORMAT;//日期时间默认格式
	private String dateFormat = XLPDateUtil.DATE_DEFAULT_FORMAT;//日期默认格式
	private String timeFormat = XLPDateUtil.TIME_DEFAULT_FORMAT;//时间默认格式
	//标记是否启用该功能，默认启用
	private boolean open = true;
	
	public DateFormatConfig(){
		
	}
	
	public DateFormatConfig(String dateTimeFormat){
		setDateTimeFormat(dateTimeFormat);
	}
	
	public DateFormatConfig(boolean open) {
		this.open = open;
	}

	public DateFormatConfig(String dateTimeFormat, String dateFormat,
			String timeFormat) {
		this(dateTimeFormat, dateFormat, timeFormat, true);
	}

	public DateFormatConfig(String dateTimeFormat, String dateFormat,
			String timeFormat, boolean open) {
		setDateTimeFormat(dateTimeFormat);
		setDateFormat(dateFormat);
		setTimeFormat(timeFormat); 
		this.open = open;
	}

	public String getDateTimeFormat() {
		return dateTimeFormat;
	}

	public void setDateTimeFormat(String dateTimeFormat) {
		if(!XLPStringUtil.isEmpty(dateTimeFormat))
			this.dateTimeFormat = dateTimeFormat;
	}

	public String getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(String dateFormat) {
		if(!XLPStringUtil.isEmpty(dateFormat))
			this.dateFormat = dateFormat;
	}

	public String getTimeFormat() {
		return timeFormat;
	}

	public void setTimeFormat(String timeFormat) {
		if(!XLPStringUtil.isEmpty(timeFormat))
			this.timeFormat = timeFormat;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public boolean isOpen() {
		return open;
	}
	
	public String timeToString(Time time){
		if(time == null)
			return null;
		return open ? XLPDateUtil.dateToString(time, this.timeFormat)
				: time.toString();
	}
	
	public Time stringToTime(String timeString){
		if(timeString == null)
			return null;
		return new Time(XLPDateUtil.stringToDate(timeString, timeFormat)
				.getTime());
	}
	
	public String dateToString(java.sql.Date date){
		if(date == null)
			return null;
		return open ? XLPDateUtil.dateToString(date, dateFormat)
				: date.toString();
	}
	
	public java.sql.Date stringToDate(String dateString){
		if(dateString == null)
			return null;
		return new java.sql.Date(XLPDateUtil.stringToDate(dateString, dateFormat)
				.getTime());
	}
	
	public String utilDateToString(Date date){
		if(date == null)
			return null;
		return open ? XLPDateUtil.dateToString(date, dateTimeFormat)
				: date.toString();
	}
	
	public Date stringToUtilDate(String dateString){
		if(dateString == null)
			return null;
		return XLPDateUtil.stringToDate(dateString, dateTimeFormat);
	}
	
	public String calendarToString(Calendar calendar){
		Date date = calendar == null ? null : calendar.getTime();
		return utilDateToString(date);
	}
	
	public Calendar stringToCalendar(String dateString){
		Date date = stringToUtilDate(dateString);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar;
	}
	
	public String localDateTimeToString(LocalDateTime localDateTime){
		if(localDateTime == null)
			return null;
		return open ? localDateTime.format(DateTimeFormatter.ofPattern(dateTimeFormat))
				: localDateTime.toString();
	}
	
	public String localDateToString(LocalDate localDate){
		if(localDate == null)
			return null;
		return open ? localDate.format(DateTimeFormatter.ofPattern(dateFormat))
				: localDate.toString();
	}
	
	public String localTimeToString(LocalTime localTime){
		if(localTime == null)
			return null;
		return open ? localTime.format(DateTimeFormatter.ofPattern(timeFormat))
				: localTime.toString();
	}
	
	public LocalDateTime stringToLocalDateTime(String dateString){
		if(dateString == null)
			return null;
		return LocalDateTime.parse(dateString, DateTimeFormatter.ofPattern(dateTimeFormat));
	}
	
	public LocalDate stringToLocalDate(String dateString){
		if(dateString == null)
			return null;
		return LocalDate.parse(dateString, DateTimeFormatter.ofPattern(dateFormat));
	}
	
	public LocalTime stringToLocalTime(String dateString){
		if(dateString == null)
			return null;
		return LocalTime.parse(dateString, DateTimeFormatter.ofPattern(timeFormat));
	}
}
