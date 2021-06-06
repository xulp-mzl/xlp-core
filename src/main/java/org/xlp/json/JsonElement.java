package org.xlp.json;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.xlp.javabean.annotation.Formatter;
import org.xlp.json.config.JsonConfig;
import org.xlp.json.exception.JsonParseException;
import org.xlp.json.jenum.Flag;
import org.xlp.json.utils.JsonUtil;
import org.xlp.json.utils.PackingTypeUtil;
import org.xlp.utils.XLPBooleanUtil;
import org.xlp.utils.XLPDateUtil;
import org.xlp.utils.XLPFormatterUtil;
import org.xlp.utils.XLPStringUtil;

/**
 * json元素
 * 
 * @author 徐龙平
 * 
 * @version 1.0
 */
public class JsonElement {
	//值类型
	private Class<?> type;
	//值
	private Object value;
	//标记是否是bean类型
	private boolean bean = false;
	
	/**
	 * 标记当对象为Javabean，是否注解字段名称进行映射，值为true，是，false否
	 */
	private boolean isUsedAnnotation = true;
	
	private JsonConfig jsonConfig = new JsonConfig();
	
	/**
	 * 字段格式化模式
	 */
	private String formatter;
	
	protected JsonElement(){}
	
	public JsonElement(Class<?> type, Object value) {
		this(type, value, false);
	}
	
	public JsonElement(Class<?> type, Object value, JsonConfig jsonConfig) {
		this(type, value, false, jsonConfig);
	}

	public JsonElement(Class<?> type, Object value, boolean bean) {
		this(type, value, bean, null);
	}
	
	/**
	 * 构造JsonElement对象
	 * 
	 * @param type
	 * @param value
	 * @param bean
	 * @param isUsedAnnotation 
	 * 				标记当对象为Javabean，是否注解字段名称进行映射，值为true，是，false否
	 * @param formatter 字段格式化模式
	 * @param jsonConfig
	 */
	public JsonElement(Class<?> type, Object value, boolean bean, 
			boolean isUsedAnnotation, JsonConfig jsonConfig, String formatter) {
		this.type = type;
		this.value = value;
		this.bean = bean;
		setJsonConfig(jsonConfig); 
		this.isUsedAnnotation = isUsedAnnotation;
		this.formatter = formatter;
	}
	
	/**
	 * 构造JsonElement对象
	 * 
	 * @param type
	 * @param value
	 * @param bean
	 * @param isUsedAnnotation 
	 * 				标记当对象为Javabean，是否注解字段名称进行映射，值为true，是，false否
	 * @param formatter 字段格式化模式
	 * @param jsonConfig
	 */
	JsonElement(Class<?> type, Object value, boolean bean, 
			boolean isUsedAnnotation, JsonConfig jsonConfig, Formatter formatter) {
		this(type, value, bean, isUsedAnnotation, jsonConfig);
		setFormatter(formatter); 
	}
	
	/**
	 * 构造JsonElement对象
	 * 
	 * @param type
	 * @param value
	 * @param bean
	 * @param isUsedAnnotation 
	 * 				标记当对象为Javabean，是否注解字段名称进行映射，值为true，是，false否
	 * @param jsonConfig
	 */
	public JsonElement(Class<?> type, Object value, boolean bean, 
			boolean isUsedAnnotation, JsonConfig jsonConfig) {
		this(type, value, bean, isUsedAnnotation, jsonConfig, (String)null);
	}
	
	public JsonElement(Class<?> type, Object value, boolean bean, JsonConfig jsonConfig) {
		this(type, value, bean, true, jsonConfig);
	}
	
	public Class<?> getType() {
		return type;
	}
	
	void setType(Class<?> type) {
		this.type = type;
	}
	
	public Object getValue() {
		return value;
	}
	
	void setValue(Object value) {
		this.value = value;
	}

	public void setBean(boolean isBean) {
		this.bean = isBean;
	}

	public boolean isBean() {
		return bean;
	}
	
	public JsonConfig getJsonConfig() {
		return jsonConfig;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("JsonElement [bean=");
		builder.append(bean);
		builder.append(", type=");
		builder.append(type);
		builder.append(", value=");
		builder.append(value);
		builder.append("]");
		return builder.toString();
	}
	
	/**
	 * 获取JsonElement中的值
	 * 
	 * @return
	 */
	public Object getObject(){
		return getValue();
	}
	
	/**
	 * 获取JsonElement中的值
	 * 
	 * @param defaultValue
	 * @return 假如JsonElement中的值为null，则返回给定的defaultValue值
	 */
	public Object getObject(Object defaultValue){
		Object object = getObject();
		return object == null ? defaultValue : object;
	}
	
	/**
	 * 获取JsonElement中的值
	 * 
	 * @return
	 */
	public String getString(){
		return getString(null);
	}
	
	/**
	 * 获取JsonElement中的值
	 * 
	 * @param defaultValue
	 * @return 假如JsonElement中的值为null，则返回给定的defaultValue值
	 */
	public String getString(String defaultValue){
		if(value == null)
			return defaultValue;
		
		return objToString(type, value);
	}

	/**
	 * 把一个对象变成string
	 * 
	 * @param type
	 * @param value
	 * @return
	 */
	private String objToString(Class<?> cs, Object value) {
		String strVal;
		if ((cs == Long.TYPE || cs == Long.class) && XLPStringUtil.containSubString(formatter, "[yMmdHhs]")) {
			strVal = XLPDateUtil.longDateFormat((Long)value, formatter);
		}else if(PackingTypeUtil.isDecimalType(cs)){
			strVal = XLPStringUtil.isEmpty(formatter) ? jsonConfig.getNumberConfig().toDecimalString((Number)value)
					: XLPFormatterUtil.format(formatter, (Number)value);
		}else if(PackingTypeUtil.isNumberType(cs)){
			strVal = XLPStringUtil.isEmpty(formatter) ? jsonConfig.getNumberConfig().toIntString((Number)value)
					: XLPFormatterUtil.format(formatter, (Number)value);
		}else if (Date.class == cs || Timestamp.class == cs) {
			strVal = XLPStringUtil.isEmpty(formatter) 
					? jsonConfig.getDateFormatConfig().utilDateToString((Date) value)
					: XLPDateUtil.dateToString((Date)value, formatter);
		}else if (Time.class == cs) {
			strVal = XLPStringUtil.isEmpty(formatter) 
					? jsonConfig.getDateFormatConfig().timeToString((Time) value)
					: XLPDateUtil.dateToString((Time)value, formatter);
		}else if (java.sql.Date.class == cs) {
			strVal = XLPStringUtil.isEmpty(formatter) 
					? jsonConfig.getDateFormatConfig().dateToString((java.sql.Date) value)
					: XLPDateUtil.dateToString((java.sql.Date)value, formatter);
		}else if (Calendar.class.isAssignableFrom(cs)) {
			strVal = XLPStringUtil.isEmpty(formatter) 
					? jsonConfig.getDateFormatConfig().calendarToString((Calendar) value)
					: XLPDateUtil.dateToString(((Calendar)value).getTime(), formatter);
		}else if (LocalDateTime.class == cs) {
			strVal = XLPStringUtil.isEmpty(formatter) 
					? jsonConfig.getDateFormatConfig().localDateTimeToString((LocalDateTime) value)
					: XLPDateUtil.dateToString(value, formatter);
		}else if (LocalDate.class == cs) {
			strVal = XLPStringUtil.isEmpty(formatter) 
					? jsonConfig.getDateFormatConfig().localDateToString((LocalDate) value)
					: XLPDateUtil.dateToString(value, formatter);
		}else if (LocalTime.class == cs) {
			strVal = XLPStringUtil.isEmpty(formatter) 
					? jsonConfig.getDateFormatConfig().localTimeToString((LocalTime) value)
					: XLPDateUtil.dateToString(value, formatter);
		}else if (bean) {
			Json json = JsonObject.fromBean(value, jsonConfig, isUsedAnnotation);
			strVal = json.toString();
		}else if (Json.class.isAssignableFrom(cs)) {
			Json json = (Json) value;
			json.setJsonConfig(jsonConfig);
			strVal = json.toString();
		}else if (Map.class.isAssignableFrom(cs)) {
			@SuppressWarnings({ "unchecked", "rawtypes" })
			Json json = JsonObject.fromMap((Map)value, jsonConfig);
			strVal = json.toString();
		}else if (cs.isArray()) {
			Json json = JsonArray.fromArray(value, jsonConfig);
			strVal = json.toString();
		}else if (Collection.class.isAssignableFrom(cs)) {
			Json json = JsonArray.fromCollection((Collection<?>) value, jsonConfig);
			strVal = json.toString();
		}else {
			strVal = value.toString();
		}
		return strVal;
	}
	
