package org.xlp.zip;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.xlp.utils.XLPArrayUtil;
import org.xlp.utils.XLPOutputInfoUtil;
import org.xlp.utils.XLPStringUtil;
import org.xlp.utils.collection.XLPCollectionUtil;
import org.xlp.utils.io.XLPIOUtil;

/**
 * 文件压缩工具类
 * 
 * @author xlp
 * 
 */
public class ZipUtils {
	/**
	 * ZIP压缩是默认字符编码
	 */
	public static final String ZIP_DEFAULT_CHARSET_NAME = "GBK"; 
	
	/**
	 * zip压缩字符编码
	 */
	private static String charsetName = ZIP_DEFAULT_CHARSET_NAME;

	/**
	 * 压缩成ZIP
	 * 
	 * @param srcFiles
	 *            压缩文件或文件夹(同时压缩几个文件或文件夹)
	 * @param out
	 *            压缩文件输出流
	 * @param keepDirStructure
	 *            是否保留原来的目录结构,true:保留目录结构;
	 *            false:所有文件跑到压缩包根目录下(注意：不保留目录结构可能会出现同名文件,会压缩失败)
	 * @return 压缩成功返回true，否则，返回false
	 */

	public static boolean toZip(File[] srcFiles, OutputStream out,
			boolean keepDirStructure) {
		return toZip(srcFiles, "", out, keepDirStructure);
	}
	
	/**
	 * 压缩成ZIP
	 * 
	 * @param srcFiles
	 *            压缩文件或文件夹(同时压缩几个文件或文件夹)
	 * @param zipInDir
	 *            压缩文件内部目录结构           
	 * @param out
	 *            压缩文件输出流
	 * @param keepDirStructure
	 *            是否保留原来的目录结构,true:保留目录结构;
	 *            false:所有文件跑到压缩包根目录下(注意：不保留目录结构可能会出现同名文件,会压缩失败)
	 * @return 压缩成功返回true，否则，返回false
	 */

	public static boolean toZip(File[] srcFiles, String zipInDir,
			OutputStream out, boolean keepDirStructure) {
		if (XLPArrayUtil.isEmpty(srcFiles) || out == null) {
			return false;
		}
		
		Zip zip = new Zip(srcFiles, charsetName);
		zip.setZipInDir(zipInDir); 
		try {
			zip.toZip(out, keepDirStructure); 
		} catch (Exception e) {
			XLPOutputInfoUtil.println(e.getMessage());
			return false;
		}
		return true;
	}
	
	/**
	 * 压缩成ZIP
	 * 
	 * @param srcFiles
	 *            压缩文件或文件夹(同时压缩几个文件或文件夹)
	 * @param out
	 *            压缩文件输出流
	 * @return 压缩成功返回true，否则，返回false
	 */

	public static boolean toZip(File[] srcFiles, OutputStream out) {
		return toZip(srcFiles, out, false);
	}
	
	/**
	 * 压缩成ZIP
	 * 
	 * @param srcDirs
	 *            压缩文件夹路径(同时压缩几个文件夹)
	 * @param out
	 *            压缩文件输出流
	 * @param keepDirStructure
	 *            是否保留原来的目录结构,true:保留目录结构;
	 *            false:所有文件跑到压缩包根目录下(注意：不保留目录结构可能会出现同名文件,会压缩失败)
	 * @return 压缩成功返回true，否则，返回false
	 */

	public static boolean toZip(String[] srcDirs, OutputStream out,
			boolean keepDirStructure) {
		return toZip(srcDirs, null, out, keepDirStructure);
	}
	
	/**
	 * 压缩成ZIP
	 * 
	 * @param srcDirs
	 *            压缩文件夹路径(同时压缩几个文件夹)
	 * @param zipInDir
	 * @param out
	 *            压缩文件输出流
	 * @param keepDirStructure
	 *            是否保留原来的目录结构,true:保留目录结构;
	 *            false:所有文件跑到压缩包根目录下(注意：不保留目录结构可能会出现同名文件,会压缩失败)
	 * @return 压缩成功返回true，否则，返回false
	 */

