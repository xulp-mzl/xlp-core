package org.xlp.javabean.convert.mapandbean;

import java.util.List;
import java.util.Map;

/**
 * map与bean相互转换接口
 * 
 * @author 徐龙平
 *         <p>
 *         2017-1-13
 *         </p>
 * @version 1.0
 * 
 */
public interface MapBean<T> {
	/**
	 * 用map创建一个指定类型的bean对象
	 * 
	 * @param map
	 *            key存储的字段名，value存储的是值
	 * @param cs
	 * 			  bean类型
	 * @return
	 */
	public T mapToBean(Map<String, ?> map, Class<T> cs);
	
	/**
	 * 把bean转换成Map
	 * 
	 * @param bean
	 * @return
	 */
	public Map<String, ?> beanToMap(T bean);
	
	/**
	 * 用List<Map<String, Object>>创建一个指定类型的List<T>对象
	 * 
	 * @param mapList
	 *            key存储的字段名，value存储的是值
	 * @param cs
	 * 			  bean类型
	 * @return
	 */
	public List<T> mapListToBeanList(List<Map<String, ?>> mapList, Class<T> cs);
	
	/**
	 * 把List<T> beaList转换成List<Map<String, Object>> Maps
	 * 
	 * @param beaList
	 * @return
	 */
	public List<Map<String, ?>> beanListToMapList(List<T> beaList);
}
