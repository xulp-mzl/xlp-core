package org.xlp.json;

import java.lang.reflect.Array;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.xlp.assertion.AssertUtils;
import org.xlp.javabean.JavaBeanPropertiesDescriptor;
import org.xlp.javabean.PropertyDescriptor;
import org.xlp.javabean.annotation.Bean;
import org.xlp.javabean.annotation.FieldName;
import org.xlp.javabean.annotation.Formatter;
import org.xlp.json.config.JsonConfig;
import org.xlp.json.exception.JsonException;
import org.xlp.json.jenum.Flag;
import org.xlp.json.utils.BeanUtil;
import org.xlp.json.utils.JsonUtil;
import org.xlp.utils.XLPOutputInfoUtil;
import org.xlp.utils.XLPStringUtil;


/**
 * json对象
 * 
 * @author 徐龙平
 * 
 * @version 1.0
 */
public final class JsonObject extends Json{
	//存储json元素值
	//key为唯一标记, value为JsonElement对象
	private  Map<String, Object> map;
	
	public JsonObject() {
		this(false);
		
	}

	public JsonObject(JsonConfig jsonConfig) {
		this(jsonConfig, false);
	}
	
	/**
	 * 构造函数
	 * 
	 * @since 2.0
	 * @param keyIsOrder 标记<code>JsonObject</code>对象的key是否有序, true:有序，false:无序
	 */
	public JsonObject(boolean keyIsOrder) {
		this(null, keyIsOrder);
	}

	/**
	 * 构造函数
	 * 
	 * @since 2.0
	 * @param jsonConfig
	 * @param keyIsOrder 标记<code>JsonObject</code>对象的key是否有序, true:有序，false:无序
	 */
	public JsonObject(JsonConfig jsonConfig, boolean keyIsOrder) {
		super(jsonConfig);
		if (keyIsOrder) {
			this.map = new LinkedHashMap<String, Object>();
		}else {
			this.map = new HashMap<String, Object>();
		}
	}

	/**
	 * 把指定的值放入json对象中，假如指定的key已存在，则用新值覆盖旧值并返回旧值
	 * 
	 * @param key
	 * @param value
	 * @return 返回旧值
	 */
	public Object put(String key, int value){
		JsonElement jsonElement = new JsonElement(Integer.TYPE, value, jsonConfig);
		return map.put(key, jsonElement);
	}
	
	/**
	 * 把指定的值放入json对象中，假如指定的key已存在，则用新值覆盖旧值并返回旧值
	 * 
	 * @param key
	 * @param value
	 * @return 返回旧值
	 */
	public Object put(String key, byte value){
		JsonElement jsonElement = new JsonElement(Byte.TYPE, value, jsonConfig);
		return map.put(key, jsonElement);
	}
	
	/**
	 * 把指定的值放入json对象中，假如指定的key已存在，则用新值覆盖旧值并返回旧值
	 * 
	 * @param key
	 * @param value
	 * @return 返回旧值
	 */
	public Object put(String key, short value){
		JsonElement jsonElement = new JsonElement(Short.TYPE, value, jsonConfig);
		return map.put(key, jsonElement);
	}
	
	/**
	 * 把指定的值放入json对象中，假如指定的key已存在，则用新值覆盖旧值并返回旧值
	 * 
	 * @param key
	 * @param value
	 * @return 返回旧值
	 */
	public Object put(String key, long value){
		JsonElement jsonElement = new JsonElement(Long.TYPE, value, jsonConfig);
		return map.put(key, jsonElement);
	}
	
	/**
	 * 把指定的值放入json对象中，假如指定的key已存在，则用新值覆盖旧值并返回旧值
	 * 
	 * @param key
	 * @param value
	 * @return 返回旧值
	 */
	public Object put(String key, double value){
		JsonElement jsonElement = new JsonElement(Double.TYPE, value, jsonConfig);
		return map.put(key, jsonElement);
	}
	
	/**
	 * 把指定的值放入json对象中，假如指定的key已存在，则用新值覆盖旧值并返回旧值
	 * 
	 * @param key
	 * @param value
	 * @return 返回旧值
	 */
	public Object put(String key, float value){
		JsonElement jsonElement = new JsonElement(Float.TYPE, value, jsonConfig);
		return map.put(key, jsonElement);
	}
	
	/**
	 * 把指定的值放入json对象中，假如指定的key已存在，则用新值覆盖旧值并返回旧值
	 * 
	 * @param key
	 * @param value
	 * @return 返回旧值
	 */
	public Object put(String key, char value){
		JsonElement jsonElement = new JsonElement(Character.TYPE, value, jsonConfig);
		return map.put(key, jsonElement);
	}
	
	/**
	 * 把指定的值放入json对象中，假如指定的key已存在，则用新值覆盖旧值并返回旧值
	 * 
	 * @param key
	 * @param value
	 * @return 返回旧值
	 */
	public Object put(String key, boolean value){
		JsonElement jsonElement = new JsonElement(Boolean.TYPE, value, jsonConfig);
		return map.put(key, jsonElement);
	}
	
	/**
	 * 把指定的值放入json对象中，假如指定的key已存在，则用新值覆盖旧值并返回旧值
	 * 
	 * @param key
	 * @param value
	 * @return 返回旧值
	 */
	public Object put(String key, Object value){
		value = clone(value);
		Class<?> type = value == null ? Object.class : value.getClass();
		JsonElement jsonElement = new JsonElement(type, value,
				type.getAnnotation(Bean.class) != null, jsonConfig);
		return map.put(key, jsonElement);
	}
	
