package org.xlp.csv;

import java.io.Serializable;

/**
 * <p>创建时间：2021年1月20日 下午11:03:13</p>
 * @author xlp
 * @version 1.0 
 * @Description csv文件配置类
*/
public abstract class CSVConfig implements Serializable {
	private static final long serialVersionUID = 6296328761437887958L;

	/**
	 * 字段默认分隔符，默认逗号','
	 */
	public static final char DEFAULT_FIELD_SEPARATOR = ',';
	
	/**
	 * 文本默认包装符，默认双引号'"'
	 */
	public static final char DEFAULT_TEXT_DELIMITER = '"';
	
	/**
	 * 字段分隔符，默认逗号','
	 */
	private char fieldSeparator = DEFAULT_FIELD_SEPARATOR;
	
	/**
	 * 文本包装符，默认双引号'"'
	 */
	private char textDelimiter = DEFAULT_TEXT_DELIMITER;

	/**
	 * 设置字段分隔符，默认逗号','
	 *
	 * @param fieldSeparator 字段分隔符，默认逗号','
	 */
	public void setFieldSeparator(final char fieldSeparator) {
		this.fieldSeparator = fieldSeparator;
	}

	/**
	 * 设置 文本包装符，默认双引号'"'
	 *
	 * @param textDelimiter 文本包装符，默认双引号'"'
	 */
	public void setTextDelimiter(char textDelimiter) {
		this.textDelimiter = textDelimiter;
	}

	/**
	 * 获取字段分隔符，默认逗号','
	 * 
	 * @return
	 */
	public char getFieldSeparator() {
		return fieldSeparator;
	}

	/**
	 * 获取文本包装符，默认双引号'"'
	 * 
	 * @return
	 */
	public char getTextDelimiter() {
		return textDelimiter;
	}
}
