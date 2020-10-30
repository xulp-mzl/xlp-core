package org.xlp.zip;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.xlp.utils.XLPStringUtil;
import org.xlp.utils.io.XLPIOUtil;

/**
 * zip文件解压类，提供zip文件解压方法
 * 
 * @author xlp
 * @date 2020-04-06
 */
public class UnZip {
	public final static String ZIP_DEFAULT_CHARSET_NAME = "GBK";
	private static final int BUFFER_SIZE = 2 * 1024;
	/**
	 * 解压文件字符编码
	 */
	private String charsetName = ZIP_DEFAULT_CHARSET_NAME;

	/**
	 * 解压文件时的文件流
	 */
	private ZipInputStream zipInputStream;
	
	private boolean closeZipInputStream = false;
	
	/**
	 * 输入流
	 * 
	 * @param zipInputStream
	 * @throws NullPointerException
	 *             假如参数为空，抛出该异常
	 */
	public UnZip(ZipInputStream zipInputStream) {
		this(ZIP_DEFAULT_CHARSET_NAME, zipInputStream);
	}

	/**
	 * 构造函数
	 * 
	 * @param charsetName
	 *            zip编码
	 * @param zipInputStream
	 *            输入流
	 * @throws NullPointerException
	 *             假如参数为空，抛出该异常
	 */
	public UnZip(String charsetName, ZipInputStream zipInputStream) {
		setCharsetName(charsetName);
		setInputStream(zipInputStream);
	}
	
	/**
	 * 输入流
	 * 
	 * @param zipFile zip文件
	 * @throws IOException 假如文件读取失败，抛出该异常
	 * @throws NullPointerException
	 *             假如参数为空，抛出该异常
	 */
	public UnZip(File zipFile) throws IOException {
		this(zipFile, ZIP_DEFAULT_CHARSET_NAME);
	}

	/**
	 * 构造函数
	 * 
	 * @param zipFile
	 *          zip文件  
	 * @param charsetName
	 *           zip编码
	 * @throws IOException 假如文件读取失败，抛出该异常
	 * @throws NullPointerException
	 *             假如参数为空，抛出该异常
	 */
	public UnZip(File zipFile, String charsetName) throws IOException {
		if (zipFile == null) {
			throw new NullPointerException("zipFile 参数不能为空！");
		}
		setCharsetName(charsetName);
		zipInputStream = new ZipInputStream(new FileInputStream(zipFile), 
				Charset.forName(charsetName));
		closeZipInputStream = true;
	}
	
	/**
	 * 输入流
	 * 
	 * @param zipFile zip文件
	 * @throws IOException 假如文件读取失败，抛出该异常
	 * @throws NullPointerException
	 *             假如参数为空，抛出该异常
	 */
	public UnZip(String zipFile) throws IOException {
		this(zipFile, ZIP_DEFAULT_CHARSET_NAME);
	}

	/**
	 * 构造函数
	 * 
	 * @param zipFile
	 *          zip文件  
	 * @param charsetName
	 *           zip编码
	 * @throws IOException 假如文件读取失败，抛出该异常
	 * @throws NullPointerException
	 *             假如参数为空，抛出该异常
	 */
	public UnZip(String zipFile, String charsetName) throws IOException {
		if (zipFile == null) {
			throw new NullPointerException("zipFile 参数不能为空！");
		}
		setCharsetName(charsetName);
		zipInputStream = new ZipInputStream(new FileInputStream(zipFile), 
				Charset.forName(charsetName));
		closeZipInputStream = true;
	}

	public String getCharsetName() {
		return charsetName;
	}

	private void setCharsetName(String charsetName) {
		if (XLPStringUtil.isEmpty(charsetName)) {
			charsetName = ZIP_DEFAULT_CHARSET_NAME;
		} else {
			charsetName = charsetName.trim();
		}
		this.charsetName = charsetName;
	}

	public ZipInputStream getZipInputStream() {
		return zipInputStream;
	}

	/**
	 * @param zipInputStream
	 *            输入流
	 * @throws NullPointerException
	 *             假如参数为空，抛出该异常
	 */
	public void setInputStream(ZipInputStream zipInputStream) {
		if (zipInputStream == null) {
			throw new NullPointerException("zipInputStream 参数不能为空！");
		}
		this.zipInputStream = zipInputStream;
	}

	/**
	 * 解压文件
	 * 
	 * @param destDir
	 *            保存文件目标目录
	 * @throws IOException
	 *             假如文件读写失败，抛出该异常
	 * @throws NullPointerException
	 *             假如参数为空，抛出该异常
	 */
	public void unZip(File destDir) throws IOException {
		if (destDir == null) {
			throw new NullPointerException("destDir 参数不能空！");
		}
		if (!destDir.exists()) {
			destDir.mkdirs();
		}
		if (destDir.isFile()) {
			destDir = destDir.getParentFile();
		}
		
		BufferedOutputStream outputStream = null;
		// 读取一个entry
		try {
			ZipEntry entry = zipInputStream.getNextEntry();
			// 不为空进入循环
			while (entry != null) {
				String name = entry.getName();
				// 如果是文件夹，就创建个文件夹 
				if (entry.isDirectory()) {
					File file = new File(destDir, name);
					if (!file.exists()) {
						file.mkdirs();
					}
				}else {
					// 如果是文件，就先创建一个文件，然后用io流把内容copy过去
		            File targetFile = new File(destDir, name);
		            File parentFile = targetFile.getParentFile();
		            // 保证这个文件的父文件夹必须要存在
		            if(!parentFile.exists()){
		            	parentFile.mkdirs();
		            }
		            outputStream = new BufferedOutputStream(new FileOutputStream(targetFile));
		            int n;
		            byte[] bytes = new byte[BUFFER_SIZE];
		            while ((n = zipInputStream.read(bytes)) != -1) {
		                outputStream.write(bytes, 0, n);
		            }
				}
				zipInputStream.closeEntry();
				outputStream.close();
				outputStream = null;
				// 读取下一个目录，作为循环条件
				entry = zipInputStream.getNextEntry();
			}
		} finally {
			XLPIOUtil.closeOutputStream(outputStream);
			if (closeZipInputStream) {
				XLPIOUtil.closeInputStream(zipInputStream);
			}
		} 
	}

	/**
	 * 解压文件
	 * 
	 * @param destDir
	 *            保存文件目标目录
	 * @throws IOException
	 *             假如文件读写失败，抛出该异常
	 * @throws NullPointerException
	 *             假如参数为空，抛出该异常
	 */
	public void unZip(String destDir) throws IOException {
		if (XLPStringUtil.isEmpty(destDir)) {
			throw new NullPointerException("destDir 参数不能空！");
		}
		unZip(new File(destDir));
	}
	
	public boolean isCloseZipInputStream() {
		return closeZipInputStream;
	}

	public void setCloseZipInputStream(boolean closeZipInputStream) {
		this.closeZipInputStream = closeZipInputStream;
	}
}
