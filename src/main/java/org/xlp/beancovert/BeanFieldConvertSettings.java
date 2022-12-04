package org.xlp.beancovert;

import java.util.HashMap;
import java.util.Map;

import org.xlp.assertion.AssertUtils;

/**
 * <p>创建时间：2022年12月2日 下午10:53:35</p>
 * @author xlp
 * @version 1.0 
 * @Description bean字段转换配置
*/
public class BeanFieldConvertSettings {
	/**
	 * 记录 bean字段转换实现，key：字段名称，value：该字段转换实现类
	 */
	private Map<String, IBeanFieldCovert> beanFieldCovertMap = new HashMap<String, IBeanFieldCovert>();
	
	/**
	 * 记录是否跳过值为null的字段
	 */
	private boolean skipNull = true;
	
	/**
	 * 记录是否跳过值为""的字段，针对String类型对象生效
	 */
	private boolean skipEmpty = true;
	
	/**
	 * 记录转换出错时，是否抛出异常
	 */
	private boolean covertFailThrow = true;
	
	/**
	 * 添加字段转换实现类
	 * 
	 * @param fieldName 字段名称
	 * @param covert 字段转换实现类
	 * @throws NullPointerException 假如字段名称为null或空，则抛出该异常
	 */
	public void addCovert(String fieldName, IBeanFieldCovert covert){
		AssertUtils.isNotNull(fieldName, "fieldName parameter is null or empty");
		if (covert != null) {
			beanFieldCovertMap.put(fieldName, covert);
		}
	}
	
	/**
	 * 获取字段转换实现类
	 * 
	 * @param fieldName 字段名称
	 * @return
	 * @throws NullPointerException 假如字段名称为null或空，则抛出该异常
	 */
	public IBeanFieldCovert getCovert(String fieldName){
		AssertUtils.isNotNull(fieldName, "fieldName parameter is null or empty");
		return beanFieldCovertMap.get(fieldName);
	}
	
	/**
	 * 获取 bean字段转换实现映射关系，key：字段名称，value：该字段转换实现类
	 * @return
	 */
	Map<String, IBeanFieldCovert> getBeanFieldCovertMap(){
		return beanFieldCovertMap;
	}

	/**
	 * 获取是否跳过值为null的字段
	 * 
	 * @return true：跳过，false不跳过
	 */
	public boolean isSkipNull() {
		return skipNull;
	}

	/**
	 * 设置是否跳过值为null的字段
	 * 
	 * @param skipNull true：跳过，false不跳过
	 */
	public void setSkipNull(boolean skipNull) {
		this.skipNull = skipNull;
	}

	/**
	 * 获取是否跳过值为""的字段，针对String类型对象生效
	 * 
	 * @return  true：跳过，false不跳过
	 */
	public boolean isSkipEmpty() {
		return skipEmpty;
	}

	/**
	 * 设置是否跳过值为""的字段，针对String类型对象生效
	 * 
	 * @param skipEmpty true：跳过，false不跳过
	 */
	public void setSkipEmpty(boolean skipEmpty) {
		this.skipEmpty = skipEmpty;
	}

	/**
	 * 获取转换出错时，是否抛出异常状态
	 * 
	 * @return true：抛出异常， false：不抛出异常
	 */
	public boolean isCovertFailThrow() {
		return covertFailThrow;
	}

	/**
	 * 设置转换出错时，是否抛出异常
	 * 
	 * @param covertFailThrow true：抛出异常， false：不抛出异常
	 */
	public void setCovertFailThrow(boolean covertFailThrow) {
		this.covertFailThrow = covertFailThrow;
	}
}
