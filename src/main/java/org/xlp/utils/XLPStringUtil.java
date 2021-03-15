package org.xlp.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.xlp.array.XLPArrayParseString;

/**
 * @version 2.2
 * @author 徐龙平
 *         <p>
 *         2017-4-26
 *         </p>
 * 
 *         字符串处理工具类。
 *         此类主要功能有：判断字符串是否为空；字符串为null或""或全为空白字符的处理；产生指定长度指定类型（数字，字母）的随机字符串。
 *         字符串反转，全角字符串与半角字符串的相互转化，判断是否是回文字符串
 */
public class XLPStringUtil {
	public final static String CASE = "23456789abcdefghjkmnopqrstuvwxyz01ABCDEFGHJKMNPQRSTUVWXYZ";
	public final static String EMPTY = "";// 空""
	public final static int INDEX_NOT_FOUND = -1;
	public final static String NONE = null;// 空null
	
	/**
	 * 字符串null
	 */
	public final static String NULL_STRING = "null";

	/**
	 * 返回一个不重复的字符串（uuid(全为大写) 并把中间的‘-’去掉了）
	 * 
	 * @return
	 */
	public static String uuidU() {
		return UUID.randomUUID().toString().replace("-", "").toUpperCase(Locale.US);
	}

	/**
	 * 返回一个不重复的字符串（uuid(全为小写) 并把中间的‘-’去掉了）
	 * 
	 * @return
	 */
	public static String uuidL() {
		return UUID.randomUUID().toString().replace("-", "").toLowerCase(Locale.US);
	}

	/**
	 * 获取指定字符串在源字符串中第几次出现的位置
	 * 
	 * @param string
	 *            源字符串
	 * @param s
	 *            指定字符
	 * @param num
	 *            第几次出现从1开始
	 * @return 出现的位置0开始 如没有找到返回-1
	 * @throws IllegalArgumentException
	 *             当传入的num参数值错误抛出此异常，其值应为[1~源字符串长度]
	 */
	public static int getCharacterPosition(String string, String s, int num) {
		int len1, len2;
		if (string == null || s == null || (len2 = s.length()) > (len1 = string.length())) {
			return INDEX_NOT_FOUND;
		}

		if (num < 1 || num > len1) {
			throw new IllegalArgumentException("传入的num参数值错误，其值应为[1~源字符串长度]");
		}

		if (len1 == 0 && len2 == 0) {
			return 0;
		}

		if (len2 == 0) {
			return num - 1;
		}

		int mIdx = 0;// 出现次数
		// 指定字符第某次出现的位置
		int index = INDEX_NOT_FOUND;

		do {
			index = string.indexOf(s, index + 1);
			if (index != INDEX_NOT_FOUND) {
				mIdx++;
				if (mIdx == num)
					break;
			}
		} while (index != INDEX_NOT_FOUND);
		return index;
	}

	/**
	 * 清除字符串中的所有空白字符
	 * 
	 * @param resourceStr
	 *            源字符串
	 * @return 返回处理后的字符串,当源字符串为null返回null
	 */
	public static String clearBlank(String resourceStr) {
		if (isNullOrEmpty(resourceStr)) {
			return resourceStr;
		}
		return resourceStr.replaceAll("\\s", "");
	}

	/**
	 * 产生随机数字与字母组成字符串
	 * 
	 * @param length
	 *            要产生随机数字与字母组成字符串的长度
	 * @return string
	 * @throws IllegalArgumentException
	 *             当传入的字符串的长度小于0抛出此异常
	 */
	public static String createRandomCase(int length) {
		if (length < 0) {
			throw new IllegalArgumentException("要产生字符串的长度不能小于0");
		}
		Random random = new Random();
		StringBuffer stringBuffer = new StringBuffer(length);
		int len = CASE.length();
		for (int i = 0; i < length; i++) {
			int code = random.nextInt(len);
			stringBuffer.append(CASE.charAt(code));
		}
		return stringBuffer.toString();
	}

	/**
	 * 产生随机数字字符串
	 * 
	 * @param length
	 *            要产生随机数字字符串的长度
	 * @return string
	 * @throws IllegalArgumentException
	 *             当传入的字符串的长度小于0抛出此异常
	 */
	public static String createRandomNum(int length) {
		if (length < 0) {
			throw new IllegalArgumentException("要产生字符串的长度不能小于0");
		}

		StringBuffer stringBuffer = new StringBuffer(length);
		Random rd = new Random();
		for (int i = 0; i < length; i++) {
			int code = rd.nextInt(10);
			stringBuffer.append(code);
		}
		return stringBuffer.toString();
	}

