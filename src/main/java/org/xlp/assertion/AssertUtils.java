package org.xlp.assertion;

import java.io.File;

import org.xlp.utils.XLPStringUtil;

/**
 * 一些异常断言工具类
 * 
 * @author xlp
 * @date 2020-05-13
 */
public class AssertUtils {
	/**
	 * 判断给定的对象是否为空
	 * 
	 * @param object 
	 * @param errorMsg 错误提示信息
	 * @throws NullPointerException 假如给定的对象为空抛出该异常
	 */
	public static void isNotNull(Object object, String errorMsg){
		if (object == null) {
			throw new NullPointerException(errorMsg);
		}
	}
	
	/**
	 * 判断给定的字符串是否为空
	 * 
	 * @param str 
	 * @param errorMsg 错误提示信息
	 * @throws NullPointerException 假如给定的字符串为空抛出该异常
	 */
	public static void isNotNull(String str, String errorMsg){
		if (XLPStringUtil.isEmpty(str)) {
			throw new NullPointerException(errorMsg);
		}
	}
	
	/**
	 * 判断文件是否存在
	 * 
	 * @param file
	 * @param errorMsg 错误提示信息
	 * @throws IllegalObjectException 假如给定的文件不存在抛出该异常
	 */
	public static void fileIsExits(File file, String errorMsg){
		if (!file.exists()) {
			throw new IllegalObjectException(errorMsg);
		}
	}
	
	/**
	 * 判断文件是否是目录
	 * 
	 * @param file
	 * @param errorMsg 错误提示信息
	 * @throws IllegalObjectException 假如给定的文件是目录抛出该异常
	 */
	public static void fileIsNotDirectory(File file, String errorMsg){
		if (file.isDirectory()) {
			throw new IllegalObjectException(errorMsg);
		}
	}
	
	/**
	 * 判断给定的 file 参数是否是文件
	 * 
	 * @param file
	 * @param errorMsg 错误提示信息
	 * @throws IllegalObjectException 假如给定的文件是目录或不存在，则抛出该异常
	 * @throws NullPointerException 假如给定的对象为空，则抛出该异常
	 */
	public static void assertFile(File file){
		isNotNull(file, "给定的文件参数为null！");
		fileIsExits(file, "给定的文件[" + file.getPath() + "]不存在！");
		fileIsNotDirectory(file, "给定的文件[" + file.getPath() + "]是目录！");
	}
}
