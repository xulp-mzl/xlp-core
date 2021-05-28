package org.xlp.utils.collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.xlp.utils.XLPOutputInfoUtil;

/**
 * @version 1.0
 * @author 徐龙平
 *         <p>
 *         2017-5-19
 *         </p>
 * 
 *         简化集合操作的工具类。
 *         <p>
 *         此类主要功能有：判断集合是否为null或大小为0，把集合转换为一定格式的字符串等
 */
public class XLPCollectionUtil {
	public static final String DEFAULT_SPLIT = ",";// 默认分隔符

	/**
	 * 判断指定集合是否为null或指定集合的大小是否为0
	 * 
	 * @param collection
	 * @return 假如为null或大小为0，返回true，否则返回false
	 */
	public static <E> boolean isEmpty(Collection<E> collection) {
		return (collection == null || collection.size() == 0);
	}

	/**
	 * 把指定的集合变成无前后缀的字符串，不清除集合中的null值
	 * 
	 * @param collection
	 * @param split
	 *            分隔符
	 * @return 假如指定的集合为null或size=0，返回"",否则返回以指定分隔符分隔的字符串
	 */
	public static <E> String toString(Collection<E> collection, String split) {
		return toString(collection, null, split, null, false);
	}

	/**
	 * 把指定的集合变成无前后缀的字符串，清除集合中的null值
	 * 
	 * @param collection
	 * @param split
	 *            分隔符
	 * @return 假如指定的集合为null或size=0，返回"",否则返回以指定分隔符分隔的字符串
	 */
	public static <E> String toCleanNullStr(Collection<E> collection,
			String split) {
		return toString(collection, null, split, null, true);
	}

	/**
	 * 把指定的集合变成字符串
	 * 
	 * @param collection
	 * @param split
	 *            分隔符
	 * @param prefix
	 *            前缀，假如此值设为null，则无前缀
	 * @param suffix
	 *            后缀，假如此值设为null，则无后缀
	 * @param cleanNull
	 *            表示当集合中存在null时，是否清除返回此集合的字符串，此值设为false清除，否则不清楚
	 * @return 假如指定的集合为null或size=0，返回"",否则返回以指定分隔符分隔的字符串
	 */
	private static <E> String toString(Collection<E> collection, String prefix,
			String split, String suffix, boolean cleanNull) {
		StringBuffer sb = new StringBuffer();
		if (prefix != null)
			sb.append(prefix);// 添加前缀
		// 判断集合是否为null或size=0
		if (!isEmpty(collection)) {
			if (split == null)
				split = DEFAULT_SPLIT;
			boolean start = true;
			for (E e : collection) {
				if (e == null && cleanNull)// 判断是否要清楚null
					continue;
				if (!start)
					sb.append(split); // 添加分隔符
				sb.append(e);// 添加元素
				start = false;
			}
		}
		if (suffix != null)
			sb.append(suffix);// 添加后缀
		return sb.toString();
	}

	/**
	 * 把指定的集合变成带前后缀的字符串，不清除集合中的null值
	 * 
	 * @param collection
	 * @param split
	 *            分隔符
	 * @param prefix
	 *            前缀，假如此值设为null，则无前缀
	 * @param suffix
	 *            后缀，假如此值设为null，则无后缀
	 * @return 假如指定的集合为null或size=0，返回prefix + "" + suffix,否则返回以指定分隔符分隔的字符串
	 */
	public static <E> String toCleanNullStr(Collection<E> collection,
			String prefix, String split, String suffix) {
		return toString(collection, prefix, split, suffix, true);
	}

	/**
	 * 把指定的集合变成带前后缀的字符串，清除集合中的null值
	 * 
	 * @param collection
	 * @param split
	 *            分隔符
	 * @param prefix
	 *            前缀，假如此值设为null，则无前缀
	 * @param suffix
	 *            后缀，假如此值设为null，则无后缀
	 * @return 假如指定的集合为null或size=0，返回prefix + "" + suffix,否则返回以指定分隔符分隔的字符串
	 */
	public static <E> String toString(Collection<E> collection, String prefix,
			String split, String suffix) {
		return toString(collection, prefix, split, suffix, false);
	}

	/**
	 * 把大小为0的指定集合设置为null
	 * 
	 * @param collection
	 *            给定的集合
	 * @return 假如给定的集合大小为0返回null，否则返回给定的集合
	 */
	public static <E> Collection<E> toNull(Collection<E> collection) {
		return (collection != null && collection.size() == 0) ? null
				: collection;
	}

	/**
	 * 移除指定集合中的指定的值
	 * 
	 * @param collection
	 *            指定集合
	 * @param value
	 *            指定值，当此值设为null时，其效果等同于<code>removeNull()</code>
	 * @return 假如集合为null，返回null，否则返回移除指定值之后的新集合
	 *         <p>
	 *         假如内部出错，也返回null
	 */
	@SuppressWarnings("unchecked")
	public static <E> Collection<E> removeValue(Collection<E> collection,
			Object value) {
		if (isEmpty(collection))
			return collection;
		Collection<E> newCollection = null;
		try {
			// 创建一个新的集合
			newCollection = collection.getClass().newInstance();
		} catch (Exception e1) {
			XLPOutputInfoUtil.println(e1);
		}

		for (E e : collection) {
			if ((e == null && value == null)
					|| (e != null && (e == value || e.equals(value))))// 移除指定的值
				continue;
			newCollection.add(e);
		}
		return newCollection;
	}

	/**
	 * 移除指定集合中的null值
	 * 
	 * @param collection
	 *            指定集合
	 * @return 假如集合为null，返回null，否则返回移除null之后的新集合
	 *         <p>
	 *         假如内部出错，也返回null
	 */
	public static <E> Collection<E> removeNull(Collection<E> collection) {
		return removeValue(collection, null);
	}

	/**
	 * 用给定的值填充给定的集合
	 * 
	 * @param collection
	 * @param params
	 * @throws IllegalArgumentException
	 *             假如给定的集合为null或给定的填充值为null，则抛出此异常
	 */
	@SafeVarargs
	public static <E> void fill(Collection<E> collection, E... params) {
		if (collection == null || params == null)
			throw new IllegalArgumentException("给定的集合或给定的填充值必须不为null！");
		for (E e : params) {
			collection.add(e);
		}
	}

	/**
	 * 向指定集合中填充数组值（不抛出异常）
	 * 
	 * @param list
	 *            要填充的list
	 * @param array
	 *            填充值数组
	 */
	@SafeVarargs
	public static <E> void fillByArray(Collection<E> collection, E... array) {
		if (array == null || collection == null)
			return;
		for (E e : array)
			collection.add(e);
	}
	
	/**
	 * 通过参数获取一个ArrayList对象
	 * 
	 * @param params
	 * @return ArrayList<E>
	 */
	@SafeVarargs
	public static <E> List<E> initList(E... params){ 
		List<E> list = new ArrayList<E>();
		if (params != null) {
			for (E param : params) {
				list.add(param);
			}
		}
		return list;
	}
}
