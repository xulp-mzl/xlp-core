package org.xlp.utils.io.path;

import java.util.ArrayList;
import java.util.List;

import org.xlp.utils.XLPStringUtil;
import org.xlp.utils.XLPSystemParamUtil;
import org.xlp.utils.collection.XLPCollectionUtil;

/**
 * <p>
 * 创建时间：2021年1月13日 下午11:01:07
 * </p>
 * 
 * @author xlp
 * @version 1.0
 * @Description 文件路径操作类
 */
public class XLPFilePathUtil {
	/**
	 * 字符常量：斜杠 {@code '/'}
	 */
	public static final char SLASH = '/';
	
	/**
	 * 字符串常量：斜杠 {@code "/"}
	 */
	public static final String SLASH_STR = "/";
	
	/**
	 * 字符常量：反斜杠 {@code '\\'}
	 */
	public static final char BACKSLASH = '\\';
	
	/**
	 * 字符串常量：反斜杠 {@code "\\"}
	 */
	public static final String BACKSLASH_STR = "\\";

	/**
	 * 修复路径<br>
	 * 如果原路径尾部有分隔符，则保留为标准分隔符（/），否则不保留
	 * <ol>
	 * <li>1. 去除两边空格</li>
	 * <li>2. .. 和 . 转换为绝对路径，去掉.. 和 .</li>
	 * </ol>
	 * <p>
	 * 例子：
	 * 
	 * <pre>
	 * "/foo//" =》 "/foo/"
	 * "/foo/./" =》 "/foo/"
	 * "/foo/../bar" =》 "/foo/bar"
	 * </pre>
	 *
	 * @param path
	 *            原路径
	 * @return 修复后的路径
	 */
	public static String normalize(String path) {
		if (path == null) {
			return path;
		}
		path = path.trim();
		//把“\”转换成"/"
		path = path.replace(BACKSLASH, SLASH);
		String[] everyPartPath = XLPStringUtil.split(path, SLASH_STR, false);
		List<String> normaPaths = new ArrayList<String>(everyPartPath.length);
		for (String partPath : everyPartPath) {
			//过滤掉全是点的部分
			if (partPath.matches("^[.]+$")) { 
				continue;
			}
			normaPaths.add(partPath);
		}
		String osFileSplit = XLPSystemParamUtil.getFileSeparator();
		String normaPath = XLPCollectionUtil.toString(normaPaths, osFileSplit);
		if (path.startsWith(SLASH_STR) && !normaPath.startsWith(osFileSplit)) { 
			normaPath = osFileSplit + normaPath;
		}
		if (path.endsWith(SLASH_STR) && !normaPath.endsWith(osFileSplit)) { 
			normaPath = normaPath + osFileSplit;
		}
		return normaPath;
	}
}
