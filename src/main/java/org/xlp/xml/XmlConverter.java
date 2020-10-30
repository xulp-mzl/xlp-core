package org.xlp.xml;

import org.xlp.utils.XLPSystemParamUtil;

/**
 * <p>创建时间：2020年7月18日 下午4:02:31</p>
 * @author xlp
 * @version 1.0 
 * @Description 转换成xml格式字符串接口
*/
public interface XmlConverter {
	/**
	 * xml 首行encoding的编码格式
	 */
	public final static String XML_CHARSET_NAME = "UTF-8";
	/**
	 * 系统换行符
	 */
	public static String NEW_LINE = XLPSystemParamUtil.getSystemNewline();
	/**
	 * xml 首行字符串
	 */
	public final static String XML_FIRST_LINE = "<?xml version=\"1.0\" encoding=\"%s\"?>";
	/**
	 * xml 首行字符串带encoding的编码
	 */
	public final static String XML_DEFAULT_FIRST_LINE = String.format(XML_FIRST_LINE, XML_CHARSET_NAME); 
	/**
	 * xml格式化时，首行缩进空格数目，默认值为4
	 */
	public final static int SPACE_COUNT = 4;
	
	/**
	 * 转换成xml格式字符串
	 * 
	 * @return
	 */
	public String toXmlString();
	
	/**
	 * 转换成格式化后的xml格式字符串
	 * 
	 * @param isFormat 是否格式化输出，true：是，false，不格式化输出
	 * @return
	 */
	public String toXmlString(boolean isFormat);
	
	/**
	 * 获取转换成xml是，xml标签名称字符格式，大写，小写，正常
	 * 
	 * @return
	 */
	public XMLTagType getXmlTagType();
	
	/**
	 * 转换成xml是，设置xml标签名称字符格式，大写，小写，正常
	 * 
	 * @param xmlTagType
	 */
	public void setXmlTagType(XMLTagType xmlTagType);
}