	/**
	 * 把指定的值放入json对象中，假如指定的key已存在，则用新值覆盖旧值并返回旧值
	 * 
	 * @param key
	 * @param bean
	 * @param isUsedAnnotation
	 *            是否启用FieldName注解转换 ,true是，false不启用
	 * @return 返回旧值
	 */
	@SuppressWarnings("unchecked")
	public <T> Object putBean(String key, T bean, boolean isUsedAnnotation){
		bean = (T) clone(bean);
		Class<?> type = bean == null ? Object.class : bean.getClass();
		JsonElement jsonElement = new JsonElement(type, bean, true, 
				isUsedAnnotation, jsonConfig);
		return map.put(key, jsonElement);
	}
	
	/**
	 * 把指定的值放入json对象中，假如指定的key已存在，则用新值覆盖旧值并返回旧值
	 * 
	 * @param key
	 * @param bean
	 * @return 返回旧值
	 */
	public <T> Object putBean(String key, T bean){
		return putBean(key, bean, true);
	}
	
	//-----------------------------------------------------------------
	/**
	 * 把指定的值放入json对象中，假如指定的key已存在，则用新值覆盖旧,假如新值为null，移除该键值对并返回移除的值
	 * <p>假如给定的值为null,则不向json对象中添加该条数据
	 * 
	 * @param key
	 * @param value
	 * @return 返回移除的值
	 */
	public Object element(String key, Object value){
		if (map.containsKey(key)) {
			Object v = map.get(key);
			if(value == null){
				map.remove(key);
				return v;
			}
		}
		
		if(value == null)
			return null;
		
		value = clone(value);
		Class<?> type = value.getClass();
		JsonElement jsonElement = new JsonElement(type, value,
				type.getAnnotation(Bean.class) != null, jsonConfig);
		map.put(key, jsonElement);
		return null;
	}
	
	/**
	 * 把指定的值放入json对象中，假如指定的key已存在，则用新值覆盖旧,假如新值为null，移除该键值对并返回移除的值
	 * <p>假如给定的值为null,则不向json对象中添加该条数据
	 * 
	 * @param key
	 * @param value
	 * @param isUsedAnnotation
	 *            是否启用FieldName注解转换 ,true是，false不启用
	 * @return 返回移除的值
	 */
	@SuppressWarnings("unchecked")
	public <T> Object elementBean(String key, T value, boolean isUsedAnnotation){
		if (map.containsKey(key)) {
			Object v = map.get(key);
			if(value == null){
				map.remove(key);
				return v;
			}
		}
		
		if(value == null)
			return null;
		
		value = (T) clone(value);
		Class<?> type = value.getClass();
		JsonElement jsonElement = new JsonElement(type, value,
				true, isUsedAnnotation, jsonConfig);
		map.put(key, jsonElement);
		return null;
	}
	
	/**
	 * 把指定的值放入json对象中，假如指定的key已存在，则用新值覆盖旧,假如新值为null，移除该键值对并返回移除的值
	 * <p>假如给定的值为null,则不向json对象中添加该条数据
	 * 
	 * @param key
	 * @param value
	 * @return 返回移除的值
	 */
	public <T> Object elementBean(String key, T value){
		return elementBean(key, value, true);
	}
	
	//----------------------------------------------------------------
	/**
	 * 把指定的值放入json对象中，假如指定的key已存在，则用新值不覆盖旧值,把该key对应的值改成数组形式
	 * 
	 * @param key
	 * @param value
	 */
	public void accumulate(String key, int value){
		if (map.containsKey(key)) {
			Object v = map.get(key);
			if(v.getClass().isArray()){
				JsonElement[] jes = (JsonElement[]) v;
				int length = jes.length;
				jes = Arrays.copyOf(jes, length + 1);
				jes[length] = new JsonElement(Integer.TYPE, value, jsonConfig);
				map.put(key, jes);
			}else {
				JsonElement[] jes = new JsonElement[2];
				jes[0] = (JsonElement) v;
				jes[1] = new JsonElement(Integer.TYPE, value, jsonConfig);
				map.put(key, jes);
			}
		}else {
			JsonElement jsonElement = new JsonElement(Integer.TYPE, value, jsonConfig);
			map.put(key, jsonElement);
		}
	}
	
	/**
	 * 把指定的值放入json对象中，假如指定的key已存在，则用新值不覆盖旧值,把该key对应的值改成数组形式
	 * 
	 * @param key
	 * @param value
	 */
	public void accumulate(String key, byte value){
		if (map.containsKey(key)) {
			Object v = map.get(key);
			if(v.getClass().isArray()){
				JsonElement[] jes = (JsonElement[]) v;
				int length = jes.length;
				jes = Arrays.copyOf(jes, length + 1);
				jes[length] = new JsonElement(Byte.TYPE, value, jsonConfig);
				map.put(key, jes);
			}else {
				JsonElement[] jes = new JsonElement[2];
				jes[0] = (JsonElement) v;
				jes[1] = new JsonElement(Byte.TYPE, value, jsonConfig);
				map.put(key, jes);
			}
		}else {
			JsonElement jsonElement = new JsonElement(Byte.TYPE, value, jsonConfig);
			map.put(key, jsonElement);
		}
	}
	
	/**
	 * 把指定的值放入json对象中，假如指定的key已存在，则用新值不覆盖旧值,把该key对应的值改成数组形式
	 * 
	 * @param key
	 * @param value
	 */
	public void accumulate(String key, short value){
		if (map.containsKey(key)) {
			Object v = map.get(key);
			if(v.getClass().isArray()){
				JsonElement[] jes = (JsonElement[]) v;
				int length = jes.length;
				jes = Arrays.copyOf(jes, length + 1);
				jes[length] = new JsonElement(Short.TYPE, value, jsonConfig);
				map.put(key, jes);
			}else {
				JsonElement[] jes = new JsonElement[2];
				jes[0] = (JsonElement) v;
				jes[1] = new JsonElement(Short.TYPE, value, jsonConfig);
				map.put(key, jes);
			}
		}else {
			JsonElement jsonElement = new JsonElement(Short.TYPE, value, jsonConfig);
			map.put(key, jsonElement);
		}
	}
	
