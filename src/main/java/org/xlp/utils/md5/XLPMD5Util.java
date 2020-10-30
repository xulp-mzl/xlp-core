package org.xlp.utils.md5;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class XLPMD5Util {
	/**
	 * byte数组转换成16进制字符串
	 * 
	 * @param bytes
	 * @return
	 */
	private static String bytesToHexString(byte[] bytes) {
		StringBuilder stringBuilder = new StringBuilder();
		if (bytes == null || bytes.length <= 0) {
			return null;
		}
		for (int i = 0; i < bytes.length; i++) {
			int v = bytes[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
		}
		return stringBuilder.toString();
	}

	/**
	 * MD5加密
	 * 
	 * @param origin
	 *            原始字符串
	 * @return 加密成功 返回加密后的字符串 失败 返回原始字符串
	 */
	public static String MD5Encode(String origin) {
		return MD5Encode(origin, null);
	}
	
	/**
	 * MD5加密
	 * 
	 * @param origin
	 *            原始字符串
	 * @param encryptExt 加密扩展因子
	 * @return 加密成功 返回加密后的字符串 失败 返回原始字符串
	 */
	public static String MD5Encode(String origin, String encryptExt) {
		String newString = null;
		if (origin != null) {
			try {
				// 加密
				MessageDigest md = MessageDigest.getInstance("MD5");
				if (encryptExt != null) { 
					origin += encryptExt;
				}
				md.update(origin.getBytes());
				newString = bytesToHexString(md.digest());
			} catch (NoSuchAlgorithmException e) {
				newString = origin;
				e.printStackTrace();
			}
		}

		return newString == null ? newString : newString.toUpperCase();
	}
}
