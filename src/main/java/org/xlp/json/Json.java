package org.xlp.json;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Stack;

import org.xlp.assertion.AssertUtils;
import org.xlp.json.config.JsonConfig;
import org.xlp.json.exception.JsonException;
import org.xlp.json.utils.JsonUtil;
import org.xlp.json.utils.PackingTypeUtil;
import org.xlp.utils.XLPDateUtil;
import org.xlp.utils.XLPFormatterUtil;
import org.xlp.utils.XLPStringUtil;
import org.xlp.utils.XLPSystemParamUtil;
import org.xlp.utils.XLPVerifedUtil;

/**
 * json抽象类,主要实现json的一些公有功能
 * 
 * @author 徐龙平
 * 
 * @version 1.0
 */
public abstract class Json{
	//json转换配置
	protected volatile JsonConfig jsonConfig = new JsonConfig();
	protected static final int NOT_FOUND_INDEX = -1;
	protected static final int LEFT_M_LEN = JsonUtil.LEFT_M.length();
	protected static final int RIGHT_M_LEN = JsonUtil.RIGHT_M.length();
	protected static final int LEFT_B_LEN = JsonUtil.LEFT_B.length();
	protected static final int RIGHT_B_LEN = JsonUtil.RIGHT_B.length();
	protected static final int COMMA_LEN = JsonUtil.COMMA.length();
	protected static final int COLON_LEN = JsonUtil.COLON.length();
	protected static final int DOUBLE_QUOTES_LEN = JsonUtil.DOUBLE_QUOTES.length();
	
	/**
	 * 缩进空格数目
	 */
	protected int spaceCount = DEFALUT_SPACE_COUNT;
	
	/**
	 * 默认缩进空格数目为2
	 */
	protected static final int DEFALUT_SPACE_COUNT = 2;
	
	//""
	protected static final String EMPTY = JsonUtil.EMPTY;
	
	protected OutJsoStringPreDeal outJsoStringPreDeal;
	
	public Json(){
	}
	
	public Json(JsonConfig jsonConfig){
		setJsonConfig(jsonConfig); 
	}
	
	public void setJsonConfig(JsonConfig jsonConfig) {
		if(jsonConfig != null)
			this.jsonConfig = jsonConfig;
	}

	public JsonConfig getJsonConfig() {
		return jsonConfig;
	}
	
