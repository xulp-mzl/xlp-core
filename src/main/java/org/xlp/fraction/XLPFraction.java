package org.xlp.fraction;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.math.BigDecimal;

import org.xlp.utils.XLPOutputInfoUtil;
import org.xlp.utils.io.XLPIOUtil;

/**
 * @version 1.0
 * @author 徐龙平
 *         <p>
 *         2017-5-7
 *         </p>
 * 
 *         分数抽象类，功能主要有分数的四则运算，分数化简
 */
public abstract class XLPFraction extends Number implements Serializable,
		Comparable<XLPFraction> {
	private static final long serialVersionUID = -2471621653241765623L;

	private static final int DEFAULT_ACCURACY = 10;
	
	/**
	 * 分数加法
	 * 
	 * @param fraction
	 * @return 返回一个新的分数对象
	 */
	public abstract XLPFraction add(XLPFraction fraction);

	/**
	 * 分数减法
	 * 
	 * @param fraction
	 * @return 返回一个新的分数对象
	 */
	public abstract XLPFraction subtract(XLPFraction fraction);

	/**
	 * 分数乘法
	 * 
	 * @param fraction
	 * @return 返回一个新的分数对象
	 */
	public abstract XLPFraction multiply(XLPFraction fraction);

	/**
	 * 分数除法
	 * 
	 * @param fraction
	 * @return 返回一个新的分数对象
	 */
	public abstract XLPFraction divide(XLPFraction fraction);

	/**
	 * 取反
	 * 
	 * @return 返回一个与this相反的分数新对象
	 */
	public abstract XLPFraction negate();

	/**
	 * 取绝对值
	 * 
	 * @return
	 */
	public abstract XLPFraction abs();

	/**
	 * 计算this的n次幂（n =0,1,2,3,....）
	 * 
	 * @param n
	 *            n次幂
	 * @return this的n次幂
	 */
	public abstract XLPFraction pow(int n);

	/**
	 * 得到最简分数
	 * 
	 * @return
	 */
	public abstract XLPFraction getFraction();

	/**
	 * 分数转换成指定的精度的小数BigDecimal
	 * 
	 * @param accuracy
	 *            假如accuracy小于0，则重新设置为0
	 * @return
	 */
	public abstract BigDecimal decimalVlaue(int accuracy);

	/**
	 * 分数转换成小数BigDecimal
	 * 
	 * @return
	 */
	public abstract BigDecimal decimalVlaue();

	/**
	 * 分数转换成指定的精度的小数
	 * 
	 * @param accury
	 *            假如accuracy小于0，则重新设置为0
	 * @return
	 */
	public double doubleValue(int accuracy) {
		return decimalVlaue(accuracy).doubleValue();
	}

	/**
	 * 分数转换成小数
	 * 
	 * @return
	 */
	@Override
	public double doubleValue() {
		return decimalVlaue(DEFAULT_ACCURACY).doubleValue();
	}

	@Override
	public int intValue() {
		return decimalVlaue(DEFAULT_ACCURACY).intValue();
	}

	@Override
	public long longValue() {
		return decimalVlaue(DEFAULT_ACCURACY).longValue();
	}

	@Override
	public float floatValue() {
		return decimalVlaue(DEFAULT_ACCURACY).floatValue();
	}
	
	/**
	 * 得到两个分数中较大的那个分数
	 * 
	 * @param fraction
	 *            要比较的分数
	 * @return 假如this > fraction反回this，否则返回fraction
	 */
	public XLPFraction max(XLPFraction fraction) {
		return this.compareTo(fraction) > 0 ? this : fraction;
	}

	/**
	 * 得到两个分数中较小的那个分数
	 * 
	 * @param fraction
	 *            要比较的分数
	 * @return 假如this < fraction反回this，否则返回fraction
	 */
	public XLPFraction min(XLPFraction fraction) {
		return this.compareTo(fraction) < 0 ? this : fraction;
	}

	/**
	 * 深度克隆（得到此对象副本）
	 */
	@Override
	public Object clone() {
		ObjectOutputStream oo = null;
		ObjectInputStream oi = null;
		try {
			ByteArrayOutputStream bo = new ByteArrayOutputStream();
			oo = new ObjectOutputStream(bo);
			oo.writeObject(this);// 从流里读出来
			ByteArrayInputStream bi = new ByteArrayInputStream(bo.toByteArray());
			oi = new ObjectInputStream(bi);
			return (oi.readObject());
		} catch (ClassNotFoundException e) {
			XLPOutputInfoUtil.println(e);
		} catch (IOException e) {
			XLPOutputInfoUtil.println(e);
		} finally {
			XLPIOUtil.closeOutputStream(oo);
			XLPIOUtil.closeInputStream(oi);
		}
		return null;
	}

	/**
	 * 获取分子
	 * 
	 * @return
	 */
	public abstract Object getFZ();

	/**
	 * 获取分母
	 * 
	 * @return
	 */
	public abstract Object getFM();

	/**
	 * 是否返回最简分式
	 * 
	 * @return
	 */
	public abstract boolean isReturnLowestF();
}
