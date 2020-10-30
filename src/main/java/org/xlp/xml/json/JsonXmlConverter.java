package org.xlp.xml.json;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import org.xlp.json.JsonArray;
import org.xlp.json.JsonElement;
import org.xlp.json.JsonObject;
import org.xlp.json.config.JsonConfig;
import org.xlp.utils.XLPStringUtil;
import org.xlp.xml.AbstractXmlConverter;
import org.xlp.xml.XMLTagType;

/**
 * <p>创建时间：2020年7月18日 上午12:48:49</p>
 * @author xlp
 * @version 1.0 
 * @Description JsonObject对象转换成xml字符串转换器
*/
public class JsonXmlConverter extends AbstractXmlConverter{
	/**
	 * JsonObject对象
	 */
	private JsonObject jsonObject;
	
	/**
	 * 构造函数
	 */
	public JsonXmlConverter() {
	}
	
	/**
	 * 构造函数
	 * 
	 * @param jsonObject
	 */
	public JsonXmlConverter(JsonObject jsonObject) {
		this.jsonObject = jsonObject;
	}
	
	/**
	 * 构造函数
	 * 
	 * @param jsonObject
	 * @param rootTagName xml更标签名称
	 */
	public JsonXmlConverter(JsonObject jsonObject, String rootTagName) {
		this(jsonObject);
		setRootTagName(rootTagName);
	}
	
	/**
	 * 构造函数
	 * 
	 * @param jsonObject
	 * @param rootTagName
	 * @param charsetName xml的encoding=[charsetName]
	 */
	public JsonXmlConverter(JsonObject jsonObject, String rootTagName, String charsetName) {
		this(charsetName, jsonObject);
		setRootTagName(rootTagName); 
	}
	
	/**
	 * 构造函数
	 * 
	 * @param charsetName xml的encoding=[charsetName]
	 * @param jsonObject
	 */
	public JsonXmlConverter(String charsetName, JsonObject jsonObject) {
		this(jsonObject);
		setCharsetName(charsetName);
	}
	
	/**
	 * 构造函数
	 * 
	 * @param jsonObject
	 * @param rootTagName xml更标签名称
	 */
	public JsonXmlConverter(JsonArray jsonArray, String rootTagName) {
		jsonObject = new JsonObject();
		if (jsonArray != null) {
			jsonObject.setJsonConfig(jsonArray.getJsonConfig());
			Iterator<JsonElement> iterator = jsonArray.iterator();
			JsonObject tempJson = new JsonObject(jsonArray.getJsonConfig()), v;
			while (iterator.hasNext()) {
				JsonElement jsonElement = iterator.next(), tElement;
				Class<?> cs = jsonElement.getType();
				if (jsonElement.isBean() || Map.class.isAssignableFrom(cs)
						|| JsonObject.class.equals(cs)) { 
					v = jsonElement.getJsonObject();
					//获取JsonObject对象的key
					String key = v.getFirstKey();
					tElement = v.getJsonElement(key);
					cs = tElement.getType();
					if (v.count() == 1 && (tElement.isBean() 
							|| Map.class.isAssignableFrom(cs) || JsonObject.class.equals(cs))) {
						tempJson.accumulateElement(key, tElement);
					}else {
						tempJson.accumulateElement(rootTagName, jsonElement);
					}
				}else {
					tempJson.accumulateElement(rootTagName, jsonElement);
				}
			}
			jsonObject.put(rootTagName, tempJson);
		}
	}
	
	/**
	 * 构造函数
	 * 
	 * @param jsonObject
	 * @param rootTagName
	 * @param charsetName xml的encoding=[charsetName]
	 */
	public JsonXmlConverter(JsonArray jsonArray, String rootTagName, String charsetName) {
		this(jsonArray, rootTagName);
		setCharsetName(charsetName); 
	}
	
	@Override
	public String toXmlString(boolean isFormat) {
		if (XLPStringUtil.isEmpty(charsetName)) {
			charsetName = XML_CHARSET_NAME;
		}
		StringBuilder xmlSb = new StringBuilder();
		boolean isNeedFirstLine = isNeedFirstLine();
		if (isNeedFirstLine) {
			xmlSb.append(String.format(XML_FIRST_LINE, charsetName));
		}
		boolean rootTagNameIsEmpty = XLPStringUtil.isEmpty(rootTagName);
		if (!rootTagNameIsEmpty) {
			if (isNeedFirstLine) { 
				xmlSb.append(formatSpace(0, true));
			}
			xmlSb.append("<").append(rootTagName).append(">");
		}
		if (jsonObject != null) {
			int space = rootTagNameIsEmpty ? 0 : getSpaceCount();
			parserToXml(jsonObject, xmlSb, space, isFormat);
		}
		if (!rootTagNameIsEmpty) {
			xmlSb.append(formatSpace(0, isFormat)).append("</").append(rootTagName).append(">");
		}
		return xmlSb.toString();
	}