	/**
	 * 获取JsonElement中的值
	 * 
	 * @return
	 * @throws ClassCastException 假如类转换异常，则抛出该异常
	 */
	@SuppressWarnings("unchecked")
	public <T> T get(){
		return (T) new JsonValueProcesser().processValue(type, this);
	}
	
	/**
	 * 获取JsonElement中的值
	 * 
	 * @param defaultValue
	 * @return 假如JsonElement中的值为null或无法解析成给定类型的值，则返回给定的defaultValue值
	 */
	public <T> T get(T defaultValue){
		T object = null;
		try {
			object = get();
		} catch (Exception e) {
		}
		return object == null ? defaultValue : object; 
	}
	
	/**
	 * 获取JsonElement中的值
	 * 
	 * @return 
	 * @throws JsonParseException
	 * 			假如无法解析成给定类型的值，则抛出该异常
	 */
	public int getint(){
		Integer v = getInteger();
		if (v != null) {
			return v.intValue();
		}
		throw throwJsonParseException("int");
	}
	
	/**
	 * 获取JsonElement中的值
	 * 
	 * @param defaultValue
	 * @return 假如JsonElement中的值为null或""或无法解析成给定类型的值，则返回给定的defaultValue值
	 */
	public int getint(int defaultValue){
		return getInteger(Integer.valueOf(defaultValue)).intValue();
	}
	
	/**
	 * 获取JsonElement中的值
	 * 
	 * @param defaultValue
	 * @return 假如JsonElement中的值为null或""或无法解析成给定类型的值，则返回给定的defaultValue值
	 */
	public Integer getInteger(Integer defaultValue){
		Integer v = defaultValue;
		try {
			v = getInteger();
		} catch (Exception e) {
		}
		return v == null ? defaultValue : v;
	}
	
	/**
	 * 获取JsonElement中的值
	 * 
	 * @return 假如JsonElement中的值为null或""，则返回null
	 * @throws JsonParseException
	 * 			假如无法解析成给定类型的值，则抛出该异常
	 */
	public Integer getInteger(){
		if(valueIsEmptyOrNull())
			return null;
		if(PackingTypeUtil.isNumber(value))
			return ((Number)value).intValue();
		if(CharSequence.class.isAssignableFrom(type)){
			if(!XLPStringUtil.isEmpty(formatter)) {
				return XLPFormatterUtil.parse(formatter, value.toString()).intValue();
			}
			return Integer.valueOf(value.toString());
		}
		throw throwJsonParseException("Integer");
	}
	
	/**
	 * 获取JsonElement中的值
	 * 
	 * @return 
	 * @throws JsonParseException
	 * 			假如无法解析成给定类型的值，则抛出该异常
	 */
	public byte getbyte(){
		Byte v = getByte();
		if (v != null) {
			return v.byteValue();
		}
		throw throwJsonParseException("byte");
	}
	
	/**
	 * 获取JsonElement中的值
	 * 
	 * @param defaultValue
	 * @return 假如JsonElement中的值为null或""或无法解析成给定类型的值，则返回给定的defaultValue值
	 */
	public byte getbyte(byte defaultValue){
		return getByte(Byte.valueOf(defaultValue)).byteValue();
	}
	
	/**
	 * 获取JsonElement中的值
	 * 
	 * @param defaultValue
	 * @return 假如JsonElement中的值为null或""或无法解析成给定类型的值，则返回给定的defaultValue值
	 */
	public Byte getByte(Byte defaultValue){
		Byte v = defaultValue;
		try {
			v = getByte();
		} catch (Exception e) {
		}
		return v == null ? defaultValue : v;
	}
	
	/**
	 * 获取JsonElement中的值
	 * 
	 * @return 假如JsonElement中的值为null或""，则返回null
	 * @throws JsonParseException
	 * 			假如无法解析成给定类型的值，则抛出该异常
	 */
	public Byte getByte(){
		if (valueIsEmptyOrNull())
			return null;
		if(PackingTypeUtil.isNumber(value))
			return ((Number)value).byteValue();
		if(CharSequence.class.isAssignableFrom(type)){
			if(!XLPStringUtil.isEmpty(formatter)) {
				return XLPFormatterUtil.parse(formatter, value.toString()).byteValue();
			}
			return Byte.valueOf(value.toString());
		}
		throw throwJsonParseException("Byte");
	}
	
	/**
	 * 获取JsonElement中的值
	 * 
	 * @return 
	 * @throws JsonParseException
	 * 			假如无法解析成给定类型的值，则抛出该异常
	 */
	public short getshort(){
		Short v = getShort();
		if (v != null) {
			return v.shortValue();
		}
		throw throwJsonParseException("short");
	}
	
	/**
	 * 获取JsonElement中的值
	 * 
	 * @param defaultValue
	 * @return 假如JsonElement中的值为null或""或无法解析成给定类型的值，则返回给定的defaultValue值
	 */
	public short getshort(short defaultValue){
		return getShort(Short.valueOf(defaultValue)).shortValue();
	}
	
	/**
	 * 获取JsonElement中的值
	 * 
	 * @param defaultValue
	 * @return 假如JsonElement中的值为null或""或无法解析成给定类型的值，则返回给定的defaultValue值
	 */
	public Short getShort(Short defaultValue){
		Short v = defaultValue;
		try {
			v = getShort();
		} catch (Exception e) {
		}
		return v == null ? defaultValue : v;
	}
	
	/**
	 * 获取JsonElement中的值
	 * 
	 * @return 假如JsonElement中的值为null或""，则返回null
	 * @throws JsonParseException
	 * 			假如无法解析成给定类型的值，则抛出该异常
	 */
	public Short getShort(){
		if (valueIsEmptyOrNull())
			return null;
		if(PackingTypeUtil.isNumber(value))
			return ((Number)value).shortValue();
		if(CharSequence.class.isAssignableFrom(type)){
			if(!XLPStringUtil.isEmpty(formatter)) {
				return XLPFormatterUtil.parse(formatter, value.toString()).shortValue();
			}
			return Short.valueOf(value.toString());
		}
		throw throwJsonParseException("Short");
	}
	
	/**
	 * 获取JsonElement中的值
	 * 
	 * @return 
	 * @throws JsonParseException
	 * 			假如无法解析成给定类型的值，则抛出该异常
	 */
	public long getlong(){
		Long v = getLong();
		if (v != null) {
			return v.longValue();
		}
		throw throwJsonParseException("long");
	}
	
	/**
	 * 获取JsonElement中的值
	 * 
	 * @param defaultValue
	 * @return 假如JsonElement中的值为null或""或无法解析成给定类型的值，则返回给定的defaultValue值
	 */
	public long getlong(long defaultValue){
		return getLong(Long.valueOf(defaultValue)).longValue();
	}
	
	/**
	 * 获取JsonElement中的值
	 * 
	 * @param defaultValue
	 * @return 假如JsonElement中的值为null或""或无法解析成给定类型的值，则返回给定的defaultValue值
	 */
	public Long getLong(Long defaultValue){
		Long v = defaultValue;
		try {
			v = getLong();
		} catch (Exception e) {
		}
		return v == null ? defaultValue : v;
	}
	