	/**
	 * 处理单个jsonElement对象
	 * 
	 * @param je
	 * @param jsonSB
	 * @param spaceCount 缩颈空格数
	 * @param isAddSpace 是否缩进
	 */
	@SuppressWarnings("all")
	protected void dealValue(JsonElement je, StringBuilder jsonSB,
			int spaceCount, boolean isAddSpace) {
		Class<?> cs = je.getType();
		Object value = je.getValue();
		boolean bean = je.isBean();
		boolean isUsedAnnotation = je.isUsedAnnotation();
		JsonConfig jsonConfig = je.getJsonConfig();
		if(value == null && !jsonConfig.getNullConfig().isOpen())
			return;
		if(value == null){ //假如包含对空值进行处理
			if(cs.isArray() || JsonArray.class == cs 
					|| Collection.class.isAssignableFrom(cs))
				jsonSB.append(JsonUtil.LEFT_M + JsonUtil.RIGHT_M);
			else if(JsonObject.class == cs || Map.class.isAssignableFrom(cs))
				jsonSB.append(JsonUtil.LEFT_B + JsonUtil.RIGHT_B);
			else
				jsonSB.append(jsonConfig.getNullConfig().getNone());
			return;
		}
		
		String formatter = je.getFormatter();
		String strVal;
		if ((cs == Long.TYPE || cs == Long.class) && XLPStringUtil.containSubString(formatter, "[yMmdHhs]")) {
			jsonSB.append(JsonUtil.DOUBLE_QUOTES).append(XLPDateUtil.longDateFormat((Long)value, formatter))
				.append(JsonUtil.DOUBLE_QUOTES);
		}else if(PackingTypeUtil.isDecimalType(cs)){
			strVal = XLPStringUtil.isEmpty(formatter) ? jsonConfig.getNumberConfig().toDecimalString((Number)value)
					: XLPFormatterUtil.format(formatter, (Number)value);
			jsonSB.append(strVal);
		}else if(PackingTypeUtil.isNumberType(cs)){
			strVal = XLPStringUtil.isEmpty(formatter) ? jsonConfig.getNumberConfig().toIntString((Number)value)
					: XLPFormatterUtil.format(formatter, (Number)value);
			jsonSB.append(strVal);
		}else if (Date.class == cs || Timestamp.class == cs) {
			strVal = XLPStringUtil.isEmpty(formatter) 
					? jsonConfig.getDateFormatConfig().utilDateToString((Date) value)
					: XLPDateUtil.dateToString((Date)value, formatter);
			jsonSB.append(JsonUtil.DOUBLE_QUOTES).append(strVal).append(JsonUtil.DOUBLE_QUOTES);
		}else if (Time.class == cs) {
			strVal = XLPStringUtil.isEmpty(formatter) 
					? jsonConfig.getDateFormatConfig().timeToString((Time) value)
					: XLPDateUtil.dateToString((Time)value, formatter);
			jsonSB.append(JsonUtil.DOUBLE_QUOTES).append(strVal).append(JsonUtil.DOUBLE_QUOTES);
		}else if (java.sql.Date.class == cs) {
			strVal = XLPStringUtil.isEmpty(formatter) 
					? jsonConfig.getDateFormatConfig().dateToString((java.sql.Date) value)
					: XLPDateUtil.dateToString((java.sql.Date)value, formatter);
			jsonSB.append(JsonUtil.DOUBLE_QUOTES).append(strVal).append(JsonUtil.DOUBLE_QUOTES);
		}else if (Calendar.class.isAssignableFrom(cs)) {
			strVal = XLPStringUtil.isEmpty(formatter) 
					? jsonConfig.getDateFormatConfig().calendarToString((Calendar) value)
					: XLPDateUtil.dateToString(((Calendar)value).getTime(), formatter);
			jsonSB.append(JsonUtil.DOUBLE_QUOTES).append(strVal).append(JsonUtil.DOUBLE_QUOTES);
		}else if (LocalDateTime.class == cs) {
			strVal = XLPStringUtil.isEmpty(formatter) 
					? jsonConfig.getDateFormatConfig().localDateTimeToString((LocalDateTime) value)
					: XLPDateUtil.dateToString(value, formatter);
			jsonSB.append(JsonUtil.DOUBLE_QUOTES).append(strVal).append(JsonUtil.DOUBLE_QUOTES);
		}else if (LocalDate.class == cs) {
			strVal = XLPStringUtil.isEmpty(formatter) 
					? jsonConfig.getDateFormatConfig().localDateToString((LocalDate) value)
					: XLPDateUtil.dateToString(value, formatter);
			jsonSB.append(JsonUtil.DOUBLE_QUOTES).append(strVal).append(JsonUtil.DOUBLE_QUOTES);
		}else if (LocalTime.class == cs) {
			strVal = XLPStringUtil.isEmpty(formatter) 
					? jsonConfig.getDateFormatConfig().localTimeToString((LocalTime) value)
					: XLPDateUtil.dateToString(value, formatter);
			jsonSB.append(JsonUtil.DOUBLE_QUOTES).append(strVal).append(JsonUtil.DOUBLE_QUOTES);
		}else if (bean) {
			Json json = JsonObject.fromBean(value, jsonConfig, isUsedAnnotation);
			json.spaceCount = this.spaceCount;
			jsonSB.append(json.format(spaceCount + this.spaceCount, isAddSpace));
		}else if (Json.class.isAssignableFrom(cs)) {
			Json json = (Json) value;
			json.setJsonConfig(jsonConfig);
			json.spaceCount = this.spaceCount;
			jsonSB.append(json.format(spaceCount + this.spaceCount, isAddSpace));
		}else if (Map.class.isAssignableFrom(cs)) {
			Json json = JsonObject.fromMap((Map)value, jsonConfig);
			json.spaceCount = this.spaceCount;
			jsonSB.append(json.format(spaceCount + this.spaceCount, isAddSpace));
		}else if (cs.isArray()) {
			Json json = JsonArray.fromArray(value, jsonConfig);
			json.spaceCount = this.spaceCount;
			jsonSB.append(json.format(spaceCount + this.spaceCount, isAddSpace));
		}else if (Collection.class.isAssignableFrom(cs)) {
			Json json = JsonArray.fromCollection((Collection<?>) value, jsonConfig);
			json.spaceCount = this.spaceCount;
			jsonSB.append(json.format(spaceCount + this.spaceCount, isAddSpace));
		}else if (cs == Boolean.TYPE || cs == Boolean.class) {
			jsonSB.append(Boolean.TRUE.equals(value)); 
		} else {
			value = value.toString();
			value = jsonConfig.getSpecialCharacterConfig().toString((String) value);
			jsonSB.append(JsonUtil.DOUBLE_QUOTES).append(value)
					.append(JsonUtil.DOUBLE_QUOTES);
		}
	}
	
