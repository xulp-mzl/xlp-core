package org.xlp.xml;

/**
 * <p>创建时间：2020年7月14日 下午11:46:59</p>
 * @author xlp
 * @version 1.0 
 * @Description json或map转换文xml的tag时，key的名称是否转换成大写，小写，正常
*/
public enum XMLTagType {
	/**
	 * key转换成大写
	 */
	TO_UPPER,
	
	/**
	 * key转换成小写
	 */
	TO_LOWER,
	
	/**
	 * key不转换
	 */
	NORMAL
}