	/**
	 * 获取JsonElement中的值
	 * 
	 * @return 假如JsonElement中的值为null或""，则返回null
	 * @throws JsonParseException
	 * 			假如无法解析成给定类型的值，则抛出该异常
	 */
	public Long getLong(){
		if (valueIsEmptyOrNull())
			return null;
		if(PackingTypeUtil.isNumber(value))
			return ((Number)value).longValue();
		if(CharSequence.class.isAssignableFrom(type)){
			if (XLPStringUtil.containSubString(formatter, "[yMmdHhs]")) {
				return XLPDateUtil.stringDateToLong(value.toString(), formatter);
			}else if(!XLPStringUtil.isEmpty(formatter)) {
				return XLPFormatterUtil.parse(formatter, value.toString()).longValue();
			}
			return Long.valueOf(value.toString());
		}
		if (value instanceof Date) 
			return ((Date)value).getTime();
		if (value instanceof LocalDateTime) 
			return XLPDateUtil.localDateTimeToLongDate((LocalDateTime)value);
		if (value instanceof LocalDate) 
			return XLPDateUtil.localDateToLongDate((LocalDate)value);
		if (value instanceof LocalTime)
			return XLPDateUtil.localTimeToLongDate((LocalTime)value);
		if (value instanceof Calendar) 
			return ((Calendar)value).getTimeInMillis();
		throw throwJsonParseException("Long");
	}
	
	/**
	 * 获取JsonElement中的值
	 * 
	 * @return 
	 * @throws JsonParseException
	 * 			假如无法解析成给定类型的值，则抛出该异常
	 */
	public float getfloat(){
		Float v = getFloat();
		if (v != null) {
			return v.floatValue();
		}
		throw throwJsonParseException("float");
	}
	
	/**
	 * 获取JsonElement中的值
	 * 
	 * @param defaultValue
	 * @return 假如JsonElement中的值为null或""或无法解析成给定类型的值，则返回给定的defaultValue值
	 */
	public float getfloat(float defaultValue){
		return getFloat(Float.valueOf(defaultValue)).floatValue();
	}
	
	/**
	 * 获取JsonElement中的值
	 * 
	 * @param defaultValue
	 * @return 假如JsonElement中的值为null或""或无法解析成给定类型的值，则返回给定的defaultValue值
	 */
	public Float getFloat(Float defaultValue){
		Float v = defaultValue;
		try {
			v = getFloat();
		} catch (Exception e) {
		}
		return v == null ? defaultValue : v;
	}
	
	/**
	 * 获取JsonElement中的值
	 * 
	 * @return 假如JsonElement中的值为null或""，则返回null
	 * @throws JsonParseException
	 * 			假如无法解析成给定类型的值，则抛出该异常
	 */
	public Float getFloat(){
		if (valueIsEmptyOrNull())
			return null;
		if(PackingTypeUtil.isNumber(value))
			return ((Number)value).floatValue();
		if(CharSequence.class.isAssignableFrom(type)){
			if(!XLPStringUtil.isEmpty(formatter)) {
				return XLPFormatterUtil.parse(formatter, value.toString()).floatValue();
			}
			return Float.valueOf(value.toString());
		}
		throw throwJsonParseException("Float");
	}
	
	/**
	 * 获取JsonElement中的值
	 * 
	 * @return 
	 * @throws JsonParseException
	 * 			假如无法解析成给定类型的值，则抛出该异常
	 */
	public double getdouble(){
		Double v = getDouble();
		if (v != null) {
			return v.doubleValue();
		}
		throw throwJsonParseException("double");
	}
	
	/**
	 * 获取JsonElement中的值
	 * 
	 * @param defaultValue
	 * @return 假如JsonElement中的值为null或""或无法解析成给定类型的值，则返回给定的defaultValue值
	 */
	public double getdouble(double defaultValue){
		return getDouble(Double.valueOf(defaultValue)).doubleValue();
	}
	
	/**
	 * 获取JsonElement中的值
	 * 
	 * @param defaultValue
	 * @return 假如JsonElement中的值为null或""或无法解析成给定类型的值，则返回给定的defaultValue值
	 */
	public Double getDouble(Double defaultValue){
		Double v = defaultValue;
		try {
			v = getDouble();
		} catch (Exception e) {
		}
		return v == null ? defaultValue : v;
	}
	
	/**
	 * 获取JsonElement中的值
	 * 
	 * @return 假如JsonElement中的值为null或""，则返回null
	 * @throws JsonParseException
	 * 			假如无法解析成给定类型的值，则抛出该异常
	 */
	public Double getDouble(){
		if (valueIsEmptyOrNull())
			return null;
		if(PackingTypeUtil.isNumber(value))
			return ((Number)value).doubleValue();
		if(CharSequence.class.isAssignableFrom(type)){
			if(!XLPStringUtil.isEmpty(formatter)) {
				return XLPFormatterUtil.parse(formatter, value.toString()).doubleValue();
			}
			return Double.valueOf(value.toString());
		}
		throw throwJsonParseException("Double");
	}
	
	/**
	 * 获取JsonElement中的值
	 * 
	 * @param defaultValue
	 * @return 假如JsonElement中的值为null或""或无法解析成给定类型的值，则返回给定的defaultValue值
	 */
	public Number getNumber(Number defaultValue){
		Number v = defaultValue;
		try {
			v = getNumber();
		} catch (Exception e) {
		}
		return v == null ? defaultValue : v;
	}
	
	/**
	 * 获取JsonElement中的值
	 * 
	 * @return 假如JsonElement中的值为null或""，则返回null
	 * @throws JsonParseException
	 * 			假如无法解析成给定类型的值，则抛出该异常
	 */
	public Number getNumber(){
		if (valueIsEmptyOrNull())
			return null;
		if(PackingTypeUtil.isNumber(value))
			return (Number)value;
		if(CharSequence.class.isAssignableFrom(type)){
			if(!XLPStringUtil.isEmpty(formatter)) {
				return XLPFormatterUtil.parse(formatter, value.toString());
			}
			return new BigDecimal((String) value);
		}
		throw throwJsonParseException("Number");
	}
	
	/**
	 * 获取JsonElement中的值
	 * 
	 * @param defaultValue
	 * @return 假如JsonElement中的值为null或""或无法解析成给定类型的值，则返回给定的defaultValue值
	 */
	public BigInteger getBigInteger(BigInteger defaultValue){
		BigInteger v = defaultValue;
		try {
			v = getBigInteger();
		} catch (Exception e) {
		}
		return v == null ? defaultValue : v;
	}
	
	/**
	 * 获取JsonElement中的值
	 * 
	 * @return 假如JsonElement中的值为null或""，则返回null
	 * @throws JsonParseException
	 * 			假如无法解析成给定类型的值，则抛出该异常
	 */
	public BigInteger getBigInteger(){
		if (valueIsEmptyOrNull())
			return null;
		if(PackingTypeUtil.isNumber(value))
			return new BigInteger(value.toString());
		if(CharSequence.class.isAssignableFrom(type))
			return new BigInteger(value.toString());
		throw throwJsonParseException("BigInteger");
	}
	
	/**
	 * 获取JsonElement中的值
	 * 
	 * @param defaultValue
	 * @return 假如JsonElement中的值为null或""或无法解析成给定类型的值，则返回给定的defaultValue值
	 */
	public BigDecimal getBigDecimal(BigDecimal defaultValue){
		BigDecimal v = defaultValue;
		try {
			v = getBigDecimal();
		} catch (Exception e) {
		}
		return v == null ? defaultValue : v;
	}
	
	/**
	 * 获取JsonElement中的值
	 * 
	 * @return 假如JsonElement中的值为null或""，则返回null
	 * @throws JsonParseException
	 * 			假如无法解析成给定类型的值，则抛出该异常
	 */
	public BigDecimal getBigDecimal(){
		if (valueIsEmptyOrNull())
			return null;
		if(PackingTypeUtil.isNumber(value))
			return new BigDecimal(value.toString());
		if(CharSequence.class.isAssignableFrom(type))
			return new BigDecimal(value.toString());
		throw throwJsonParseException("BigDecimal");
	}
	