	/**
	 * 判断字符串是否为null或""或全为空白字符
	 * 
	 * @param s
	 *            要判断的字符串
	 * @return 假如为null或""或全为空白字符返回true，否则返回false
	 * 
	 *         <p>
	 *         如isEmpty("") 返回true
	 *         <p>
	 *         isEmpty(null) 返回true
	 *         <p>
	 *         isEmpty(" \n") 返回true
	 *         <p>
	 *         isEmpty(" 2 \n") 返回false
	 */
	public static boolean isEmpty(String s) {
		if (s == null || s.trim().isEmpty()) {
			return true;
		}
		return false;
	}

	/**
	 * 判断字符串是否为null或""
	 * 
	 * @param s
	 *            要判断的字符串
	 * @return 假如为null或""返回true，否则返回false
	 * 
	 *         <p>
	 *         如isNullOrEmpty("") 返回true
	 *         <p>
	 *         isNullOrEmpty(null) 返回true
	 *         <p>
	 *         isNullOrEmpty(" \n") 返回false
	 *         <p>
	 *         isNullOrEmpty(" 2 \n") 返回false
	 */
	public static boolean isNullOrEmpty(String s) {
		if (s == null || s.isEmpty()) {
			return true;
		}
		return false;
	}

	/**
	 * 判断字符串是否为""或全为空白字符
	 * 
	 * @param s
	 *            要判断的字符串
	 * @return 假如字符串是为""或全为空白字符返回true，否则返回false
	 * 
	 *         <p>
	 *         isBlankSpace(null) 返回false
	 *         <p>
	 *         isBlankSpace("") 返回true
	 *         <p>
	 *         isBlankSpace(" ") 返回true
	 *         <p>
	 *         isBlankSpace(" 13") 返回false
	 */
	public static boolean isBlankSpace(String s) {
		boolean is = false;
		if (s != null)
			is = s.trim().isEmpty() ? true : false;
		return is;
	}

	/**
	 * 把为null的字符串或""或全为空白字符的字符串处理成""
	 * 
	 * @param s
	 *            要处理的源字符串
	 * @return 如果字符串为null或""或全为空白字符返回"", 否则返回源字符串
	 * 
	 *         <p>
	 *         toEmpty("") 返回""
	 *         <p>
	 *         toEmpty(null) 返回""
	 *         <p>
	 *         toEmpty(" ") 返回""
	 *         <p>
	 *         toEmpty(" \n\t") 返回""
	 *         <p>
	 *         toEmpty(" 33 \n\t") 返回" 33 \n\t"
	 */
	public static String toEmpty(String s) {
		return isEmpty(s) ? EMPTY : s;
	}

	/**
	 * 把为null的字符串或""处理成""
	 * 
	 * @param s
	 *            要处理的源字符串
	 * @return 如果字符串为null或""返回"", 否则返回源字符串
	 * 
	 *         <p>
	 *         nullToEmpty("") 返回""
	 *         <p>
	 *         nullToEmpty(null) 返回""
	 *         <p>
	 *         nullToEmpty(" ") 返回" "
	 *         <p>
	 *         nullToEmpty(" \n\t") 返回" \n\t"
	 *         <p>
	 *         nullToEmpty(" 33 \n\t") 返回" 33 \n\t"
	 */
	public static String nullToEmpty(String s) {
		return isNullOrEmpty(s) ? EMPTY : s;
	}

	/**
	 * 把为""或全为空白字符的字符串处理成""
	 * 
	 * @param s
	 *            要处理的源字符串
	 * @return 如果字符串为""或全为空白字符返回"", 否则返回源字符串
	 * 
	 *         <p>
	 *         toBlankSpace("") 返回""
	 *         <p>
	 *         toBlankSpace(null) 返回null
	 *         <p>
	 *         toBlankSpace(" ") 返回""
	 *         <p>
	 *         toBlankSpace(" \n\t") 返回""
	 *         <p>
	 *         toBlankSpace(" 33 \n\t") 返回" 33 \n\t"
	 */
	public static String toBlankSpace(String s) {
		return isBlankSpace(s) ? EMPTY : s;
	}

