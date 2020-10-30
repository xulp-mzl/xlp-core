package org.xlp.json.utils;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.util.Collection;
import java.util.Map;
import java.util.Properties;

import org.xlp.json.Json;
import org.xlp.json.JsonArray;
import org.xlp.json.JsonElement;
import org.xlp.json.JsonObject;
import org.xlp.json.config.JsonConfig;
import org.xlp.utils.XLPStringUtil;
import org.xlp.utils.io.XLPIOUtil;

/**
 * 简化json操作工具类
 * <br/>该工具类的把json数据转换为各种资源或把一些资源转换为json对象
 * 
 * @author 徐龙平
 * 
 * @version 1.0
 */
public class JsonHelper {
	/**
	 * 把json对象写入IO流中
	 * 
	 * @param json json对象
	 * @param charSet 字符编码
	 * @param out 输出流
	 * @throws IOException 
	 * 			假如IO操作出错时，抛出该异常
	 * @throws NullPointerException
	 * 		          假如json对象或输出流为null，抛出该异常
	 */
	public static void toOutputStream(Json json, String charSet
			, OutputStream out) throws IOException{
		if(json == null || out == null)
			throw new NullPointerException("参数必须不为null");
		XLPIOUtil.write(json.format(), out, charSet);
	}
	
	/**
	 * 把json对象写入IO流中
	 * 
	 * @param json json对象
	 * @param out 输出流
	 * @throws IOException 
	 * 			假如IO操作出错时，抛出该异常
	 * @throws NullPointerException
	 * 		          假如json对象或输出流为null，抛出该异常
	 */
	public static void toOutputStream(Json json
			, OutputStream out) throws IOException{
		toOutputStream(json, null, out);
	}
	
	/**
	 * 把json对象写入IO流中
	 * 
	 * @param json json对象
	 * @param writer 输出流
	 * @throws IOException 
	 * 			假如IO操作出错时，抛出该异常
	 * @throws NullPointerException
	 * 		          假如json对象或输出流为null，抛出该异常
	 */
	public static void toWriter(Json json, Writer writer) 
		throws IOException{
		if(json == null || writer == null)
			throw new NullPointerException("参数必须不为null");
		XLPIOUtil.write(json.format(), writer);
	}
	
	/**
	 * 把json对象写入File中
	 * 
	 * @param json json对象
	 * @param file 输出流
	 * @param charSetName 字符编码
	 * @throws IOException 
	 * 			假如IO操作出错时，抛出该异常
	 * @throws NullPointerException
	 * 		          假如json对象或File对象为null，抛出该异常
	 */
	public static void toFile(Json json, File file, 
			String charSetName) throws IOException{
		if(json == null || file == null)
			throw new NullPointerException("参数必须不为null");
		XLPIOUtil.writeToFile(json.format(), file, charSetName);
	}
	
	
	/**
	 * 把json对象写入File中
	 * 
	 * @param json json对象
	 * @param file 输出流
	 * @throws IOException 
	 * 			假如IO操作出错时，抛出该异常
	 * @throws NullPointerException
	 * 		          假如json对象或File对象为null，抛出该异常
	 */
	public static void toFile(Json json, File file) 
			throws IOException{
		toFile(json, file, null);
	}
	
	/**
	 * 把json对象写入指定文件名File中
	 * 
	 * @param json json对象
	 * @param fileName 输出流
	 * @param charSetName 字符编码
	 * @throws IOException 
	 * 			假如IO操作出错时，抛出该异常
	 * @throws NullPointerException
	 * 		          假如json对象或fileName为null，抛出该异常
	 */
	public static void toFile(Json json, String fileName, 
			String charSetName) throws IOException{
		if(json == null || fileName == null)
			throw new NullPointerException("参数必须不为null");
		XLPIOUtil.writeToFile(json.format(), fileName, charSetName);
	}
	
	/**
	 * 把json对象写入指定文件名File中
	 * 
	 * @param json json对象
	 * @param fileName 输出流
	 * @throws IOException 
	 * 			假如IO操作出错时，抛出该异常
	 * @throws NullPointerException
	 * 		          假如json对象或fileName为null，抛出该异常
	 */
	public static void toFile(Json json, String fileName) 
			throws IOException{
		toFile(json, fileName, null);
	}
	
	/**
	 * 把jsonObject对象转换成Properties对象
	 * 
	 * @param jsonObject
	 * @return
	 * @throws NullPointerException
	 * 		          假如参数为null，抛出该异常
	 */
	public static Properties toProperty(JsonObject jsonObject){
		if(jsonObject == null)
			throw new NullPointerException("参数必须不为null");
		Properties properties = new Properties();
		parserToProp(null, properties, jsonObject);
		return properties;
	}
	