	/**
	 * 获取JsonElement中的值
	 * 
	 * @return 假如值为0或false，返回FALSE，假如值为1或true，返回true
	 * @throws JsonParseException
	 * 			假如无法解析成给定类型的值，则抛出该异常
	 */
	public boolean getboolean(){
		try {
			return XLPBooleanUtil.valueOf(value).booleanValue();
		} catch (Exception e) {
			throw throwJsonParseException("boolean");
		}
	}
	
	/**
	 * 获取JsonElement中的值
	 * 
	 * @param defaultValue
	 * @return 假如JsonElement中的值为null或无法解析成给定类型的值，则返回给定的defaultValue值
	 * 			<br/>假如值为0或false，返回FALSE，假如值为1或true，返回true
	 */
	public boolean getboolean(boolean defaultValue){
		try {
			return getboolean();
		} catch (Exception e) {
			return defaultValue;
		}
	}
	
	/**
	 * 获取JsonElement中的值
	 * 
	 * @return 假如值为0或false，返回FALSE，假如值为1或true，返回true
	 * @throws JsonParseException
	 * 			假如无法解析成给定类型的值，则抛出该异常
	 */
	public Boolean getBoolean(){
		try {
			if(valueIsEmptyOrNull())
				return null;
			return XLPBooleanUtil.valueOf(value);
		} catch (Exception e) {
			throw throwJsonParseException("Boolean");
		}
	}
	
	/**
	 * 获取JsonElement中的值
	 * 
	 * @param defaultValue
	 * @return 假如JsonElement中的值为null或无法解析成给定类型的值，则返回给定的defaultValue值
	 * 			<br/>假如值为0或false，返回FALSE，假如值为1或true，返回true
	 */
	public Boolean getBoolean(Boolean defaultValue){
		Boolean v = defaultValue;
		try {
			v = getBoolean();
		} catch (Exception e) {
		}
		return v == null ? defaultValue : v;
	}
	
	/**
	 * 获取JsonElement中的值
	 * 
	 * @return 
	 * @throws JsonParseException
	 * 			假如无法解析成给定类型的值，则抛出该异常
	 */
	public char getChar(){
		Character v = getCharacter();
		if (v != null) {
			return v.charValue();
		}
		throw throwJsonParseException("char");
	}
	
	/**
	 * 获取JsonElement中的值
	 * 
	 * @param defaultValue
	 * @return 假如JsonElement中的值为null或""或无法解析成给定类型的值，则返回给定的defaultValue值
	 */
	public char getChar(char defaultValue){
		return getCharacter(Character.valueOf(defaultValue)).charValue();
	}
	
	/**
	 * 获取JsonElement中的值
	 * 
	 * @param defaultValue
	 * @return 假如JsonElement中的值为null或""或无法解析成给定类型的值，则返回给定的defaultValue值
	 */
	public Character getCharacter(Character defaultValue){
		Character v = defaultValue;
		try {
			v = getCharacter();
		} catch (Exception e) {
		}
		return v == null ? defaultValue : v;
	}
	
	/**
	 * 获取JsonElement中的值
	 * 
	 * @return 假如JsonElement中的值为null或""，则返回null
	 * @throws JsonParseException
	 * 			假如无法解析成给定类型的值，则抛出该异常
	 */
	public Character getCharacter(){
		if (valueIsEmptyOrNull())
			return null;
		if(type == Character.TYPE || type == Character.class)
			return (Character)value;
		if(type == String.class && ((String)value).length() == 1)
			return ((String) value).charAt(0);
		throw throwJsonParseException("Character");
	}
	
	/**
	 * 获取JsonElement中的值
	 * 
	 * @return 假如该值为数组类型或JsonArray对象或Collection集合，返回的数据格式是：（xx,xx,xx...）
	 * 			<br/>假如值为null，则返回""
	 * @throws JsonParseException
	 * 			假如该值不是数组类型或JsonArray对象或Collection集合，则抛出该异常
	 */
	public String getArrayString(){
		if (type.isArray() || JsonArray.class == type 
				|| Collection.class.isAssignableFrom(type)) {
			return getArrayString(type, value);
		}else {
			throw new JsonParseException(value + "要解析的对象不是数组类型或JsonArray对象或Collection集合");
		}
	}
	
	/**
	 * 获取JsonElement中的值
	 * 
	 * @return 假如该值为数组类型或JsonArray对象或Collection集合，返回的数据格式是：（xx,xx,xx...）
	 * 			<br/>否则返回原值
	 */
	private String getArrayString(Class<?> type, Object value){
		if(value == null)
			return "";
		
		StringBuilder sb = new StringBuilder();
		if (type.isArray()) {
			int len = Array.getLength(value);
			Object o;
			boolean isAddComma = false;
			for (int i = 0; i < len; i++) {
				o =  Array.get(value, i);
				if(!XLPStringUtil.isNullOrEmpty(o)){
					if(isAddComma)
						sb.append(",");
					sb.append(objToString(o.getClass(), o)); 
					isAddComma = true;
				}
			}
		}else if (type == JsonArray.class) {
			Iterator<JsonElement> iterator = ((JsonArray)value).iterator();
			Object o;
			boolean isAddComma = false;
			while (iterator.hasNext()) {
				o = iterator.next().getObject();
				if(!XLPStringUtil.isNullOrEmpty(o)){
					if(isAddComma)
						sb.append(",");
					sb.append(objToString(o.getClass(), o));
					isAddComma = true;
				}
			}
		}else if (Collection.class.isAssignableFrom(type)) { 
			Iterator<?> iterator = ((Collection<?>)value).iterator();
			Object o;
			boolean isAddComma = false;
			while (iterator.hasNext()) {
				o = iterator.next();
				if(!XLPStringUtil.isNullOrEmpty(o)){
					if(isAddComma)
						sb.append(",");
					sb.append(objToString(o.getClass(), o));
					isAddComma = true;
				}
			}
		}else {
			sb.append(value); 
		}
		return sb.toString();
	}
	
	/**
	 * 获取JsonElement中的值
	 * 
	 * @param defaultValue
	 * @return 假如该值为数组类型或JsonArray对象或Collection集合，返回的数据格式是：（xx,xx,xx...）
	 * 			<br/>假如值为null，则返回"",假如该值不是数组类型或JsonArray对象或Collection集合，则返回defaultValue
	 */
	public String getArrayString(String defaultValue){
		try {
			return getArrayString();
		} catch (Exception e) {
		}
		return defaultValue;
	}
	
	/**
	 * 获取JsonElement中的值
	 * 
	 * @return 假如值为null，则返回null,否则返回JsonArray对象
	 * @throws JsonParseException
	 * 			假如无法解析成给定类型的值，则抛出该异常
	 */
	public JsonArray getJsonArray(){
		return getJsonArray(jsonConfig);
	}
	
	/**
	 * 获取JsonElement中的值
	 * 
	 * @param defaultValue
	 * @return 假如值为null或假如无法解析成给定类型的值，则返回defaultValue,否则返回JsonArray对象
	 */
	public JsonArray getJsonArray(JsonArray defaultValue){
		JsonArray v = defaultValue;
		try {
			v =	getJsonArray();
		} catch (Exception e) {
		}
		return v == null ? defaultValue : v;
	}
	
	/**
	 * 获取JsonElement中的值
	 * 
	 * @param jsonConfig json配置
	 * @return 假如值为null，则返回null,否则返回JsonArray对象
	 * @throws JsonParseException
	 * 			假如无法解析成给定类型的值，则抛出该异常
	 */
	public JsonArray getJsonArray(JsonConfig jsonConfig){
		if (valueIsEmptyOrNull())
			return null;
		if (JsonArray.class == type) {
			JsonArray jsonArray = (JsonArray) value;
			jsonArray.setJsonConfig(jsonConfig);
			return jsonArray;
		}
		
		if (CharSequence.class.isAssignableFrom(type)){
			return JsonArray.fromJsonString(value.toString(), jsonConfig);
		}
		
		if(type.isArray())
			return JsonArray.fromArray(value, jsonConfig);
		if(Collection.class.isAssignableFrom(type))
			return JsonArray.fromCollection((Collection<?>) value, jsonConfig); 
		
		throw throwJsonParseException("JsonArray");
	}
	
