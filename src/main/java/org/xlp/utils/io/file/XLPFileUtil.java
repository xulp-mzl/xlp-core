package org.xlp.utils.io.file;

import java.io.File;

import org.xlp.assertion.AssertUtils;
import org.xlp.utils.io.path.XLPFilePathUtil;

/**
 * <p>
 * 创建时间：2021年1月11日 下午11:42:02
 * </p>
 * 
 * @author xlp
 * @version 1.0
 * @Description 提供简化文集目录创建等操作
 */
public class XLPFileUtil {
	/**
	 * 当给定的文件的目录不存在时，则创建该目录
	 * 
	 * @param file
	 *            要处理的文件
	 * @param asFile
	 *            标记是否作为文件处理， 值为true时，当做文件处理，否则当做目录处理
	 * @return 假如返回true，则创建成功，当创建失败或已存在时，返回false
	 * @throws NullPointerException
	 *             假如参数为null，则抛出该异常
	 */
	public static boolean mkdirs(File file, boolean asFile) {
		AssertUtils.isNotNull(file, "file paramter is null!");
		if (!file.exists()) {
			if (asFile) {
				file = file.getParentFile();
			}
			if (file != null) {
				return file.mkdirs();
			} 
		}
		return false;
	}
	
	/**
	 * 当给定的文件的目录不存在时，则创建该目录
	 * 
	 * @param file
	 *            要处理的文件
	 * @return 假如返回true，则创建成功，当创建失败或已存在时，返回false
	 * @throws NullPointerException
	 *             假如参数为null，则抛出该异常
	 */
	public static boolean mkdirs(File file) {
		return mkdirs(file, false);
	}
	
	/**
	 * 当给定的文件的目录不存在时，则创建该目录
	 * 
	 * @param filePath
	 *            文件物理路径
	 * @param asFile
	 *            标记是否作为文件处理， 值为true时，当做文件处理，否则当做目录处理
	 * @return 假如返回true，则创建成功，当创建失败或已存在时，返回false
	 * @throws NullPointerException
	 *             假如参数为null或空，则抛出该异常
	 */
	public static boolean mkdirs(String filePath, boolean asFile) {
		AssertUtils.isNotNull(filePath, "filePath paramter is null or empty!");
		filePath = XLPFilePathUtil.normalize(filePath);
		return mkdirs(new File(filePath), asFile);
	}
	
	/**
	 * 当给定的文件的目录不存在时，则创建该目录
	 * 
	 * @param filePath
	 *            文件物理路径
	 * @return 假如返回true，则创建成功，当创建失败或已存在时，返回false
	 * @throws NullPointerException
	 *             假如参数为null或空，则抛出该异常
	 */
	public static boolean mkdirs(String filePath) {
		return mkdirs(filePath, false);
	}
	
	/**
	 * 递归删除文件或目录
	 * 
	 * @param file 要删除的文件或目录
	 * @param deleteDir 标记是否删除目录，true删除，false不删除
	 * @return 假如返回true，则删除成功，否则返回false
	 * @throws NullPointerException
	 *             假如参数为null，则抛出该异常
	 */
	public static boolean deleteFile(File file, boolean deleteDir){
		AssertUtils.isNotNull(file, "file Paramter is null!");
		if (!file.exists()) {
			return false;
		}
		if (file .isFile()) {
			return file.delete();
		}
		boolean succ = false;
		File[] files = file.listFiles();
		if (files != null) { 
			boolean tempSucc;
			for (File file2 : files) { 
				//递归删除文件
				tempSucc = deleteFile(file2, deleteDir);
				if (tempSucc) {
					succ = tempSucc;
				}
			}
			//删除目录
			if (deleteDir) { 
				tempSucc = file.delete();
				if (tempSucc) {
					succ = tempSucc;
				}
			}
		}
		return succ;
	}
	
	/**
	 * 递归删除文件和目录，默认目录也删除
	 * 
	 * @param file 要删除的文件或目录
	 * @return 假如返回true，则删除成功，否则返回false
	 * @throws NullPointerException
	 *             假如参数为null，则抛出该异常
	 */
	public static boolean deleteFile(File file){
		return deleteFile(file, true);
	}
	
	/**
	 * 递归删除文件或目录
	 * 
	 * @param filePath
	 *            文件物理路径
	 * @param deleteDir 标记是否删除目录，true删除，false不删除
	 * @return 假如返回true，则删除成功，否则返回false
	 * @throws NullPointerException
	 *             假如参数为null或空，则抛出该异常
	 */
	public static boolean deleteFile(String filePath, boolean deleteDir){
		AssertUtils.isNotNull(filePath, "filePath paramter is null or empty!");
		filePath = XLPFilePathUtil.normalize(filePath);
		return deleteFile(new File(filePath), deleteDir);
	}
	
	/**
	 * 递归删除文件和目录，默认目录也删除
	 * 
	 * @param filePath
	 *            文件物理路径
	 * @return 假如返回true，则删除成功，否则返回false
	 * @throws NullPointerException
	 *             假如参数为null或空，则抛出该异常
	 */
	public static boolean deleteFile(String filePath){
		return deleteFile(filePath, true);
	}
	
	
}
