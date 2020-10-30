package org.xlp.utils;

/**
 * @version 1.0
 * @author 徐龙平
 *         <p>
 *         2017-12-07
 *         </p>
 * 
 *         字符编码处理工具类。
 *         <p>
 *         此类主要功能有：判断给定的字节数组的编码格式是否是utf-8
 */
public class XLPCharsetUtil {
	public static final String UTF8 = "utf-8";
	public static final String GBK = "gbk";
	
	/**
	 * 1）对于单字节的符号，字节的第一位设为0，后面7位为这个符号的unicode码。因此对于英语字母，UTF-8编码和ASCII码是相同的。
	 * 2）对于n字节的符号（n>1），第一个字节的前n位都设为1，第n+1位设为0，后面字节的前两位一律设为10。剩下的没有提及的二进制位，
	 * 全部为这个符号的unicode码。 根据以上说明 下面给出一段java代码判断UTF-8格式
	 */

	/**
	 * UTF-8编码格式判断
	 * 
	 * @param rawtext
	 *            需要分析的数据
	 * @return 是否为UTF-8编码格式，假如是返回true，否则返回false
	 * <br/>ps：可能会有时判断错误
	 */
	public static boolean isUTF8(byte[] rawtext) {
		if(rawtext == null)
			return false;
		int score = 0;
		int i, rawtextlen = 0;
		int goodbytes = 0, asciibytes = 0;
		// Maybe also use UTF8 Byte Order Mark: EF BB BF
		// Check to see if characters fit into acceptable ranges
		rawtextlen = rawtext.length;
		for (i = 0; i < rawtextlen; i++) {
			if ((rawtext[i] & (byte) 0x7F) == rawtext[i]) {
				// 最高位是0的ASCII字符
				asciibytes++;
				// Ignore ASCII, can throw off count
			} else if (-64 <= rawtext[i]
					&& rawtext[i] <= -33
					// -0x40~-0x21
					&& // Two bytes
					i + 1 < rawtextlen && -128 <= rawtext[i + 1]
					&& rawtext[i + 1] <= -65) {
				goodbytes += 2;
				i++;
			} else if (-32 <= rawtext[i]
					&& rawtext[i] <= -17
					&& // Three bytes
					i + 2 < rawtextlen && -128 <= rawtext[i + 1]
					&& rawtext[i + 1] <= -65 && -128 <= rawtext[i + 2]
					&& rawtext[i + 2] <= -65) {
				goodbytes += 3;
				i += 2;
			}
		}
		if (asciibytes == rawtextlen) {
			return false;
		}
		score = 100 * goodbytes / (rawtextlen - asciibytes);
		// If not above 98, reduce to zero to prevent coincidental matches
		// Allows for some (few) bad formed sequences
		if (score > 98) {
			return true;
		} else if (score > 95 && goodbytes > 30) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 获取字节数组编码格式暂时只返回两种可能 UTF-8 | GBK
	 * 
	 * @param rawtext 需要判断的二进制数据
	 * @return 
	 */
	public static String gainCharsetName(byte[] rawtext){
		return isUTF8(rawtext) ? UTF8 : GBK;
	}
}