	/**
	 * 把指定的值放入json对象中，假如指定的key已存在，则用新值不覆盖旧值,把该key对应的值改成数组形式
	 * 
	 * @param key
	 * @param value
	 */
	public void accumulate(String key, long value){
		if (map.containsKey(key)) {
			Object v = map.get(key);
			if(v.getClass().isArray()){
				JsonElement[] jes = (JsonElement[]) v;
				int length = jes.length;
				jes = Arrays.copyOf(jes, length + 1);
				jes[length] = new JsonElement(Long.TYPE, value, jsonConfig);
				map.put(key, jes);
			}else {
				JsonElement[] jes = new JsonElement[2];
				jes[0] = (JsonElement) v;
				jes[1] = new JsonElement(Long.TYPE, value, jsonConfig);
				map.put(key, jes);
			}
		}else {
			JsonElement jsonElement = new JsonElement(Long.TYPE, value, jsonConfig);
			map.put(key, jsonElement);		
		}
	}
	
	/**
	 * 把指定的值放入json对象中，假如指定的key已存在，则用新值不覆盖旧值,把该key对应的值改成数组形式
	 * 
	 * @param key
	 * @param value
	 */
	public void accumulate(String key, double value){
		if (map.containsKey(key)) {
			Object v = map.get(key);
			if(v.getClass().isArray()){
				JsonElement[] jes = (JsonElement[]) v;
				int length = jes.length;
				jes = Arrays.copyOf(jes, length + 1);
				jes[length] = new JsonElement(Double.TYPE, value, jsonConfig);
				map.put(key, jes);
			}else {
				JsonElement[] jes = new JsonElement[2];
				jes[0] = (JsonElement) v;
				jes[1] = new JsonElement(Double.TYPE, value, jsonConfig);
				map.put(key, jes);
			}
		}else {
			JsonElement jsonElement = new JsonElement(Double.TYPE, value, jsonConfig);
			map.put(key, jsonElement);	
		}
	}
	
	/**
	 * 把指定的值放入json对象中，假如指定的key已存在，则用新值不覆盖旧值,把该key对应的值改成数组形式
	 * 
	 * @param key
	 * @param value
	 */
	public void accumulate(String key, float value){
		if (map.containsKey(key)) {
			Object v = map.get(key);
			if(v.getClass().isArray()){
				JsonElement[] jes = (JsonElement[]) v;
				int length = jes.length;
				jes = Arrays.copyOf(jes, length + 1);
				jes[length] = new JsonElement(Float.TYPE, value, jsonConfig);
				map.put(key, jes);
			}else {
				JsonElement[] jes = new JsonElement[2];
				jes[0] = (JsonElement) v;
				jes[1] = new JsonElement(Float.TYPE, value, jsonConfig);
				map.put(key, jes);
			}
		}else {
			JsonElement jsonElement = new JsonElement(Float.TYPE, value, jsonConfig);
			map.put(key, jsonElement);
		}
	}
	
	/**
	 * 把指定的值放入json对象中，假如指定的key已存在，则用新值不覆盖旧值,把该key对应的值改成数组形式
	 * 
	 * @param key
	 * @param value
	 */
	public void accumulate(String key, char value){
		if (map.containsKey(key)) {
			Object v = map.get(key);
			if(v.getClass().isArray()){
				JsonElement[] jes = (JsonElement[]) v;
				int length = jes.length;
				jes = Arrays.copyOf(jes, length + 1);
				jes[length] = new JsonElement(Character.TYPE, value, jsonConfig);
				map.put(key, jes);
			}else {
				JsonElement[] jes = new JsonElement[2];
				jes[0] = (JsonElement) v;
				jes[1] = new JsonElement(Character.TYPE, value, jsonConfig);
				map.put(key, jes);
			}
		}else {
			JsonElement jsonElement = new JsonElement(Character.TYPE, value, jsonConfig);
			map.put(key, jsonElement);
		}
	}
	
	/**
	 * 把指定的值放入json对象中，假如指定的key已存在，则用新值不覆盖旧值,把该key对应的值改成数组形式
	 * 
	 * @param key
	 * @param value
	 */
	public void accumulate(String key, boolean value){
		if (map.containsKey(key)) {
			Object v = map.get(key);
			if(v.getClass().isArray()){
				JsonElement[] jes = (JsonElement[]) v;
				int length = jes.length;
				jes = Arrays.copyOf(jes, length + 1);
				jes[length] = new JsonElement(Boolean.TYPE, value, jsonConfig);
				map.put(key, jes);
			}else {
				JsonElement[] jes = new JsonElement[2];
				jes[0] = (JsonElement) v;
				jes[1] = new JsonElement(Boolean.TYPE, value, jsonConfig);
				map.put(key, jes);
			}
		}else {
			JsonElement jsonElement = new JsonElement(Boolean.TYPE, value, jsonConfig);
			map.put(key, jsonElement);
		}
	}
	