	public static boolean toZip(String[] srcDirs, String zipInDir, OutputStream out,
			boolean keepDirStructure) {
		if (XLPArrayUtil.isEmpty(srcDirs) || out == null) {
			return false;
		}

		int len = srcDirs.length;
		File[] files = new File[len];
		for (int i = 0; i < len; i++) { 
			files[i] = new File(srcDirs[i]); 
		}
		return toZip(files, zipInDir, out, keepDirStructure);
	}
	
	/**
	 * 压缩成ZIP
	 * 
	 * @param srcDirs
	 *            压缩文件夹路径(同时压缩几个文件夹)
	 * @param out
	 *            压缩文件输出流
	 * @return 压缩成功返回true，否则，返回false
	 */

	public static boolean toZip(String[] srcDirs, OutputStream out) {
		return toZip(srcDirs, out, false);
	}
	
	/**
	 * 压缩成ZIP
	 * 
	 * @param srcFile
	 *            压缩文件或文件夹
	 * @param out
	 *            压缩文件输出流
	 * @param keepDirStructure
	 *            是否保留原来的目录结构,true:保留目录结构;
	 *            false:所有文件跑到压缩包根目录下(注意：不保留目录结构可能会出现同名文件,会压缩失败)
	 * @return 压缩成功返回true，否则，返回false
	 */

	public static boolean toZip(File srcFile, OutputStream out,
			boolean keepDirStructure) {
		return toZip(srcFile, null, out, keepDirStructure);
	}
	
	/**
	 * 压缩成ZIP
	 * 
	 * @param srcFile
	 *            压缩文件或文件夹
	 * @param zipInDir
	 * @param out
	 *            压缩文件输出流
	 * @param keepDirStructure
	 *            是否保留原来的目录结构,true:保留目录结构;
	 *            false:所有文件跑到压缩包根目录下(注意：不保留目录结构可能会出现同名文件,会压缩失败)
	 * @return 压缩成功返回true，否则，返回false
	 */

	public static boolean toZip(File srcFile, String zipInDir, OutputStream out,
			boolean keepDirStructure) {
		if (srcFile == null || out == null) {
			return false;
		}
		
		File[] files = {srcFile};
		return toZip(files, zipInDir, out, keepDirStructure);
	}
	
	/**
	 * 压缩成ZIP
	 * 
	 * @param srcFile
	 *            压缩文件或文件夹
	 * @param out
	 *            压缩文件输出流
	 * @return 压缩成功返回true，否则，返回false
	 */

	public static boolean toZip(File srcFile, OutputStream out) {
		return toZip(srcFile, out, false);
	}
	
	/**
	 * 压缩成ZIP
	 * 
	 * @param srcDir
	 *            压缩文件夹路径
	 * @param out
	 *            压缩文件输出流
	 * @param keepDirStructure
	 *            是否保留原来的目录结构,true:保留目录结构;
	 *            false:所有文件跑到压缩包根目录下(注意：不保留目录结构可能会出现同名文件,会压缩失败)
	 * @return 压缩成功返回true，否则，返回false
	 */

	public static boolean toZip(String srcDir, OutputStream out, 
			boolean keepDirStructure) {
		return toZip(srcDir, null, out, keepDirStructure);
	}
	
	/**
	 * 压缩成ZIP
	 * 
	 * @param srcDir
	 *            压缩文件夹路径
	 * @param out
	 *            压缩文件输出流
	 * @param keepDirStructure
	 *            是否保留原来的目录结构,true:保留目录结构;
	 *            false:所有文件跑到压缩包根目录下(注意：不保留目录结构可能会出现同名文件,会压缩失败)
	 * @return 压缩成功返回true，否则，返回false
	 */

	public static boolean toZip(String srcDir, String zipInDir, OutputStream out, 
			boolean keepDirStructure) {
		if (XLPStringUtil.isEmpty(srcDir) || out == null) {
			return false;
		}

		String[] srcDirs = {srcDir};
		return toZip(srcDirs, zipInDir, out, keepDirStructure);
	}
	
	/**
	 * 压缩成ZIP
	 * 
	 * @param srcDir
	 *            压缩文件夹路径
	 * @param out
	 *            压缩文件输出流
	 * @return 压缩成功返回true，否则，返回false
	 */