	/**
	 * 获取JsonElement中的值
	 * 
	 * @param jsonConfig
	 * @param defaultValue
	 * @return 假如值为null或假如无法解析成给定类型的值，则返回defaultValue,否则返回JsonArray对象
	 */
	public JsonArray getJsonArray(JsonConfig jsonConfig, JsonArray defaultValue){
		JsonArray v = defaultValue;
		try {
			v =	getJsonArray(jsonConfig);
		} catch (Exception e) {
		}
		return v == null ? defaultValue : v;
	}
	
	/**
	 * 获取JsonElement中的值
	 * 
	 * @return 假如值为null，则返回null,否则返回JsonObject对象
	 * @throws JsonParseException
	 * 			假如无法解析成给定类型的值，则抛出该异常
	 */
	public JsonObject getJsonObject(){
		return getJsonObject(jsonConfig);
	}
	
	/**
	 * 获取JsonElement中的值
	 * 
	 * @param defaultValue
	 * @return 假如值为null或假如无法解析成给定类型的值，则返回defaultValue,否则返回JsonObject对象
	 */
	public JsonObject getJsonObject(JsonObject defaultValue){
		JsonObject v = defaultValue;
		try {
			v = getJsonObject();
		} catch (Exception e) {
		}
		return v == null ? defaultValue : v;
	}
	
	/**
	 * 获取JsonElement中的值
	 * 
	 * @param jsonConfig json配置
	 * @return 假如值为null，则返回null,否则返回JsonObject对象
	 * @throws JsonParseException
	 * 			假如无法解析成给定类型的值，则抛出该异常
	 */
	public JsonObject getJsonObject(JsonConfig jsonConfig){
		if(valueIsEmptyOrNull())
			return null;
		if (type == JsonObject.class) {
			JsonObject jsonObject = (JsonObject) value;
			jsonObject.setJsonConfig(jsonConfig);
			return jsonObject;
		}
		
		if (bean) {
			return JsonObject.fromBean(value, jsonConfig, isUsedAnnotation);
		}
		
		if (Map.class.isAssignableFrom(type)) {
			return JsonObject.fromMap((Map<?, ?>) value, jsonConfig);
		}
		
		if (CharSequence.class.isAssignableFrom(type)){
			return JsonObject.fromJsonString(value.toString(), jsonConfig);
		}
		throw throwJsonParseException("JsonObject");
	}
	
	/**
	 * 获取JsonElement中的值
	 * 
	 * @param jsonConfig
	 * @param defaultValue
	 * @return 假如值为null或假如无法解析成给定类型的值，则返回defaultValue,否则返回JsonObject对象
	 */
	public JsonObject getJsonObject(JsonConfig jsonConfig, JsonObject defaultValue){
		JsonObject v = defaultValue;
		try {
			v = getJsonObject(jsonConfig);
		} catch (Exception e) {
		}
		return v == null ? defaultValue : v;
	}
	
	/**
	 * 获取JsonElement中的值
	 * 
	 * @return 假如该值为Map对象或JsonObject对象时，返回map集合
	 * 			<br/>假如值为null，则返回null
	 * @throws JsonParseException
	 * 			假如无法解析成给定类型的值，则抛出该异常
	 */
	public Map<?, ?> getMap(){
		if(value == null)
			return null;
		if(Map.class.isAssignableFrom(type))
			return (Map<?, ?>) value;
		if(type == JsonObject.class)
			return ((JsonObject)value).toMap();
		if (bean) 
			return JsonObject.fromBean(value, jsonConfig, isUsedAnnotation).toMap();
		throw throwJsonParseException("Map");
	}
	
	/**
	 * 获取JsonElement中的值
	 * 
	 * @param defaultValue
	 * @return 假如该值为Map对象或JsonObject对象时，返回map集合
	 * 			<br/>假如值为null或无法解析成给定类型的值，则返回defaultValue
	 */
	public Map<?, ?> getMap(Map<?, ?> defaultValue){
		Map<?, ?> v = defaultValue;
		try {
			v = getMap();
		} catch (Exception e) {
		}
		return v == null ? defaultValue : v;
	}
	
	/**
	 * 获取JsonElement中的值
	 * 
	 * @return 假如该值为数组类型或JsonArray对象时，返回数组对象
	 * 			<br/>假如值为null，则返回null
	 * @throws JsonParseException
	 * 			假如无法解析成给定类型的值，则抛出该异常
	 */
	public Object getArray(){
		if(value == null)
			return null;
		if(type.isArray())
			return value;
		if(type == JsonArray.class)
			return ((JsonArray)value).toArray();
		if (Collection.class.isAssignableFrom(type)) 
			return JsonArray.fromCollection((Collection<?>) value).toArray();
		throw throwJsonParseException("Array");
	}
	
	/**
	 * 获取JsonElement中的值
	 * 
	 * @param defaultArray
	 * @return 假如该值为数组类型或JsonArray对象时，返回数组对象
	 * 			<br/>假如值为null或无法解析成给定类型的值，则返回defaultArray
	 */
	public Object getArray(Object defaultArray){
		Object v = null;
		try {
			v = getArray();
		} catch (Exception e) {
		}
		return v == null ? defaultArray : v;
	}
	
	/**
	 * 获取JsonElement中的值
	 * 
	 * @param cs 返回的数组类型
	 * @return 假如该值为数组类型或JsonArray对象时，返回数组对象
	 * 			<br/>假如值为null，则返回null
	 * @throws JsonParseException
	 * 			假如无法解析成给定类型的值，则抛出该异常
	 */
	@SuppressWarnings("unchecked")
	public <T> T[] getArray(Class<?> cs){
		if(value == null)
			return null;
		if(type.isArray())
			return (T[]) value;
		if(type == JsonArray.class)
			return (T[]) ((JsonArray)value).toArray(cs);
		if (Collection.class.isAssignableFrom(type)) 
			return (T[]) JsonArray.fromCollection((Collection<T>) value).toArray(cs);
		throw throwJsonParseException("Array");
	}
	
	/**
	 * 获取JsonElement中的值
	 * 
	 * @param cs 返回的数组类型
	 * @param defaultArray
	 * @return 假如该值为数组类型或JsonArray对象时，返回数组对象
	 * 			<br/>假如值为null或无法解析成给定类型的值，则返回defaultArray
	 */
	public <T> T[] getArray(Class<?> cs, T[] defaultArray){
		T[] v = null;
		try {
			v = getArray(cs);
		} catch (Exception e) {
		}
		return v == null ? defaultArray : v;
	}
	
	/**
	 * 获取JsonElement中的值
	 * 
	 * @return 假如该值为Collection集合类型或JsonArray对象时，返回List集合
	 * 			<br/>假如值为null，则返回null
	 * @throws JsonParseException
	 * 			假如无法解析成给定类型的值，则抛出该异常
	 */
	public List<?> getList(){
		if(value == null)
			return null;
		if(List.class.isAssignableFrom(type))
			return (List<?>) value;
		if (Collection.class.isAssignableFrom(type))
			return new ArrayList<Object>((Collection<?>)value);
		if(type == JsonArray.class)
			return ((JsonArray)value).toList();
		if (type.isArray()) 
			return JsonArray.fromArray(value).toList();
		throw throwJsonParseException("List");
	}
	
	/**
	 * 获取JsonElement中的值
	 * 
	 * @param defaultList
	 * @return 假如该值为Collection集合类型或JsonArray对象时，返回List集合
	 * 			<br/>假如值为null或无法解析成给定类型的值，则返回defaultList
	 */
	public List<?> getList(List<?> defaultList){
		List<?> v = null;
		try {
			v = getList();
		} catch (Exception e) {
		}
		return v == null ? defaultList : v;
	}
	