	/**
	 * 把指定的值放入json对象中，假如指定的key已存在，则用新值不覆盖旧值,把该key对应的值改成数组形式
	 * 
	 * @param key
	 * @param value
	 */
	public void accumulate(String key, Object value){
		value = clone(value);
		Class<?> type = value == null ? Object.class : value.getClass();
		if (map.containsKey(key)) {
			Object v = map.get(key);
			if(v.getClass().isArray()){
				JsonElement[] jes = (JsonElement[]) v;
				int length = jes.length;
				jes = Arrays.copyOf(jes, length + 1);
				jes[length] = new JsonElement(type, value,
						type.getAnnotation(Bean.class) != null, jsonConfig);
				map.put(key, jes);
			}else {
				JsonElement[] jes = new JsonElement[2];
				jes[0] = (JsonElement) v;
				jes[1] = new JsonElement(type, value,
						type.getAnnotation(Bean.class) != null, jsonConfig);
				map.put(key, jes);
			}
		}else {
			JsonElement jsonElement = new JsonElement(type, value,
					type.getAnnotation(Bean.class) != null, jsonConfig);
			map.put(key, jsonElement);
		}
	}
	
	/**
	 * 把指定的值放入json对象中，假如指定的key已存在，则用新值不覆盖旧值,把该key对应的值改成数组形式
	 * 
	 * @param key
	 * @param value
	 * @param isUsedAnnotation
	 *            是否启用FieldName注解转换 ,true是，false不启用
	 */
	@SuppressWarnings("unchecked")
	public <T> void accumulateBean(String key, T value, boolean isUsedAnnotation){
		value = (T) clone(value);
		Class<?> type = value == null ? Object.class : value.getClass();
		if (map.containsKey(key)) {
			Object v = map.get(key);
			if(v.getClass().isArray()){
				JsonElement[] jes = (JsonElement[]) v;
				int length = jes.length;
				jes = Arrays.copyOf(jes, length + 1);
				jes[length] = new JsonElement(type, value,
						true, isUsedAnnotation, jsonConfig);
				map.put(key, jes);
			}else {
				JsonElement[] jes = new JsonElement[2];
				jes[0] = (JsonElement) v;
				jes[1] = new JsonElement(type, value,
						true, isUsedAnnotation, jsonConfig);
				map.put(key, jes);
			}
		}else {
			JsonElement jsonElement = new JsonElement(type, value,
					true, isUsedAnnotation, jsonConfig);
			map.put(key, jsonElement);
		}
	}
	
	/**
	 * 把指定的值放入json对象中，假如指定的key已存在，则用新值不覆盖旧值,把该key对应的值改成数组形式
	 * 
	 * @param key
	 * @param value
	 */
	public <T> void accumulateBean(String key, T value){
		accumulateBean(key, value, true); 
	}
	
	/**
	 * 给json对象放入一个JsonElement或JsonElement[]对象
	 * 
	 * @param key
	 * @param jsonElements
	 */
	protected void putElements(String key, Object jsonElements){
		map.put(key, jsonElements);
	}
	
	/**
	 * 给json对象放入一个JsonElement对象
	 * 
	 * @param key
	 * @param jsonElement
	 */
	public void putElement(String key, JsonElement jsonElement){
		if(jsonElement != null)
			map.put(key, jsonElement);
	}
	
	/**
	 * 给json对象放入一个JsonElement对象, 假如指定的key已存在，则用新值不覆盖旧值,把该key对应的值改成数组形式
	 * 
	 * @param key
	 * @param jsonElement
	 */
	public void accumulateElement(String key, JsonElement jsonElement){
		if (map.containsKey(key)) {
			Object v = map.get(key);
			if(v.getClass().isArray()){
				JsonElement[] jes = (JsonElement[]) v;
				int length = jes.length;
				jes = Arrays.copyOf(jes, length + 1);
				jes[length] = jsonElement;
				map.put(key, jes);
			}else {
				JsonElement[] jes = new JsonElement[2];
				jes[0] = (JsonElement) v;
				jes[1] = jsonElement;
				map.put(key, jes);
			}
		}else {
			map.put(key, jsonElement);
		}
	}
	
	/**
	 * 浅克隆
	 * 
	 * @param object
	 * 
	 * @return 当给定的对象与本对象相同，则返回其副本，否则返回给定的对象
	 */
	public Object clone(Object object){
		if (object == this) {
			JsonObject jsonObject = new JsonObject(((JsonObject)object).getJsonConfig());
			for (Map.Entry<String, Object> entry : 
				((JsonObject)object).map.entrySet()) {
				jsonObject.putElements(entry.getKey(), entry.getValue());
			}
			return jsonObject;
		}
		return object;
	}
	
	/**
	 * 用指定的key获取JsonElement对象
	 * 
	 * @param key
	 * @return 从不返回null, 假如用指定的key未找到JsonElement对象，则返回（new JsonElement(Object.class, null)）
	 */
	public JsonElement getJsonElement(String key){
		Object v = map.get(key);
		if(v == null)
			return new JsonElement(Object.class, null, false, jsonConfig);
		JsonElement jsonElement;
		if(v.getClass().isArray()){
			JsonElement[] jsonElements= ((JsonElement[])v);
			JsonArray jsonArray = new JsonArray(jsonConfig);
			int len = jsonElements.length;
			for (int i = 0; i < len; i++) {
				jsonArray.addElement(jsonElements[i]);
			}
			jsonElement = new JsonElement(JsonArray.class, jsonArray, jsonConfig);
		}else {
			jsonElement = (JsonElement) v;
		}
		return jsonElement;
	}
	
	/**
	 * 用指定的key获取JsonElement[]对象
	 * 
	 * @param key
	 * @return 从不返回null, 假如用指定的key未找到JsonElement对象，则返回（new JsonElement[0]）
	 */
	public JsonElement[] getJsonElements(String key){
		Object v = map.get(key);
		if(v == null)
			return new JsonElement[]{};
		if(v.getClass().isArray()){
			JsonElement[] jsonElements = (JsonElement[]) v;
			return jsonElements;
		}
		JsonElement jsonElement = (JsonElement) v;
		return new JsonElement[]{jsonElement};
	}
	
