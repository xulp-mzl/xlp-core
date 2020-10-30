package org.xlp.json;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.xlp.json.annotation.Bean;
import org.xlp.json.config.JsonConfig;
import org.xlp.json.exception.JsonException;
import org.xlp.json.utils.JsonUtil;

/**
 * JsonArray
 * 
 * @author 徐龙平
 * 
 * @version 1.0
 */
public final class JsonArray extends Json{
	//jsonArray对象用来储蓄数据的容器
	private volatile List<JsonElement>  list = new LinkedList<JsonElement>();
	
	public JsonArray() {
		super();
	}

	public JsonArray(JsonConfig jsonConfig) {
		super(jsonConfig);
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
	String format(int spaceCount, boolean isAddSpace) {
		StringBuilder jsonSB = new StringBuilder();
		jsonSB.append(JsonUtil.LEFT_M);
		if (!isEmpty())
			formatSpace(jsonSB, spaceCount, isAddSpace);
		int i = 0;
		for(JsonElement jsonElement : list){
			if(i != 0){
				jsonSB.append(JsonUtil.COMMA);
				formatSpace(jsonSB, spaceCount, isAddSpace);
			}
			dealValue(jsonElement, jsonSB, spaceCount, isAddSpace);
			i++;
		}
		if (!isEmpty())
			formatSpace(jsonSB, spaceCount - SPACE_COUNT, isAddSpace);
		jsonSB.append(JsonUtil.RIGHT_M);
		String jsonString = jsonSB.toString();
		return outJsoStringPreDeal == null ? jsonString :
				outJsoStringPreDeal.preDeal(jsonString);
	}
	
	/**
	 * 把给定的值放入<code>JsonArray</code>对象中
	 * 
	 * @param value
	 */
	public void add(int value){
		JsonElement jsonElement = new JsonElement(Integer.TYPE, value, jsonConfig);
		list.add(jsonElement);
	}
	
	/**
	 * 把给定的值放入<code>JsonArray</code>对象中
	 * 
	 * @param value
	 */
	public void add(byte value){
		JsonElement jsonElement = new JsonElement(Byte.TYPE, value, jsonConfig);
		list.add(jsonElement);
	}
	
	/**
	 * 把给定的值放入<code>JsonArray</code>对象中
	 * 
	 * @param value
	 */
	public void add(short value){
		JsonElement jsonElement = new JsonElement(Short.TYPE, value, jsonConfig);
		list.add(jsonElement);
	}
	
	/**
	 * 把给定的值放入<code>JsonArray</code>对象中
	 * 
	 * @param value
	 */
	public void add(long value){
		JsonElement jsonElement = new JsonElement(Long.TYPE, value, jsonConfig);
		list.add(jsonElement);
	}
	
	/**
	 * 把给定的值放入<code>JsonArray</code>对象中
	 * 
	 * @param value
	 */
	public void add(double value){
		JsonElement jsonElement = new JsonElement(Double.TYPE, value, jsonConfig);
		list.add(jsonElement);
	}
	
	/**
	 * 把给定的值放入<code>JsonArray</code>对象中
	 * 
	 * @param value
	 */
	public void add(float value){
		JsonElement jsonElement = new JsonElement(Float.TYPE, value, jsonConfig);
		list.add(jsonElement);
	}
	
	/**
	 * 把给定的值放入<code>JsonArray</code>对象中
	 * 
	 * @param value
	 */
	public void add(char value){
		JsonElement jsonElement = new JsonElement(Character.TYPE, value, jsonConfig);
		list.add(jsonElement);
	}
	
	/**
	 * 把给定的值放入<code>JsonArray</code>对象中
	 * 
	 * @param value
	 */
	public void add(boolean value){
		JsonElement jsonElement = new JsonElement(Boolean.TYPE, value, jsonConfig);
		list.add(jsonElement);
	}
	
	/**
	 * 把给定的值放入<code>JsonArray</code>对象中指定的位置
	 * 
	 * @param value
	 * @param index
	 */
	public void add(int index, int value){
		index = index < 0 ? 0 : index;
		JsonElement jsonElement = new JsonElement(Integer.TYPE, value, jsonConfig);
		list.add(index, jsonElement);
	}
	
	/**
	 * 把给定的值放入<code>JsonArray</code>对象中指定的位置
	 * 
	 * @param value
	 * @param index
	 */
	public void add(int index, byte value){
		index = index < 0 ? 0 : index;
		JsonElement jsonElement = new JsonElement(Byte.TYPE, value, jsonConfig);
		list.add(index, jsonElement);
	}
	
	/**
	 * 把给定的值放入<code>JsonArray</code>对象中指定的位置
	 * 
	 * @param value
	 * @param index
	 */
	public void add(int index, short value){
		index = index < 0 ? 0 : index;
		JsonElement jsonElement = new JsonElement(Short.TYPE, value, jsonConfig);
		list.add(index, jsonElement);
	}
	
	/**
	 * 把给定的值放入<code>JsonArray</code>对象中指定的位置
	 * 
	 * @param value
	 * @param index
	 */
	public void add(int index, long value){
		index = index < 0 ? 0 : index;
		JsonElement jsonElement = new JsonElement(Long.TYPE, value, jsonConfig);
		list.add(index, jsonElement);
	}
	
	/**
	 * 把给定的值放入<code>JsonArray</code>对象中指定的位置
	 * 
	 * @param value
	 * @param index
	 */
	public void add(int index, double value){
		index = index < 0 ? 0 : index;
		JsonElement jsonElement = new JsonElement(Double.TYPE, value, jsonConfig);
		list.add(index, jsonElement);
	}
	
	/**
	 * 把给定的值放入<code>JsonArray</code>对象中指定的位置
	 * 
	 * @param value
	 * @param index
	 */
	public void add(int index, float value){
		index = index < 0 ? 0 : index;
		JsonElement jsonElement = new JsonElement(Float.TYPE, value, jsonConfig);
		list.add(index, jsonElement);
	}
	
	/**
	 * 把给定的值放入<code>JsonArray</code>对象中指定的位置
	 * 
	 * @param value
	 * @param index
	 */
	public void add(int index, char value){
		index = index < 0 ? 0 : index;
		JsonElement jsonElement = new JsonElement(Character.TYPE, value, jsonConfig);
		list.add(index, jsonElement);
	}
	
	/**
	 * 把给定的值放入<code>JsonArray</code>对象中指定的位置
	 * 
	 * @param value
	 * @param index
	 */
	public void add(int index, boolean value){
		index = index < 0 ? 0 : index;
		JsonElement jsonElement = new JsonElement(Boolean.TYPE, value, jsonConfig);
		list.add(index, jsonElement);
	}
	
	/**
	 * 把给定的值放入<code>JsonArray</code>对象中指定的位置
	 * 
	 * @param jsonElement
	 * @param index
	 * @return 假如添加成功返回true，否则返回false。即参数jsonElement为null时，添加不成功
	 */
	public boolean addElement(int index, JsonElement jsonElement){
		index = index < 0 ? 0 : index;
		boolean addSucc = false;
		if(jsonElement != null){
			 list.add(index, jsonElement);
			 addSucc = true;
		}
		return addSucc;
	}
	
	/**
	 * 把给定的值放入<code>JsonArray</code>对象中指定的位置
	 * 
	 * @param jsonElement
	 * @return 假如添加成功返回true，否则返回false。即参数jsonElement为null时，添加不成功
	 */
	public boolean addElement(JsonElement jsonElement){
		boolean addSucc = false;
		if(jsonElement != null){
			 list.add(jsonElement);
			 addSucc = true;
		}
		return addSucc;
	}
	
	/**
	 * 把给定的值放入<code>JsonArray</code>对象中指定的位置
	 * 
	 * @param value
	 * @param index
	 */
	public void add(int index, Object value){
		index = index < 0 ? 0 : index;
		value = clone(value);
		Class<?> type = value == null ? Object.class : value.getClass();
		JsonElement jsonElement = new JsonElement(type, value,
				type.getAnnotation(Bean.class) != null, jsonConfig);
		list.add(index, jsonElement);
	}
	
	/**
	 * 把给定的值放入<code>JsonArray</code>对象中指定的位置
	 * 
	 * @param value
	 * @param index
	 * @param isUsedAnnotation
	 *            是否启用FieldName注解转换 ,true是，false不启用
	 */
	@SuppressWarnings("unchecked")
	public <T> void addBean(int index, T value, boolean isUsedAnnotation){
		index = index < 0 ? 0 : index;
		value = (T) clone(value);
		Class<?> type = value == null ? Object.class : value.getClass();
		JsonElement jsonElement = new JsonElement(type, value,
				true, isUsedAnnotation, jsonConfig);
		list.add(index, jsonElement);
	}
	
	/**
	 * 把给定的值放入<code>JsonArray</code>对象中指定的位置
	 * 
	 * @param value
	 * @param index
	 */
	public <T> void addBean(int index, T value){
		addBean(index, value, true);
	}
	
	/**
	 * 把给定的值放入<code>JsonArray</code>对象中
	 * 
	 * @param value
	 */
	public void add(Object value){
		value = clone(value);
		Class<?> type = value == null ? Object.class : value.getClass();
		JsonElement jsonElement = new JsonElement(type, value,
				type.getAnnotation(Bean.class) != null, jsonConfig);
		list.add(jsonElement);
	}
	
	/**
	 * 把给定的值放入<code>JsonArray</code>对象中
	 * 
	 * @param value
	 * @param isUsedAnnotation
	 *            是否启用FieldName注解转换 ,true是，false不启用
	 */
	@SuppressWarnings("unchecked")
	public <T> void addBean(T value, boolean isUsedAnnotation){
		value = (T) clone(value);
		Class<?> type = value == null ? Object.class : value.getClass();
		JsonElement jsonElement = new JsonElement(type, value,
				true, isUsedAnnotation, jsonConfig);
		list.add(jsonElement);
	}
	
	/**
	 * 把给定的值放入<code>JsonArray</code>对象中
	 * 
	 * @param value
	 */
	public <T> void addBean(T value){
		addBean(value, true); 
	}
	
	/**
	 * 浅克隆
	 * 
	 * @param value
	 * @return 当给定的对象与本对象相同，则返回其副本，否则返回给定的对象
	 */
	public Object clone(Object value){
		if(value == this){
			JsonArray jsonArray = new JsonArray();
			List<JsonElement> jsonElements = new LinkedList<JsonElement>(
					((JsonArray)value).list);
			for (JsonElement jsonElement : jsonElements) {
				jsonArray.addElement(jsonElement);
			}
			return jsonArray;
		}
		return value;
	}

	/**
	 * 通过给定的索引值获取JSonElement对象
	 * 
	 * @param index
	 * @return 假如给定的索引不小于JsonArray中元素的个数或小于0，返回一个新的JsonElement对象。
	 * <p>即 new JsonElement(Object.class, null)
	 */
	public JsonElement getJsonElement(int index) {
		if(index < 0 || index >= list.size())
			return new JsonElement(Object.class, null, false, jsonConfig);
		JsonElement jsonElement = list.get(index);
		jsonElement.setJsonConfig(jsonConfig);
		return jsonElement;
	}

	/**
	 * 获取JsonArray中的所有JsonElement对象数据
	 * 
	 * @return
	 */
	public JsonElement[] getJsonElements() {
		JsonElement[] jsonElements = list.toArray((JsonElement[]) 
				Array.newInstance(JsonElement.class, list.size()));
		for (JsonElement jsonElement : jsonElements) {
			jsonElement.setJsonConfig(jsonConfig);
		}
		return jsonElements;
	}
	
	/**
	 * 清除jsonArray对象中的数据
	 */
	public void clear(){
		list.clear();
	}
	
	/**
	 * 获取jsonArray对象中数据的个数
	 * 
	 * @return
	 */
	public int count(){
		return list.size();
	}
	
	/**
	 * 如果jsonArray对象中不包含元素，则返回 true。
	 * 
	 * @return
	 */
	public boolean isEmpty(){
		return list.isEmpty();
	}
	
	/**
	 * 移除jsonArray对象中指定索引的值
	 * 
	 * @param index
	 * @return
	 * @throws IllegalArgumentException
	 * 			假如指定的索引不存在，则抛出该异常
	 */
	public JsonElement remove(int index){
		if(index < 0 || index >= list.size())
			throw new IllegalArgumentException("[" + index + "]该索引在此JsonArray对象中不存在");
		return list.remove(index); 
	}
	
	/**
	 * 获取JsonArray对象数据组成的迭代器
	 * 
	 * @return
	 */
	public Iterator<JsonElement> iterator(){
		Iterator<JsonElement> iterator = list.iterator();
		while (iterator.hasNext()) {
			iterator.next().setJsonConfig(jsonConfig);
		}
		return list.iterator();
	}
	
	/**
	 * 把array转换成JsonArray对象
	 * 
	 * @param array 数组对象
	 * @param jsonConfig json配置
	 * @return JsonArray对象
	 * @throws IllegalArgumentException
	 * 			假如给定的第一个参数不是数组类型或为null，则抛出该异常
	 */
	public static JsonArray fromArray(Object array, JsonConfig jsonConfig){
		if(array == null || !array.getClass().isArray())
			throw new IllegalArgumentException("给定的第一个参数必须是数组类型");
		JsonArray jsonArray = new JsonArray(jsonConfig);
		int length = Array.getLength(array);//用反射获取数组的长度
		Object ele = null;
		for (int i = 0; i < length; i++) {
			ele = Array.get(array, i);//用反射获取数组中的元素
			jsonArray.add(ele);
		}
		return jsonArray;
	}
	
	/**
	 * 把array转换成JsonArray对象
	 * 
	 * @param array 数组对象
	 * @return JsonArray对象
	 * @throws IllegalArgumentException
	 * 			假如给定的参数不是数组类型或为null，则抛出该异常
	 */
	public static JsonArray fromArray(Object array){
		return fromArray(array, new JsonConfig());
	}
	
	/**
	 * 把Collection集合转换成JsonArray对象
	 * 
	 * @param collection 
	 * @param jsonConfig json配置
	 * @return JsonArray对象 ,如给定的第一个参数为null,返回数据个数为0的JsonArray对象
	 */
	public static <E> JsonArray fromCollection(Collection<E> collection,
			JsonConfig jsonConfig){
		JsonArray jsonArray = new JsonArray(jsonConfig);
		if(collection == null)
			return jsonArray;
		Iterator<E> iterator = collection.iterator();//获取迭代器
		Object ele = null;
		while (iterator.hasNext()) {//通过迭代器循环遍历集合数据
			ele = iterator.next();
			jsonArray.add(ele);
		}
		return jsonArray;
	}
	
	/**
	 * 把Collection集合转换成JsonArray对象
	 * 
	 * @param collection 
	 * @param jsonConfig json配置
	 * @param isUsedAnnotation
	 *            是否启用FieldName注解转换 ,true是，false不启用
	 * @return JsonArray对象 ,如给定的第一个参数为null,返回数据个数为0的JsonArray对象
	 */
	public static <T> JsonArray fromCollectionBean(Collection<T> collection,
			JsonConfig jsonConfig, boolean isUsedAnnotation){
		JsonArray jsonArray = new JsonArray(jsonConfig);
		if(collection == null)
			return jsonArray;
		Iterator<T> iterator = collection.iterator();//获取迭代器
		Object ele = null;
		while (iterator.hasNext()) {//通过迭代器循环遍历集合数据
			ele = iterator.next();
			jsonArray.addBean(ele, isUsedAnnotation);
		}
		return jsonArray;
	}
	
	/**
	 * 把Collection集合转换成JsonArray对象
	 * 
	 * @param collection 
	 * @param isUsedAnnotation
	 *            是否启用FieldName注解转换 ,true是，false不启用
	 * @return JsonArray对象 ,如给定的第一个参数为null,返回数据个数为0的JsonArray对象
	 */
	public static <T> JsonArray fromCollectionBean(Collection<T> collection,
			boolean isUsedAnnotation){
		return fromCollectionBean(collection, new JsonConfig(), true);
	}
	
	/**
	 * 把Collection集合转换成JsonArray对象
	 * 
	 * @param collection 
	 * @param jsonConfig json配置
	 * @return JsonArray对象 ,如给定的第一个参数为null,返回数据个数为0的JsonArray对象
	 */
	public static <T> JsonArray fromCollectionBean(Collection<T> collection,
			JsonConfig jsonConfig){
		return fromCollectionBean(collection, jsonConfig, true);
	}
	
	/**
	 * 把Collection集合转换成JsonArray对象
	 * 
	 * @param collection 
	 * @return JsonArray对象 ,如给定的第一个参数为null,返回数据个数为0的JsonArray对象
	 */
	public static <T> JsonArray fromCollectionBean(Collection<T> collection){
		return fromCollectionBean(collection, new JsonConfig(), true);
	}
	
	/**
	 * 把Collection集合转换成JsonArray对象
	 * 
	 * @param collection 
	 * @return JsonArray对象 ,如给定的参数为null,返回数据个数为0的JsonArray对象
	 */
	public static <E> JsonArray fromCollection(Collection<E> collection){
		return fromCollection(collection, new JsonConfig());
	}
	
	/**
	 * 把jsonArray格式的字符串转换成JsonArray对象
	 * 
	 * @param jsonString
	 * @param jsonConfig json配置
	 * @return JsonArray对象 ,如给定的第一个参数为null,返回数据个数为0的JsonArray对象
	 */
	public static JsonArray fromJsonString(String jsonString,
			JsonConfig jsonConfig){
		if (jsonConfig == null) 
			jsonConfig = new JsonConfig();
		if(jsonString == null)
			throw new JsonException("要解析的字符串必须不为null");
		jsonString = jsonString.trim();//去除首尾空格
		
		if(!jsonString.startsWith(JsonUtil.LEFT_M))
			throw new JsonException(jsonString + "该要解析的字符串不是以" + JsonUtil.LEFT_M + "开头");
		if(!jsonString.endsWith(JsonUtil.RIGHT_M))
			throw new JsonException(jsonString + "该要解析的字符串不是以" + JsonUtil.RIGHT_M + "结尾");
	
		int len = jsonString.length();
		if(len < LEFT_M_LEN + RIGHT_M_LEN)
			throw new JsonException(jsonString + "该要解析的字符串不复合json数组格式");
		
		if((JsonUtil.LEFT_M + JsonUtil.RIGHT_M).equals(jsonString)) //判断字符串是否==[]
			return new JsonArray(jsonConfig);
		return (JsonArray) parser(jsonString, jsonConfig);
	}
	
	/**
	 * 把jsonArray格式的字符串转换成JsonArray对象
	 * 
	 * @param jsonString
	 * @return JsonArray对象 ,如给定的第一个参数为null,返回数据个数为0的JsonArray对象
	 */
	public static JsonArray fromJsonString(String jsonString){
		return fromJsonString(jsonString, null);
	}
			
	/**
	 * 把jsonArray对象转换成list集合
	 * 
	 * @return
	 */
	public List<Object> toList(){
		return toList(Object.class);
	}
	
	/**
	 * 把jsonArray对象转换成指定类型的list集合
	 * 
	 * @param cs
	 * @return
	 */
	public <E> List<E> toList(Class<E> cs){
		List<E> list = new ArrayList<E>();
		Object value;
		Class<?> type;
		for (JsonElement je : this.list) {
			value = je.getValue();
			type = je.getType();
			if(!jsonConfig.getNullConfig().isOpen() && value == null)
				continue;
			if (type == JsonObject.class) {
				value = ((JsonObject)value).toMap(jsonConfig);
			}else if(type == JsonArray.class){
				value = ((JsonArray)value).toList(cs);
			}
			list.add(cs.cast(value));
		}
		return list;
	}
	
	/**
	 * 把jsonArray对象转换成array
	 * 
	 * @return
	 */
	public Object[] toArray(){
		return toList().toArray();
	}
	
	/**
	 * 把jsonArray对象转换成指定类型的array
	 * 
	 * @param cs
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> T[] toArray(Class<T> cs){
		List<T> list = toList(cs);
		return list.toArray((T[]) Array.newInstance(
				cs, list.size()));
	}
	
	/**
	 * 获取值
	 * 
	 * @param index
	 * @return
	 */
	public Object getObject(int index){
		return getJsonElement(index).getObject();
	}
	
	/**
	 * 获取值
	 * 
	 * @param index
	 * @param defaultValue
	 * @return 假如值为null，则返回给定的defaultVlaue值
	 */
	public Object getObject(int index, Object defaultValue){
		return getJsonElement(index).getObject(defaultValue);
	}
	
	/**
	 * 获取值
	 * 
	 * @param index
	 * @return
	 */
	public <T> T get(int index){
		return getJsonElement(index).get();
	}
	
	/**
	 * 获取值
	 * 
	 * @param index
	 * @param defaultValue
	 * @return 假如值为null，则返回给定的defaultVlaue值
	 */
	public <T> T get(int index, T defaultValue){
		return getJsonElement(index).get(defaultValue);
	}
	
	/**
	 * 获取值
	 * 
	 * @param index
	 * @return
	 */
	public String getString(int index){
		return getJsonElement(index).getString();
	}
	
	/**
	 * 获取值
	 * 
	 * @param index
	 * @param defaultValue
	 * @return 假如值为null，则返回给定的defaultVlaue值
	 */
	public String getString(int index, String defaultValue){
		return getJsonElement(index).getString(defaultValue);
	}
  }