	/**
	 * 获取JsonElement中的值
	 * 
	 * @param cs
	 * @return 假如该值为Collection集合类型或JsonArray对象时，返回List集合
	 * 			<br/>假如值为null，则返回null
	 * @throws JsonParseException
	 * 			假如无法解析成给定类型的值，则抛出该异常
	 */
	@SuppressWarnings("unchecked")
	public <E> List<E> getList(Class<?> cs){
		if(value == null)
			return null;
		if(List.class.isAssignableFrom(type))
			return (List<E>) value;
		if (Collection.class.isAssignableFrom(type))
			return new ArrayList<E>((Collection<E>)value);
		if(type == JsonArray.class)
			return (List<E>) ((JsonArray)value).toList(cs);
		if (type.isArray()) 
			return (List<E>) JsonArray.fromArray(value).toList(cs);
		throw throwJsonParseException("List");
	}
	
	/**
	 * 获取JsonElement中的值
	 * 
	 * @param cs
	 * @param defaultList
	 * @return 假如该值为Collection集合类型或JsonArray对象时，返回List集合
	 * 			<br/>假如值为null或无法解析成给定类型的值，则返回defaultList
	 */
	public <E> List<E> getList(Class<?> cs, List<E> defaultList){
		List<E> v = null;
		try {
			v = getList(cs);
		} catch (Exception e) {
		}
		return v == null ? defaultList : v;
	}
	
	protected JsonParseException throwJsonParseException(String typeDesc){
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append(type).append("->").append(value)
			.append(": 该值不能解析成").append(typeDesc).append("值");
		return new JsonParseException(sBuilder.toString());
	}
	
	/**
	 * 判断value值是否为""或null
	 * 
	 * @return 假如是返回true,否则返回false
	 */
	protected boolean valueIsEmptyOrNull(){
		return value == null || JsonUtil.EMPTY.equals(value);
	}
	
	/**
	 * 获取JsonElement中的值
	 * 
	 * @param format 时间转换格式
	 * @return 假如值为null，则返回null
	 * @throws JsonParseException
	 * 			假如无法解析成给定类型的值，则抛出该异常
	 */
	public Date getDate(String format){
		if(valueIsEmptyOrNull())
			return null;
		if(format == null)
			format = XLPDateUtil.DATE_FORMAT;
		
		if(value instanceof Date)
			return (Date) value;
		if (value instanceof Calendar) 
			return ((Calendar)value).getTime();
		if (value instanceof LocalDateTime) {
	        return XLPDateUtil.localDateTimeToDate((LocalDateTime)value);
		}
		if (value instanceof Number) 
			return new Date(((Number) value).longValue());
		if(CharSequence.class.isAssignableFrom(type))
			try {
				return XLPDateUtil.stringToDate(value.toString(), format);
			} catch (Exception e) {
				throw throwJsonParseException("Date");
			}
		throw throwJsonParseException("Date");
	}
	
	/**
	 * 获取JsonElement中的值
	 * 
	 * @return 假如值为null，则返回null
	 * @throws JsonParseException
	 * 			假如无法解析成给定类型的值，则抛出该异常
	 */
	public Date getDate(){
		return getDate(XLPStringUtil.isEmpty(formatter) 
				? jsonConfig.getDateFormatConfig().getDateTimeFormat() : formatter);
	}
	
	/**
	 * 获取JsonElement中的值
	 * 
	 * @param format 时间转换格式
	 * @param defaultValue
	 * @return 假如值为null或无法解析成给定类型的值，则返回defaultValue
	 */
	public Date getDate(Date defaultValue){
		Date v = null;
		try {
			v = getDate();
		} catch (Exception e) {
		}
		return v == null ? defaultValue : v;
	}
	
	/**
	 * 获取JsonElement中的值
	 * 
	 * @param format 时间转换格式
	 * @param defaultValue
	 * @return 假如值为null或无法解析成给定类型的值，则返回defaultValue
	 */
	public Date getDate(String format, Date defaultValue){
		Date v = null;
		try {
			v = getDate(format);
		} catch (Exception e) {
		}
		return v == null ? defaultValue : v;
	}
	
	/**
	 * 获取JsonElement中的值
	 * 
	 * @param format 时间转换格式
	 * @return 假如值为null，则返回null
	 * @throws JsonParseException
	 * 			假如无法解析成给定类型的值，则抛出该异常
	 */
	public Timestamp getTimestamp(String format){
		if(valueIsEmptyOrNull())
			return null;
		if(format == null)
			format = XLPDateUtil.DATE_FORMAT;
		
		if(Timestamp.class == type)
			return (Timestamp) value;
		if(Date.class == type)
			return new Timestamp(((Date)value).getTime());
		if(CharSequence.class.isAssignableFrom(type))
			try {
				return new Timestamp(XLPDateUtil.stringToDate(value.toString(), format)
						.getTime());
			} catch (Exception e) {
				throw throwJsonParseException("Timestamp");
			}
		throw throwJsonParseException("Timestamp");
	}
	
	/**
	 * 获取JsonElement中的值
	 * 
	 * @return 假如值为null，则返回null
	 * @throws JsonParseException
	 * 			假如无法解析成给定类型的值，则抛出该异常
	 */
	public Timestamp getTimestamp(){
		return getTimestamp(XLPStringUtil.isEmpty(formatter) 
				? jsonConfig.getDateFormatConfig().getDateTimeFormat() : formatter);
	}
	
	/**
	 * 获取JsonElement中的值
	 * 
	 * @param defaultValue
	 * @return 假如值为null或无法解析成给定类型的值，则返回defaultValue
	 */
	public Timestamp getTimestamp(Timestamp defaultValue){
		Timestamp v = null;
		try {
			v = getTimestamp();
		} catch (Exception e) {
		}
		return v == null ? defaultValue : v;
	}
	
	/**
	 * 获取JsonElement中的值
	 * 
	 * @param format 时间转换格式
	 * @param defaultValue
	 * @return 假如值为null或无法解析成给定类型的值，则返回defaultValue
	 */
	public Timestamp getTimestamp(String format, Timestamp defaultValue){
		Timestamp v = null;
		try {
			v = getTimestamp(format);
		} catch (Exception e) {
		}
		return v == null ? defaultValue : v;
	}
	
	/**
	 * 获取JsonElement中的值
	 * 
	 * @param format 时间转换格式
	 * @return 假如值为null，则返回null
	 * @throws JsonParseException
	 * 			假如无法解析成给定类型的值，则抛出该异常
	 */
	public java.sql.Date getSqlDate(String format){
		if(valueIsEmptyOrNull())
			return null;
		if(format == null)
			format = XLPDateUtil.DATE_DEFAULT_FORMAT;
		
		if(java.sql.Date.class == type)
			return (java.sql.Date) value;
		if(Date.class == type)
			return new java.sql.Date(((Date)value).getTime());
		if(CharSequence.class.isAssignableFrom(type))
			try {
				return new java.sql.Date(XLPDateUtil.stringToDate(value.toString(), format)
						.getTime());
			} catch (Exception e) {
				throw throwJsonParseException("Sql-Date");
			}
		throw throwJsonParseException("Sql-Date");
	}

	/**
	 * 获取JsonElement中的值
	 * 
	 * @return 假如值为null，则返回null
	 * @throws JsonParseException
	 * 			假如无法解析成给定类型的值，则抛出该异常
	 */
	public java.sql.Date getSqlDate(){
		return getSqlDate(XLPStringUtil.isEmpty(formatter) 
				? jsonConfig.getDateFormatConfig().getDateFormat() : formatter);
	}
	
	/**
	 * 获取JsonElement中的值
	 * 
	 * @param defaultValue
	 * @return 假如值为null或无法解析成给定类型的值，则返回defaultValue
	 */
	public java.sql.Date getSqlDate(java.sql.Date defaultValue){
		java.sql.Date v = null;
		try {
			v = getSqlDate();
		} catch (Exception e) {
		}
		return v == null ? defaultValue : v; 
	}
	
