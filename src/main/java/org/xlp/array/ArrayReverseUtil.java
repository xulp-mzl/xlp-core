package org.xlp.array;

import java.lang.reflect.Array;

import org.xlp.utils.XLPArrayUtil;

/**
 * 数组反转工具类
 * 
 * @author 徐龙平
 *         <p>
 *         2017-03-13
 *         </p>
 * @version 1.0
 * 
 */
public class ArrayReverseUtil {
	/**
	 * @param sourceArray
	 * @return 返回反转后的新数组，原数组未被反转
	 */
	public static int[] arrayReverse(int[] sourceArray) {
		if (sourceArray == null) {
			return null;
		}

		int len = sourceArray.length;
		// 创建新数组
		int[] newArray = new int[len];
		for (int i = 0, j = len - 1; i < len; i++, j--) {// 反转
			newArray[j] = sourceArray[i];
		}

		return newArray;
	}

	/**
	 * @param sourceArray
	 * @return 返回反转后的新数组，原数组未被反转
	 */
	public static byte[] arrayReverse(byte[] sourceArray) {
		if (sourceArray == null) {
			return null;
		}

		int len = sourceArray.length;
		// 创建新数组
		byte[] newArray = new byte[len];
		for (int i = 0, j = len - 1; i < len; i++, j--) {// 反转
			newArray[j] = sourceArray[i];
		}

		return newArray;
	}

	/**
	 * @param sourceArray
	 * @return 返回反转后的新数组，原数组未被反转
	 */
	public static short[] arrayReverse(short[] sourceArray) {
		if (sourceArray == null) {
			return null;
		}

		int len = sourceArray.length;
		// 创建新数组
		short[] newArray = new short[len];
		for (int i = 0, j = len - 1; i < len; i++, j--) {// 反转
			newArray[j] = sourceArray[i];
		}

		return newArray;
	}

	/**
	 * @param sourceArray
	 * @return 返回反转后的新数组，原数组未被反转
	 */
	public static long[] arrayReverse(long[] sourceArray) {
		if (sourceArray == null) {
			return null;
		}

		int len = sourceArray.length;
		// 创建新数组
		long[] newArray = new long[len];
		for (int i = 0, j = len - 1; i < len; i++, j--) {// 反转
			newArray[j] = sourceArray[i];
		}

		return newArray;
	}

	/**
	 * @param sourceArray
	 * @return 返回反转后的新数组，原数组未被反转
	 */
	public static float[] arrayReverse(float[] sourceArray) {
		if (sourceArray == null) {
			return null;
		}

		int len = sourceArray.length;
		// 创建新数组
		float[] newArray = new float[len];
		for (int i = 0, j = len - 1; i < len; i++, j--) {// 反转
			newArray[j] = sourceArray[i];
		}

		return newArray;
	}

	/**
	 * @param sourceArray
	 * @return 返回反转后的新数组，原数组未被反转
	 */
	public static double[] arrayReverse(double[] sourceArray) {
		if (sourceArray == null) {
			return null;
		}

		int len = sourceArray.length;
		// 创建新数组
		double[] newArray = new double[len];
		for (int i = 0, j = len - 1; i < len; i++, j--) {// 反转
			newArray[j] = sourceArray[i];
		}

		return newArray;
	}

	/**
	 * @param sourceArray
	 * @return 返回反转后的新数组，原数组未被反转
	 */
	public static char[] arrayReverse(char[] sourceArray) {
		if (sourceArray == null) {
			return null;
		}

		int len = sourceArray.length;
		// 创建新数组
		char[] newArray = new char[len];
		for (int i = 0, j = len - 1; i < len; i++, j--) {// 反转
			newArray[j] = sourceArray[i];
		}

		return newArray;
	}

	/**
	 * @param sourceArray
	 * @return 返回反转后的新数组，原数组未被反转
	 */
	public static boolean[] arrayReverse(boolean[] sourceArray) {
		if (sourceArray == null) {
			return null;
		}

		int len = sourceArray.length;
		// 创建新数组
		boolean[] newArray = new boolean[len];
		for (int i = 0, j = len - 1; i < len; i++, j--) {// 反转
			newArray[j] = sourceArray[i];
		}

		return newArray;
	}

	/**
	 * @param sourceArray
	 * @param arrayClass
	 *            数组类型
	 * @return 返回反转后的新数组，原数组未被反转
	 */
	public static <T> T[] arrayReverse(T[] sourceArray, Class<T> arrayClass) {
		if (sourceArray == null || arrayClass == null) {
			return null;
		}

		int len = sourceArray.length;
		// 创建新数组
		@SuppressWarnings("unchecked")
		T[] newArray = (T[]) Array.newInstance(arrayClass, len);
		return XLPArrayUtil.reverse(newArray);
	}

	/**
	 * @param sourceArray
	 * @return 返回反转后的数组，原数组被反转
	 */
	public static <T> T[] arrayReverse(T[] sourceArray) {
		return XLPArrayUtil.reverse(sourceArray);
	}
}
