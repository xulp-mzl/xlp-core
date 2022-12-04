package org.xlp.beancovert;

/**
 * <p>创建时间：2022年12月2日 下午10:48:49</p>
 * @author xlp
 * @version 1.0 
 * @Description bean字段转换接口，处理指定两个bean特殊字段转换接口
*/
@FunctionalInterface
public interface IBeanFieldCovert {
	/**
	 * bean字段转换
	 * @param source 源字段对象
	 * @return 转换后的字段类型值
	 */
	<T, S> T covert(S source);
}