	/**
	 * 把为null的字符串或""或全为空白字符的字符串处理成null
	 * 
	 * @param s
	 *            要处理的源字符串
	 * @return 如果字符串为null或""或全为空白字符返回null, 否则返回源字符串
	 * 
	 *         <p>
	 *         toNull("") 返回null
	 *         <p>
	 *         toNull(null) 返回null
	 *         <p>
	 *         toNull(" ") 返回null
	 *         <p>
	 *         toNull(" \n\t") 返回null
	 *         <p>
	 *         toNull(" 33 \n\t") 返回" 33 \n\t"
	 */
	public static String toNull(String s) {
		return isEmpty(s) ? NONE : s;
	}

	/**
	 * 把为null的字符串或""处理成null
	 * 
	 * @param s
	 *            要处理的源字符串
	 * @return 如果字符串为null或""返回null, 否则返回源字符串
	 * 
	 *         <p>
	 *         emptyToNull("") 返回null
	 *         <p>
	 *         emptyToNull(null) 返回null
	 *         <p>
	 *         emptyToNull(" ") 返回" "
	 *         <p>
	 *         emptyToNull(" 33 \n\t") 返回" 33 \n\t"
	 */
	public static String emptyToNull(String s) {
		return isNullOrEmpty(s) ? NONE : s;
	}

	/**
	 * 将字符串反转
	 * 
	 * @param s
	 *            将要反转的字符串
	 * @return 返回反转后的字符串
	 */
	public static String reverse(String s) {
		if (isNullOrEmpty(s)) {
			return s;
		}

		char[] array = s.toCharArray();
		int start = 0, end = array.length - 1;
		for (; start < end; start++, end--) {
			char temp = array[start];
			array[start] = array[end];
			array[end] = temp;
		}

		return new String(array);
	}

	/**
	 * 去除字符串中右边的所有空白字符
	 * 
	 * @param s
	 *            要处理的字符串
	 * @return 处理后的字符串
	 */
	public static String trimR(String s) {
		if (isNullOrEmpty(s))
			return s;

		int len = s.length();
		int end = 0;
		for (int i = len - 1; i >= 0; i--) {
			if (!Character.isWhitespace(s.charAt(i))) {
				end = ++i;
				break;
			}
		}
		return s.substring(0, end);
	}

	/**
	 * 去除字符串中左边的所有空白字符
	 * 
	 * @param s
	 *            要处理的字符串
	 * @return 处理后的字符串
	 */
	public static String trimL(String s) {
		if (isNullOrEmpty(s))
			return s;

		int len = s.length();
		int start = 0;
		for (int i = 0; i < len; i++) {
			if (!Character.isWhitespace(s.charAt(i))) {
				start = i;
				break;
			}
		}
		return s.substring(start);
	}

	/**
	 * 把字符串中的所有半角字符都转换成全字符
	 * 
	 * @param hS
	 *            要处理掉的字符串
	 * @return 如果要处理掉的字符串为null或""返回源串，否则返回处理后的字符串
	 */
	public static String hSToFS(String hS) {
		if (isNullOrEmpty(hS))
			return hS;

		char[] sch = hS.toCharArray();
		int len = sch.length;
		for (int i = 0; i < len; i++)
			sch[i] = XLPCharUtil.hCharToFChar(sch[i]);
		return new String(sch);
	}

	/**
	 * 把字符串中的所有全角字符都转换成半角字符
	 * 
	 * @param fS
	 *            要处理掉的字符串
	 * @return 如果要处理掉的字符串为null或""返回源串，否则返回处理后的字符串
	 */
	public static String fSToHS(String fS) {
		if (isNullOrEmpty(fS))
			return fS;

		char[] sch = fS.toCharArray();
		int len = sch.length;
		for (int i = 0; i < len; i++)
			sch[i] = XLPCharUtil.fCharToHChar(sch[i]);
		return new String(sch);
	}

