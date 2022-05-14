package org.xlp.utils;

import java.util.ArrayList;
import java.util.List;

import org.xlp.assertion.AssertUtils;

/**
 * 数组或List分组工具类
 * 
 * @author xlp
 *
 */
public class XLPSplitUtils {
	/**
	 * 把原List分成指定长度的片段
	 * 
	 * @param source 原List
	 * @param length 长度
	 * @return 
	 * @throws NullPointerException 假如原List为null，则抛出该异常
	 * @throws IllegalArgumentException 假如分片长度小于0，则抛出该异常
	 */
	public static <T> List<List<T>> split(List<T> source, int length) {
		AssertUtils.isNotNull(source, "分片对象不能为null！");
		if (length <= 0) {
			throw new IllegalArgumentException("分片长度 length 不能小于0");
		}
		List<List<T>> list = new ArrayList<List<T>>();
		int init = 0;
		int size = source.size();
		int round = size % length == 0 ? size / length : size / length + 1;
		List<T> subList;
		for(int i = 1; i <= round; i++){
			subList = new ArrayList<T>();
			for(int j = init * length; j < size && j < i * length; j++){
				subList.add(source.get(j));
			}
			list.add(subList);
			init = i;
		}
		return list;
	}
	
	/**
	 * 把原Array分成指定长度的片段
	 * 
	 * @param source 原数组
	 * @param length 长度
	 * @return 
	 * @throws NullPointerException 假如原List为null，则抛出该异常
	 * @throws IllegalArgumentException 假如分片长度小于0，则抛出该异常
	 */
	public static <T> List<T[]> split(T[] source, int length) {
		AssertUtils.isNotNull(source, "分片对象不能为null！");
		if (length <= 0) {
			throw new IllegalArgumentException("分片长度 length 不能小于0");
		}
		@SuppressWarnings("unchecked")
		Class<T> sourceElementClass = (Class<T>) source[0].getClass();
		List<T[]> list = new ArrayList<T[]>();
		int init = 0;
		int size = source.length;
		int round = size % length == 0 ? size / length : size / length + 1;
		List<T> subList;
		for(int i = 1; i <= round; i++){
			subList = new ArrayList<T>();
			for(int j = init * length; j < size && j < i * length; j++){
				subList.add(source[j]);
			}
			list.add(XLPListArrayUtil.listToArray(subList, sourceElementClass));
			init = i;
		}
		return list;
	}
}