	public static boolean toZip(String srcDir, OutputStream out) {
		return toZip(srcDir, out, false);
	}

	/**
	 * 压缩成ZIP
	 * 
	 * @param srcFiles
	 *            压缩文件或文件夹(同时压缩几个文件或文件夹)
	 * @param zipInDir
	 * @param destFile
	 *            zip目标保存文件
	 * @param keepDirStructure
	 *            是否保留原来的目录结构,true:保留目录结构;
	 *            false:所有文件跑到压缩包根目录下(注意：不保留目录结构可能会出现同名文件,会压缩失败)
	 * @return 压缩成功返回true，否则，返回false
	 */

	public static boolean toZip(File[] srcFiles, String zipInDir, File destFile,
			boolean keepDirStructure) {
		if (XLPArrayUtil.isEmpty(srcFiles)) {
			return false;
		}
		
		Zip zip = new Zip(srcFiles, charsetName);
		zip.setZipInDir(zipInDir); 
		try {
			zip.toZip(destFile, keepDirStructure);
		} catch (IOException e) {
			XLPOutputInfoUtil.println(e.getMessage());
			return false;
		}
		return true;
	}
	
	/**
	 * 压缩成ZIP
	 * 
	 * @param srcFiles
	 *            压缩文件或文件夹(同时压缩几个文件或文件夹)
	 * @param destFile
	 *            zip目标保存文件
	 * @param keepDirStructure
	 *            是否保留原来的目录结构,true:保留目录结构;
	 *            false:所有文件跑到压缩包根目录下(注意：不保留目录结构可能会出现同名文件,会压缩失败)
	 * @return 压缩成功返回true，否则，返回false
	 */

	public static boolean toZip(File[] srcFiles, File destFile,
			boolean keepDirStructure) {
		return toZip(srcFiles, null, destFile, keepDirStructure);
	}
	
	/**
	 * 压缩成ZIP
	 * 
	 * @param srcFiles
	 *            压缩文件或文件夹(同时压缩几个文件或文件夹)
	 * @param destFile
	 *            Zip目标保存文件
	 * @return 压缩成功返回true，否则，返回false
	 */

	public static boolean toZip(File[] srcFiles, File destFile) {
		return toZip(srcFiles, destFile, false);
	}
	
	/**
	 * 压缩成ZIP
	 * 
	 * @param srcDirs
	 *            压缩文件夹路径(同时压缩几个文件夹)
	 * @param zipInDir
	 * @param destFile
	 *            Zip目标保存文件
	 * @param keepDirStructure
	 *            是否保留原来的目录结构,true:保留目录结构;
	 *            false:所有文件跑到压缩包根目录下(注意：不保留目录结构可能会出现同名文件,会压缩失败)
	 * @return 压缩成功返回true，否则，返回false
	 */

	public static boolean toZip(String[] srcDirs, String zipInDir, File destFile,
			boolean keepDirStructure) {
		if (XLPArrayUtil.isEmpty(srcDirs)) {
			return false;
		}

		int len = srcDirs.length;
		File[] files = new File[len];
		for (int i = 0; i < len; i++) { 
			files[i] = new File(srcDirs[i]); 
		}
		return toZip(files, zipInDir, destFile, keepDirStructure);
	}
	
	/**
	 * 压缩成ZIP
	 * 
	 * @param srcDirs
	 *            压缩文件夹路径(同时压缩几个文件夹)
	 * @param destFile
	 *            Zip目标保存文件
	 * @param keepDirStructure
	 *            是否保留原来的目录结构,true:保留目录结构;
	 *            false:所有文件跑到压缩包根目录下(注意：不保留目录结构可能会出现同名文件,会压缩失败)
	 * @return 压缩成功返回true，否则，返回false
	 */

	public static boolean toZip(String[] srcDirs, File destFile,
			boolean keepDirStructure) {
		return toZip(srcDirs, null, destFile, keepDirStructure);
	}
	
	/**
	 * 压缩成ZIP
	 * 
	 * @param srcDirs
	 *            压缩文件夹路径(同时压缩几个文件夹)
	 * @param destFile
	 *            Zip目标保存文件
	 * @return 压缩成功返回true，否则，返回false
	 */

