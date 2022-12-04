package org.xlp.beancovert;

import java.util.HashMap;
import java.util.Map;

import org.xlp.assertion.AssertUtils;
import org.xlp.beancovert.exception.BeanConvertException;
import org.xlp.javabean.JavaBeanPropertiesDescriptor;
import org.xlp.javabean.MethodException;
import org.xlp.javabean.PropertyDescriptor;
import org.xlp.utils.XLPOutputInfoUtil;
import org.xlp.utils.XLPStringUtil;

/**
 * <p>创建时间：2022年12月2日 下午11:29:08</p>
 * @author xlp
 * @version 1.0 
 * @Description bean转换类
*/
public class BeanCovert {
	/**
	 * 记录是否跳过值为null的字段，true：跳过，false不跳过
	 */
	private boolean skipNull = true;
	
	/**
	 * 记录是否跳过值为""的字段，针对String类型对象生效，true：跳过，false不跳过
	 */
	private boolean skipEmpty = true;
	
	/**
	 * 记录转换出错时，是否抛出异常
	 */
	private boolean covertFailThrow = true;
	
	/**
	 * 记录bean字段转换配置
	 */
	private BeanFieldConvertSettings convertSettings;
	
	public BeanCovert(){}
	
	/**
	 * 构造函数
	 * @param convertSettings bean转换设置
	 */
	public BeanCovert(BeanFieldConvertSettings convertSettings){
		setConvertSettings(convertSettings);
	}
	
	/**
	 * bean对象转换
	 * 
	 * @param targetBean 目标bean
	 * @param sourceBean 要转换的bean
	 * @throws NullPointerException 假如第一个参数为null或<code>covertFailThrow</code>值为false并且转换失败时，则抛出该异常
	 * @throws BeanConvertException 假如bean转换失败，则抛出该异常
	 */
	public <T, S> void covert(T targetBean, S sourceBean) throws BeanConvertException{
		AssertUtils.isNotNull(targetBean, "targetBean parameter is null");
		if (sourceBean == null) return;
		//记录源对象字段信息
		Map<String, PropertyDescriptor<S>> sMap = getBeanFieldMapper(sourceBean);
		//记录目标对象字段信息
		Map<String, PropertyDescriptor<T>> tMap = getBeanFieldMapper(targetBean);
		String fieldName;
		PropertyDescriptor<T> tp;
		PropertyDescriptor<S> sp;
		for(Map.Entry<String, PropertyDescriptor<T>> entry : tMap.entrySet()){
			//字段值浅拷贝
			fieldName = entry.getKey();
			//源bean字段描述
			sp = sMap.get(fieldName);
			
			if (sp == null) continue;
			
			//目标bean字段描述
			tp = entry.getValue();
			
			//获取源bean字段值
			Object value = null;
			try {
				value = sp.executeReadMethod(sourceBean);
			} catch (MethodException e) {
				if (covertFailThrow) {
					throw new BeanConvertException("源对象的【" + fieldName + "】的字段值读取失败，", e);
				}
				XLPOutputInfoUtil.println(e);
				//不抛出异常时，继续拷贝其他的字段
				continue;
			}
			
			//判断是否需要把源字段的值赋值给目标字段
			if (skipNull && value == null) continue;
			if (skipEmpty && XLPStringUtil.EMPTY.equals(value)) continue;
			
			//获取bean字段转换器
			IBeanFieldCovert fieldCovert = convertSettings == null ? null 
					: convertSettings.getCovert(fieldName);
			
			//赋值个目标bean
			try {
				Object tv = fieldCovert == null ? FieldCovertUtils.covert(value, tp, sp) 
						: fieldCovert.covert(value);
				
				tp.executeWriteMethod(targetBean, tv);
			} catch (MethodException e) {
				if (covertFailThrow) {
					throw new BeanConvertException("目标对象的【" + fieldName + "】的字段赋值失败，", e);
				}
				XLPOutputInfoUtil.println(e);
			} catch (Exception e) {
				if (covertFailThrow) {
					throw new BeanConvertException("【" + fieldName + "】的字段复制失败，", e);
				}
				XLPOutputInfoUtil.println(e);
			}
		}
	}

	/**
	 * 对象字段信息 key：字段名称，value：字段对应的PropertyDescriptor对象
	 * @param bean
	 * @return
	 */
	private <T> Map<String, PropertyDescriptor<T>> getBeanFieldMapper(T bean) {
		Map<String, PropertyDescriptor<T>> map = new HashMap<>();
		@SuppressWarnings("unchecked")
		JavaBeanPropertiesDescriptor<T> jps = 
				new JavaBeanPropertiesDescriptor<T>((Class<T>) bean.getClass());
		PropertyDescriptor<T>[] pds = jps.getPds();
		for (PropertyDescriptor<T> pd : pds) {
			map.put(pd.getFieldName(), pd);
		}
		return map;
	}
	
	/**
	 * bean对象转换
	 * 
	 * @param targetBeanClass 目标bean Class对象
	 * @param sourceBean 要转换的bean
	 * @throws NullPointerException 假如第一个参数为null，则抛出该异常
	 * @return 假如要转换的bean为null或<code>covertFailThrow</code>值为false并且转换失败时，则返回null，否则返回转换后的对象
	 * @throws BeanConvertException 假如bean转换失败，则抛出该异常
	 */
	public <T, S> T covert(Class<T> targetBeanClass, S sourceBean) throws BeanConvertException{
		AssertUtils.isNotNull(targetBeanClass, "targetBeanClass parameter is null");
		if (sourceBean == null) return null;
		T target = null;
		try {
			target = targetBeanClass.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			if (covertFailThrow) {
				throw new BeanConvertException("目标bean Class实例化失败：", e);
			}
			XLPOutputInfoUtil.println(e);
			return null;
		}
		covert(target, sourceBean);
		return target;
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

	/**
	 * 获取bean字段转换配置
	 * 
	 * @return bean字段转换配置
	 */
	public BeanFieldConvertSettings getConvertSettings() {
		return convertSettings;
	}

	/**
	 * 设置bean字段转换配置
	 * 
	 * @param convertSettings bean字段转换配置
	 */
	public void setConvertSettings(BeanFieldConvertSettings convertSettings) {
		this.convertSettings = convertSettings;
		if (convertSettings != null) {
			skipEmpty = convertSettings.isSkipEmpty();
			skipNull = convertSettings.isSkipNull();
			covertFailThrow = covertFailThrow || convertSettings.isCovertFailThrow();
		}
	}
}
