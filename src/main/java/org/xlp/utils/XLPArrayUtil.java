package org.xlp.utils;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * 工具类: 把基本类型数据数组转换成其对应的包装类型数组 把包装类型数组转换成其对应的基本类型数据数组
 * 
 * @author 徐龙平
 *         <p>
 *         2016-12-04
 *         </p>
 * @version 1.0
 * 
 */
public final class XLPArrayUtil {
	/**
	 * int[]->Integer[]
	 * 
	 * @param array
	 * @return 假如参数为null，返回空
	 */
	public static Integer[] intArrayToIntegerArray(int[] array) {
		if (array == null) {
			return null;
		}

		int len = array.length;
		Integer[] myArray = new Integer[len];
		for (int i = 0; i < len; i++) {
			myArray[i] = Integer.valueOf(array[i]);
		}
		return myArray;
	}

	/**
	 * Integer[]->int[]
	 * 
	 * @param array
	 * @return 假如参数为null，返回空
	 */
	public static int[] integerArrayToIntArray(Integer[] array) {
		if (array == null) {
			return null;
		}

		int len = array.length;
		int[] myArray = new int[len];
		for (int i = 0; i < len; i++) {
			myArray[i] = array[i];
		}
		return myArray;
	}

	/**
	 * long[]->Long[]
	 * 
	 * @param array
	 * @return 假如参数为null，返回空
	 */
	public static Long[] longArrayToLongArray(long[] array) {
		if (array == null) {
			return null;
		}

		int len = array.length;
		Long[] myArray = new Long[len];
		for (int i = 0; i < len; i++) {
			myArray[i] = Long.valueOf(array[i]);
		}
		return myArray;
	}

	/**
	 * Long[]->long[]
	 * 
	 * @param array
	 * @return 假如参数为null，返回空
	 */
	public static long[] longArrayToLongArray(Long[] array) {
		if (array == null) {
			return null;
		}

		int len = array.length;
		long[] myArray = new long[len];
		for (int i = 0; i < len; i++) {
			myArray[i] = array[i];
		}
		return myArray;
	}

	/**
	 * short[]->Short[]
	 * 
	 * @param array
	 * @return 假如参数为null，返回空
	 */
	public static Short[] shortArrayToShortArray(short[] array) {
		if (array == null) {
			return null;
		}

		int len = array.length;
		Short[] myArray = new Short[len];
		for (int i = 0; i < len; i++) {
			myArray[i] = Short.valueOf(array[i]);
		}
		return myArray;
	}

	/**
	 * Short[]->short[]
	 * 
	 * @param array
	 * @return 假如参数为null，返回空
	 */
	public static short[] shortArrayToShortArray(Short[] array) {
		if (array == null) {
			return null;
		}

		int len = array.length;
		short[] myArray = new short[len];
		for (int i = 0; i < len; i++) {
			myArray[i] = array[i];
		}
		return myArray;
	}

	/**
	 * byte[]->Byte[]
	 * 
	 * @param array
	 * @return 假如参数为null，返回空
	 */
	public static Byte[] byteArrayToByteArray(byte[] array) {
		if (array == null) {
			return null;
		}

		int len = array.length;
		Byte[] myArray = new Byte[len];
		for (int i = 0; i < len; i++) {
			myArray[i] = Byte.valueOf(array[i]);
		}
		return myArray;
	}

	/**
	 * Byte[]->byte[]
	 * 
	 * @param array
	 * @return 假如参数为null，返回空
	 */
	public static byte[] byteArrayToByteArray(Byte[] array) {
		if (array == null) {
			return null;
		}

		int len = array.length;
		byte[] myArray = new byte[len];
		for (int i = 0; i < len; i++) {
			myArray[i] = array[i];
		}
		return myArray;
	}

	/**
	 * float[]->Float[]
	 * 
	 * @param array
	 * @return 假如参数为null，返回空
	 */
	public static Float[] floatArrayToFloatArray(float[] array) {
		if (array == null) {
			return null;
		}

		int len = array.length;
		Float[] myArray = new Float[len];
		for (int i = 0; i < len; i++) {
			myArray[i] = Float.valueOf(array[i]);
		}
		return myArray;
	}

