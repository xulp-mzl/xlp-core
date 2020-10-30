package org.xlp.javabean.convert.arrayandbean;

import java.util.List;

/**
 * 数组与bean相互转换接口
 * 
 *  @author 徐龙平
 *         <p>
 *         2017-5-22
 *         </p>
 * @version 2.0
 * 
 */
public interface ArrayBean<T> {
	/**
	 * 用array创建一个指定类型的bean对象
	 * 
	 * @param array
	 *            字段值
	 * @param cs
	 * 			  bean类型
	 * @return
	 */
	public T arrayToBean(Object[] array, Class<T> cs);
	
	/**
	 * 把bean转换成Array
	 * 
	 * @param bean
	 * @return
	 */
	public Object[] beanToArray(T bean);
	
	/**
	 * 用List<Object[]>创建一个指定类型的List<T>对象
	 * 
	 * @param arrays
	 * @param cs
	 * 			  bean类型
	 * @return
	 */
	public List<T> arrayListToBeanList(List<Object[]> arrays, Class<T> cs);
	
	/**
	 * 把List<T> beaList转换成List<Object[]> arrays
	 * 
	 * @param beaList
	 * @return
	 */
	public List<Object[]> beanListToArrayList(List<T> beaList);
}
