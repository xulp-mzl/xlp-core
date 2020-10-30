package org.xlp.array;

/**
 * 字符串解析成数组
 * 
 * @author 徐龙平
 *         <p>
 *         2016-12-28
 *         </p>
 * @version 1.0
 * 
 */
public class XLPStringParseArray {
	//前缀
	private String prefix = "[";
	//后缀
	private String suffix = "]";
	//分隔符
	private String split = ", ";
	//要转换成数组的字符串
	private String arrayString = null;
	
	public XLPStringParseArray(String arrayString){
		this.arrayString = arrayString;
	}
	
	/**
	 * @param prefix 前缀
	 * @param suffix 前缀
	 */
	public XLPStringParseArray(String arrayString, String prefix, String suffix){
		this(arrayString);
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
	public XLPStringParseArray(String arrayString, String prefix, String suffix, String split){
		this(arrayString, prefix, suffix);
		initSplit(split);
	}

	/**
	 * @param split
	 */
	private void initSplit(String split) {
		if (split != null && split.length() > 0) {
			this.split = split;
		}
	}
	
	/**
	 * @param split 分隔符
	 */
	public XLPStringParseArray(String arrayString, String split){
		this(arrayString);
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
	 * @return
	 * @throws XLPParseException 
	 */
	public int[] toIntArray() throws XLPParseException{
		check(arrayString);
		
		int length = arrayString.length();
		int start = prefix.length();
		int end = length - suffix.length();
		if (length == 0 || start == end) {
			return new int[]{};
		}
		if (start > end) {
			throw new XLPParseException("转换字符串的格式有误");
		}
		
		String[] strings = toStringArray(arrayString.substring(start,end));
		length = strings.length;
		int[] array = new int[length];
		for (int i = 0; i < length; i++) {
			array[i] = Integer.parseInt(strings[i]);
		}
		return array;
	}
	
	public short[] toShortArray() throws XLPParseException{
		check(arrayString);
		
		int length = arrayString.length();
		int start = prefix.length();
		int end = length - suffix.length();
		if (length == 0 || start == end) {
			return new short[]{};
		}
		if (start > end) {
			throw new XLPParseException("转换字符串的格式有误");
		}
		
		String[] strings = toStringArray(arrayString.substring(start,end));
		length = strings.length;
		short[] array = new short[length];
		for (int i = 0; i < length; i++) {
			array[i] = Short.parseShort(strings[i]);
		}
		return array;
	}

	public long[] toLongArray() throws XLPParseException{
		check(arrayString);
		
		int length = arrayString.length();
		int start = prefix.length();
		int end = length - suffix.length();
		if (length == 0 || start == end) {
			return new long[]{};
		}
		if (start > end) {
			throw new XLPParseException("转换字符串的格式有误");
		}
		
		String[] strings = toStringArray(arrayString.substring(start,end));
		length = strings.length;
		long[] array = new long[length];
		for (int i = 0; i < length; i++) {
			array[i] = Long.parseLong(strings[i]);
		}
		return array;
	}
	
	public byte[] toByteArray() throws XLPParseException{
		check(arrayString);
		
		int length = arrayString.length();
		int start = prefix.length();
		int end = length - suffix.length();
		if (length == 0 || start == end) {
			return new byte[]{};
		}
		if (start > end) {
			throw new XLPParseException("转换字符串的格式有误");
		}
		
		String[] strings = toStringArray(arrayString.substring(start,end));
		length = strings.length;
		byte[] array = new byte[length];
		for (int i = 0; i < length; i++) {
			array[i] = Byte.parseByte(strings[i]);
		}
		return array;
	}
	
	public double[] toDoubleArray() throws XLPParseException{
		check(arrayString);
		
		int length = arrayString.length();
		int start = prefix.length();
		int end = length - suffix.length();
		if (length == 0 || start == end) {
			return new double[]{};
		}
		if (start > end) {
			throw new XLPParseException("转换字符串的格式有误");
		}
		
		String[] strings = toStringArray(arrayString.substring(start,end));
		length = strings.length;
		double[] array = new double[length];
		for (int i = 0; i < length; i++) {
			array[i] = Double.parseDouble(strings[i]);
		}
		return array;
	}
	
	public float[] toFloatArray() throws XLPParseException{
		check(arrayString);
		
		int length = arrayString.length();
		int start = prefix.length();
		int end = length - suffix.length();
		if (length == 0 || start == end) {
			return new float[]{};
		}
		if (start > end) {
			throw new XLPParseException("转换字符串的格式有误");
		}
		
		String[] strings = toStringArray(arrayString.substring(start,end));
		length = strings.length;
		float[] array = new float[length];
		for (int i = 0; i < length; i++) {
			array[i] = Float.parseFloat(strings[i]);
		}
		return array;
	}
	
	public boolean[] toBooleanArray() throws XLPParseException{
		check(arrayString);
		
		int length = arrayString.length();
		int start = prefix.length();
		int end = length - suffix.length();
		if (length == 0 || start == end) {
			return new boolean[]{};
		}
		if (start > end) {
			throw new XLPParseException("转换字符串的格式有误");
		}
		
		String[] strings = toStringArray(arrayString.substring(start,end));
		length = strings.length;
		boolean[] array = new boolean[length];
		for (int i = 0; i < length; i++) {
			if (!(strings[i].trim().equalsIgnoreCase("true")
					|| strings[i].trim().equalsIgnoreCase("false"))) {
				throw new XLPParseException("转换字符串的格式有误");
			}
			array[i] = Boolean.parseBoolean(strings[i]);
		}
		return array;
	}
	
	public char[] toCharArray() throws XLPParseException{
		check(arrayString);
		
		int length = arrayString.length();
		int start = prefix.length();
		int end = length - suffix.length();
		if (length == 0 || start == end) {
			return new char[]{};
		}
		if (start > end) {
			throw new XLPParseException("转换字符串的格式有误");
		}
		
		String[] strings = toStringArray(arrayString.substring(start,end));
		length = strings.length;
		char[] array = new char[length];
		for (int i = 0; i < length; i++) {
			if (strings[i] != null && strings[i].length() == 1) {
				array[i] = strings[i].charAt(0);
			}else {
				throw new XLPParseException("转换字符串的格式有误");
			}
		}
		return array;
	}
	
	public String[] toStringArray() throws XLPParseException{
		check(arrayString);
		
		int length = arrayString.length();
		int start = prefix.length();
		int end = length - suffix.length();
		if (length == 0 || start == end) {
			return new String[]{};
		}
		if (start > end) {
			throw new XLPParseException("转换字符串的格式有误");
		}
		
		return toStringArray(arrayString.substring(start,end));
	}
	
	private String[] toStringArray(String arrayString) {
		return arrayString.split(split);
	}

	private void check(String arrayString) throws XLPParseException {
		if (arrayString == null) {
			throw new XLPParseException("转换字符串为null");
		}
		
		int length = arrayString.length();
		if (length > 0) {
			if (!arrayString.startsWith(prefix) || 
					!arrayString.endsWith(suffix)) {
				throw new XLPParseException("转换字符串的格式有误");
			}
		}
	}
}