	/**
	 * Float[]->float[]
	 * 
	 * @param array
	 * @return 假如参数为null，返回空
	 */
	public static float[] floatArrayToFloatArray(Float[] array) {
		if (array == null) {
			return null;
		}

		int len = array.length;
		float[] myArray = new float[len];
		for (int i = 0; i < len; i++) {
			myArray[i] = array[i];
		}
		return myArray;
	}

	/**
	 * Double[]->double[]
	 * 
	 * @param array
	 * @return 假如参数为null，返回空
	 */
	public static double[] doubleArrayToDoubleArray(Double[] array) {
		if (array == null) {
			return null;
		}

		int len = array.length;
		double[] myArray = new double[len];
		for (int i = 0; i < len; i++) {
			myArray[i] = array[i];
		}
		return myArray;
	}

	/**
	 * double[]->Double[]
	 * 
	 * @param array
	 * @return 假如参数为null，返回空
	 */
	public static Double[] doubleArrayToDoubleArray(double[] array) {
		if (array == null) {
			return null;
		}

		int len = array.length;
		Double[] myArray = new Double[len];
		for (int i = 0; i < len; i++) {
			myArray[i] = Double.valueOf(array[i]);
		}
		return myArray;
	}

	/**
	 * char[]->Character[]
	 * 
	 * @param array
	 * @return 假如参数为null，返回空
	 */
	public static Character[] charArrayToCharacterArray(char[] array) {
		if (array == null) {
			return null;
		}

		int len = array.length;
		Character[] myArray = new Character[len];
		for (int i = 0; i < len; i++) {
			myArray[i] = Character.valueOf(array[i]);
		}
		return myArray;
	}

	/**
	 * Character[]->char[]
	 * 
	 * @param array
	 * @return 假如参数为null，返回空
	 */
	public static char[] charArrayToCharacterArray(Character[] array) {
		if (array == null) {
			return null;
		}

		int len = array.length;
		char[] myArray = new char[len];
		for (int i = 0; i < len; i++) {
			myArray[i] = array[i];
		}
		return myArray;
	}

	/**
	 * boolean[]->Boolean[]
	 * 
	 * @param array
	 * @return 假如参数为null，返回空
	 */
	public static Boolean[] booleanArrayToBooleanArray(boolean[] array) {
		if (array == null) {
			return null;
		}

		int len = array.length;
		Boolean[] myArray = new Boolean[len];
		for (int i = 0; i < len; i++) {
			myArray[i] = Boolean.valueOf(array[i]);
		}
		return myArray;
	}

	/**
	 * Boolean[]->boolean[]
	 * 
	 * @param array
	 * @return 假如参数为null，返回空
	 */
	public static boolean[] booleanArrayToBooleanArray(Boolean[] array) {
		if (array == null) {
			return null;
		}

		int len = array.length;
		boolean[] myArray = new boolean[len];
		for (int i = 0; i < len; i++) {
			myArray[i] = array[i];
		}
		return myArray;
	}

	/**
	 * 逆序输出数组
	 * 
	 * @param sourceArray
	 * @return
	 */
	public static <T> T[] reverse(T[] sourceArray) {
		if (sourceArray != null && sourceArray.length > 1) {
			int end = sourceArray.length, start;
			T temp;
			for (start = 0, --end; start < end; end--, start++) {
				temp = sourceArray[start];
				sourceArray[start] = sourceArray[end];
				sourceArray[end] = temp;
			}
		}
		return sourceArray;
	}

	/**
	 * 把数组转换成字符串
	 * 
	 * @param sourceArray
	 * @return
	 */
	public static <T> String arrayToString(T[] sourceArray) {
		return Arrays.toString(sourceArray);
	}
	
	/**
	 * 判断一个数组是否为null或大小为0
	 * 
	 * @param array 要判断的数组
	 * @return 假如为null或大小为0，返回true，否则返回false
	 */
	public static <T> boolean isEmpty(T[] array){
		return array == null || array.length == 0;
	}
	
	/**
	 * 判断一个数组是否为null或大小为0
	 * 
	 * @param array 要判断的数组
	 * @return 假如为null或大小为0或不是数组，返回true，否则返回false
	 */
	public static boolean isEmpty(Object array){
		if (array == null || !array.getClass().isArray()) {
			return true;
		}
		return Array.getLength(array) == 0;
	}
}
