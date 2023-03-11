package org.xlp.json;

import org.xlp.javabean.annotation.Bean;
import org.xlp.javabean.processer.ValueProcesser;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

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
			int length = Array.getLength(value);//用反射获取数组的长度
			if (length == 0) return null;
			value = Array.get(value, 0);//用反射获取数组中的元素;
			if (value == null) return null;
			valueType = value.getClass();
			jsonElement = new JsonElement(valueType, value, 
					valueType.getAnnotation(Bean.class) != null, 
					jsonElement.isUsedAnnotation(),
					jsonElement.getJsonConfig(),
					jsonElement.getFormatter());
		}

		if (fieldType == char[].class && CharSequence.class.isAssignableFrom(valueType)) {
			value = value.toString().toCharArray();
		} else if (fieldType.equals(String.class) && (Collection.class.isAssignableFrom(valueType)
				|| valueType.equals(JsonArray.class) || valueType.isArray())) {
			value = jsonElement.getArrayString();
		} else if (fieldType.equals(String.class)) {
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
		} else if (Calendar.class.isAssignableFrom(fieldType)) {
			value = jsonElement.getCalendar();
		} else if (JsonObject.class.equals(valueType) && !JsonObject.class.equals(fieldType)) {
			value = ((JsonObject)value).toBean(fieldType, jsonElement.isUsedAnnotation());
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
		} else if (Number.class.isAssignableFrom(fieldType)) {
			value = jsonElement.getNumber();
		} else if (List.class.isAssignableFrom(fieldType)) {
			value = jsonElement.getList();
		} else if (Map.class.isAssignableFrom(fieldType)) {
			value = jsonElement.getMap();
		} else if (fieldType.equals(JsonObject.class)) {
			value = jsonElement.getJsonObject(jsonElement.getJsonConfig());
		} else if (fieldType.equals(JsonArray.class)) {
			value = jsonElement.getJsonArray(jsonElement.getJsonConfig()); 
		} 
		return value;
	}
}