	public static boolean toZip(String[] srcDirs, File destFile) {
		return toZip(srcDirs, destFile, false);
	}
	
	/**
	 * 压缩成ZIP
	 * 
	 * @param srcFile
	 *            压缩文件或文件夹
	 * @param zipInDir
	 * @param destFile
	 *            Zip目标保存文件
	 * @param keepDirStructure
	 *            是否保留原来的目录结构,true:保留目录结构;
	 *            false:所有文件跑到压缩包根目录下(注意：不保留目录结构可能会出现同名文件,会压缩失败)
	 * @return 压缩成功返回true，否则，返回false
	 */

	public static boolean toZip(File srcFile, String zipInDir, File destFile,
			boolean keepDirStructure) {
		if (srcFile == null) {
			return false;
		}
		
		File[] files = {srcFile};
		return toZip(files, zipInDir, destFile, keepDirStructure);
	}
	
	/**
	 * 压缩成ZIP
	 * 
	 * @param srcFile
	 *            压缩文件或文件夹
	 * @param destFile
	 *            Zip目标保存文件
	 * @param keepDirStructure
	 *            是否保留原来的目录结构,true:保留目录结构;
	 *            false:所有文件跑到压缩包根目录下(注意：不保留目录结构可能会出现同名文件,会压缩失败)
	 * @return 压缩成功返回true，否则，返回false
	 */

	public static boolean toZip(File srcFile, File destFile,
			boolean keepDirStructure) {
		return toZip(srcFile, null, destFile, keepDirStructure);
	}
	
	/**
	 * 压缩成ZIP
	 * 
	 * @param srcFile
	 *            压缩文件或文件夹
	 * @param destFile
	 *            Zip目标保存文件
	 * @return 压缩成功返回true，否则，返回false
	 */

	public static boolean toZip(File srcFile, File destFile) {
		return toZip(srcFile, destFile, false);
	}
	
	/**
	 * 压缩成ZIP
	 * 
	 * @param srcDir
	 *            压缩文件夹路径
	 * @param zipInDir
	 * @param destFile
	 *            Zip目标保存文件
	 * @param keepDirStructure
	 *            是否保留原来的目录结构,true:保留目录结构;
	 *            false:所有文件跑到压缩包根目录下(注意：不保留目录结构可能会出现同名文件,会压缩失败)
	 * @return 压缩成功返回true，否则，返回false
	 */

	public static boolean toZip(String srcDir, String zipInDir, File destFile, 
			boolean keepDirStructure) {
		if (XLPStringUtil.isEmpty(srcDir)) {
			return false;
		}

		String[] srcDirs = {srcDir};
		return toZip(srcDirs, zipInDir, destFile, keepDirStructure);
	}
	
	/**
	 * 压缩成ZIP
	 * 
	 * @param srcDir
	 *            压缩文件夹路径
	 * @param destFile
	 *            Zip目标保存文件
	 * @param keepDirStructure
	 *            是否保留原来的目录结构,true:保留目录结构;
	 *            false:所有文件跑到压缩包根目录下(注意：不保留目录结构可能会出现同名文件,会压缩失败)
	 * @return 压缩成功返回true，否则，返回false
	 */

	public static boolean toZip(String srcDir, File destFile, 
			boolean keepDirStructure) {
		return toZip(srcDir, null, destFile, keepDirStructure);
	}
	
	/**
	 * 压缩成ZIP
	 * 
	 * @param srcDir
	 *            压缩文件夹路径
	 * @param destFile
	 *            Zip目标保存文件
	 * @return 压缩成功返回true，否则，返回false
	 */

	public static boolean toZip(String srcDir, File destFile) {
		return toZip(srcDir, destFile, false);
	}
	
	///////////////////////////////////////////////////////////////
	/**
	 * 压缩成ZIP
	 * 
	 * @param srcFiles
	 *            压缩文件或文件夹(同时压缩几个文件或文件夹)
	 * @param zipInDir
	 * @param destFile
	 *            zip目标保存文件
	 * @param keepDirStructure
	 *            是否保留原来的目录结构,true:保留目录结构;
	 *            false:所有文件跑到压缩包根目录下(注意：不保留目录结构可能会出现同名文件,会压缩失败)
	 * @return 压缩成功返回true，否则，返回false
	 */