	/**
	 * 
	 * @param jsonObject
	 * @param xmlSb
	 * @param spaceCount
	 */
	private void parserToXml(JsonObject jsonObject, StringBuilder xmlSb, 
			int spaceCount, boolean isFormat) {
		JsonElement jsonElement;
		for (Map.Entry<String, Object> entry : jsonObject.entrySet()) {
			String key = entry.getKey();//获取key
			jsonElement = jsonObject.getJsonElement(key);
			dealEachElement(xmlSb, jsonElement, key, spaceCount, isFormat);
		}
	}

	/**
	 * 处理jsonArray
	 * 
	 * @param value
	 * @param xmlSb
	 * @param key
	 * @param spaceCount
	 */
	private void parserToXml(JsonArray value, StringBuilder xmlSb,
			String key, int spaceCount, boolean isFormat) {
		Iterator<JsonElement> iterator = value.iterator();
		while(iterator.hasNext()){
			JsonElement jsonElement = iterator.next();
			Object v = jsonElement.getValue();//元素值
			Class<?> type = jsonElement.getType();
			if(v == null){
				xmlSb.append(formatSpace(spaceCount, isFormat));
				xmlSb.append("<").append(key).append(">");
				xmlSb.append("</").append(key).append(">");
			}else if (type.isArray() || JsonArray.class == type 
					|| Collection.class.isAssignableFrom(type)) {
				xmlSb.append(formatSpace(spaceCount, isFormat));
				xmlSb.append("<").append(key).append(">");
				dealEachElement(xmlSb, jsonElement, key, spaceCount + SPACE_COUNT, isFormat);
				xmlSb.append(formatSpace(spaceCount, isFormat));
				xmlSb.append("</").append(key).append(">");
			}else if (Map.class.isAssignableFrom(type)
					|| jsonElement.isBean() || JsonObject.class == type) {
				dealEachElement(xmlSb, jsonElement, key, spaceCount, isFormat);
			}else {
				xmlSb.append(formatSpace(spaceCount, isFormat));
				xmlSb.append("<").append(key).append(">");
				xmlSb.append(dealContent(jsonElement.getString(""))); 
				xmlSb.append("</").append(key).append(">");
			}
		}
	}

	/**
	 * 处理单个元素
	 * 
	 * @param xmlSb
	 * @param jsonObject
	 * @param key
	 * @param spaceCount
	 */
	private void dealEachElement(StringBuilder xmlSb, JsonElement jsonElement,
			String key, int spaceCount, boolean isFormat){
		key = dealContent(key);  
		XMLTagType xmlTagType = getXmlTagType();
		if (xmlTagType == XMLTagType.TO_UPPER) {
			key = key.toUpperCase();
		}else if (xmlTagType == XMLTagType.TO_LOWER) {
			key = key.toLowerCase();
		}
		Class<?> type;
		type = jsonElement.getType();//元素类型
		Object value = jsonElement.getValue();//元素值
		JsonConfig jsonConfig = jsonElement.getJsonConfig();
		if(value == null){
			xmlSb.append(formatSpace(spaceCount, isFormat));
			xmlSb.append("<").append(key).append(">");
			xmlSb.append("</").append(key).append(">");
		}else if(type == JsonObject.class){
			xmlSb.append(formatSpace(spaceCount, isFormat));
			xmlSb.append("<").append(key).append(">");
			parserToXml(jsonElement.getJsonObject(), xmlSb, spaceCount + SPACE_COUNT, isFormat);
			xmlSb.append(formatSpace(spaceCount, isFormat));
			xmlSb.append("</").append(key).append(">");
		}else if (Map.class.isAssignableFrom(type)) {  
			xmlSb.append(formatSpace(spaceCount, isFormat));
			xmlSb.append("<").append(key).append(">");
			parserToXml(JsonObject.fromMap((Map<?, ?>) value, jsonConfig), xmlSb,
					spaceCount + SPACE_COUNT, isFormat);
			xmlSb.append(formatSpace(spaceCount, isFormat));
			xmlSb.append("</").append(key).append(">");
		}else if (jsonElement.isBean()) {
			xmlSb.append(formatSpace(spaceCount, isFormat));
			xmlSb.append("<").append(key).append(">");
			parserToXml(JsonObject.fromBean(value, jsonConfig), xmlSb, 
					spaceCount + SPACE_COUNT, isFormat);
			xmlSb.append(formatSpace(spaceCount, isFormat));
			xmlSb.append("</").append(key).append(">");
		}else if (type.isArray()) {
			parserToXml(JsonArray.fromArray(value, jsonConfig), xmlSb, key, 
					spaceCount, isFormat);
		}else if (Collection.class.isAssignableFrom(type)) {
			parserToXml(JsonArray.fromCollection((Collection<?>) value, jsonConfig), 
					xmlSb, key, spaceCount, isFormat);
		}else if (type == JsonArray.class) {
			parserToXml((JsonArray) value, xmlSb, key, spaceCount, isFormat);
		}else {
			xmlSb.append(formatSpace(spaceCount, isFormat));
			xmlSb.append("<").append(key).append(">");
			xmlSb.append(dealContent(jsonElement.getString(""))); 
			xmlSb.append("</").append(key).append(">");
		}
	}

	public JsonObject getJsonObject() {
		return jsonObject;
	}

	public void setJsonObject(JsonObject jsonObject) {
		this.jsonObject = jsonObject;
	}

}