	/**
	 * 获取json格式字符串
	 * 
	 * @return
	 */
	public abstract String toString();
	
	/**
	 * 浅克隆
	 * 
	 * @param value
	 * @return 当给定的对象与本对象相同，则返回其副本，否则返回给定的对象
	 */
	public abstract Object clone(Object value);
	
	/**
	 * 把json格式的字符串转换成JsonObject对象或JsonArray对象
	 * 
	 * @param jsonString
	 * @param jsonConfig
	 * @return
	 */
	protected static Object parser(String jsonString, JsonConfig jsonConfig){
		Stack<String> tempStack = new Stack<String>();//存储分解后字符串
		Stack<Object> jsonStack = new Stack<Object>();//存储JsonObject|JsonArray|String对象 
		String origin = jsonString;
		if(jsonString.startsWith(JsonUtil.COMMA))
			throwJsonException(origin);
		
		while (!jsonString.equals(EMPTY)) {
			if (jsonString.startsWith(JsonUtil.COMMA)) {
				jsonString = jsonString.substring(COMMA_LEN).trim();
			}else if (jsonString.startsWith(JsonUtil.LEFT_M)) {//[开始
				jsonStack.push(new JsonArray(jsonConfig));//当遇到JsonUtil.LEFT_M时，向栈中放入一个JsonArray
				tempStack.push(JsonUtil.LEFT_M);
				jsonString = jsonString.substring(LEFT_M_LEN).trim();//截取JsonUtil.LEFT_M之后的字符串
			}else if (jsonString.startsWith(JsonUtil.LEFT_B)) {//{开始
				jsonStack.push(new JsonObject(jsonConfig, true));//当遇到JsonUtil.LEFT_B时，向栈中放入一个JsonObject
				tempStack.push(JsonUtil.LEFT_B);
				jsonString = jsonString.substring(LEFT_B_LEN).trim();//截取JsonUtil.LEFT_B之后的字符串
			}else if (jsonString.startsWith(JsonUtil.DOUBLE_QUOTES)) {//"开始
				String temp = jsonString;
				jsonString = jsonString.substring(DOUBLE_QUOTES_LEN);//截取JsonUtil.DOUBLE_QUOTES之后的字符串
				
				int count = 1;
				String k_v = "";
				int index = -1;
				while (count % 2 != 0) {
					count = 0;
					index = jsonString.indexOf(JsonUtil.DOUBLE_QUOTES, index + DOUBLE_QUOTES_LEN);//从截取JsonUtil.DOUBLE_QUOTES之后的字符串中获取第一次出现JsonUtil.DOUBLE_QUOTES的索引值
					if(index == NOT_FOUND_INDEX)//假如未找到，则说明不符合json格式
						throw new JsonException("..." + temp + "该处不符合json格式");
					k_v = jsonString.substring(0, index);//获取双引号之间的值
					int len = k_v.length();
					for(int i = len - 1; i >= 0; i-- ){
						if (k_v.charAt(i) == '\\') {
							count++;
						}else {
							break;
						}
					}
				}
				k_v = jsonConfig.getSpecialCharacterConfig().toRawString(k_v);
				jsonString = jsonString.substring(index + DOUBLE_QUOTES_LEN).trim();
				jsonString = dealEachElement(jsonString, jsonConfig, origin, jsonStack, k_v, temp);
			}else if (jsonString.startsWith(JsonUtil.RIGHT_B)) {
				if (tempStack.empty() || !JsonUtil.LEFT_B.equals(tempStack.peek()))
					throwJsonException(origin);
				else{
					tempStack.pop();
					jsonString = jsonString.substring(RIGHT_B_LEN).trim();
					initJson(jsonStack, jsonString);
				}
			}else if (jsonString.startsWith(JsonUtil.RIGHT_M)) {
				if (tempStack.empty() || !JsonUtil.LEFT_M.equals(tempStack.peek()))
					throwJsonException(origin);
				else{
					tempStack.pop();
					jsonString = jsonString.substring(RIGHT_M_LEN).trim();
					initJson(jsonStack, jsonString);
				}
			}else if (jsonString.startsWith("null")) {
				jsonString = jsonString.substring("null".length()).trim();
				if(!(jsonString.startsWith(JsonUtil.COMMA) || jsonString.startsWith(JsonUtil.RIGHT_M)))
					throw new JsonException("..." + jsonString + "该处不符合json格式");
				Object object = jsonStack.pop();
				if(JsonArray.class != object.getClass())
					throwJsonException(origin);
				((JsonArray) object).add(null);
				jsonStack.push(object);
			}else if (jsonString.startsWith("false")) {
				jsonString = jsonString.substring("false".length()).trim();
				if(!(jsonString.startsWith(JsonUtil.COMMA) || jsonString.startsWith(JsonUtil.RIGHT_M)))
					throw new JsonException("..." + jsonString + "该处不符合json格式");
				Object object = jsonStack.pop();
				if(JsonArray.class != object.getClass())
					throwJsonException(origin);
				((JsonArray) object).add(false);
				jsonStack.push(object);
			}else if (jsonString.startsWith("true")) {
				jsonString = jsonString.substring("true".length()).trim();
				if(!(jsonString.startsWith(JsonUtil.COMMA) || jsonString.startsWith(JsonUtil.RIGHT_M)))
					throw new JsonException("..." + jsonString + "该处不符合json格式");
				Object object = jsonStack.pop();
				if(JsonArray.class != object.getClass())
					throwJsonException(origin);
				((JsonArray) object).add(true);
				jsonStack.push(object);
			}else {
				String temp = jsonString;
				int index = getFirstIndexNotNumberOfString(jsonString);
				if(index == NOT_FOUND_INDEX)//假如未找到，则说明不符合json格式
					throw new JsonException("..." + temp + "该处不符合json格式");
				String k_v = jsonString.substring(0, index);//获取数字
				if(!XLPVerifedUtil.isNumber(k_v)) 
					throw new JsonException("..." + temp + "该处不符合json格式");
				
				jsonString = jsonString.substring(index).trim();
				if(!(jsonString.startsWith(JsonUtil.COMMA) || jsonString.startsWith(JsonUtil.RIGHT_M)))
					throw new JsonException("..." + jsonString + "该处不符合json格式");
				Object object = jsonStack.pop();
				if(JsonArray.class != object.getClass())
					throwJsonException(origin);
				((JsonArray) object).add(k_v);
				jsonStack.push(object);
				//jsonString = dealEachElement(jsonString, jsonConfig, origin, jsonStack, k_v, temp);
			}
		}
		if(!tempStack.empty())//判断堆栈是否为空
			throwJsonException(origin);

		if (jsonStack.size() != 1) 
			throwJsonException(origin);
		return jsonStack.pop();
	}
	