	public static boolean toZip(File[] srcFiles, String zipInDir, String destFile,
			boolean keepDirStructure) {
		if (XLPArrayUtil.isEmpty(srcFiles)) {
			return false;
		}

		File file = null;
		if (!XLPStringUtil.isEmpty(destFile)) {
			file = new File(destFile.trim());
		}
		return toZip(srcFiles, zipInDir, file, keepDirStructure);
	}
	
	/**
	 * 压缩成ZIP
	 * 
	 * @param srcFiles
	 *            压缩文件或文件夹(同时压缩几个文件或文件夹)
	 * @param destFile
	 *            zip目标保存文件
	 * @param keepDirStructure
	 *            是否保留原来的目录结构,true:保留目录结构;
	 *            false:所有文件跑到压缩包根目录下(注意：不保留目录结构可能会出现同名文件,会压缩失败)
	 * @return 压缩成功返回true，否则，返回false
	 */

	public static boolean toZip(File[] srcFiles, String destFile,
			boolean keepDirStructure) {
		return toZip(srcFiles, null, destFile, keepDirStructure);
	}
	
	/**
	 * 压缩成ZIP
	 * 
	 * @param srcFiles
	 *            压缩文件或文件夹(同时压缩几个文件或文件夹)
	 * @param destFile
	 *            Zip目标保存文件
	 * @return 压缩成功返回true，否则，返回false
	 */

	public static boolean toZip(File[] srcFiles, String destFile) {
		return toZip(srcFiles, destFile, false);
	}
	
	/**
	 * 压缩成ZIP
	 * 
	 * @param srcDirs
	 *            压缩文件夹路径(同时压缩几个文件夹)
	 * @param zipInDir
	 * @param destFile
	 *            Zip目标保存文件
	 * @param keepDirStructure
	 *            是否保留原来的目录结构,true:保留目录结构;
	 *            false:所有文件跑到压缩包根目录下(注意：不保留目录结构可能会出现同名文件,会压缩失败)
	 * @return 压缩成功返回true，否则，返回false
	 */

	public static boolean toZip(String[] srcDirs, String zipInDir, String destFile,
			boolean keepDirStructure) {
		File file = null;
		if (!XLPStringUtil.isEmpty(destFile)) {
			file = new File(destFile.trim());
		}
		return toZip(srcDirs, zipInDir, file, keepDirStructure);
	}
	
	/**
	 * 压缩成ZIP
	 * 
	 * @param srcDirs
	 *            压缩文件夹路径(同时压缩几个文件夹)
	 * @param destFile
	 *            Zip目标保存文件
	 * @param keepDirStructure
	 *            是否保留原来的目录结构,true:保留目录结构;
	 *            false:所有文件跑到压缩包根目录下(注意：不保留目录结构可能会出现同名文件,会压缩失败)
	 * @return 压缩成功返回true，否则，返回false
	 */

	public static boolean toZip(String[] srcDirs, String destFile,
			boolean keepDirStructure) {
		return toZip(srcDirs, null, destFile, keepDirStructure);
	}
	
	/**
	 * 压缩成ZIP
	 * 
	 * @param srcDirs
	 *            压缩文件夹路径(同时压缩几个文件夹)
	 * @param destFile
	 *            Zip目标保存文件
	 * @return 压缩成功返回true，否则，返回false
	 */

	public static boolean toZip(String[] srcDirs, String destFile) {
		return toZip(srcDirs, destFile, false);
	}
	
	/**
	 * 压缩成ZIP
	 * 
	 * @param srcFile
	 *            压缩文件或文件夹
	 * @param zipInDir
	 * @param destFile
	 *            Zip目标保存文件
	 * @param keepDirStructure
	 *            是否保留原来的目录结构,true:保留目录结构;
	 *            false:所有文件跑到压缩包根目录下(注意：不保留目录结构可能会出现同名文件,会压缩失败)
	 * @return 压缩成功返回true，否则，返回false
	 */

