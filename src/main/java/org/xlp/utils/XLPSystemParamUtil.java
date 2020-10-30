package org.xlp.utils;

public class XLPSystemParamUtil {
	/**
	 * 获取系统换行符
	 * 
	 * @return
	 */
	public static String getSystemNewline(){
		try {
			return System.getProperty("line.separator", "\n");
		} catch (Exception e) {
		}
		return "\n";
	}
	
	/**
	 * Java的运行环境版本
	 * 
	 * @return
	 */
	public static String getJDKVersion(){
		try {
			return System.getProperty("java.version", "");
		} catch (Exception e) {
		}
		return "";
	}
	
	/**
	 * Java 运行时环境供应商
	 * 
	 * @return
	 */
	public static String getJDKVendor(){
		try {
			return System.getProperty("java.vendor", "");
		} catch (Exception e) {
		}
		return "";
	}
	
	/**
	 * Java 供应商的 URL
	 * 
	 * @return
	 */
	public static String getJDKVendorUrl(){
		try {
			return System.getProperty("java.vendor.url", "");
		} catch (Exception e) {
		}
		return "";
	}
	
	/**
	 * Java 安装目录
	 * 
	 * @return
	 */
	public static String getJavaHome(){
		try {
			return System.getProperty("java.home", "");
		} catch (Exception e) {
		}
		return "";
	}
	
	/**
	 * 用户的当前工作目录
	 * 
	 * @return
	 */
	public static String getUserDir(){
		try {
			return System.getProperty("user.dir", "");
		} catch (Exception e) {
		}
		return "";
	}
	
	/**
	 * 用户的主目录
	 * 
	 * @return
	 */
	public static String getUserHome(){
		try {
			return System.getProperty("user.home", "");
		} catch (Exception e) {
		}
		return "";
	}
	
	/**
	 * 用户的账户名称
	 * 
	 * @return
	 */
	public static String getUserName(){
		try {
			return System.getProperty("user.name", "");
		} catch (Exception e) {
		}
		return "";
	}
	
	/**
	 * 路径分隔符（在 UNIX 系统中是“:”）
	 * 
	 * @return
	 */
	public static String getPathSeparator(){
		try {
			return System.getProperty("path.separator", "");
		} catch (Exception e) {
		}
		return "";
	}
	
	/**
	 * 文件分隔符（在 UNIX 系统中是“/”）
	 * 
	 * @return
	 */
	public static String getFileSeparator(){
		try {
			return System.getProperty("file.separator", "\\");
		} catch (Exception e) {
		}
		return "\\";
	}
	
	/**
	 * 操作系统的版本
	 * 
	 * @return
	 */
	public static String getOSVersion(){
		try {
			return System.getProperty("os.version", "");
		} catch (Exception e) {
		}
		return "";
	}
	
	/**
	 * 操作系统的架构
	 * 
	 * @return
	 */
	public static String getOSRrch(){
		try {
			return System.getProperty("os.arch", "");
		} catch (Exception e) {
		}
		return "";
	}
	
	/**
	 * 操作系统的名称
	 * 
	 * @return
	 */
	public static String getOSName(){
		try {
			return System.getProperty("os.name", "");
		} catch (Exception e) {
		}
		return "";
	}
	
	/**
	 * Java 扩展目录的路径
	 * 
	 * @return
	 */
	public static String getJavaExtDirs(){
		try {
			return System.getProperty("java.ext.dirs", "");
		} catch (Exception e) {
		}
		return "";
	}
	
	/**
	 * 要使用的 JIT 编译器的名称
	 * 
	 * @return
	 */
	public static String getJavaCompiler(){
		try {
			return System.getProperty("java.compiler", "");
		} catch (Exception e) {
		}
		return "";
	}
	
	/**
	 * 默认的临时文件路径
	 * 
	 * @return
	 */
	public static String getJavaIOTmpdir(){
		try {
			return System.getProperty("java.io.tmpdir", "");
		} catch (Exception e) {
		}
		return "";
	}
	