	/**
	 * 获取解析后的Json对象
	 * 
	 * @param jsonStack
	 * @param jsonString
	 * @return
	 */
	private static void initJson(Stack<Object> jsonStack, String jsonString) {
		Object firstObject = null, object;
		if (!jsonStack.empty()) {
			firstObject = jsonStack.pop();
		}
		
		if (!jsonStack.empty()) {
			Object preObject = null;
			object = jsonStack.pop();
			Class<?> cs = object.getClass();
			if (cs.equals(String.class)) {
				if(jsonStack.empty())
					throwJsonException(jsonString);
				preObject = jsonStack.pop();
				if(!preObject.getClass().equals(JsonObject.class))
					throwJsonException(jsonString);
				((JsonObject) preObject).put((String)object, firstObject);
				firstObject = preObject;
			}else if (cs.equals(JsonArray.class)) {
				((JsonArray)object).add(firstObject);
				firstObject = object;
			}else {
				throwJsonException(jsonString);
			}
		}
		jsonStack.push(firstObject);
	}

	/**
	 * 
	 * @param string
	 */
	protected static void throwJsonException(String string){
		throw new JsonException(string + "：该字符串不符合json格式");
	}
	
	/**
	 * 
	 * @param jsonString
	 * @param jsonConfig
	 * @param origin
	 * @param jsonStack
	 * @param k_v key或value值
	 * @param temp
	 * @return
	 */
	private static String dealEachElement(String jsonString, JsonConfig jsonConfig, String origin,
			Stack<Object> jsonStack, String k_v, String temp ){
		int index;
		if (jsonString.startsWith(JsonUtil.COMMA) || jsonString.startsWith(JsonUtil.RIGHT_M)) {//判断是否是jsonArray中的值
			Object object = jsonStack.pop();//获取堆栈中的第一个对象值
			if(object.getClass() != JsonArray.class)
				throwJsonException(origin);
			((JsonArray)object).add(k_v);
			jsonStack.push(object);//把对象放进堆栈中
		}else if (jsonString.startsWith(JsonUtil.COLON)) {//判断是否是json格式的键值对
			jsonString = jsonString.substring(COLON_LEN).trim();
			if (jsonString.startsWith(JsonUtil.LEFT_B)) {
				jsonStack.push(k_v);
			}else if (jsonString.startsWith(JsonUtil.LEFT_M)) {
				jsonStack.push(k_v);
			}else if (jsonString.startsWith(JsonUtil.DOUBLE_QUOTES)) {
				temp = jsonString;
				jsonString = jsonString.substring(DOUBLE_QUOTES_LEN);//截取JsonUtil.DOUBLE_QUOTES之后的字符串
				
				int count = 1;
				String _k_v = "";
				index = -1;
				while (count % 2 != 0) {
					count = 0;
					index = jsonString.indexOf(JsonUtil.DOUBLE_QUOTES, index + DOUBLE_QUOTES_LEN);//从截取JsonUtil.DOUBLE_QUOTES之后的字符串中获取第一次出现JsonUtil.DOUBLE_QUOTES的索引值
					if(index == NOT_FOUND_INDEX)//假如未找到，则说明不符合json格式
						throw new JsonException("..." + temp + "该处不符合json格式");
					_k_v = jsonString.substring(0, index);//获取双引号之间的值
					int len = _k_v.length();
					for(int i = len - 1; i >= 0; i-- ){
						if (_k_v.charAt(i) == '\\') {
							count++;
						}else {
							break;
						}
					}
				}

				jsonString = jsonString.substring(index + DOUBLE_QUOTES_LEN).trim();
				if(!(jsonString.startsWith(JsonUtil.COMMA) || jsonString.startsWith(JsonUtil.RIGHT_B)))
					throw new JsonException("..." + temp + "该处不符合json格式");
				
				_k_v = jsonConfig.getSpecialCharacterConfig().toRawString(_k_v);
				Object object = jsonStack.pop();
				if(JsonObject.class != object.getClass())
					throwJsonException(origin);
				((JsonObject) object).put(k_v, _k_v);
				jsonStack.push(object);
			}else if (jsonString.startsWith("null")) {
				jsonString = jsonString.substring("null".length()).trim();
				if(!(jsonString.startsWith(JsonUtil.COMMA) || jsonString.startsWith(JsonUtil.RIGHT_B)))
					throw new JsonException("..." + temp + "该处不符合json格式");
				Object object = jsonStack.pop();
				if(JsonObject.class != object.getClass())
					throwJsonException(origin);
				((JsonObject) object).put(k_v, null);
				jsonStack.push(object);
			}else if (jsonString.startsWith("false")) {
				jsonString = jsonString.substring("false".length()).trim();
				if(!(jsonString.startsWith(JsonUtil.COMMA) || jsonString.startsWith(JsonUtil.RIGHT_B)))
					throw new JsonException("..." + temp + "该处不符合json格式");
				Object object = jsonStack.pop();
				if(JsonObject.class != object.getClass())
					throwJsonException(origin);
				((JsonObject) object).put(k_v, false);
				jsonStack.push(object);
			}else if (jsonString.startsWith("true")) {
				jsonString = jsonString.substring("true".length()).trim();
				if(!(jsonString.startsWith(JsonUtil.COMMA) || jsonString.startsWith(JsonUtil.RIGHT_B)))
					throw new JsonException("..." + temp + "该处不符合json格式");
				Object object = jsonStack.pop();
				if(JsonObject.class != object.getClass())
					throwJsonException(origin);
				((JsonObject) object).put(k_v, true);
				jsonStack.push(object);
			}else {
				//获取指定字符串中非数字字符第一次出现的索引值
				index = getFirstIndexNotNumberOfString(jsonString);
				
				if(index == NOT_FOUND_INDEX)//假如未找到，则说明不符合json格式
					throw new JsonException("..." + jsonString + "该处不符合json格式");
				String _k_v = jsonString.substring(0, index);
				if(!XLPVerifedUtil.isNumber(_k_v))
					throw new JsonException("..." + jsonString + "该处不符合json格式");
				
				jsonString = jsonString.substring(index).trim();
				if(!(jsonString.startsWith(JsonUtil.COMMA) || jsonString.startsWith(JsonUtil.RIGHT_B)))
					throw new JsonException("..." + temp + "该处不符合json格式");
				
				_k_v = jsonConfig.getSpecialCharacterConfig().toRawString(_k_v);
				Object object = jsonStack.pop();
				if(JsonObject.class != object.getClass())
					throwJsonException(origin);
				((JsonObject) object).put(k_v, _k_v);
				jsonStack.push(object);
			}
			
		}else  {
			throw new JsonException("..." + temp + "该处不符合json格式");
		}
		
		return jsonString;
	}

