package org.xlp.utils;

import java.util.Map;

/**
 * 2022-05-21
 * @author xlp
 * 简化map操作工具类
 */
public final class XLPMapUtil {
	/**
	 * 判断给定的Map对象是否为null或大小为0
	 * 
	 * @param map
	 * @return true：是，false：不是
	 */
	public static <K, V> boolean isEmpty(Map<K,V> map){
		return map == null || map.isEmpty();
	}
}