	@Override
	public String toString() {
		return format(0, false);
	}
	
	/**
	 * 格式化json字符串
	 * 
	 * @param spaceCount 缩颈空格数
	 * @param isAddSpace 是否缩进
	 * @return
	 */
	@Override
	String format(int spaceCount, boolean isAddSpace){
		StringBuilder jsonSB = new StringBuilder();
		parserString(jsonSB, spaceCount, isAddSpace);
		String jsonString = jsonSB.toString();
		return outJsoStringPreDeal == null ? jsonString : 
				outJsoStringPreDeal.preDeal(jsonString);
	}
	
	/**
	 * 
	 * @param jsonSB
	 */
	private void parserString(StringBuilder jsonSB, int spaceCount, boolean isAddSpace) {
		jsonSB.append(JsonUtil.LEFT_B);
		if(!isEmpty())
			formatSpace(jsonSB, spaceCount, isAddSpace);
		int i = 0;
		for (Entry<String, Object> entry : map.entrySet()) {
			Object v = entry.getValue();
			boolean isArray = v.getClass().isArray();
			if(!isArray && ((JsonElement)v).getValue() == null && 
					!jsonConfig.getNullConfig().isOpen()) //判断是否是数组并且其值是否为null
				continue;
			
			if(i != 0){
				jsonSB.append(JsonUtil.COMMA);
				formatSpace(jsonSB, spaceCount, isAddSpace);
			}
			String key = entry.getKey();
			key = jsonConfig.getSpecialCharacterConfig().toString(key);
			
			jsonSB.append(JsonUtil.DOUBLE_QUOTES).append(key)//拼接key
				.append(JsonUtil.DOUBLE_QUOTES).append(JsonUtil.COLON);
			
			if (isArray) {
				JsonElement[] jsonElements = (JsonElement[]) v;
				int length = jsonElements.length;
				List<Object> tempList = new ArrayList<Object>(length);
				for (int j = 0; j < length; j++) {
					tempList.add(jsonElements[j].getValue());
				}
				v = new JsonElement(List.class, tempList, false, jsonConfig);
			}
			JsonElement je = (JsonElement) v;
			dealValue(je, jsonSB, spaceCount, isAddSpace);
			
			i++;
		}
		if(!isEmpty())
			formatSpace(jsonSB, spaceCount - this.spaceCount, isAddSpace);
		jsonSB.append(JsonUtil.RIGHT_B);
	}

	/**
	 * 把bean转换成json对象
	 * 
	 * @param bean
	 * @return
	 */
	public static <T> JsonObject fromBean(T bean){
		return fromBean(bean, new JsonConfig());
	}
	
	/**
	 * 把bean转换成json对象
	 * 
	 * @param bean
	 * @param jsonConfig json配置
	 * @return
	 */
	public static <T> JsonObject fromBean(T bean, JsonConfig jsonConfig){
		return fromBean(bean, jsonConfig, true);
	}
	

	/**
	 * 把bean转换成json对象
	 * 
	 * @param bean
	 * @param isUsedAnnotation
	 *            是否启用FieldName注解转换 ,true是，false不启用
	 * @return
	 */
	public static <T> JsonObject fromBean(T bean, boolean isUsedAnnotation){
		return fromBean(bean, new JsonConfig(), isUsedAnnotation);
	}
	
	/**
	 * 把bean转换成json对象
	 * 
	 * @param bean
	 * @param jsonConfig json配置
	 * @param isUsedAnnotation
	 *            是否启用FieldName注解转换 ,true是，false不启用
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> JsonObject fromBean(T bean, JsonConfig jsonConfig,
			boolean isUsedAnnotation){
		jsonConfig = jsonConfig == null ? new JsonConfig() : jsonConfig;
		
		JsonObject jsonObject = new JsonObject(jsonConfig);
		if(bean == null)
			return jsonObject;
		PropertyDescriptor<T>[] pds = new JavaBeanPropertiesDescriptor<T>(
				(Class<T>) bean.getClass()).getPds();
		
		FieldName fn = null;
		String fnAlias = null; //获取字段别名
		//json字段格式化模式
		Formatter jsonFormatter;
		for (PropertyDescriptor<T> pd : pds) {
			fnAlias = null;
			if (isUsedAnnotation) {
				fn = pd.getFieldAnnotation(FieldName.class);
				if (fn != null) {
					fnAlias = XLPStringUtil.isEmpty(fn.name())
							? pd.getFieldName() : XLPStringUtil.emptyTrim(fn.name()); 
				}
			}else {
				fnAlias = pd.getFieldName();
			}
			if (fnAlias != null) {
				jsonFormatter = pd.getFieldAnnotation(Formatter.class);
				Class<?> fieldType = pd.getFiledClassType();
				Object value = BeanUtil.callGetter(bean, pd);
				//处理bean字段类型为Object时，导致字段实际类型不准确问题
				fieldType = value != null && Object.class == fieldType ? value.getClass() : fieldType;
				JsonElement jsonElement = new JsonElement(fieldType, value, 
						fieldType.getAnnotation(Bean.class) != null, 
						isUsedAnnotation, jsonConfig, jsonFormatter);
				jsonObject.putElement(fnAlias, jsonElement);
			}
		}
		return jsonObject;
	}
	
	/**
	 * 把map转换成json对象
	 * 
	 * @param map
	 * 
	 * @return
	 */
	public static <K,V> JsonObject fromMap(Map<K, V> map){
		return fromMap(map, new JsonConfig());
	}
	