	private static void parserToProp(String key, Properties prop, JsonObject jsonObject){
		String key0, temp ;
		JsonElement jsonElement;
		Class<?> type;
		Object v;
		for (Map.Entry<String, Object> entry : jsonObject.entrySet()) {
			temp = key;
			key0 = entry.getKey();//获取key
			jsonElement = jsonObject.getJsonElement(key0);
			type = jsonElement.getType();
			v = jsonElement.getObject();
			if(v == null || XLPStringUtil.isEmpty(key0))//判断key-value是否为null，如是，则不运行下面的代码
				continue;
			if(type == JsonArray.class || type.isArray() 
					|| Collection.class.isAssignableFrom(type)){//判断是否是JsonArray，array，collection
				v = jsonElement.getArrayString();
				if(!XLPStringUtil.isEmpty((String) v)){
					temp = temp == null ? key0 : temp + "." +key0;
					prop.setProperty(temp, (String) v);
				}
			}else if (jsonElement.isBean()) {//判断是否是bean对象，是转换为JsonObject对象
				temp = temp == null ? key0 : temp + "." +key0;
				parserToProp(temp, prop, JsonObject.fromBean(v, jsonElement.getJsonConfig()));//递归调用
			}else if (type == JsonObject.class) {//判断是否是JsonObject对象
				temp = temp == null ? key0 : temp + "." +key0;
				parserToProp(temp, prop, (JsonObject) v);//递归调用
			}else if (Map.class.isAssignableFrom(type)) {//判断是否是map对象，是转换为JsonObject对象
				temp = temp == null ? key0 : temp + "." +key0;
				parserToProp(temp, prop, JsonObject.fromMap((Map<?, ?>) v, 
						jsonElement.getJsonConfig()));//递归调用
			}else {
				v = jsonElement.getString();
				if(!XLPStringUtil.isEmpty((String) v)){
					temp = temp == null ? key0 : temp + "." +key0;
					prop.setProperty(temp, (String) v);
				}
			}
		}
	}
	
	/**
	 * 把Properties资源转换为JsonObject对象
	 * 
	 * @param properties
	 * @param jsonConfig
	 * @param toArray 标记一些数据是否转化为JsonArray，值为true是，否则不是
	 * @return 假如参数properties为null，返回null
	 */
	public static JsonObject fromProperty(Properties properties
			, JsonConfig jsonConfig, boolean toArray){
		if(properties == null)
			return null;
		JsonObject jsonObject = new JsonObject(jsonConfig);
		String[] keys;
		JsonObject newJso, temp;
		for (String key : properties.stringPropertyNames()) {
			temp = jsonObject;
			keys = key.split("\\.");
			int len = keys.length;
			if(len > 1){
				for(int i = 1; i < len; i++){
					newJso = temp.getJsonElement(keys[i - 1]).getJsonObject();
					if (newJso == null) {
						for (int j = i; j < len; j++) {
							newJso = new JsonObject(jsonConfig);
							if(j == len - 1){
								String propValue = properties.getProperty(key);
								if (propValue.contains(",") && toArray) 
									newJso.accumulate(keys[j], JsonArray.fromArray(propValue.split(","),
											jsonConfig));	
								else 
									newJso.accumulate(keys[j], propValue);
								temp.accumulate(keys[j - 1], newJso);
							}else {
								temp.accumulate(keys[j - 1], newJso);
								temp = newJso;
							}
						}
						break;
					}else {
						if(i == len - 1){
							String propValue = properties.getProperty(key);
							if (propValue.contains(",") && toArray) 
								newJso.accumulate(keys[i], JsonArray.fromArray(propValue.split(","),
										jsonConfig));	
							else 
								newJso.accumulate(keys[i], propValue);
						}else{
							temp = newJso;
						}
					}
				}
			}else {
				String propValue = properties.getProperty(key);
				if (propValue.contains(",") && toArray) 
					jsonObject.accumulate(keys[0], JsonArray.fromArray(propValue.split(","),
							jsonConfig));	
				else 
					jsonObject.accumulate(keys[0], propValue);
			}
		}
		
		return jsonObject;
	}
	
	/**
	 * 把Properties资源转换为JsonObject对象
	 * 
	 * @param properties
	 * @param jsonConfig
	 * @return 假如参数properties为null，返回null
	 */
	public static JsonObject fromProperty(Properties properties
			, JsonConfig jsonConfig){
		return fromProperty(properties, jsonConfig, false);
	}
	
	/**
	 * 把Properties资源转换为JsonObject对象
	 * 
	 * @param properties
	 * @return 假如参数为null，返回null
	 */
	public static JsonObject fromProperty(Properties properties){
		return fromProperty(properties, null);
	}
	
	/**
	 * 把Properties资源转换为JsonObject对象
	 * 
	 * @param properties
	 * @param toArray 标记一些数据是否转化为JsonArray，值为true是，否则不是
	 * @return 假如参数properties为null，返回null
	 */
	public static JsonObject fromProperty(Properties properties
			, boolean toArray){
		return fromProperty(properties, null, toArray);
	}
}
