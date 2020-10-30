package org.xlp.xml.map;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.xlp.json.JsonArray;
import org.xlp.json.JsonObject;
import org.xlp.utils.XLPStringUtil;
import org.xlp.xml.AbstractXmlConverter;
import org.xlp.xml.json.JsonXmlConverter;

/**
 * <p>创建时间：2020年7月18日 上午12:49:53</p>
 * @author xlp
 * @version 1.0 
 * @Description Map对象转换成xml字符串转换器
*/
public class MapXmlConverter extends AbstractXmlConverter{
	/**
	 * XmlConverter对象
	 */
	private AbstractXmlConverter xmlConverter;
	
	/**
	 * 构造函数
	 * 
	 * @param map
	 */
	public MapXmlConverter(Map<String, Object> map) {
		this(map, null, null);
	}
	
	/**
	 * 构造函数
	 * 
	 * @param map
	 * @param rootTagName xml更标签名称
	 */
	public MapXmlConverter(Map<String, Object> map, String rootTagName) {
		this(map, rootTagName, null);
	}
	
	/**
	 * 构造函数
	 * 
	 * @param map
	 * @param rootTagName
	 * @param charsetName xml的encoding=[charsetName]
	 */
	public MapXmlConverter(Map<String, Object> map, String rootTagName, String charsetName) {
		Map<String, Object> tempMap = map;
		if (!XLPStringUtil.isEmpty(rootTagName)) {
			tempMap = new HashMap<String, Object>(1);
			tempMap.put(rootTagName, map);
		}
		JsonObject jsonObject = JsonObject.fromMap(tempMap); 
		xmlConverter = new JsonXmlConverter(jsonObject, rootTagName, charsetName);
	}
	
	/**
	 * 构造函数
	 * 
	 * @param charsetName xml的encoding=[charsetName]
	 * @param map
	 */
	public MapXmlConverter(String charsetName, Map<String, Object> map) {
		this(map, null, charsetName);
	}
	
	/**
	 * 构造函数
	 * 
	 * @param maps
	 * @param rootTagName xml更标签名称
	 */
	public MapXmlConverter(List<Map<String, Object>> maps, String rootTagName) {
		this(maps, rootTagName, null);
	}
	
	/**
	 * 构造函数
	 * 
	 * @param maps
	 * @param rootTagName
	 * @param charsetName xml的encoding=[charsetName]
	 */
	public MapXmlConverter(List<Map<String, Object>> maps, String rootTagName, String charsetName) {
		JsonArray jsonArray = JsonArray.fromCollection(maps);
		xmlConverter = new JsonXmlConverter(jsonArray, rootTagName, charsetName);
	}
	
	@Override
	public String toXmlString(boolean isFormat) {
		xmlConverter.setNeedFirstLine(isNeedFirstLine());
		xmlConverter.setCharsetName(getCharsetName());
		xmlConverter.setRootTagName(getRootTagName());
		xmlConverter.setSpaceCount(getSpaceCount());
		xmlConverter.setXmlTagType(getXmlTagType()); 
		return xmlConverter.toXmlString(isFormat); 
	}
}