	/**
	 * 判断字符串是否是回文字符串
	 * 
	 * @param s
	 *            要判断的字符串
	 * @return 假如是回文字符串返回true，否则返回false
	 */
	public static boolean isPlalindrome(String s) {
		if (s == null)
			return false;
		int end = s.length() - 1, start = 0;
		for (; start < end; start++, end--) {
			if (s.charAt(start) != s.charAt(end)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 判断一个对象是否为null或""
	 * 
	 * @param object
	 * @return 假如是返回true，否则返回false
	 */
	public static boolean isNullOrEmpty(Object object) {
		return object == null || EMPTY.equals(object);
	}

	/**
	 * 获取匹配的子串
	 * 
	 * @param s
	 *            源字符串
	 * @param regex
	 *            正则表达式
	 * @return 假如参数为null，或未找到,返回null
	 */
	public static String findSubString(String s, String regex) {
		if (s == null || regex == null)
			return null;
		Pattern pattern = Pattern.compile(regex, Pattern.DOTALL);
		Matcher matcher = pattern.matcher(s);
		if (matcher.find()) {// 判断是否找到匹配的子串
			int start = matcher.start(); // 子串的开始位置从0开始
			int end = matcher.end(); // 子串的结束位置从0开始
			return s.substring(start, end);
		}
		return null;
	}

	/**
	 * 获取匹配的子串
	 * 
	 * @param s
	 *            源字符串
	 * @param regex
	 *            正则表达式
	 * @return 假如参数为null，或未找到,返回String[0]
	 */
	public static String[] findSubStrings(String s, String regex) {
		if (s == null || regex == null)
			return new String[0];
		Pattern pattern = Pattern.compile(regex, Pattern.DOTALL);
		Matcher matcher = pattern.matcher(s);
		List<String> list = new ArrayList<String>();
		while (matcher.find()) {
			list.add(s.substring(matcher.start(), matcher.end()));
		}
		return XLPListArrayUtil.listToArray(list, String.class);
	}

	/**
	 * 判断给定的字符串中是否包含符合给定需求的子串
	 * 
	 * @param s
	 *            源字符串
	 * @param regex
	 *            正则表达式
	 * @return 假如包含返回true，否则返回false
	 */
	public static boolean containSubString(String s, String regex) {
		if (s == null || regex == null)
			return false;
		Pattern pattern = Pattern.compile(regex, Pattern.DOTALL);
		Matcher matcher = pattern.matcher(s);
		return matcher.find();
	}

	/**
	 * 去除字符串的前后空格
	 * 
	 * @param s
	 *            源字符串
	 * @return 去除前后空格的新字符串
	 */
	public static String trim(String s) {
		return isNullOrEmpty(s) ? s : s.trim();
	}

	/**
	 * 获取适合长度的字符串
	 * 
	 * @param s
	 *            源字符串
	 * @param len
	 *            给定的长度
	 * @return 返回给定长度的字符串，假如指定的字符串长度小于给定的长度，则返回源字符串，否则返回适合长度的字符串(去除了前后空格) <br/>
	 *         例如：s="sde的发v都是"; len=5; <br/>
	 *         返回s.substring(0,5);即:"sde的发"
	 */
	public static String getSuitLenString(String s, int len) {
		if (isNullOrEmpty((s = trim(s))))
			return s;
		int length = s.length();
		return length > len ? s.substring(0, len) : s;
	}

	/**
	 * 判断给定的字符串是否以给定的格式开头
	 * 
	 * @param s
	 *            源字符串
	 * @param regex
	 *            正则表达式
	 * @return 假如是返回true，假如参数为空或不是返回false
	 */
	public static boolean startsWith(String s, String regex) {
		if (s == null || regex == null)
			return false;
		int index = indexOf(s, regex);
		return index != 0 ? false : true;
	}

	/**
	 * 判断给定的字符串是否以给定的格式结尾
	 * 
	 * @param s
	 *            源字符串
	 * @param regex
	 *            正则表达式
	 * @return 假如是返回true，假如参数为空或不是返回false
	 */
	public static boolean endsWith(String s, String regex) {
		if (s == null || regex == null)
			return false;
		String[] matcheStr = findSubStrings(s, regex);
		for (String string : matcheStr) {
			if (s.endsWith(string)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 获取子字符串在给定的字符串第一次出现的位置
	 * 
	 * @param s
	 *            源字符串
	 * @param regex
	 *            正则表达式
	 * @return 假如找到返回第一次出现的位置，否则返回-1
	 */
	public static int indexOf(String s, String regex) {
		if (s == null || regex == null)
			return INDEX_NOT_FOUND;
		Pattern pattern = Pattern.compile(regex, Pattern.DOTALL);
		Matcher matcher = pattern.matcher(s);
		if (matcher.find()) {// 判断是否找到匹配的子串
			return matcher.start();
		}
		return INDEX_NOT_FOUND;
	}

	/**
	 * 获取子字符串在给定的字符串最后一次出现的位置
	 * 
	 * @param s
	 *            源字符串
	 * @param regex
	 *            正则表达式
	 * @return 假如找到返回最后一次出现的位置，否则返回-1
	 */
	public static int lastIndexOf(String s, String regex) {
		if (s == null || regex == null)
			return INDEX_NOT_FOUND;
		Pattern pattern = Pattern.compile(regex, Pattern.DOTALL);
		Matcher matcher = pattern.matcher(s);
		int start = INDEX_NOT_FOUND;
		while (matcher.find())
			start = matcher.start();
		return start;
	}

	/**
	 * 
	 * 把为null的字符串或""处理成""
	 * 
	 * @param s
	 *            要处理的源字符串
	 * @return 如果字符串为null或全是空白字符，返回"", 否则返回源字符串【s.trim()】
	 * 
	 *         <p>
	 *         nullToEmpty("") 返回""
	 *         <p>
	 *         nullToEmpty(null) 返回""
	 *         <p>
	 *         nullToEmpty(" ") 返回""
	 *         <p>
	 *         nullToEmpty(" \n\t") 返回""
	 *         <p>
	 *         nullToEmpty(" 33 \n\t") 返回"33"
	 */
	public static String emptyTrim(String s) {
		return isEmpty(s) ? "" : s.trim();
	}

	/**
	 * 把为""或全为空白字符的字符串处理成""
	 * 
	 * @param s
	 *            要处理的源字符串
	 * @return 如果字符串为""或全为空白字符返回"", 否则返回源字符串
	 * 
	 *         <p>
	 *         blankSpaceToNull("") 返回null
	 *         <p>
	 *         blankSpaceToNull(null) 返回null
	 *         <p>
	 *         blankSpaceToNull(" ") 返回null
	 *         <p>
	 *         blankSpaceToNull(" \n\t") 返回null
	 *         <p>
	 *         blankSpaceToNull(" 33 \n\t") 返回"33"
	 */
	public static String blankSpaceToNull(String s) {
		return isEmpty(s) ? NONE : s.trim();
	}

	/**
	 * 切分字符串，不限制分片数量,参考hutool
	 *
	 * @param str
	 *            被切分的字符串
	 * @param regex
	 *            分隔符正则
	 * @param isTrim
	 *            是否去除切分字符串后每个元素两边的空格
	 * @param ignoreEmpty
	 *            是否忽略空串
	 * @param limit
	 *            切分成数组的长度
	 * @see {@link String#split()}
	 * @return 切分后的集合, 假如要切分的字符串为null，返回String[0]
	 */
	public static String[] split(CharSequence str, String regex, boolean isTrim, boolean ignoreEmpty, int limit) {
		if (str == null) {
			return new String[0];
		}
		String[] splits = str.toString().split(regex, limit);
		List<String> result = new ArrayList<String>(splits.length);
		for (String str1 : splits) {
			if (str1 != null && isTrim) {
				str1 = str1.trim();
			}
			if (ignoreEmpty && XLPStringUtil.isEmpty(str1)) {
				continue;
			}
			result.add(str1);
		}
		return result.toArray(new String[0]);
	}

	/**
	 * 切分字符串，不限制分片数量,参考hutool
	 *
	 * @param str
	 *            被切分的字符串
	 * @param regex
	 *            分隔符正则
	 * @param isTrim
	 *            是否去除切分字符串后每个元素两边的空格
	 * @param limit
	 *            切分成数组的长度
	 * @see {@link String#split()}
	 * @return 切分后的集合, 假如要切分的字符串为null，返回String[0]
	 */
	public static String[] split(CharSequence str, String regex, boolean isTrim, int limit) {
		return split(str, regex, isTrim, true, limit);
	}

	/**
	 * 切分字符串，不限制分片数量,参考hutool
	 *
	 * @param str
	 *            被切分的字符串
	 * @param regex
	 *            分隔符正则
	 * @param isTrim
	 *            是否去除切分字符串后每个元素两边的空格
	 * @return 切分后的集合, 假如要切分的字符串为null，返回String[0]
	 */
	public static String[] split(CharSequence str, String regex, boolean isTrim) {
		return split(str, regex, isTrim, 0);
	}

	/**
	 * 切分字符串，不限制分片数量,参考hutool
	 *
	 * @param str
	 *            被切分的字符串
	 * @param regex
	 *            分隔符正则
	 * @param ignoreEmpty
	 *            是否忽略空串
	 * @param limit
	 *            切分成数组的长度
	 * @see {@link String#split()}
	 * @return 切分后的集合, 假如要切分的字符串为null，返回String[0]
	 */
	public static String[] split(CharSequence str, boolean ignoreEmpty, String regex, int limit) {
		return split(str, regex, true, ignoreEmpty, limit);
	}

	/**
	 * 切分字符串，不限制分片数量,参考hutool
	 *
	 * @param str
	 *            被切分的字符串
	 * @param regex
	 *            分隔符正则
	 * @param ignoreEmpty
	 *            是否忽略空串
	 * @return 切分后的集合, 假如要切分的字符串为null，返回String[0]
	 */
	public static String[] split(CharSequence str, boolean ignoreEmpty, String regex) {
		return split(str, ignoreEmpty, regex, 0);
	}

	/**
	 * 切分字符串，不限制分片数量,参考hutool
	 *
	 * @param str
	 *            被切分的字符串
	 * @param regex
	 *            分隔符正则
	 * @param limit
	 *            切分成数组的长度
	 * @see {@link String#split()}
	 * @return 切分后的集合, 假如要切分的字符串为null，返回String[0]
	 */
	public static String[] split(CharSequence str, String regex, int limit) {
		return split(str, regex, true, true, limit);
	}

	/**
	 * 切分字符串，不限制分片数量,参考hutool
	 *
	 * @param str
	 *            被切分的字符串
	 * @param regex
	 *            分隔符正则
	 * @return 切分后的集合, 假如要切分的字符串为null，返回String[0]
	 */
	public static String[] split(CharSequence str, String regex) {
		return split(str, regex, 0);
	}

	/**
	 * 把字符串数组拼接成字符串
	 * 
	 * @param strs
	 *            字符串数组
	 * @param prefix
	 *            前缀(假如为null->"[")
	 * @param suffix
	 *            后缀(假如为null->"]")
	 * @param split
	 *            拼接分隔字符串(假如为null->", ")
	 * @param isTrim
	 *            是否去掉字符串数组每个元素两边的空格
	 * @param ignoreEmpty
	 *            是否忽略字符串数组中的空元素
	 * @return 拼接后的字符串，假如字符串数组为null，返回""
	 */
	public static String join(String[] strs, String prefix, String suffix, String split, boolean isTrim,
			boolean ignoreEmpty) {
		if (XLPArrayUtil.isEmpty(strs)) {
			return EMPTY;
		}
		List<String> result = new ArrayList<String>(strs.length);
		for (String str : strs) {
			if (str != null && isTrim) {
				str = str.trim();
			}
			if (ignoreEmpty && XLPStringUtil.isEmpty(str)) {
				continue;
			}
			result.add(str);
		}

		XLPArrayParseString arrayParseString = new XLPArrayParseString(prefix, suffix, split);
		return arrayParseString.toString(result.toArray());
	}

	/**
	 * 把字符串数组拼接成字符串
	 * 
	 * @param strs
	 *            字符串数组
	 * @param prefix
	 *            前缀(假如为null->"[")
	 * @param suffix
	 *            后缀(假如为null->"]")
	 * @param split
	 *            拼接分隔字符串(假如为null->", ")
	 * @return 拼接后的字符串，假如字符串数组为null，返回""
	 */
	public static String join(String[] strs, String prefix, String suffix, String split) {
		return join(strs, prefix, suffix, split, false, false);
	}

	/**
	 * 把字符串数组拼接成字符串
	 * 
	 * @param strs
	 *            字符串数组
	 * @param split
	 *            拼接分隔字符串(假如为null->", ")
	 * @param isTrim
	 *            是否去掉字符串数组每个元素两边的空格
	 * @param ignoreEmpty
	 *            是否忽略字符串数组中的空元素
	 * @return 拼接后的字符串，假如字符串数组为null，返回""
	 */
	public static String join(String[] strs, String split, boolean isTrim, boolean ignoreEmpty) {
		return join(strs, EMPTY, EMPTY, split, isTrim, ignoreEmpty);
	}

	/**
	 * 把字符串数组拼接成字符串
	 * 
	 * @param strs
	 *            字符串数组
	 * @param split
	 *            拼接分隔字符串(假如为null->", ")
	 * @return 拼接后的字符串，假如字符串数组为null，返回""
	 */
	public static String join(String[] strs, String split) {
		return join(strs, EMPTY, EMPTY, split);
	}
}
