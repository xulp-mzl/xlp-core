package org.xlp.javabean.config;

import org.xlp.utils.XLPDateUtil;

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
	
	public DateFormatConfig(){
		
	}
	
	public DateFormatConfig(String dateTimeFormat){
		this.dateTimeFormat = dateTimeFormat;
	}
	
	public DateFormatConfig(String dateTimeFormat, String dateFormat,
			String timeFormat) {
		super();
		this.dateTimeFormat = dateTimeFormat;
		this.dateFormat = dateFormat;
		this.timeFormat = timeFormat;
	}

	
	public String getDateTimeFormat() {
		return dateTimeFormat;
	}

	public void setDateTimeFormat(String dateTimeFormat) {
		this.dateTimeFormat = dateTimeFormat;
	}

	public String getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	public String getTimeFormat() {
		return timeFormat;
	}

	public void setTimeFormat(String timeFormat) {
		this.timeFormat = timeFormat;
	}
}