	/**
	 * 获取指定字符串中非数字字符第一次出现的索引值
	 * 
	 * @param jsonString
	 * @return
	 */
	private static int getFirstIndexNotNumberOfString(String jsonString) {
		int index;
		index = NOT_FOUND_INDEX;
		int len = jsonString.length();
		for (int i = 0; i < len; i++) {
			char ch = jsonString.charAt(i);
			if ((ch >= '0' && ch <= '9') || ch == '+' || ch == '-' 
				|| ch == '.' || ch == 'e' || ch == 'E') 
				continue;
			index = i;
			break;
		}
		return index;
	}
	
	/**
	 * 设置toString()之前预处理器
	 * 
	 * @param outJsoStringPreDeal
	 */
	public void setOutJsoStringPreDeal(OutJsoStringPreDeal outJsoStringPreDeal) {
		this.outJsoStringPreDeal = outJsoStringPreDeal;
	}

	/**
	 * 
	 * 把json对象转换成json格式字符串后，把该字符串中一些特殊字符转换后输出
	 *
	 */
	public interface OutJsoStringPreDeal{
		public String preDeal(String string);
	}
	
	/**
	 * 格式json字符串
	 * 
	 * @param sb
	 * @param spaceCount 缩颈空格数
	 * @param isAddSpace 是否缩进
	 */
	protected void formatSpace(StringBuilder sb, int spaceCount, 
			boolean isAddSpace){
		if (isAddSpace) {
			if (spaceCount >= 0) {
				sb.append(XLPSystemParamUtil.getSystemNewline());
				for (int i = 0; i < spaceCount; i++) {
					sb.append(" ");
				}
			}
		}
	}