	public static boolean toZip(File srcFile, String zipInDir, String destFile,
			boolean keepDirStructure) {
		if (srcFile == null) {
			return false;
		}
		
		File[] files = {srcFile};
		return toZip(files, zipInDir, destFile, keepDirStructure);
	}
	
	/**
	 * 压缩成ZIP
	 * 
	 * @param srcFile
	 *            压缩文件或文件夹
	 * @param destFile
	 *            Zip目标保存文件
	 * @param keepDirStructure
	 *            是否保留原来的目录结构,true:保留目录结构;
	 *            false:所有文件跑到压缩包根目录下(注意：不保留目录结构可能会出现同名文件,会压缩失败)
	 * @return 压缩成功返回true，否则，返回false
	 */

	public static boolean toZip(File srcFile, String destFile,
			boolean keepDirStructure) {
		return toZip(srcFile, null, destFile, keepDirStructure);
	}
	
	/**
	 * 压缩成ZIP
	 * 
	 * @param srcFile
	 *            压缩文件或文件夹
	 * @param destFile
	 *            Zip目标保存文件
	 * @return 压缩成功返回true，否则，返回false
	 */

	public static boolean toZip(File srcFile, String destFile) {
		return toZip(srcFile, destFile, false);
	}
	
	/**
	 * 压缩成ZIP
	 * 
	 * @param srcDir
	 *            压缩文件夹路径
	 * @param zipInDir
	 * @param destFile
	 *            Zip目标保存文件
	 * @param keepDirStructure
	 *            是否保留原来的目录结构,true:保留目录结构;
	 *            false:所有文件跑到压缩包根目录下(注意：不保留目录结构可能会出现同名文件,会压缩失败)
	 * @return 压缩成功返回true，否则，返回false
	 */

	public static boolean toZip(String srcDir, String zipInDir, String destFile, 
			boolean keepDirStructure) {
		if (XLPStringUtil.isEmpty(srcDir)) {
			return false;
		}

		String[] srcDirs = {srcDir};
		return toZip(srcDirs, zipInDir, destFile, keepDirStructure);
	}
	
	/**
	 * 压缩成ZIP
	 * 
	 * @param srcDir
	 *            压缩文件夹路径
	 * @param destFile
	 *            Zip目标保存文件
	 * @param keepDirStructure
	 *            是否保留原来的目录结构,true:保留目录结构;
	 *            false:所有文件跑到压缩包根目录下(注意：不保留目录结构可能会出现同名文件,会压缩失败)
	 * @return 压缩成功返回true，否则，返回false
	 */

	public static boolean toZip(String srcDir, String destFile, 
			boolean keepDirStructure) {
		return toZip(srcDir, null, destFile, keepDirStructure);
	}
	
	/**
	 * 压缩成ZIP
	 * 
	 * @param srcDir
	 *            压缩文件夹路径
	 * @param destFile
	 *            Zip目标保存文件
	 * @return 压缩成功返回true，否则，返回false
	 */

	public static boolean toZip(String srcDir, String destFile) {
		return toZip(srcDir, destFile, false);
	}
	
	/**
	 * 压缩成ZIP
	 * 
	 * @param byteList
	 *            要压缩的输入流
	 * @param inZipNames
	 *            Zip内部对应输入流的文件名称
	 * @param out
	 *            输出流
	 * @return 压缩成功返回true，否则，返回false
	 */

	public static boolean toZip(List<byte[]> byteList, List<String> inZipNames, 
			OutputStream out) {
		if (XLPCollectionUtil.isEmpty(byteList) || XLPCollectionUtil.isEmpty(inZipNames)
				|| out == null) { 
			return false;
		}
		int len = Math.min(byteList.size(), inZipNames.size());
		
		ZipOutputStream zos = null;
		try {
			zos = new ZipOutputStream(out, Charset.forName(charsetName));
			for(int i = 0; i < len; i++){
				// 向zip输出流中添加一个zip实体，构造器中name为zip实体的文件的名字
				zos.putNextEntry(new ZipEntry(inZipNames.get(i))); 
				zos.write(byteList.get(i)); 
				zos.closeEntry();
			}
		} catch (Exception e) {
			XLPOutputInfoUtil.println(e.getMessage());
			return false;
		} finally {
			XLPIOUtil.closeOutputStream(zos);
		}
		return true;
	}
	
