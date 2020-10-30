package org.xlp.utils;

/**
 * @version 1.0
 * @author 徐龙平
 *         <p>
 *         2017-4-29
 *         </p>
 * 
 *         字符处理工具类。
 *         <p>
 *         此类主要功能有：全角字符与半角字符的相互转换
 *         <br>判断是否是中文字符或符号
 */
public class XLPCharUtil {
	/**
	 * ASCII表中可见字符从!开始，偏移位值为33(Decimal)
	 */
	public static final char HALF_CHAR_START = 33; // 半角!

	/**
	 * ASCII表中可见字符到~结束，偏移位值为126(Decimal)
	 */
	public static final char HALF_CHAR_END = 126; // 半角~

	/**
	 * 全角对应于ASCII表的可见字符从！开始，偏移值为65281
	 */
	public static final char FULL_CHAR_START = 65281; // 全角！

	/**
	 * 全角对应于ASCII表的可见字符到～结束，偏移值为65374
	 */
	public static final char FULL_CHAR_END = 65374; // 全角～

	/**
	 * ASCII表中除空格外的可见字符与对应的全角字符的相对偏移
	 */
	public static final int CONVERT_STEP = 65248; // 全角半角转换间隔

	/**
	 * 全角空格的值，它没有遵从与ASCII的相对偏移，必须单独处理
	 */
	public static final char FULL_SPACE = 12288; // 全角空格 12288

	/**
	 * 半角空格的值，在ASCII中为32(Decimal)
	 */
	public static final char HALF_SPACE = ' '; // 半角空格

	/**
	 * 半角字符转换成全角字符（如已是全角字符就不作处理）
	 * 
	 * @param hChar
	 *            要转换的字符
	 * @return 转换后的字符
	 */
	public static final char hCharToFChar(char hChar) {
		char temp;
		if (hChar == HALF_SPACE) {// 如果是半角空格，直接用全角空格替代
			temp = FULL_SPACE;
		} else if (hChar >= HALF_CHAR_START && hChar <= HALF_CHAR_END) {
			temp = (char) (hChar + CONVERT_STEP);// 字符是!到~之间的可见字符
		} else {// 不对空格以及ascii表中其他可见字符之外的字符做任何处理
			temp = hChar;
		}
		return temp;
	}

	/**
	 * 全角字符转换成半角字符（如不在范围内或已是半角字符就不作处理）
	 * 
	 * @param fChar
	 *            要转换的字符
	 * @return 转换后的字符
	 */
	public static final char fCharToHChar(char fChar) {
		char temp;
		if (fChar == FULL_SPACE) {// 如果是全角空格，直接用半角空格替代
			temp = HALF_SPACE;
		} else if (fChar >= FULL_CHAR_START && fChar <= FULL_CHAR_END) {
			temp = (char) (fChar - CONVERT_STEP);// 字符是！到～之间的可见字符
		} else {// 不对空格以及ascii表中其他可见字符之外的字符做任何处理
			temp = fChar;
		}
		return temp;
	}

	/**
	 * 根据Unicode编码完美的判断中文汉字和中文符号
	 * 
	 * @param c
	 *            要判断的字符
	 * @return 假如是返true，否则返回false
	 */
	public static boolean isChinese(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		return (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
				|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
				|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
				|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) ;
	}
	
	/**
	 * 判断给定的字符是否是希腊字母
	 * 
	 * @param c
	 *            要判断的字符
	 * @return 假如是返true，否则返回false
	 */
	public static boolean isGreekAlphabet(char c){
		return (c >= '\u03b1' && c <= '\u03c9')
				|| (c >= Character.toUpperCase('\u03b1') && c <= Character.toUpperCase('\u03c9'));
	}
}
