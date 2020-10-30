package org.xlp.xml;

import org.xlp.utils.XLPStringUtil;

/**
 * <p>创建时间：2020年7月18日 下午4:02:31</p>
 * @author xlp
 * @version 1.0 
 * @Description 转换成xml格式字符串接口
*/
public abstract class AbstractXmlConverter implements XmlConverter{
	/**
	 *  xml格式化时，首行缩进空格数目
	 */
	private int spaceCount = SPACE_COUNT;
	
	/**
	 * 转换成xml是，xml标签名称字符格式，大写，小写，正常
	 */
	private XMLTagType xmlTagType = XMLTagType.NORMAL;
	
	/**
	 * 是否需要xml的首行
	 */
	private boolean isNeedFirstLine = true;
	
	/**
	 * xml根标签名称
	 */
	protected String rootTagName;
	
	/**
	 * xml 首行字符串带encoding的编码
	 */
	protected String charsetName = XML_CHARSET_NAME;
	
	
	/**
	 * 获取转换成xml是，xml标签名称字符格式，大写，小写，正常
	 * 
	 * @return
	 */
	public XMLTagType getXmlTagType(){
		return xmlTagType;
	}
	
	/**
	 * 转换成xml是，设置xml标签名称字符格式，大写，小写，正常
	 * 
	 * @param xmlTagType
	 */
	public void setXmlTagType(XMLTagType xmlTagType){
		if (xmlTagType != null) {
			this.xmlTagType = xmlTagType;
		}
	}

	public int getSpaceCount() {
		return spaceCount;
	}

	/**
	 * 设置 xml格式化时，首行缩进空格数目
	 * 
	 * @param spaceCount
	 */
	public void setSpaceCount(int spaceCount) {
		this.spaceCount = spaceCount;
	}
	
	/**
	 * 把xml中的特殊字符用对应的实体进行替换
	 * 
	 * @param string
	 * @return
	 */
	private String replace(String string){
		return string.replace("&", "&amp;").replace("<", "&lt;")
			.replace(">", "&gt;").replace("\'", "&apos;")
			.replace("\"", "&quot;");
	}
	
	/**
	 * 处理xml标签内容中的特殊字符
	 * 
	 * @param value
	 * @return
	 */
	protected String dealContent(String value){
		value = XLPStringUtil.emptyTrim(value);
		return XLPStringUtil.isEmpty(value) ? value : replace(value);
	}
	
	/**
	 * 获取首行缩进空格数的字符串
	 * 
	 * @param spaceCount 缩进空格数
	 * @param isFormat 是否格式化xml
	 */
	protected String formatSpace(int spaceCount, boolean isFormat){
		if (!isFormat) {
			return "";
		}
		StringBuilder sb = new StringBuilder(); 
		if (spaceCount >= 0) {
			sb.append(NEW_LINE);
			for (int i = 0; i < spaceCount; i++) {
				sb.append(" ");
			}
		}
		return sb.toString();
	}

	public boolean isNeedFirstLine() {
		return isNeedFirstLine;
	}

	/**
	 * 设置是否需要xml的首行
	 * 
	 * @param isNeedFirstLine，true是，false，不需要
	 */
	public void setNeedFirstLine(boolean isNeedFirstLine) {
		this.isNeedFirstLine = isNeedFirstLine;
	}

	protected String getRootTagName() {
		return rootTagName; 
	}
	
	/**
	 * xml根标签名称
	 * 
	 * @param rootTagName
	 */
	public void setRootTagName(String rootTagName) {
		this.rootTagName = XLPStringUtil.emptyTrim(rootTagName); 
	}

	public String getCharsetName() {
		return charsetName;
	}

	/**
	 * 设置xml首行字符串带encoding的编码
	 * 
	 * @param charsetName
	 */
	public void setCharsetName(String charsetName) {
		this.charsetName = XLPStringUtil.emptyTrim(charsetName); ;
	}
	
	/**
	 * 转换成格式化后的xml格式字符串，默认格式化输出
	 * 
	 * @return
	 */
	@Override
	public String toXmlString() {
		return toXmlString(true);
	}
}