	/**
	 * 压缩成ZIP
	 * 
	 * @param byteList
	 *            要压缩的输入流
	 * @param inZipNames
	 *            Zip内部对应输入流的文件名称
	 * @param destFile
	 *            输出流
	 * @return 压缩成功返回true，否则，返回false
	 */

	public static boolean toZip(List<byte[]> byteList, List<String> inZipNames, 
			File destFile) {
		if (XLPCollectionUtil.isEmpty(byteList) || XLPCollectionUtil.isEmpty(inZipNames)) { 
			return false;
		}
		String name = inZipNames.get(0);
		int index = name.lastIndexOf("\\");
		String[] names = null;
		if (index < 0) {
			index = name.lastIndexOf("/");
			if (index >= 0 ) {
				names = name.split("/");
			}
		}else {
			names = name.split("\\\\");
		}
		if (!XLPArrayUtil.isEmpty(names)) { 
			name = names[names.length - 1];
		}
		
		name = name + ".zip";
		
		if (destFile == null) { 
			destFile = new File(name);
		}
		if (destFile.isDirectory()) {
			destFile = new File(destFile, name);
		}
		
		File parentFile = destFile.getParentFile();
		if (parentFile != null && !parentFile.exists()) {
			parentFile.mkdirs();
		}
		
		BufferedOutputStream out = null;
		try {
			out = new BufferedOutputStream(new FileOutputStream(destFile));
		} catch (IOException e) {
			XLPIOUtil.closeOutputStream(out);
			out = null;
		} 
		
		boolean success = toZip(byteList, inZipNames, out);
		XLPIOUtil.closeOutputStream(out);
		out = null;
		return success;
	}
	
	/**
	 * 压缩成ZIP
	 * 
	 * @param bytes
	 *            要压缩的输入流
	 * @param inZipName
	 *            Zip内部对应输入流的文件名称
	 * @param out
	 *            输出流
	 * @return 压缩成功返回true，否则，返回false
	 */

	public static boolean toZip(byte[] bytes, String inZipName, 
			OutputStream out) {
		if (XLPArrayUtil.isEmpty(bytes) || XLPStringUtil.isEmpty(inZipName) 
				|| out == null) { 
			return false;
		}
		
		ZipOutputStream zos = null;
		try {
			zos = new ZipOutputStream(out, Charset.forName(charsetName)); 
			// 向zip输出流中添加一个zip实体，构造器中name为zip实体的文件的名字
			zos.putNextEntry(new ZipEntry(inZipName)); 
			zos.write(bytes);
			zos.closeEntry();
		} catch (Exception e) {
			XLPOutputInfoUtil.println(e.getMessage());
			return false;
		} finally {
			XLPIOUtil.closeOutputStream(zos);
		}
		return true;
	}
	
	/**
	 * 压缩成ZIP
	 * 
	 * @param bytes
	 *            要压缩的输入流
	 * @param inZipName
	 *            Zip内部对应输入流的文件名称
	 * @param destFile
	 *            输出流
	 * @return 压缩成功返回true，否则，返回false
	 */

	public static boolean toZip(byte[] bytes, String inZipName,
			File destFile) {
		if (bytes == null || XLPStringUtil.isEmpty(inZipName)) { 
			return false;
		}
		List<byte[]> byteList = new ArrayList<byte[]>();
		byteList.add(bytes);
		List<String> inZipNames = new ArrayList<String>();
		inZipNames.add(inZipName);
		return toZip(byteList, inZipNames, destFile);
	}

	/**
	 * 设置压缩文件时字符编码
	 * 
	 * @param charsetName
	 */
	public static void setCharsetName(String charsetName) {
		if (XLPStringUtil.isEmpty(charsetName)) {
			ZipUtils.charsetName = ZIP_DEFAULT_CHARSET_NAME;
		}else {
			ZipUtils.charsetName = charsetName.trim();
		}
	}
}
