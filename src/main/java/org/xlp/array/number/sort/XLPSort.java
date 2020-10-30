package org.xlp.array.number.sort;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.math.BigDecimal;
import java.util.List;

import org.xlp.utils.XLPArrayUtil;
import org.xlp.utils.XLPListArrayUtil;

/**
 * 排序类，针对基本数字类型的包装类型
 * 
 * @author 徐龙平
 *         <p>
 *         2016-12-04
 *         </p>
 * @version 1.0
 * 
 */
public class XLPSort<T> {
	// 直接选择排序
	public final static int SELECTION_SORT = 1;
	// 冒泡排序
	public final static int BUBBLE_SORT = 2;
	// 快速排序
	public final static int FAST_SORT = 3;
	// 归并排序
	public final static int MERGING_SORT = 4;
	// 插入排序
	public final static int INSERTION_SORT = 5;
	// shell排序
	public final static int SHELL_SORT = 6;

	private int sortType = MERGING_SORT;
	// 存储排序的数据的类型
	private Class<T> cs = null;

	// 标志是排序方式，默认为升序
	private boolean desc = false;

	/**
	 * @param cs
	 *            排序的数据的类型
	 * @throws XLPSortException
	 */
	public XLPSort(Class<T> cs) throws XLPSortException {
		this(cs, MERGING_SORT);
	}

	/**
	 * @param cs
	 *            排序的数据的类型
	 * @param sortType
	 *            排序方式
	 * @throws XLPSortException
	 */
	public XLPSort(Class<T> cs, int sortType) throws XLPSortException {
		if (cs == null || !isNumberType(cs)) {
			throw new XLPSortException("排序内容的类型不是数字类型！");
		}
		this.sortType = sortType;
		this.cs = cs;
	}

	/**
	 * @param cs
	 *            排序的数据的类型
	 * @param desc
	 *            排序方式（从高到低desc=true,否则为false）
	 * @throws XLPSortException
	 */
	public XLPSort(Class<T> cs, boolean desc) throws XLPSortException {
		if (cs == null || !isNumberType(cs)) {
			throw new XLPSortException("排序内容的类型不是数字类型！");
		}
		this.desc = desc;
		this.cs = cs;
	}

	/**
	 * @param cs
	 *            排序的数据的类型
	 * @param desc
	 *            排序方式（从高到低desc=true,否则为false）
	 * @param sortType
	 *            排序方式(何种排序)
	 * @throws XLPSortException
	 */
	public XLPSort(Class<T> cs, boolean desc, int sortType)
			throws XLPSortException {
		this(cs, desc);
		this.sortType = sortType;
	}

	public void setSortType(int sortType) {
		this.sortType = sortType;
	}

	public void setDesc(boolean desc) {
		this.desc = desc;
	}

