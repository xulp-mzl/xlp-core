package org.xlp.utils.collection;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.xlp.utils.XLPPackingTypeUtil;
import org.xlp.utils.XLPStringUtil;

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
 *         <p>
 *         把字一定格式的字符串转换成指定类型的Set集合
 */
public class XLPSetUtil extends XLPCollectionUtil {
	/**
	 * 把一定格式的字符串解析成Set集合
	 * 
	 * @param valueString
	 *            要解析的字符串
	 * @param prefix
	 *            前缀，值为null，无前缀
	 * @param suffix
	 *            后缀，值为null，无后缀
	 * @param split
	 *            分隔符，值为null时，则以默认分隔符“,”进行解析，此格式是正则表达式格式
	 * @param type
	 *            解析的集合类型，假如此值不为基本类型的包装类型时，都以<code>String</code>进行解析
	 * @return <code>Set<String></code>集合，假如要解析的字符串为空，返回大小为0的集合
	 *         <p>
	 *         注意：返回的集合是不可扩充或删减的，如何对该集合进行那些操作则抛出NotSupportedOptionException
	 * @throws IllegalArgumentException
	 *             假如要解析的字符串的长度小于前缀+后缀+分隔符的长度，抛出该异常
	 */
	public static <T> Set<?> parser(String valueString, String prefix,
			String split, String suffix, Class<T> type) {
		if (XLPStringUtil.isEmpty(valueString))
			return XLPPackingTypeUtil.isPackingType(type) ? new XLPSet<T>()
					: new XLPSet<String>();
		if (split == null)
			split = DEFAULT_SPLIT;
		int preLen = 0, sufLen = 0, splitLen = split.length();
		int valueLen = valueString.length();
		if (prefix != null)
			preLen = prefix.length();
		if (suffix != null)
			sufLen = suffix.length();
		if (valueLen < preLen + splitLen + sufLen)
			throw new IllegalArgumentException("[" + valueString
					+ "]---此字符串无法解析");
		String[] values = valueString.substring(preLen, valueLen - sufLen)
				.split(split);

		return new XLPSet<T>(XLPPackingTypeUtil.convert(values, type));
	}

	/**
	 * 把一定格式的字符串解析成Set集合
	 * 
	 * @param valueString
	 *            要解析的字符串
	 * @param split
	 *            分隔符，值为null时，则以默认分隔符“,”进行解析，此格式是正则表达式格式
	 * @param type
	 *            解析的集合类型，假如此值不为基本类型的包装类型时，都以<code>String</code>进行解析
	 * @return <code>Set<String></code>集合，假如要解析的字符串为空，返回大小为0的集合
	 *         <p>
	 *         注意：返回的集合是不可扩充或删减的，如何对该集合进行那些操作则抛出NotSupportedOptionException
	 */
	public static <T> Set<?> parser(String valueString, String split,
			Class<T> type) {
		return parser(valueString, null, split, null, type);
	}

	/**
	 * 把一定格式的字符串解析成Set字符串集合
	 * 
	 * @param valueString
	 *            要解析的字符串
	 * @param split
	 *            分隔符，值为null时，则以默认分隔符“,”进行解析，此格式是正则表达式格式
	 * @return <code>Set<String></code>集合，假如要解析的字符串为空，返回大小为0的集合
	 *         <p>
	 *         注意：返回的集合是不可扩充或删减的，如何对该集合进行那些操作则抛出NotSupportedOptionException
	 */
	public static <T> Set<?> parser(String valueString, String split) {
		return parser(valueString, split, null);
	}
	
	/**
	 * @version 1.0
	 * @author 徐龙平
	 *         <p>
	 *         2017-5-19
	 *         </p>
	 * 
	 *         内容不可变的Set集合
	 */
	private static class XLPSet<E> implements Set<E> {
		private Set<E> set;

		public XLPSet() {
			set = new HashSet<E>();
		}

		public XLPSet(E[] values) {
			this();
			for (E e : values) {
				set.add(e);
			}
		}

		public boolean add(E e) {
			throw new NotSupportedOptionException("不支持该操作");
		}

		public boolean addAll(Collection<? extends E> c) {
			throw new NotSupportedOptionException("不支持该操作");
		}

		public void clear() {
			throw new NotSupportedOptionException("不支持该操作");
		}

		public boolean contains(Object o) {
			return set.contains(o);
		}

		public boolean containsAll(Collection<?> c) {
			return set.containsAll(c);
		}

		public boolean isEmpty() {
			return set.isEmpty();
		}

		public Iterator<E> iterator() {
			return set.iterator();
		}

		public boolean remove(Object o) {
			throw new NotSupportedOptionException("不支持该操作");
		}

		public boolean removeAll(Collection<?> c) {
			throw new NotSupportedOptionException("不支持该操作");
		}

		public boolean retainAll(Collection<?> c) {
			throw new NotSupportedOptionException("不支持该操作");
		}

		public int size() {
			return set.size();
		}

		public Object[] toArray() {
			return set.toArray();
		}

		public <T> T[] toArray(T[] a) {
			return set.toArray(a);
		}

		@Override
		public String toString() {
			return set.toString();
		}
	}
}