	/**
	 * 获取JsonElement中的值
	 * 
	 * @param format 时间转换格式
	 * @param defaultValue
	 * @return 假如值为null或无法解析成给定类型的值，则返回defaultValue
	 */
	public java.sql.Date getSqlDate(String format, java.sql.Date defaultValue){
		java.sql.Date v = null;
		try {
			v = getSqlDate(format);
		} catch (Exception e) {
		}
		return v == null ? defaultValue : v; 
	}
	
	/**
	 * 获取JsonElement中的值
	 * 
	 * @param format 时间转换格式
	 * @return 假如值为null，则返回null
	 * @throws JsonParseException
	 * 			假如无法解析成给定类型的值，则抛出该异常
	 */
	public Time getTime(String format){
		if(valueIsEmptyOrNull())
			return null;
		if(format == null)
			format = XLPDateUtil.TIME_DEFAULT_FORMAT;
		if(Time.class == type)
			return (Time) value;
		if(Date.class == type)
			return new Time(((Date)value).getTime());
		if(CharSequence.class.isAssignableFrom(type))
			try {
				return new Time(XLPDateUtil.stringToDate(value.toString(), format)
						.getTime());
			} catch (Exception e) {
				throw throwJsonParseException("Time");
			}
		throw throwJsonParseException("Time");
	}

	/**
	 * 获取JsonElement中的值
	 * 
	 * @return 假如值为null，则返回null
	 * @throws JsonParseException
	 * 			假如无法解析成给定类型的值，则抛出该异常
	 */
	public Time getTime(){
		return getTime(XLPStringUtil.isEmpty(formatter) 
				? jsonConfig.getDateFormatConfig().getTimeFormat() : formatter);
	}
	
	/**
	 * 获取JsonElement中的值
	 * 
	 * @param defaultValue
	 * @return 假如值为null或无法解析成给定类型的值，则返回defaultValue
	 */
	public Time getTime(Time defaultValue){
		Time v = null;
		try {
			v = getTime();
		} catch (Exception e) {
		}
		return v == null ? defaultValue : v;
	}
	
	/**
	 * 获取JsonElement中的值
	 * 
	 * @param format 时间转换格式
	 * @param defaultValue
	 * @return 假如值为null或无法解析成给定类型的值，则返回defaultValue
	 */
	public Time getTime(String format, Time defaultValue){
		Time v = null;
		try {
			v = getTime(format);
		} catch (Exception e) {
		}
		return v == null ? defaultValue : v;
	}
	
	/**
	 * 获取JsonElement中的值
	 * 
	 * @param format 时间转换格式
	 * @return 假如值为null，则返回null
	 * @throws JsonParseException
	 * 			假如无法解析成给定类型的值，则抛出该异常
	 */
	public Calendar getCalendar(String format){
		if(valueIsEmptyOrNull())
			return null;
		if(format == null)
			format = XLPDateUtil.DATE_FORMAT;
		
		if(value instanceof Date){
			return XLPDateUtil.dateToCalendar((Date) value);
		}
		if (value instanceof LocalDateTime) {
			return XLPDateUtil.localDateTimeToCalendar((LocalDateTime)value);
		}
		if (value instanceof Number) {
			return XLPDateUtil.longDateToCalendar(((Number) value).longValue());
		}
		if(Calendar.class.isAssignableFrom(type))
			return (Calendar) value;
		if(CharSequence.class.isAssignableFrom(type)){
			Calendar calendar = Calendar.getInstance();
			Date date;
			try {
				date = XLPDateUtil.stringToDate(value.toString(), format);
			} catch (Exception e) {
				throw throwJsonParseException("Calendar");
			}
			calendar.setTime(date);
			return calendar;
		}
		throw throwJsonParseException("Calendar");
	}

	/**
	 * 获取JsonElement中的值
	 * 
	 * @return 假如值为null，则返回null
	 * @throws JsonParseException
	 * 			假如无法解析成给定类型的值，则抛出该异常
	 */
	public Calendar getCalendar(){
		return getCalendar(XLPStringUtil.isEmpty(formatter) 
				? jsonConfig.getDateFormatConfig().getDateTimeFormat() : formatter);
	}
	
	/**
	 * 获取JsonElement中的值
	 * 
	 * @param defaultValue
	 * @return 假如值为null或无法解析成给定类型的值，则返回defaultValue
	 */
	public Calendar getCalendar(Calendar defaultValue){
		Calendar v = null;
		try {
			v = getCalendar();
		} catch (Exception e) {
		}
		return v == null ? defaultValue : v;
	}
	
	/**
	 * 获取JsonElement中的值
	 * 
	 * @param format 时间转换格式
	 * @param defaultValue
	 * @return 假如值为null或无法解析成给定类型的值，则返回defaultValue
	 */
	public Calendar getCalendar(String format, Calendar defaultValue){
		Calendar v = null;
		try {
			v = getCalendar(format);
		} catch (Exception e) {
		}
		return v == null ? defaultValue : v;
	}
	
	/**
	 * 获取JsonElement中的值
	 * 
	 * @param format 时间转换格式
	 * @return 假如值为null，则返回null
	 * @throws JsonParseException
	 * 			假如无法解析成给定类型的值，则抛出该异常
	 */
	public LocalDateTime getLocalDateTime(String format){
		if(valueIsEmptyOrNull())
			return null;
		if(format == null)
			format = XLPDateUtil.DATE_FORMAT;
		if(LocalDateTime.class == type)
			return (LocalDateTime) value;
		
		if (value instanceof Number) {
			return XLPDateUtil.longDateToLocalDateTime(((Number) value).longValue());
		}
		if (value instanceof Calendar) {
			return XLPDateUtil.dateToLocalDateTime(((Calendar)value).getTime()); 
		}
		if (value instanceof Date) {
			return XLPDateUtil.dateToLocalDateTime((Date)value); 
		}
		
		if(CharSequence.class.isAssignableFrom(type))
			try {
				return LocalDateTime.parse(value.toString(), DateTimeFormatter.ofPattern(format)); 
			} catch (Exception e) {
				throw throwJsonParseException("LocalDateTime");
			}
		throw throwJsonParseException("LocalDateTime");
	}
	
	/**
	 * 获取JsonElement中的值
	 * 
	 * @param format 时间转换格式
	 * @return 假如值为null，则返回null
	 * @throws JsonParseException
	 * 			假如无法解析成给定类型的值，则抛出该异常
	 */
	public LocalDateTime getLocalDateTime(){
		return getLocalDateTime(XLPStringUtil.isEmpty(formatter) 
				? jsonConfig.getDateFormatConfig().getDateTimeFormat() : formatter);
	}
	
	/**
	 * 获取JsonElement中的值
	 * 
	 * @param format 时间转换格式
	 * @param defaultValue
	 * @return 假如值为null或无法解析成给定类型的值，则返回defaultValue
	 */
	public LocalDateTime getLocalDateTime(String format, LocalDateTime defaultValue){
		LocalDateTime v = null;
		try {
			v = getLocalDateTime(format);
		} catch (Exception e) {
		}
		return v == null ? defaultValue : v;
	}
	
	/**
	 * 获取JsonElement中的值
	 * 
	 * @param defaultValue
	 * @return 假如值为null或无法解析成给定类型的值，则返回defaultValue
	 */
	public LocalDateTime getLocalDateTime(LocalDateTime defaultValue){
		LocalDateTime v = null;
		try {
			v = getLocalDateTime();
		} catch (Exception e) {
		}
		return v == null ? defaultValue : v;
	}
	