	/**
	 * 判断是否是数字类型
	 * 
	 * @param cs
	 */
	private boolean isNumberType(Class<T> cs) {
		T obj = null;
		try {
			Constructor<T> constructor = cs
					.getConstructor(new Class<?>[] { String.class });
			obj = constructor.newInstance(new Object[] { "0" });
			if (!(obj instanceof Number)) {
				return false;
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return true;
	}

	/**
	 * 直接选择排序是不稳定的排序，平均时间复杂度为O(n^2) 最好最坏都为O(n^2),空间复杂度为O(1) 算法简述： 选择排序（Selection
	 * sort）是一种简单直观的排序算法。 它的工作原理是每一次从待排序的数据元素中选出最小（或最大）的一个元素，
	 * 存放在序列的起始位置，直到全部待排序的数据元素排完。 选择排序是不 稳定的排序方法（比如序列[5， 5， 3]第一次就将第一个[5]与[3]交换，
	 * 导致第一个5挪动到第二个5后面）
	 * 
	 * @param sourceArray
	 *            排序数组
	 * @return
	 */
	private T[] selectionSort(T[] sourceArray) {
		int k;
		int len = sourceArray.length;
		for (int i = 0; i < len; i++) {
			k = i;
			for (int j = i + 1; j < len; j++) {
				if (this.compareTo(sourceArray[k], sourceArray[j]) > 0) {
					k = j; // 选出最小数的数组下标
				}
			}

			if (k != i) {// 把选择好的数据放到外循环中选中的位置中
				T temp = sourceArray[k];
				sourceArray[k] = sourceArray[i];
				sourceArray[i] = temp; // 把最小数放在第一个位置
			}
		}
		return sourceArray;
	}

	/**
	 * 改进的冒泡排序 此排序是稳定排序，平均时间复杂度为O(n^2)最好为O(n)，最坏为O(n^2), 空间复杂度为O(1)
	 * 冒泡排序（BubbleSort）的基本概念是：依次比较相邻的两个数，将小数放在前面，
	 * 大数放在后面。即在第一趟：首先比较第1个和第2个数，将小数放前，大数放 后。然后比较第2个数和第3个数，将小数放前，大数放后，如此继续，直至比
	 * 较最后两个数，将小数放前，大数放后。
	 * 
	 * @param sourceArray
	 * @return
	 */
	private T[] bubbleSort(T[] sourceArray) {
		// 用来存储是否相邻两个数是否交换
		boolean changed;
		int len = sourceArray.length;
		for (int i = 0; i < len; i++) {
			// 假设开始没有交换
			changed = false;
			for (int j = 1; j < len - i; j++) {
				if (this.compareTo(sourceArray[j - 1], sourceArray[j]) > 0) {
					T temp = sourceArray[j];
					sourceArray[j] = sourceArray[j - 1];
					sourceArray[j - 1] = temp; // 把最大数向后移动一个位置
					changed = true;
				}
			}

			if (!changed) {
				break;
			}
		}
		return sourceArray;
	}

	/**
	 * 快速排序： 此排序是不稳定排序，平均时间复杂度为O(nlog2n)最好为O(nlog2n)，最坏为O(n^2), 空间复杂度为O(nlog2n)
	 * 快速排序由C. A. R. Hoare在1962年提出。它的基本思想是：通过一趟排序将
	 * 要排序的数据分割成独立的两部分，其中一部分的所有数据都比另外一部分的所 有数据都要小，然后再按此方法对这两部分数据分别进行快速排序，整个排序过
	 * 程可以递归进行，以此达到整个数据变成有序序列。
	 * 
	 * @param sourceArray
	 * @return
	 */
	private T[] fastSort(T[] sourceArray) {
		if (sourceArray == null || sourceArray.length <= 1) {
			return sourceArray;
		}
		return this.fastSort(sourceArray, 0, sourceArray.length - 1);
	}

	/**
	 * 递归实现
	 * 
	 * @param sourceArray
	 * @param low
	 * @param high
	 * @return
	 */
	private T[] fastSort(T[] sourceArray, int low, int high) {
		if (low < high) {
			/* 完成一次单元排序 */
			int index = sortUnit(sourceArray, low, high);
			/* 对左边单元进行排序 */
			fastSort(sourceArray, low, index - 1);
			/* 对右边单元进行排序 */
			fastSort(sourceArray, index + 1, high);
		}
		return sourceArray;
	}

	/**
	 * 一趟快速排序的结果
	 * 
	 * @param array
	 * @param low
	 * @param high
	 * @return
	 */
	private int sortUnit(T[] array, int low, int high) {
		T key = array[low];
		while (low < high) {
			/* 从后向前搜索比key小的值 */
			while (this.compareTo(array[high], key) >= 0 && high > low) {
				--high;
			}
			/* 比key小的放左边 */
			array[low] = array[high];
			/* 从前向后搜索比key大的值，比key大的放右边 */
			while (this.compareTo(array[low], key) <= 0 && high > low) {
				++low;
			}
			/* 比key大的放右边 */
			array[high] = array[low];
		}
		/* 左边都比key小，右边都比key大。//将key放在游标当前位置。//此时low等于high */
		array[low] = key;
		return high;
	}

	/**
	 * 归并排序（MERGE-SORT）是建立在归并操作上的一种有效的排序算法,该算法是采用分治法（Divide and Conquer）
	 * 的一个非常典型的应用。将已有序的子序列合并，得到完全有序的序列；即先使每个子序列有序，再使子序列段间有序。
	 * 若将两个有序表合并成一个有序表，称为二路归并。 此排序是稳定排序，平均时间复杂度为O(nlogn)最好为O(nlogn)，最坏为O(nlogn),
	 * 空间复杂度为O(n)
	 * 
	 * @param sourceArray
	 * @return
	 */
	private T[] mergeSort(T[] sourceArray) {
		if (sourceArray == null || sourceArray.length <= 1) {
			return sourceArray;
		}
		mergeSort(sourceArray, 0, 1);
		return sourceArray;
	}

	/**
	 * <pre>
	 * 二路归并
	 * 原理：将两个有序表合并和一个有序表
	 * </pre>
	 * 
	 * @param　a 　
	 * @param　s 第一个有序表的起始下标 　
	 * @param　m 第二个有序表的起始下标
	 * @param　t 　第二个有序表的结束下标 　 　
	 */
	private void merge(T[] a, int s, int m, int t) {
		@SuppressWarnings("unchecked")
		T[] tmp = (T[]) Array.newInstance(cs, t - s + 1);
		int i = s, j = m, k = 0;
		while (i < m && j <= t) {
			if (this.compareTo(a[i], a[j]) <= 0) {
				tmp[k] = a[i];
				k++;
				i++;
			} else {
				tmp[k] = a[j];
				j++;
				k++;
			}
		}
		while (i < m) {
			tmp[k] = a[i];
			i++;
			k++;
		}
		while (j <= t) {
			tmp[k] = a[j];
			j++;
			k++;
		}
		System.arraycopy(tmp, 0, a, s, tmp.length);
	}

	/**
	 * @param　a 　
	 * @param　s 　
	 * @param　len 每次归并的有序集合的长度 　
	 */
	private void mergeSort(T[] a, int s, int len) {
		int size = a.length;
		int mid = size / (len << 1);
		int c = size & ((len << 1) - 1);
		// 　-------归并到只剩一个有序集合的时候结束算法-------//
		if (mid == 0) {
			return;
		}
		// 　------进行一趟归并排序-------//
		for (int i = 0; i < mid; ++i) {
			s = i * (len << 1);
			merge(a, s, s + len, (len << 1) + s - 1);
		}
		// 　-------将剩下的数和倒数一个有序集合归并-------//
		if (c != 0) {
			merge(a, size - c - (len << 1), size - c, size - 1);
		}
		// 　-------递归执行下一趟归并排序------//
		mergeSort(a, 0, (len << 1));
	}

	/**
	 * 插入排序 平均时间复杂度为O(n^2)，最坏为O(n^2)
	 * 
	 * @param sourceArray
	 * @return
	 */
	private T[] insertionSort(T[] sourceArray) {
		int length = sourceArray.length;
		for (int i = 1; i < length; i++) {
			T temp = sourceArray[i];
			int j = i - 1;
			for (; j >= 0 && this.compareTo(temp, sourceArray[j]) < 0; j--) {
				sourceArray[j + 1] = sourceArray[j];
			}
			sourceArray[j + 1] = temp;
		}

		return sourceArray;
	}

	/**
	 * shell排序 平均时间复杂度为O(n^3/2)，最坏为O(n^2)
	 * 
	 * @param sourceArray
	 * @return
	 */
	private T[] shellSort(T[] sourceArray) {
		int length = sourceArray.length;
		T temp;
		for (int r = length / 2; r >= 1; r /= 2) {
			for (int i = r; i < length; i++) {
				temp = sourceArray[i];
				int j = i - r;
				while (j >= 0 && this.compareTo(temp, sourceArray[j]) < 0) {
					sourceArray[j + r] = sourceArray[j];
					j -= r;
				}
				sourceArray[j + r] = temp;
			}
		}

		return sourceArray;
	}

	/**
	 * 比较两个数的大小
	 * 
	 * @param n1
	 * @param n2
	 * @return 0|1|-1
	 */
	private int compareTo(T n1, T n2) {
		BigDecimal bg1 = new BigDecimal(n1 + "");
		BigDecimal bg2 = new BigDecimal(n2 + "");
		return bg1.compareTo(bg2);
	}

	/**
	 * 排序
	 * 
	 * @param sourceArray
	 * @return
	 */
	public T[] sort(T[] sourceArray) {
		if (sourceArray != null) {
			switch (sortType) {
			case BUBBLE_SORT:
				sourceArray = bubbleSort(sourceArray);
				break;
			case SELECTION_SORT:
				sourceArray = selectionSort(sourceArray);
				break;
			case FAST_SORT:
				sourceArray = fastSort(sourceArray);
				break;
			case MERGING_SORT:
				sourceArray = mergeSort(sourceArray);
				break;
			case INSERTION_SORT:
				sourceArray = insertionSort(sourceArray);
				break;
			case SHELL_SORT:
				sourceArray = shellSort(sourceArray);
				break;
			default:
				break;
			}
		}
		if (desc) {
			XLPArrayUtil.reverse(sourceArray);
		}
		return sourceArray;
	}

	/**
	 * 排序 返回list集合
	 * 
	 * @param sourceArray
	 * @return
	 */
	public List<T> sortToList(T[] sourceArray) {
		return XLPListArrayUtil.arrayToList(sort(sourceArray));
	}

	/**
	 * 排序 返回数组
	 * 
	 * @param source
	 *            待排序的list集合
	 * @return
	 */
	public T[] sort(List<T> source) {
		return sort(XLPListArrayUtil.listToArray(source, cs));
	}

	/**
	 * 排序 返回list集合
	 * 
	 * @param source
	 *            待排序的list集合
	 * @return
	 */
	public List<T> sortToList(List<T> source) {
		return XLPListArrayUtil.arrayToList(sort(source));
	}

}
