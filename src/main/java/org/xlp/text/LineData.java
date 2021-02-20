package org.xlp.text;

import org.xlp.utils.XLPStringUtil;

/**
 * <p>创建时间：2021年2月18日 下午6:21:14</p>
 * @author xlp
 * @version 1.0 
 * @Description 文本文件每行数据类
*/
public class LineData {
	/**
	 * 行号
	 */
	private long lineNo;
	
	/**
	 * 每行数据，不包括换行符
	 */
	private String lineData;
	
	/**
	 * 构造函数
	 */
	public LineData(){}

	/**
	 * 构造函数
	 * 
	 * @param lineNo
	 * @param lineData
	 */
	public LineData(long lineNo, String lineData) {
		this.lineNo = lineNo;
		this.lineData = lineData;
	}

	/**
	 * @return 行号
	 */
	public long getLineNo() {
		return lineNo;
	}

	/**
	 * @param lineNo 设置行号
	 */
	public void setLineNo(long lineNo) {
		this.lineNo = lineNo;
	}

	/**
	 * @return 获取每行数据
	 */
	public String getLineData() {
		return lineData;
	}

	/**
	 * @param lineData 设置数据
	 */
	public void setLineData(String lineData) {
		this.lineData = lineData;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("LineData [lineNo=").append(lineNo).append(", lineData=").append(lineData).append("]");
		return builder.toString();
	}
	
	/**
	 * 通过指定的行处理器，返回处理后的数据，假如指定的行处理器为null，返回this
	 * 
	 * @param lineHandler
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> T lineHandle(LineHandler<T> lineHandler){
		if (lineHandler == null) {
			return (T) this;
		}
		return lineHandler.handle(this);
	}
	
	/**
	 * 判断改行是否是空行
	 * 
	 * @return
	 */
	public boolean isEmpty(){
		return XLPStringUtil.isNullOrEmpty(lineData);
	}
}
