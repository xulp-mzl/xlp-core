package org.xlp.array;

/**
 * 数组解析成字符串
 * 
 * @author 徐龙平
 *         <p>
 *         2016-12-28
 *         </p>
 * @version 1.0
 * 
 */
public class XLPArrayParseString {
	//前缀
	private String prefix = "[";
	//后缀
	private String suffix = "]";
	//分隔符
	private String split = ", ";
	
	public XLPArrayParseString(){}
	
	/**
	 * @param prefix 前缀
	 * @param suffix 前缀
	 */
	public XLPArrayParseString(String prefix, String suffix){
		if (prefix != null) {
			this.prefix = prefix;
		}
		if (suffix != null) {
			this.suffix = suffix;
		}
	}
	
	/**
	 * @param prefix 前缀
	 * @param suffix 前缀
	 * @param split 分隔符
	 */
	public XLPArrayParseString(String prefix, String suffix, String split){
		this(prefix, suffix);
		initSplit(split);
	}

	/**
	 * @param split
	 */
	private void initSplit(String split) {
		if (split != null) {
			this.split = split;
		}
	}
	
	/**
	 * @param split 分隔符
	 */
	public XLPArrayParseString(String split){
		initSplit(split);
	}

	/**
	 * @param prefix前缀
	 */
	public void setPrefix(String prefix) {
		if (prefix != null) {
			this.prefix = prefix;
		}
	}

	/**
	 * @param suffix 后缀
	 */
	public void setSuffix(String suffix) {
		if (suffix != null) {
			this.suffix = suffix;
		}
	}

	/**
	 * @param split 分隔符
	 */
	public void setSplit(String split) {
		initSplit(split);
	}
	
	/**
	 * @param array
	 * @return
	 */
	public String toString(int[] array){
		if (array == null) {
			return "null";
		}
		
		int len = array.length;
		int lastIndex = len - 1;
		if (lastIndex == -1) {
			return prefix + suffix;
		}
		
		StringBuilder sb = new StringBuilder(len + prefix.length()
				+ suffix.length() + split.length() * (len - 1));
		sb.append(prefix);
		for (int i = 0; ; i++) {
			sb.append(array[i]);
			if (i == lastIndex) {
				return sb.append(suffix).toString();
			}
			sb.append(split);
		}
	}
	
	public String toString(short[] array){
		if (array == null) {
			return "null";
		}
		
		int len = array.length;
		int lastIndex = len - 1;
		if (lastIndex == -1) {
			return prefix + suffix;
		}
		
		StringBuilder sb = new StringBuilder(len + prefix.length()
				+ suffix.length() + split.length() * (len - 1));
		sb.append(prefix);
		for (int i = 0; ; i++) {
			sb.append(array[i]);
			if (i == lastIndex) {
				return sb.append(suffix).toString();
			}
			sb.append(split);
		}
	}
	
	public String toString(long[] array){
		if (array == null) {
			return "null";
		}
		
		int len = array.length;
		int lastIndex = len - 1;
		if (lastIndex == -1) {
			return prefix + suffix;
		}
		
		StringBuilder sb = new StringBuilder(len + prefix.length()
				+ suffix.length() + split.length() * (len - 1));
		sb.append(prefix);
		for (int i = 0; ; i++) {
			sb.append(array[i]);
			if (i == lastIndex) {
				return sb.append(suffix).toString();
			}
			sb.append(split);
		}
	}
	
	public String toString(byte[] array){
		if (array == null) {
			return "null";
		}
		
		int len = array.length;
		int lastIndex = len - 1;
		if (lastIndex == -1) {
			return prefix + suffix;
		}
		
		StringBuilder sb = new StringBuilder(len + prefix.length()
				+ suffix.length() + split.length() * (len - 1));
		sb.append(prefix);
		for (int i = 0; ; i++) {
			sb.append(array[i]);
			if (i == lastIndex) {
				return sb.append(suffix).toString();
			}
			sb.append(split);
		}
	}
	
	public String toString(double[] array){
		if (array == null) {
			return "null";
		}
		
		int len = array.length;
		int lastIndex = len - 1;
		if (lastIndex == -1) {
			return prefix + suffix;
		}
		
		StringBuilder sb = new StringBuilder(len + prefix.length()
				+ suffix.length() + split.length() * (len - 1));
		sb.append(prefix);
		for (int i = 0; ; i++) {
			sb.append(array[i]);
			if (i == lastIndex) {
				return sb.append(suffix).toString();
			}
			sb.append(split);
		}
	}
	
	public String toString(float[] array){
		if (array == null) {
			return "null";
		}
		
		int len = array.length;
		int lastIndex = len - 1;
		if (lastIndex == -1) {
			return prefix + suffix;
		}
		
		StringBuilder sb = new StringBuilder(len + prefix.length()
				+ suffix.length() + split.length() * (len - 1));
		sb.append(prefix);
		for (int i = 0; ; i++) {
			sb.append(array[i]);
			if (i == lastIndex) {
				return sb.append(suffix).toString();
			}
			sb.append(split);
		}
	}
	
	public String toString(char[] array){
		if (array == null) {
			return "null";
		}
		
		int len = array.length;
		int lastIndex = len - 1;
		if (lastIndex == -1) {
			return prefix + suffix;
		}
		
		StringBuilder sb = new StringBuilder(len + prefix.length()
				+ suffix.length() + split.length() * (len - 1));
		sb.append(prefix);
		for (int i = 0; ; i++) {
			sb.append(array[i]);
			if (i == lastIndex) {
				return sb.append(suffix).toString();
			}
			sb.append(split);
		}
	}
	
	public String toString(boolean[] array){
		if (array == null) {
			return "null";
		}
		
		int len = array.length;
		int lastIndex = len - 1;
		if (lastIndex == -1) {
			return prefix + suffix;
		}
		
		StringBuilder sb = new StringBuilder(len + prefix.length()
				+ suffix.length() + split.length() * (len - 1));
		sb.append(prefix);
		for (int i = 0; ; i++) {
			sb.append(array[i]);
			if (i == lastIndex) {
				return sb.append(suffix).toString();
			}
			sb.append(split);
		}
	}
	
	public <T> String toString(T[] array){
		if (array == null) {
			return "null";
		}
		
		int len = array.length;
		int lastIndex = len - 1;
		if (lastIndex == -1) {
			return prefix + suffix;
		}
		
		StringBuilder sb = new StringBuilder(len + prefix.length()
				+ suffix.length() + split.length() * (len - 1));
		sb.append(prefix);
		for (int i = 0; ; i++) {
			sb.append(array[i]);
			if (i == lastIndex) {
				return sb.append(suffix).toString();
			}
			sb.append(split);
		}
	}
}