	/**
	 * Java 加载库时搜索的路径列表
	 * 
	 * @return
	 */
	public static String getJavaLibraryPath(){
		try {
			return System.getProperty("java.library.path", "");
		} catch (Exception e) {
		}
		return "";
	}
	
	/**
	 * Java 类路径
	 * 
	 * @return
	 */
	public static String getJavaClassPath(){
		try {
			return System.getProperty("java.class.path", "");
		} catch (Exception e) {
		}
		return "";
	}
	
	/**
	 * Java 类格式版本号
	 * 
	 * @return
	 */
	public static String getJavaClassVersion(){
		try {
			return System.getProperty("java.class.version", "");
		} catch (Exception e) {
		}
		return "";
	}
	
	/**
	 * Java 运行时环境规范名称
	 * 
	 * @return
	 */
	public static String getJavaSpecificationName(){
		try {
			return System.getProperty("java.specification.name", "");
		} catch (Exception e) {
		}
		return "";
	}
	
	/**
	 * Java 运行时环境规范供应商
	 * 
	 * @return
	 */
	public static String getJavaSpecificationVendor(){
		try {
			return System.getProperty("java.specification.vendor", "");
		} catch (Exception e) {
		}
		return "";
	}
	
	/**
	 * Java 运行时环境规范版本
	 * 
	 * @return
	 */
	public static String getJavaSpecificationVersion(){
		try {
			return System.getProperty("java.specification.version", "");
		} catch (Exception e) {
		}
		return "";
	}
	
	/**
	 * Java 虚拟机实现名称
	 * 
	 * @return
	 */
	public static String getJVMName(){
		try {
			return System.getProperty("java.vm.name", "");
		} catch (Exception e) {
		}
		return "";
	}
	
	/**
	 * Java 虚拟机实现供应商
	 * 
	 * @return
	 */
	public static String getJVMVendor(){
		try {
			return System.getProperty("java.vm.vendor", "");
		} catch (Exception e) {
		}
		return "";
	}
	
	/**
	 * Java 虚拟机实现版本
	 * 
	 * @return
	 */
	public static String getJVMVersion(){
		try {
			return System.getProperty("java.vm.version", "");
		} catch (Exception e) {
		}
		return "";
	}
	
	/**
	 * Java 虚拟机规范名称
	 * 
	 * @return
	 */
	public static String getJVMSpecificatioName(){
		try {
			return System.getProperty("java.vm.specification.name", "");
		} catch (Exception e) {
		}
		return "";
	}
	
	/**
	 * Java 虚拟机规范供应商
	 * 
	 * @return
	 */
	public static String getJVMSpecificatioVendor(){
		try {
			return System.getProperty("java.vm.specification.vendor", "");
		} catch (Exception e) {
		}
		return "";
	}
	
	/**
	 * Java 虚拟机规范版本
	 * 
	 * @return
	 */
	public static String getJVMSpecificatioVersion(){
		try {
			return System.getProperty("java.vm.specification.version", "");
		} catch (Exception e) {
		}
		return "";
	}
	
	/**
	 *  获取计算机名
	 * 
	 * @return
	 */
	public static String getComputerName(){
		String v = null;
		try {
			 v = System.getenv("COMPUTERNAME");
		} catch (Exception e) {
		}
		return v == null ? "" : v;
	}
	
	/**
	 * 获取用户名
	 * 
	 * @return
	 */
	public static String getUsername(){
		String v = null;
		try {
			 v = System.getenv("USERNAME");
		} catch (Exception e) {
		}
		return v == null ? "" : v;
	}
	
	/**
	 * 获取计算机域名
	 * 
	 * @return
	 */
	public static String getUserDomain(){
		String v = null;
		try {
			 v = System.getenv("USERDOMAIN");
		} catch (Exception e) {
		}
		return v == null ? "" : v;
	}
}
