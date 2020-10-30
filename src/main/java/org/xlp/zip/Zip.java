package org.xlp.zip;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.xlp.utils.XLPStringUtil;

/**
 * zip文件压缩类，提供zip文件压缩方法
 * 
 * @author xlp
 * @date 2020-04-07
 */
public class Zip {
	private static final int BUFFER_SIZE = 2 * 1024;
	
	/**
	 * ZIP压缩是默认字符编码
	 */
	private static final String ZIP_DEFAULT_CHARSET_NAME = "GBK"; 
	
	/**
	 * zip压缩字符编码
	 */
	private String charsetName = ZIP_DEFAULT_CHARSET_NAME;
	/**
	 * 要压缩的文件目录或文件
	 */
	private File[] srcFiles;
	/**
	 * 给zip内部添加目录
	 */
	private String zipInDir = ""; 
	
	/**
	 * @param srcFiles
	 * @throws NullPointerException 假如参数为空，则抛出该异常
	 */
	public Zip(File[] srcFiles){
		if (srcFiles == null) {
			throw new NullPointerException("srcFiles 参数不能为空！");
		}
		this.srcFiles = srcFiles;
	}
	
	/**
	 * @param srcFiles
	 * @param charsetName
	 * @throws NullPointerException 假如参数为空，则抛出该异常
	 */
	public Zip(File[] srcFiles, String charsetName){
		this(srcFiles);
		setCharsetName(charsetName);
	}
	
	/**
	 * @param srcDirs
	 * @throws NullPointerException 假如参数为空，则抛出该异常
	 */
	public Zip(String[] srcDirs){
		if (srcDirs == null) {
			throw new NullPointerException("srcDirs 参数不能为空！");
		}
		int len = srcDirs.length;
		File[] files = new File[len];
		for (int i = 0; i < len; i++) { 
			files[i] = new File(srcDirs[i]); 
		}
		this.srcFiles = files;
	}
	
	/**
	 * @param srcDirs
	 * @param charsetName
	 * @throws NullPointerException 假如参数为空，则抛出该异常
	 */
	public Zip(String[] srcDirs, String charsetName){
		this(srcDirs);
		setCharsetName(charsetName);
	}
	
	/**
	 * @param srcDir
	 * @throws NullPointerException 假如参数为空，则抛出该异常
	 */
	public Zip(String srcDir){
		if (XLPStringUtil.isEmpty(srcDir)) {
			throw new NullPointerException("srcDir 参数不能为空！");
		}
		File[] files = {new File(srcDir)};
		this.srcFiles = files;
	}
	
	/**
	 * @param srcDir
	 * @param charsetName
	 * @throws NullPointerException 假如参数为空，则抛出该异常
	 */
	public Zip(String srcDir, String charsetName){
		this(srcDir);
		setCharsetName(charsetName);
	}
	
	/**
	 * @param srcFile
	 * @throws NullPointerException 假如参数为空，则抛出该异常
	 */
	public Zip(File srcFile){
		if (srcFile == null) {
			throw new NullPointerException("srcFile 参数不能为空！");
		}
		File[] files = {srcFile};
		this.srcFiles = files;
	}
	
	/**
	 * @param srcFile
	 * @param charsetName
	 * @throws NullPointerException 假如参数为空，则抛出该异常
	 */
	public Zip(File srcFile, String charsetName){
		this(srcFile);
		setCharsetName(charsetName);
	}
	
	/**
	 * zip压缩
	 * 
	 * @param out
	 *            压缩文件输出流
	 * @param keepDirStructure
	 *            是否保留原来的目录结构,true:保留目录结构;
	 *            false:所有文件跑到压缩包根目录下(注意：不保留目录结构可能会出现同名文件,会压缩失败)
	 * @throws IOException 假如文件压缩失败，则抛出该异常  
	 * @throws NullPointerException 假如参数为空，则抛出该异常 
	 */
	public void toZip(OutputStream out, boolean keepDirStructure) throws IOException{
		if (out == null) {
			throw new NullPointerException("out 参数不能为空！");
		}
		ZipOutputStream zos = null;
		try {
			zos = new ZipOutputStream(out, Charset.forName(charsetName)); 
			zipInDir = XLPStringUtil.emptyTrim(zipInDir);
			zipInDir = zipInDir.replace("\\", "/");
			if (!zipInDir.endsWith("/")) {
				zipInDir += "/";
			}
			for (File srcFile : srcFiles) { 
				//判断压缩文件是否存在，存在则进行压缩
				if (srcFile.exists()) {
					compress(srcFile, zos, zipInDir + srcFile.getName(), keepDirStructure);
				}
			}
		} finally {
			zos.close();
		}
	}
	
	/**
	 * zip压缩
	 * 
	 * @param out
	 *            压缩文件输出流
	 * @throws IOException 假如文件压缩失败，则抛出该异常  
	 * @throws NullPointerException 假如参数为空，则抛出该异常 
	 */
	public void toZip(OutputStream out) throws IOException{
		toZip(out, false);
	}

