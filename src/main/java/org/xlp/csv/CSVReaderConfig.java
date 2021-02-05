package org.xlp.csv;

/**
 * <p>
 * 创建时间：2021年1月20日 下午11:19:32
 * </p>
 * 
 * @author xlp
 * @version 1.0
 * @Description csv文件读取配置类
 */
public class CSVReaderConfig extends CSVConfig {
	private static final long serialVersionUID = 1839115001668229427L;

	/**
	 * 记录是否跳过空行, 默认跳过空行
	 */
	private boolean skipEmptyRow = true;

	/**
	 * 记录第一行是否作为头部信息，默认携带头部信息
	 */
	private boolean hasHeader = true;

	/**
	 * 获取是否跳过空行, 默认跳过空行
	 * 
	 * @return
	 */
	public boolean isSkipEmptyRow() {
		return skipEmptyRow;
	}

	/**
	 * 设置是否跳过空行, 默认跳过空行
	 * 
	 * @param skipEmptyRow
	 */
	public void setSkipEmptyRow(boolean skipEmptyRow) {
		this.skipEmptyRow = skipEmptyRow;
	}

	/**
	 * 获取第一行是否作为头部信息，默认携带头部信息
	 * 
	 * @return
	 */
	public boolean isHasHeader() {
		return hasHeader;
	}

	/**
	 * 设置第一行是否作为头部信息，默认携带头部信息
	 * 
	 * @param hasHeader
	 */
	public void setHasHeader(boolean hasHeader) {
		this.hasHeader = hasHeader;
	}
}