	/**
	 * 格式化json字符串
	 * 
	 * @param spaceCount 缩颈空格数
	 * @return
	 */
	public String format(int spaceCount) {
		this.spaceCount = spaceCount;
		return format(spaceCount, true);
	}
	
	/**
	 * 格式化json字符串
	 * 
	 * @return
	 */
	public String format() {
		return format(DEFALUT_SPACE_COUNT);
	}
	
	/**
	 * 格式化json字符串
	 * 
	 * @param spaceCount 缩颈空格数
	 * @param isAddSpace 是否缩进
	 * @return
	 */
	abstract String format(int spaceCount, boolean isAddSpace);
	
	/**
	 * 把给定的对象转换成jso格式的字符串
	 * 
	 * @param object 要转换的对象
	 * @param jsonConfig
	 * @param isFormat 是否格式化字符串，true：格式化 false：不格式化
	 * @return 
	 * @throws NullPointerException 假如给定的要转换的对象为null，则抛出该异常
	 */
	public static <T> String toJsonString(T object, JsonConfig jsonConfig, boolean isFormat){
		AssertUtils.isNotNull(object, "object paramter is null!");
		Class<?> cs = object.getClass();
		Json json;
		if (Json.class.isAssignableFrom(cs)) {
			json = (Json) object;
		}else if (Map.class.isAssignableFrom(cs)) {
			json = JsonObject.fromMap((Map<?, ?>)object, jsonConfig);
		}else if (Collection.class.isAssignableFrom(cs)) {
			json = JsonArray.fromCollection((Collection<?>)object, jsonConfig);
		}else if (cs.isArray()) {
			json = JsonArray.fromArray(object, jsonConfig);
		}else {
			json = JsonObject.fromBean(object, jsonConfig, true);
		}
		return isFormat ? json.format() : json.toString();
	}
	
	/**
	 * 把给定的对象转换成jso格式的字符串
	 * 
	 * @param object 要转换的对象
	 * @param isFormat 是否格式化字符串，true：格式化 false：不格式化
	 * @return 
	 * @throws NullPointerException 假如给定的要转换的对象为null，则抛出该异常
	 */
	public static <T> String toJsonString(T object, boolean isFormat){
		return toJsonString(object, null, isFormat);
	}
	
	/**
	 * 把给定的对象转换成jso格式的字符串
	 * 
	 * @param object 要转换的对象
	 * @param jsonConfig
	 * @return 
	 * @throws NullPointerException 假如给定的要转换的对象为null，则抛出该异常
	 */
	public static <T> String toJsonString(T object, JsonConfig jsonConfig){
		return toJsonString(object, jsonConfig, false);
	}
	
	/**
	 * 把给定的对象转换成jso格式的字符串
	 * 
	 * @param object 要转换的对象
	 * @return 
	 * @throws NullPointerException 假如给定的要转换的对象为null，则抛出该异常
	 */
	public static <T> String toJsonString(T object){
		return toJsonString(object, null, false);
	}
}
