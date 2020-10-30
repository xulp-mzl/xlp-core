package org.xlp.array.number.find;

import java.math.BigDecimal;


/**
 * 直接查找指定值的工具类
 * 
 * @author 徐龙平
 *         <p>
 *         2017-03-11
 *         </p>
 * @version 1.0
 * 
 */
public class StartToEndFindUtil {
	// not found，未找到
	private final static int NOT_FOUND = -1;

	/**
	 * 查找一个整型数组是否存在指定的值
	 * 
	 * @param array
	 * @param key
	 *            要查找的值
	 * @return 假如存在，返回其所在数组中的位置
	 */
	public static int find(int[] array, int key) {
		if (array == null || array.length == 0)
			return NOT_FOUND;

		int len = array.length;
		for (int i = 0; i < len; i++) {
			if (key == array[i]) {
				return i;
			}
		}
		return NOT_FOUND;
	}
	
	/**
	 * 查找一个byte整型数组是否存在指定的值
	 * 
	 * @param array
	 * @param key
	 *            要查找的值
	 * @return 假如存在，返回其所在数组中的位置
	 */
	public static int find(byte[] array, byte key) {
		if (array == null || array.length == 0)
			return NOT_FOUND;

		int len = array.length;
		for (int i = 0; i < len; i++) {
			if (key == array[i]) {
				return i;
			}
		}
		return NOT_FOUND;
	}
	
	/**
	 * 查找一个long整型数组是否存在指定的值
	 * 
	 * @param array
	 * @param key
	 *            要查找的值
	 * @return 假如存在，返回其所在数组中的位置
	 */
	public static int find(long[] array, long key) {
		if (array == null || array.length == 0)
			return NOT_FOUND;

		int len = array.length;
		for (int i = 0; i < len; i++) {
			if (key == array[i]) {
				return i;
			}
		}
		return NOT_FOUND;
	}
	
	/**
	 * 查找一个short整型数组是否存在指定的值
	 * 
	 * @param array
	 * @param key
	 *            要查找的值
	 * @return 假如存在，返回其所在数组中的位置
	 */
	public static int find(short[] array, short key) {
		if (array == null || array.length == 0)
			return NOT_FOUND;

		int len = array.length;
		for (int i = 0; i < len; i++) {
			if (key == array[i]) {
				return i;
			}
		}
		return NOT_FOUND;
	}
	
	/**
	 * 查找一个double整型数组是否存在指定的值
	 * 
	 * @param array
	 * @param key
	 *            要查找的值
	 * @return 假如存在，返回其所在数组中的位置
	 */
	public static int find(double[] array, double key) {
		if (array == null || array.length == 0)
			return NOT_FOUND;

		int len = array.length;
		for (int i = 0; i < len; i++) {
			if (key == array[i]) {
				return i;
			}
		}
		return NOT_FOUND;
	}
	
	/**
	 * 查找一个float整型数组是否存在指定的值
	 * 
	 * @param array
	 * @param key
	 *            要查找的值
	 * @return 假如存在，返回其所在数组中的位置
	 */
	public static int find(float[] array, float key) {
		if (array == null || array.length == 0)
			return NOT_FOUND;

		int len = array.length;
		for (int i = 0; i < len; i++) {
			if (key == array[i]) {
				return i;
			}
		}
		return NOT_FOUND;
	}
	
	/**
	 * 查找一个T型数组是否存在指定的值
	 * 
	 * @param array 该数组必须是基本数字类型的包装类
	 * @param key 要查找的值
	 * @return
	 * 			假如存在，返回其所在数组中的位置
	 */
	public static <T> int numberFind(T[] array, T key){
		if (array == null || array.length == 0)
			return NOT_FOUND;
		
		int len = array.length;
		for (int i = 0; i < len; i++) {
			if (compareTo(key, array[i]) == 0) {
				return i;
			}
		}
		return NOT_FOUND;
	}

	/**
	 * 比较两个数的大小
	 * 
	 * @param n1
	 * @param n2
	 * @return 0|1|-1
	 */
	private static <T> int compareTo(T n1, T n2) {
		BigDecimal bg1 = new BigDecimal(n1 + "");
		BigDecimal bg2 = new BigDecimal(n2 + "");
		return bg1.compareTo(bg2);
	}
	
	/**
	 * 查找一个T型数组是否存在指定的值
	 * 
	 * @param array
	 * @param key 要查找的值
	 * @return
	 * 			假如存在，返回其所在数组中的位置
	 */
	public static <T> int objectFind(T[] array, T key){
		if (array == null || array.length == 0)
			return NOT_FOUND;
		
		int len = array.length;
		for (int i = 0; i < len; i++) {
			if (key == array[i]) {
				return i;
			}
		}
		return NOT_FOUND;
	}
}