	/**
	 * 把map转换成json对象
	 * 
	 * @param map
	 * @param jsonConfig json配置
	 * @return
	 */
	public static <K,V> JsonObject fromMap(Map<K, V> map, JsonConfig jsonConfig){
		jsonConfig = jsonConfig == null ? new JsonConfig() : jsonConfig;
		
		JsonObject jsonObject = new JsonObject(jsonConfig);
		if(map == null)
			return jsonObject;
		if (map instanceof LinkedHashMap) {
			jsonObject = new JsonObject(jsonConfig, true);
		}
		Object key = null;
		for (Map.Entry<K, V> entry: map.entrySet()) {
			key = entry.getKey();
			key = key == null ? "null" : key.toString();
			jsonObject.put((String)key, entry.getValue());
		}
		return jsonObject;
	}
	
	/**
	 * 清厨json对象中的所有数据
	 */
	public void clear(){
		map.clear();
	}
	
	/**
	 * 通过指定的key移除对应的json元素
	 * 
	 * @param key
	 * @return 返回移除结果，如果未找到要移除的json元素，怎返回null
	 */
	public Object remove(String key){
		return map.remove(key);
	}
	
	/**
	 * json对象中是否包含给定的key
	 * 
	 * @param key
	 * @return
	 */
	public boolean hasKey(String key){
		return map.containsKey(key);
	}

	/**
	 * 判断json对象中的数据是否为空
	 * 
	 * @return
	 */
	public boolean isEmpty(){
		return map.isEmpty();
	}
	
	/**
	 * 或取json对象中数据的个数
	 * 
	 * @return
	 */
	public int count(){
		return map.size();
	}
	
	/**
	 * 把JsonObject对象转换成map
	 * 
	 * @return
	 */
	public Map<String, Object> toMap(){
		return toMap(jsonConfig);
	}
	
	/**
	 * 把JsonObject对象转换成map
	 * 
	 * @param jsonConfig json配置
	 * @return
	 */
	protected Map<String, Object> toMap(JsonConfig jsonConfig){
		Map<String, Object> outMap = new HashMap<String, Object>(this.map.size());
		for (Map.Entry<String, Object> entry : this.map.entrySet()) {
			Object v = entry.getValue();
			if (v.getClass().isArray()) {
				int len = Array.getLength(v);
				List<Object> vList = new ArrayList<Object>();
				JsonElement jsonElement;
				for (int i = 0; i < len; i++) {
					jsonElement = (JsonElement)Array.get(v, i);
					Object temp = jsonElement.getValue();
					Class<?> type = jsonElement.getType();
					if(temp == null && !jsonConfig.getNullConfig().isOpen())
						continue;
					if (type == JsonObject.class) {
						temp = ((JsonObject)temp).toMap(jsonConfig);
					}else if(type == JsonArray.class){
						temp = ((JsonArray)temp).toList();
					}
					vList.add(temp);
				}
				v = vList.size() == 1 ? vList.get(0) : vList;
				outMap.put(entry.getKey(), v);
			}else {
				JsonElement jsonElement = (JsonElement)v;
				Class<?> type = jsonElement.getType();
				v = ((JsonElement)v).getValue();
				if(v == null && !jsonConfig.getNullConfig().isOpen())
					continue;
				if (type == JsonObject.class) {
					v = ((JsonObject)v).toMap(jsonConfig);
				}else if(type == JsonArray.class){
					v = ((JsonArray)v).toList();
				}
				outMap.put(entry.getKey(), v);
			}
		}
		return outMap;
	}
	
	/**
	 * 获取json对象数据集合
	 * 
	 * @return
	 */
	public Set<Map.Entry<String, Object>> entrySet(){
		Set<Map.Entry<String, Object>> entries = map.entrySet();
		for (Entry<String, Object> entry : entries) {
			Object v = entry.getValue();
			if(v.getClass().isArray()){
				int length = Array.getLength(v);
				for (int i = 0; i <length; i++) {
					((JsonElement)Array.get(v, i)).setJsonConfig(jsonConfig);
				}
			}else {
				((JsonElement) v).setJsonConfig(jsonConfig);
			}
		}
		return entries;
	}
	
	/**
	 * 把jsonObject格式的字符串转换成jsonObject对象
	 * 
	 * @param jsonString
	 * @param jsonConfig json配置
	 * @return jsonObject对象 ,如给定的第一个参数为null,返回数据个数为0的jsonObject对象
	 */
	public static JsonObject fromJsonString(String jsonString,
			JsonConfig jsonConfig){
		if (jsonConfig == null) 
			jsonConfig = new JsonConfig();
		if(jsonString == null)
			throw new JsonException("要解析的字符串必须不为null");
		jsonString = jsonString.trim();//去除首尾空格
		
		if(!jsonString.startsWith(JsonUtil.LEFT_B))
			throw new JsonException(jsonString + "该要解析的字符串不是以" + JsonUtil.LEFT_B + "开头");
		if(!jsonString.endsWith(JsonUtil.RIGHT_B))
			throw new JsonException(jsonString + "该要解析的字符串不是以" + JsonUtil.RIGHT_B + "结尾");
	
		int len = jsonString.length();
		if(len < LEFT_B_LEN + RIGHT_B_LEN)
			throw new JsonException(jsonString + "该要解析的字符串不复合json数组格式");
		
		if((JsonUtil.LEFT_B + JsonUtil.RIGHT_B).equals(jsonString)) //判断字符串是否=={}
			return new JsonObject(jsonConfig, true);
		return (JsonObject) parser(jsonString, jsonConfig);
	}
	
