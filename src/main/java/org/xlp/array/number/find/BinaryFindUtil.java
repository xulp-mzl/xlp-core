package org.xlp.array.number.find;

import java.math.BigDecimal;

/**
 * 用二分法查找指定值的工具类（只适用于数字类型）
 * 
 * @author 徐龙平
 *         <p>
 *         2017-03-11
 *         </p>
 * @version 1.0
 * 
 */
public class BinaryFindUtil {
	// not found，未找到
	private final static int NOT_FOUND = -1;

	/**
	 * 用二分查找一个整型数组是否存在指定的值
	 * 
	 * @param array
	 *            该数组必须有序，并且是由小到大排序
	 * @param key
	 *            要查找的值
	 * @return 假如存在，返回其所在数组中的位置
	 */
	public static int binaryFind(int[] array, int key) {
		if (array == null || array.length == 0)
			return NOT_FOUND;

		return binaryFind(array, 0, array.length - 1, key);
	}

	// 递归实现
	private static int binaryFind(int[] array, int start, int end, int key) {
		if (start > end) {
			return NOT_FOUND;
		}

		int mid = (end + start) / 2;// 折半

		if (key > array[mid]) {
			return binaryFind(array, mid + 1, end, key);
		}

		if (key < array[mid]) {
			return binaryFind(array, start, mid - 1, key);
		}

		return mid;
	}

	/**
	 * 用二分查找一个short型数组是否存在指定的值
	 * 
	 * @param array
	 *            该数组必须有序，并且是由小到大排序
	 * @param key
	 *            要查找的值
	 * @return 假如存在，返回其所在数组中的位置
	 */
	public static int binaryFind(short[] array, short key) {
		if (array == null || array.length == 0)
			return NOT_FOUND;

		return binaryFind(array, 0, array.length - 1, key);
	}

	// 递归实现
	private static int binaryFind(short[] array, int start, int end, short key) {
		if (start > end) {
			return NOT_FOUND;
		}

		int mid = (end + start) / 2;// 折半

		if (key > array[mid]) {
			return binaryFind(array, mid + 1, end, key);
		}

		if (key < array[mid]) {
			return binaryFind(array, start, mid - 1, key);
		}

		return mid;
	}

	/**
	 * 用二分查找一个byte型数组是否存在指定的值
	 * 
	 * @param array
	 *            该数组必须有序，并且是由小到大排序
	 * @param key
	 *            要查找的值
	 * @return 假如存在，返回其所在数组中的位置
	 */
	public static int binaryFind(byte[] array, byte key) {
		if (array == null || array.length == 0)
			return NOT_FOUND;

		return binaryFind(array, 0, array.length - 1, key);
	}

	// 递归实现
	private static int binaryFind(byte[] array, int start, int end, byte key) {
		if (start > end) {
			return NOT_FOUND;
		}

		int mid = (end + start) / 2;// 折半

		if (key > array[mid]) {
			return binaryFind(array, mid + 1, end, key);
		}

		if (key < array[mid]) {
			return binaryFind(array, start, mid - 1, key);
		}

		return mid;
	}

	/**
	 * 用二分查找一个long型数组是否存在指定的值
	 * 
	 * @param array
	 *            该数组必须有序，并且是由小到大排序
	 * @param key
	 *            要查找的值
	 * @return 假如存在，返回其所在数组中的位置
	 */
	public static int binaryFind(long[] array, long key) {
		if (array == null || array.length == 0)
			return NOT_FOUND;

		return binaryFind(array, 0, array.length - 1, key);
	}

	// 递归实现
	private static int binaryFind(long[] array, int start, int end, long key) {
		if (start > end) {
			return NOT_FOUND;
		}

		int mid = (end + start) / 2;// 折半

		if (key > array[mid]) {
			return binaryFind(array, mid + 1, end, key);
		}

		if (key < array[mid]) {
			return binaryFind(array, start, mid - 1, key);
		}

		return mid;
	}

	/**
	 * 用二分查找一个double型数组是否存在指定的值
	 * 
	 * @param array
	 *            该数组必须有序，并且是由小到大排序
	 * @param key
	 *            要查找的值
	 * @return 假如存在，返回其所在数组中的位置
	 */
	public static int binaryFind(double[] array, double key) {
		if (array == null || array.length == 0)
			return NOT_FOUND;

		return binaryFind(array, 0, array.length - 1, key);
	}

	// 递归实现
	private static int binaryFind(double[] array, int start, int end, double key) {
		if (start > end) {
			return NOT_FOUND;
		}

		int mid = (end + start) / 2;// 折半

		if (key > array[mid]) {
			return binaryFind(array, mid + 1, end, key);
		}

		if (key < array[mid]) {
			return binaryFind(array, start, mid - 1, key);
		}

		return mid;
	}

	/**
	 * 用二分查找一个float型数组是否存在指定的值
	 * 
	 * @param array
	 *            该数组必须有序，并且是由小到大排序
	 * @param key
	 *            要查找的值
	 * @return 假如存在，返回其所在数组中的位置
	 */
	public static int binaryFind(float[] array, float key) {
		if (array == null || array.length == 0)
			return NOT_FOUND;

		return binaryFind(array, 0, array.length - 1, key);
	}

	// 递归实现
	private static int binaryFind(float[] array, int start, int end, float key) {
		if (start > end) {
			return NOT_FOUND;
		}

		int mid = (end + start) / 2;// 折半

		if (key > array[mid]) {
			return binaryFind(array, mid + 1, end, key);
		}

		if (key < array[mid]) {
			return binaryFind(array, start, mid - 1, key);
		}

		return mid;
	}

	/**
	 * 用二分查找一个T型数组是否存在指定的值
	 * 
	 * @param array
	 *            该数组必须有序(是基本数字类型的包装类)，并且是由小到大排序
	 * @param key
	 *            要查找的值
	 * @return 假如存在，返回其所在数组中的位置
	 */
	public static <T> int binaryNumberFind(T[] array, T key) {
		if (array == null || array.length == 0)
			return NOT_FOUND;

		return binaryNumberFind(array, 0, array.length - 1, key);
	}

	// 递归实现
	private static <T> int binaryNumberFind(T[] array, int start, int end, T key) {
		if (start > end) {
			return NOT_FOUND;
		}

		int mid = (end + start) / 2;// 折半

		if (compareTo(key, array[mid]) > 0) {
			return binaryNumberFind(array, mid + 1, end, key);
		}

		if (compareTo(key, array[mid]) < 0) {
			return binaryNumberFind(array, start, mid - 1, key);
		}

		return mid;
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

}
