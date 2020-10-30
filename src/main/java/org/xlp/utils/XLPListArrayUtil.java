package org.xlp.utils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * list解析成Array和Array解析成list工具类
 * 
 * @author 徐龙平
 *         <p>
 *         2016-12-04
 *         </p>
 * @version 1.0
 * 
 */
public class XLPListArrayUtil {
	/**
	 * 把list集合解析成数组
	 * @param list
	 * @param cs 集合中数据的类型
	 * @return
	 */
	public static <T> T[] listToArray(List<T> list, Class<T> cs) {
		if (list == null || cs == null) {
			return null;
		}
		int size = list.size();
		@SuppressWarnings("unchecked")
		T[] array = list.toArray((T[])Array.newInstance(cs,size));
		return array;
	}
	
	/**
	 * 把数组解析成list集合
	 * @param array
	 * @return
	 */
	public static <T> List<T> arrayToList(T[] array){
		if (array == null) {
			return null;
		}
		int length = array.length;
		List<T> list = new ArrayList<T>(length);
		for (int i = 0; i < length; i++) {
			list.add(array[i]);
		}
		return list;
	}
	
	/**
	 * 把数组解析成list集合
	 * @param array
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T> arrayToList(Object array){
		if (array == null) {
			return null;
		}
		int length = Array.getLength(array);//用反射获取数组的长度
		List<T> list = new ArrayList<T>(length);
		Object ele = null;
		for (int i = 0; i < length; i++) {
			ele = Array.get(array, i);//用反射获取数组中的元素
			list.add((T) ele);
		}
		return list;
	}
}