	/**
	 * 把jsonObject格式的字符串转换成jsonObject对象
	 * 
	 * @param jsonString
	 * @return jsonObject对象 ,如给定的第一个参数为null,返回数据个数为0的jsonObject对象
	 */
	public static JsonObject fromJsonString(String jsonString){
		return fromJsonString(jsonString, null);
	}
	
	/**
	 * 把JsonObject对象转换成bean对象
	 * 
	 * @param beanClass
	 * @return
	 * @throws NullPointerException
	 * 			假如参数为null，抛出该异常
	 */
	public <T> T toBean(Class<T> beanClass){
		return toBean(beanClass, true);
	}
	
	/**
	 * 把JsonObject对象转换成bean对象
	 * 
	 * @param beanClass
	 * @param isUsedAnnotation
	 *            是否启用FieldName注解转换 true是，false不启用
	 * @return
	 * @throws NullPointerException
	 * 			假如参数为null，抛出该异常
	 */
	public <T> T toBean(Class<T> beanClass, boolean isUsedAnnotation){
		if(beanClass == null)
			throw new NullPointerException("参数必须不为null");
		PropertyDescriptor<T>[] pds = new JavaBeanPropertiesDescriptor<T>(beanClass)
				.getPds();
		
		T bean = BeanUtil.newInstance(beanClass);
		FieldName fieldName;
		String keyName = null;
		Object value = null;
		String tempFieldName;
		for (PropertyDescriptor<T> pd : pds) {
			keyName = null;
			if (isUsedAnnotation) {
				fieldName = pd.getFieldAnnotation(FieldName.class);
				if (fieldName != null) {
					keyName = !XLPStringUtil.isEmpty(tempFieldName = fieldName.name())
							? XLPStringUtil.emptyTrim(tempFieldName) : pd.getFieldName();
				}
			}else {
				keyName = pd.getFieldName();
			}
			if (keyName != null) { 
				try {
					value = getConvertBeanValue(getJsonElement(keyName), pd); 
				} catch (Exception e) {
					XLPOutputInfoUtil.println("----------调用[" + pd.getFieldName() + " ]该字段的写方法失败---------");
				}
				BeanUtil.callSetter(bean, pd, value);
			}
		}
		
		return bean;
	}

	/**
	 * 获取转换bean对应属性值
	 * 
	 * @param jsonElement
	 * @param pd
	 * @return
	 * @throws Exception 
	 */
	private <T> Object getConvertBeanValue(JsonElement jsonElement, PropertyDescriptor<T> pd) throws Exception {
		Object value;
		Class<?> fieldType;
		//json字段格式化模式
		Formatter jsonFormatter;
		fieldType = pd.getFiledClassType();
		if(List.class.isAssignableFrom(fieldType)){
			Class<?> actualType = (Class<?>) ((ParameterizedType)pd.getField().getGenericType())
					.getActualTypeArguments()[0];
			value = jsonElement.getCollectionBean(actualType, Flag.list);
		}else if (Set.class.isAssignableFrom(fieldType)) {
			Class<?> actualType = (Class<?>) ((ParameterizedType)pd.getField().getGenericType())
				.getActualTypeArguments()[0];
			value = jsonElement.getCollectionBean(actualType, Flag.set);
		}else if (fieldType.isEnum()) {
			value = jsonElement.getValue();
			if (value != null) {
				//处理枚举类型映射问题
				value = fieldType.getMethod("valueOf", String.class).invoke(fieldType, value.toString());
			}
		}else {
			jsonFormatter = pd.getFieldAnnotation(Formatter.class);
			
			jsonElement.setFormatter(jsonFormatter);
			value = new JsonValueProcesser().processValue(fieldType, jsonElement);
			
			if(value == null && fieldType.isPrimitive())
				value = JsonValueProcesser.PRIMITIVE_DEFAULTS.get(fieldType);
		}
		return value;
	}
	
	//TODO
	/**
	 * 把JsonObject对象转换成bean对象(处理带.的key值json转换成JavaBean)
	 * 
	 * @param beanClass
	 * @return
	 * @throws NullPointerException
	 * 			假如参数为null，抛出该异常
	 */
	public <T> T toBeanExt(Class<T> beanClass){
		return toBeanExt(beanClass, true);
	}
	
	/**
	 * 把JsonObject对象转换成bean对象(处理带.的key值json转换成JavaBean)
	 * 
	 * @param beanClass
	 * @param isUsedAnnotation
	 *            是否启用FieldName注解转换 true是，false不启用
	 * @return
	 * @throws NullPointerException
	 * 			假如参数为null，抛出该异常
	 */
	public <T> T toBeanExt(Class<T> beanClass, boolean isUsedAnnotation){
		AssertUtils.isNotNull(beanClass, "beanClass参数必须不为null");
		PropertyDescriptor<T>[] pds = new JavaBeanPropertiesDescriptor<T>(beanClass)
				.getPds();
		T bean = BeanUtil.newInstance(beanClass);
		Set<String> keys = keySet();
		for (String key : keys) {
			if (!XLPStringUtil.isEmpty(key)) { 
				try {
					_setBeanValue(key, bean, pds, getJsonElement(key), isUsedAnnotation);
				} catch (Exception e) {
					XLPOutputInfoUtil.println("----------函数(toBeanExt)设置[" + beanClass + "." + key + " ]该字段值失败:" + e.getMessage() + "---------");
				} 
			}
		}
		return bean;
	}
	