	/**
	 * 获取JsonElement中的值
	 * 
	 * @param format 时间转换格式
	 * @return 假如值为null，则返回null
	 * @throws JsonParseException
	 * 			假如无法解析成给定类型的值，则抛出该异常
	 */
	public LocalDate getLocalDate(String format){
		if(valueIsEmptyOrNull())
			return null;
		if(format == null)
			format = XLPDateUtil.DATE_DEFAULT_FORMAT;
		if(LocalDate.class == type)
			return (LocalDate) value;
		if(LocalDateTime.class == type)
			return ((LocalDateTime) value).toLocalDate();
		
		if (value instanceof Number) {
			return XLPDateUtil.longDateToLocalDateTime(((Number) value).longValue())
					.toLocalDate();
		}
		if (value instanceof Calendar) {
			return XLPDateUtil.dateToLocalDateTime(((Calendar)value).getTime())
					.toLocalDate(); 
		}
		if (value instanceof Date) {
			return XLPDateUtil.dateToLocalDateTime((Date)value).toLocalDate(); 
		}
		
		if(CharSequence.class.isAssignableFrom(type))
			try {
				return LocalDate.parse(value.toString(), DateTimeFormatter.ofPattern(format)); 
			} catch (Exception e) {
				throw throwJsonParseException("LocalDate");
			}
		throw throwJsonParseException("LocalDate");
	}
	
	/**
	 * 获取JsonElement中的值
	 * 
	 * @param format 时间转换格式
	 * @return 假如值为null，则返回null
	 * @throws JsonParseException
	 * 			假如无法解析成给定类型的值，则抛出该异常
	 */
	public LocalDate getLocalDate(){
		return getLocalDate(XLPStringUtil.isEmpty(formatter) 
				? jsonConfig.getDateFormatConfig().getDateFormat() : formatter);
	}
	
	/**
	 * 获取JsonElement中的值
	 * 
	 * @param format 时间转换格式
	 * @param defaultValue
	 * @return 假如值为null或无法解析成给定类型的值，则返回defaultValue
	 */
	public LocalDate getLocalDate(String format, LocalDate defaultValue){
		LocalDate v = null;
		try {
			v = getLocalDate(format);
		} catch (Exception e) {
		}
		return v == null ? defaultValue : v;
	}
	
	/**
	 * 获取JsonElement中的值
	 * 
	 * @param defaultValue
	 * @return 假如值为null或无法解析成给定类型的值，则返回defaultValue
	 */
	public LocalDate getLocalDate(LocalDate defaultValue){
		LocalDate v = null;
		try {
			v = getLocalDate();
		} catch (Exception e) {
		}
		return v == null ? defaultValue : v;
	}
	
	/**
	 * 获取JsonElement中的值
	 * 
	 * @param format 时间转换格式
	 * @return 假如值为null，则返回null
	 * @throws JsonParseException
	 * 			假如无法解析成给定类型的值，则抛出该异常
	 */
	public LocalTime getLocalTime(String format){
		if(valueIsEmptyOrNull())
			return null;
		if(format == null)
			format = XLPDateUtil.TIME_DEFAULT_FORMAT;
		if(LocalTime.class == type)
			return (LocalTime) value;
		
		if(LocalDateTime.class == type)
			return ((LocalDateTime) value).toLocalTime();
		
		if (value instanceof Number) {
			return XLPDateUtil.longDateToLocalDateTime(((Number) value).longValue())
					.toLocalTime();
		}
		if (value instanceof Calendar) {
			return XLPDateUtil.dateToLocalDateTime(((Calendar)value).getTime())
					.toLocalTime(); 
		}
		if (value instanceof Date) {
			return XLPDateUtil.dateToLocalDateTime((Date)value).toLocalTime(); 
		}
		
		if(CharSequence.class.isAssignableFrom(type))
			try {
				return LocalTime.parse(value.toString(), DateTimeFormatter.ofPattern(format)); 
			} catch (Exception e) {
				throw throwJsonParseException("LocalTime");
			}
		throw throwJsonParseException("LocalTime");
	}
	
	/**
	 * 获取JsonElement中的值
	 * 
	 * @param format 时间转换格式
	 * @return 假如值为null，则返回null
	 * @throws JsonParseException
	 * 			假如无法解析成给定类型的值，则抛出该异常
	 */
	public LocalTime getLocalTime(){
		return getLocalTime(XLPStringUtil.isEmpty(formatter) 
				? jsonConfig.getDateFormatConfig().getTimeFormat() : formatter);
	}
	
	/**
	 * 获取JsonElement中的值
	 * 
	 * @param format 时间转换格式
	 * @param defaultValue
	 * @return 假如值为null或无法解析成给定类型的值，则返回defaultValue
	 */
	public LocalTime getLocalTime(String format, LocalTime defaultValue){
		LocalTime v = null;
		try {
			v = getLocalTime(format);
		} catch (Exception e) {
		}
		return v == null ? defaultValue : v;
	}
	
	/**
	 * 获取JsonElement中的值
	 * 
	 * @param defaultValue
	 * @return 假如值为null或无法解析成给定类型的值，则返回defaultValue
	 */
	public LocalTime getLocalTime(LocalTime defaultValue){
		LocalTime v = null;
		try {
			v = getLocalTime();
		} catch (Exception e) {
		}
		return v == null ? defaultValue : v;
	}
	
	public void setJsonConfig(JsonConfig jsonConfig) {
		if (jsonConfig != null) {
			this.jsonConfig = jsonConfig;
		}
	}
	
	/**
	 * 获取JsonElement中的值
	 * 
	 * @param cs bean类型
	 * @return 假如值为null，则返回null
	 * @throws JsonParseException
	 * 			假如无法解析成给定类型的值，则抛出该异常
	 * @throws NullPointerException
	 * 			假如参数为null，则抛出该异常
	 */
	@SuppressWarnings("unchecked")
	public <T> T getBean(Class<T> cs){
		if(cs == null)
			throw new NullPointerException("参数必须不为null");
		if (value == null)
			return null;
		if (cs.isAssignableFrom(type)) 
			return (T) value;
		if(cs == JsonObject.class)
			return ((JsonObject)value).toBean(cs, isUsedAnnotation);
		if (Map.class.isAssignableFrom(type))
			return JsonObject.fromMap((Map<?, ?>) value).toBean(cs);
		throw throwJsonParseException(cs.getSimpleName());
	}
	
	/**
	 * 获取JsonElement中的值
	 * 
	 * @param cs bean类型
	 * @return 假如值为null，则返回null
	 * @throws JsonParseException
	 * 			假如无法解析成给定类型的值，则抛出该异常
	 * @throws NullPointerException
	 * 			假如参数为null，则抛出该异常
	 */
	@SuppressWarnings("unchecked")
	public <T> Collection<T> getCollectionBean(Class<T> cs, Flag flag){
		if(cs == null || flag == null)
			throw new NullPointerException("参数必须不为null");
		if (value == null)
			return null;
		
		if (Collection.class.isAssignableFrom(type))
			return (Collection<T>) value;
		if(type == JsonArray.class){
			Collection<T> collection;
			if(flag == Flag.list)
				collection = new ArrayList<T>();
			else 
				collection = new HashSet<T>();
			
			JsonElement[] jsonElements = ((JsonArray)value).getJsonElements();
			for (JsonElement jsonElement : jsonElements) { 
				Object ob = jsonElement.getValue();
				if(ob == null)
					continue;
				if (cs.isAssignableFrom(type)) {
					collection.add((T)ob);
					continue;
				}
				if(jsonElement.getType() != JsonObject.class && ob != null)
					throw throwJsonParseException("Collection<" + cs.getSimpleName() + ">");
				collection.add(((JsonObject)ob).toBean(cs, isUsedAnnotation));
			}
			return collection;
		}
			
		throw throwJsonParseException("Collection<" + cs.getSimpleName() + ">");
	}

	public boolean isUsedAnnotation() {
		return isUsedAnnotation;
	}

	public void setUsedAnnotation(boolean isUsedAnnotation) {
		this.isUsedAnnotation = isUsedAnnotation;
	}

	/**
	 * @return 字段格式化模式
	 */
	public String getFormatter() {
		return formatter;
	}

	/**
	 * 设置字段格式化模式
	 * 
	 * @param 字段格式化模式
	 */
	public void setFormatter(String formatter) {
		this.formatter = formatter;
	}
	
	/**
	 * 设置字段格式化模式
	 * 
	 * @param 字段格式化模式
	 */
	void setFormatter(Formatter formatter) {
		if (formatter != null) {
			this.formatter = formatter.formatter();
		}
	}
}