	/**
	 * 压缩成ZIP
	 * 
	 * @param destFile
	 *            zip目标保存文件
	 * @param keepDirStructure
	 *            是否保留原来的目录结构,true:保留目录结构;
	 *            false:所有文件跑到压缩包根目录下(注意：不保留目录结构可能会出现同名文件,会压缩失败)
	 * @throws IOException 假如文件压缩失败，则抛出该异常  
	 */
	public void toZip(File destFile, boolean keepDirStructure) throws IOException {
		File srcFile = srcFiles[0];
		String name = srcFile.getName();
		if (srcFile.isFile()) { 
			int index = name.lastIndexOf(".");
			if (index >= 0) {
				name = name.substring(0, index);
			}
		}
		name = name + ".zip";
		
		if (destFile == null) { 
			destFile = new File(srcFile.getParent(), name);
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
			toZip(out, keepDirStructure);
		} finally{
			if (out != null) {
				out.close();
				out = null;
			}
		}
	}
	
	/**
	 * 压缩成ZIP
	 * 
	 * @param destFile
	 *            zip目标保存文件
	 *            false:所有文件跑到压缩包根目录下(注意：不保留目录结构可能会出现同名文件,会压缩失败)
	 * @throws IOException 假如文件压缩失败，则抛出该异常  
	 */
	public void toZip(File destFile) throws IOException {
		toZip(destFile, false);
	}
	
	/**
	 * 压缩成ZIP
	 * 
	 * @param destFile
	 *            zip目标保存文件
	 * @param keepDirStructure
	 *            是否保留原来的目录结构,true:保留目录结构;
	 *            false:所有文件跑到压缩包根目录下(注意：不保留目录结构可能会出现同名文件,会压缩失败)
	 * @throws IOException 假如文件压缩失败，则抛出该异常  
	 */
	public void toZip(String destFile, boolean keepDirStructure) throws IOException {
		File file = null;
		if (!XLPStringUtil.isEmpty(destFile)) {
			file = new File(destFile.trim());
		}
		toZip(file, keepDirStructure);
	}
	
	/**
	 * 压缩成ZIP
	 * 
	 * @param destFile
	 *            zip目标保存文件
	 *            false:所有文件跑到压缩包根目录下(注意：不保留目录结构可能会出现同名文件,会压缩失败)
	 * @throws IOException 假如文件压缩失败，则抛出该异常  
	 */
	public void toZip(String destFile) throws IOException {
		toZip(destFile, false);
	}
	
	/**
	 * 递归压缩方法
	 * 
	 * @param sourceFile
	 *            源文件
	 * @param zos
	 *            zip输出流
	 * @param name
	 *            压缩后的名称
	 * @param keepDirStructure
	 *            是否保留原来的目录结构,true:保留目录结构;
	 *            false:所有文件跑到压缩包根目录下(注意：不保留目录结构可能会出现同名文件,会压缩失败)
	 * @throws IOException
	 */
	private void compress(File sourceFile, ZipOutputStream zos,
			String name, boolean keepDirStructure) throws IOException {
		byte[] buf = new byte[BUFFER_SIZE];
		if (sourceFile.isFile()) {
			FileInputStream in = null;
			try {
				// 向zip输出流中添加一个zip实体，构造器中name为zip实体的文件的名字
				zos.putNextEntry(new ZipEntry(name));
				// copy文件到zip输出流中
				int len;
				in = new FileInputStream(sourceFile);
				while ((len = in.read(buf)) != -1) {
					zos.write(buf, 0, len);
				}
			} finally {
				// Complete the entry
				if (zos != null) {
					try {
						zos.closeEntry();
					} finally {
						if (in != null) {
							in.close();
						}
					}
				}
			}
		} else {
			File[] listFiles = sourceFile.listFiles();
			if (listFiles == null || listFiles.length == 0) {
				// 需要保留原来的文件结构时,需要对空文件夹进行处理
				if (keepDirStructure) {
					try {
						// 空文件夹的处理
						zos.putNextEntry(new ZipEntry(name + "/"));
					} finally {
						if (zos != null) {
							// 没有文件，不需要文件的copy
							zos.closeEntry();
						}
					}
				}
			} else {
				for (File file : listFiles) {
					// 判断是否需要保留原来的文件结构
					if (keepDirStructure) {
						// 注意：file.getName()前面需要带上父文件夹的名字加一斜杠,
						// 不然最后压缩包中就不能保留原来的文件结构,即：所有文件都跑到压缩包根目录下了
						compress(file, zos, name + "/" + file.getName(),
								keepDirStructure);
					} else {
						compress(file, zos, file.getName(), keepDirStructure);
					}
				}
			}
		}
	}
	
	public String getCharsetName() {
		return charsetName;
	}

	public void setCharsetName(String charsetName) {
		if (XLPStringUtil.isEmpty(charsetName)) {
			this.charsetName = ZIP_DEFAULT_CHARSET_NAME;
		}else {
			this.charsetName = charsetName.trim();
		}
	}

	public File[] getSrcFiles() {
		return srcFiles;
	}

	public void setSrcFiles(File[] srcFiles) {
		this.srcFiles = srcFiles;
	}

	public String getZipInDir() {
		return zipInDir;
	}

	/**
	 * 设置zip内部目录
	 * @param zipInDir
	 */
	public void setZipInDir(String zipInDir) {
		zipInDir = (XLPStringUtil.isEmpty(zipInDir) ? "" : (zipInDir.trim() + "/"));
		this.zipInDir = zipInDir;
	}
}