	/**
	 * 深度设置bean属性
	 * 
	 * @param fieldName
	 * @param bean
	 * @param pds
	 * @param jsonElement
	 * @param isUsedAnnotation
	 *            是否启用FieldName注解转换 true是，false不启用
	 * @throws Exception 
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void _setBeanValue(String fieldName, Object bean, PropertyDescriptor<?>[] pds, JsonElement jsonElement,
			boolean isUsedAnnotation) throws Exception {
		int dotIndex = fieldName.indexOf(".");
		PropertyDescriptor<Object> pd = null;
		for (int i = 0; i < pds.length; i++) {
			if (fieldName.equalsIgnoreCase(getBeanFiledName(pds[i], isUsedAnnotation))) {
				pd = (PropertyDescriptor<Object>) pds[i];
				break; 
			}
		}
		
		if (pd != null) {
			pd.executeWriteMethod(bean, getConvertBeanValue(jsonElement, pd)); 
		} else if (dotIndex >= 0) {
			String prefixName = fieldName.substring(0, dotIndex); 
			for (int i = 0; i < pds.length; i++) {
				if (prefixName.equalsIgnoreCase(getBeanFiledName(pds[i], isUsedAnnotation))) {
					pd = (PropertyDescriptor<Object>) pds[i];
					break;
				}
			}
			if (pd != null) {
				Object _bean = pd.executeReadMethod(bean);
				Class<?> _beanClass = pd.getFiledClassType();
				//判断是否需要创建bean
				if (isUsedAnnotation && _beanClass.getAnnotation(Bean.class) == null) {
					return;
				}
				if (_bean == null ) {
					_bean = _beanClass.newInstance();
					pd.executeWriteMethod(bean, _bean); 
				}
				pds = new JavaBeanPropertiesDescriptor(_beanClass).getPds();
				_setBeanValue(fieldName.substring(dotIndex + 1), _bean, pds, jsonElement, isUsedAnnotation);
			}
		} 
	}
	
	/**
	 * 根据PropertyDescriptor对象获取字段名称
	 * 
	 * @param pd
	 * @param isUsedAnnotation 是否启用FieldName注解转换 true是，false不启用
	 * @return
	 */
	private <T> String getBeanFiledName(PropertyDescriptor<T> pd, boolean isUsedAnnotation){
		if (isUsedAnnotation) {
			FieldName fieldNameAnnotation = pd.getFieldAnnotation(FieldName.class);
			if (fieldNameAnnotation != null) {
				String name = fieldNameAnnotation.name();
				name = XLPStringUtil.isEmpty(name) ? pd.getFieldName() : name.trim();
				return name;
			}
			return null;
		}
		return pd.getFieldName();
	}
	
	/**
	 * 获取值
	 * 
	 * @param key
	 * @return
	 */
	public Object getObject(String key){
		return getJsonElement(key).getObject();
	}
	
	/**
	 * 获取值
	 * 
	 * @param key
	 * @param defaultValue
	 * @return 假如值为null，则返回给定的defaultVlaue值
	 */
	public Object getObject(String key, Object defaultValue){
		return getJsonElement(key).getObject(defaultValue);
	}
	
	/**
	 * 获取值
	 * 
	 * @param key
	 * @return
	 */
	public <T> T get(String key){
		return getJsonElement(key).get();
	}
	
	/**
	 * 获取值
	 * 
	 * @param key
	 * @param defaultValue
	 * @return 假如值为null，则返回给定的defaultVlaue值
	 */
	public <T> T get(String key, T defaultValue){
		return getJsonElement(key).get(defaultValue);
	}
	
	/**
	 * 获取值
	 * 
	 * @param key
	 * @return
	 */
	public String getString(String key){
		return getJsonElement(key).getString();
	}
	
	/**
	 * 获取值
	 * 
	 * @param key
	 * @param defaultValue
	 * @return 假如值为null，则返回给定的defaultVlaue值
	 */
	public String getString(String key, String defaultValue){
		return getJsonElement(key).getString(defaultValue);
	}
	
	/**
	 * 根据索引值获取指定位置的key
	 * <br/>
	 * 假如keyIsOrder值为true时，返回的值与放入的顺序一致，值为false时，返回的值与放入的顺序可能不一致
	 * 
	 * @param index
	 * @return
	 * @throws IllegalArgumentException 假如给定的索引值小于0或大于等于count()计算出的值，则抛出该异常
	 */
	public String getKey(int index){
		if (index < 0 || index >= count()) { 
			throw new IllegalArgumentException("给定的索引值不合法！");
		}
		int i = 0;
		for (Entry<String, Object> entry : entrySet()) { 
			if (index == i) {
				return entry.getKey();
			}
			i++;
		}
		return null;
	}
	
	/**
	 * 获取第一个key值
	 * <br/>
	 * 假如keyIsOrder值为true时，返回的值与放入的顺序一致，值为false时，返回的值与放入的顺序可能不一致
	 * @see getKey(index)
	 * @return
	 * @throws IllegalArgumentException 假如给定的索引值小于0或大于等于count()计算出的值，则抛出该异常
	 */
	public String getFirstKey(){
		return getKey(0);
	}
	
	/**
	 * 获取最后一个key值
	 * <br/>
	 * 假如keyIsOrder值为true时，返回的值与放入的顺序一致，值为false时，返回的值与放入的顺序可能不一致
	 * @see getKey(index)
	 * @return
	 * @throws IllegalArgumentException 假如给定的索引值小于0或大于等于count()计算出的值，则抛出该异常
	 */
	public String getLastKey(){
		return getKey(count() - 1);
	}
	
	/**
	 * 返回JsonObject对象的key集合
	 * 
	 * @return
	 */
	public Set<String> keySet(){
		return map.keySet();
	}
}
