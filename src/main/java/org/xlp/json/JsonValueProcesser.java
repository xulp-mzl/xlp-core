package org.xlp.json;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.xlp.javabean.processer.ValueProcesser;
import org.xlp.json.annotation.Bean;

/**
 * json对象转换bean时的值处理器
 * 
 * @author 徐龙平
 * 
 * @version 1.0
 */
class JsonValueProcesser extends ValueProcesser{
	public JsonValueProcesser(){
		
	}
	
	@Override
	public Object processValue(Class<?> fieldType, Object jsonElementoObject) {
		if (fieldType == null || jsonElementoObject == null)
			return null;
		JsonElement jsonElement = (JsonElement) jsonElementoObject;
		Class<?> valueType = jsonElement.getType();
		Object value = jsonElement.getValue();
		if(value == null || valueType == null)
			return null;
		
		//当字段类型不是数组类型时而value的值是数组时，处理成适合的值
		if (!fieldType.isArray() && valueType.isArray()) {
			if (valueType == byte[].class && ((byte[])value).length > 0) {
				value = ((byte[])value)[0];
			}else if (valueType == int[].class && ((int[])value).length > 0) {
				value = ((int[])value)[0];
			}else if (valueType == short[].class && ((short[])value).length > 0) {
				value = ((short[])value)[0];
			}else if (valueType == long[].class && ((long[])value).length > 0) {
				value = ((long[])value)[0];
			}else if (valueType == char[].class && ((char[])value).length > 0) {
				value = ((char[])value)[0];
			}else if (valueType == float[].class && ((float[])value).length > 0) {
				value = ((float[])value)[0];
			}else if (valueType == double[].class && ((double[])value).length > 0) {
				value = ((double[])value)[0];
			}else if (fieldType == String.class && valueType != String[].class) {
				value = jsonElement.getArrayString();
			}else if (valueType == boolean[].class && ((boolean[])value).length > 0) {
				value = ((boolean[])value)[0];
			}else if ((value instanceof Object[]) && ((Object[])value).length > 0) {
				value = ((Object[])value)[0];
			}else {
				return null;
			}
			
			valueType = value.getClass(); 
			jsonElement = new JsonElement(valueType, value, 
					valueType.getAnnotation(Bean.class) != null, jsonElement.getJsonConfig());
		}else if (fieldType.isArray() && !valueType.isArray()) {
			if (fieldType == char[].class && valueType.equals(String.class)) {
				value = ((String)value).toCharArray();
			}
		}
		
		if (fieldType.equals(String.class)) {
			value = jsonElement.getString();
		} else if (fieldType.equals(Integer.TYPE)
				|| fieldType.equals(Integer.class)) {
			value = jsonElement.getInteger();
		} else if (fieldType.equals(Long.TYPE) 
				|| fieldType.equals(Long.class)) {
			value = jsonElement.getLong();
		} else if (fieldType.equals(Short.TYPE)
				|| fieldType.equals(Short.class)) {
			value = jsonElement.getShort();
		} else if (fieldType.equals(Double.TYPE)
				|| fieldType.equals(Double.class)) {
			value = jsonElement.getDouble();
		} else if (fieldType.equals(Float.TYPE)
				|| fieldType.equals(Float.class)) {
			value = jsonElement.getFloat();
		} else if (fieldType.equals(Byte.class) 
				|| fieldType.equals(Byte.TYPE)) {
			value = jsonElement.getByte();
		} else if (fieldType.equals(Boolean.TYPE)
				|| fieldType.equals(Boolean.class)) {
			value = jsonElement.getBoolean();
		} else if (fieldType.equals(Character.TYPE)
				|| fieldType.equals(Character.class)) {
				value = jsonElement.getCharacter();
		} else if (fieldType.equals(Date.class)){
			value = jsonElement.getDate();
		} else if (fieldType.equals(java.sql.Date.class)) {
			value = jsonElement.getSqlDate();
		} else if (fieldType.equals(java.sql.Time.class)) {
			value = jsonElement.getTime();
		} else if (fieldType.equals(Timestamp.class)) {
			value = jsonElement.getTimestamp();
		} else if (fieldType.isAssignableFrom(Calendar.class)) {
			value = jsonElement.getCalendar();
		} else if (fieldType == String.class && (Collection.class.isAssignableFrom(valueType)
				|| valueType == JsonArray.class || valueType.isArray())) {
			value = jsonElement.getArrayString();
		} else if (JsonObject.class.equals(valueType) && !JsonObject.class.equals(fieldType)) {  
			value = ((JsonObject)value).toBean(fieldType);
		} else if (fieldType.equals(LocalDateTime.class)){
			value = jsonElement.getLocalDateTime();
		} else if (fieldType.equals(LocalDate.class)) {
			value = jsonElement.getLocalDate();
		} else if (fieldType.equals(LocalTime.class)) {
			value = jsonElement.getLocalTime();
		} else if (fieldType.equals(BigInteger.class)){
			value = jsonElement.getBigInteger();
		} else if (fieldType.equals(BigDecimal.class)) {
			value = jsonElement.getBigDecimal();
		} else if (fieldType.isAssignableFrom(Number.class)) {
			value = jsonElement.getNumber();
		} else if (fieldType.isAssignableFrom(List.class)) {
			value = jsonElement.getList();
		} else if (fieldType.isAssignableFrom(Map.class)) {
			value = jsonElement.getMap();
		} else if (fieldType.equals(JsonObject.class)) {
			value = jsonElement.getJsonObject(jsonElement.getJsonConfig());
		} else if (fieldType.equals(JsonArray.class)) {
			value = jsonElement.getJsonArray(jsonElement.getJsonConfig()); 
		} 
		
		return value;
	}
}
