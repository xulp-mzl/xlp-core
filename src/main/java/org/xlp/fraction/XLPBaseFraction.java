package org.xlp.fraction;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.xlp.utils.XLPVerifedUtil;
import org.xlp.utils.exception.XLPParamFormatException;
import org.xlp.utils.snumber.XLPMathUtil;

/**
 * @version 1.0
 * @author 徐龙平
 *         <p>
 *         2017-5-7
 *         </p>
 * 
 *         分数实现，功能主要有分数的四则运算，分数化简，主要针对小分子与分母组成的分数
 */
public class XLPBaseFraction extends XLPFraction {
	private static final long serialVersionUID = 936590011474600276L;
	/**
	 * 分子
	 */
	private volatile long numerator;
	/**
	 * 分母
	 */
	private volatile long denominator;
	private volatile boolean returnLowestF = false; // 计算结果是否以最简分数返回

	private volatile long realNumerator; // 真正的分子
	private volatile long realDenominator; // 真正的分母

	// 值为0的分数常量
	public static final XLPBaseFraction FRACTION_VALUE_OF_0 = new XLPBaseFraction(
			0l, 1l);
	// 值为1的分数常量
	public static final XLPBaseFraction FRACTION_VALUE_OF_1 = new XLPBaseFraction(
			1l, 1l);
	// 值为-1的分数常量
	public static final XLPBaseFraction FRACTION_VALUE_OF_NRGATE_1 = new XLPBaseFraction(
			-1l, 1l);

	private XLPBaseFraction() {
	}

	/**
	 * 以分子，分母，计算结果是否以最简分数返回标志构造分数对象
	 * 
	 * @param numerator
	 *            分子
	 * @param denominator
	 *            分母
	 * @param returnLowestF
	 *            计算结果是否以最简分数返回标志
	 * @throws IllegalArgumentException
	 *             假如分母为0，抛出此异常
	 */
	public XLPBaseFraction(long numerator, long denominator,
			boolean returnLowestF) {
		setNumerator(numerator);
		setDenominator(denominator);
		setReturnLowestF(returnLowestF);
	}

	/**
	 * 以分子，分母构造分数对象
	 * 
	 * @param numerator
	 *            分子
	 * @param denominator
	 *            分母
	 * @throws IllegalArgumentException
	 *             假如分母为0，抛出此异常
	 */
	public XLPBaseFraction(long numerator, long denominator) {
		this(numerator, denominator, false);
	}

	/**
	 * 以分子，分母，计算结果是否以最简分数返回标志构造分数对象
	 * 
	 * @param numerator
	 *            分子
	 * @param denominator
	 *            分母
	 * @param returnLowestF
	 *            计算结果是否以最简分数返回标志
	 * @throws IllegalArgumentException
	 *             假如分母为0，抛出此异常
	 */
	public XLPBaseFraction(int numerator, int denominator, boolean returnLowestF) {
		this((long) numerator, (long) denominator, returnLowestF);
	}

	/**
	 * 以分子，分母构造分数对象
	 * 
	 * @param numerator
	 *            分子
	 * @param denominator
	 *            分母
	 * @throws IllegalArgumentException
	 *             假如分母为0，抛出此异常
	 */
	public XLPBaseFraction(int numerator, int denominator) {
		this(numerator, denominator, false);
	}

	/**
	 * 得到分子
	 * 
	 * @return
	 */
	public long getNumerator() {
		return numerator;
	}

	/**
	 * 设置分子
	 * 
	 * @param numerator
	 */
	public void setNumerator(long numerator) {
		this.numerator = numerator;
		this.realNumerator = numerator;
	}

	/**
	 * 得到分母
	 * 
	 * @return
	 */
	public long getDenominator() {
		return denominator;
	}

	/**
	 * 设置分母
	 * 
	 * @param denominator
	 * @throws IllegalArgumentException
	 *             假如分母为0，抛出此异常
	 */
	public void setDenominator(long denominator) {
		if (denominator == 0)
			throw new IllegalArgumentException("分母不能为0");
		this.denominator = denominator;
		this.realDenominator = denominator;
		if (this.denominator < 0) {
			this.denominator = -this.denominator;
			this.numerator = -this.numerator;
		}
	}

	/**
	 * 得到是否以最简分数结果返回
	 * 
	 * @return false不是，true是
	 */
	public boolean isReturnLowestF() {
		return returnLowestF;
	}

	/**
	 * 设置是否以最简分数返回标志
	 * 
	 * @param returnLowestF
	 */
	public void setReturnLowestF(boolean returnLowestF) {
		this.returnLowestF = returnLowestF;
	}

	/**
	 * 得到真正的分子
	 * 
	 * @return
	 */
	public long getRealNumerator() {
		return realNumerator;
	}

	/**
	 * 得到真正的分母
	 * 
	 * @return
	 */
	public long getRealDenominator() {
		return realDenominator;
	}

	@Override
	public XLPFraction add(XLPFraction fraction) {
		XLPBaseFraction f = (XLPBaseFraction) fraction;
		XLPBaseFraction result = new XLPBaseFraction();
		if (f.denominator == this.denominator) {
			result.setNumerator(f.numerator + this.numerator);
			result.setDenominator(this.denominator);
		} else {
			result.setNumerator(f.denominator * this.numerator + f.numerator
					* this.denominator);
			result.setDenominator(f.denominator * this.denominator);
		}
		return returnLowestF ? result.getFraction() : result;
	}

	@Override
	public XLPFraction subtract(XLPFraction fraction) {
		XLPBaseFraction f = (XLPBaseFraction) fraction;
		XLPBaseFraction result = new XLPBaseFraction();
		if (f.denominator == this.denominator) {
			result.setNumerator(this.numerator - f.numerator);
			result.setDenominator(this.denominator);
		} else {
			result.setNumerator(f.denominator * this.numerator - f.numerator
					* this.denominator);
			result.setDenominator(f.denominator * this.denominator);
		}
		return returnLowestF ? result.getFraction() : result;
	}

	@Override
	public XLPFraction multiply(XLPFraction fraction) {
		XLPBaseFraction f = (XLPBaseFraction) fraction;
		XLPBaseFraction result = new XLPBaseFraction();
		if (f.isMinusOne()) {
			result = (XLPBaseFraction) this.negate();
		} else if (f.isOne()) {
			result = (XLPBaseFraction) this.clone();
		} else if (this.isMinusOne()) {
			result = (XLPBaseFraction) f.negate();
		} else if (this.isOne()) {
			result = (XLPBaseFraction) f.clone();
		} else {
			result = new XLPBaseFraction();
			result.setNumerator(this.numerator * f.numerator);
			result.setDenominator(this.denominator * f.denominator);
		}
		return returnLowestF ? result.getFraction() : result;
	}

	/**
	 * 分数除法
	 * 
	 * @param fraction
	 * @return 返回一个新的分数对象
	 * @throws IllegalArgumentException
	 *             假如除数为0，抛出此异常
	 */
	@Override
	public XLPFraction divide(XLPFraction fraction) {
		XLPBaseFraction f = (XLPBaseFraction) fraction;
		if (f.isZero())
			throw new IllegalArgumentException("除数不能为0");
		XLPBaseFraction result = null;
		if (f.isMinusOne()) {
			result = (XLPBaseFraction) this.negate();
		} else if (f.isOne()) {
			result = (XLPBaseFraction) this.clone();
		} else if (this.isMinusOne()) {
			result = (XLPBaseFraction) new XLPBaseFraction(f.denominator,
					f.numerator).negate();
		} else if (this.isOne()) {
			result = new XLPBaseFraction(f.denominator, f.numerator);
		} else {
			result = new XLPBaseFraction();
			result.setNumerator(this.numerator * f.denominator);
			result.setDenominator(this.denominator * f.numerator);
		}
		return returnLowestF ? result.getFraction() : result;
	}

	@Override
	public XLPFraction getFraction() {
		long num = this.numerator < 0 ? -this.numerator : numerator;
		// 得到最大公约数
		long mbc = XLPMathUtil.getMaxCommonDivisor(num, this.denominator);
		this.numerator = this.numerator / mbc;
		this.denominator = this.denominator / mbc;
		return this;
	}

	/**
	 * 判断分数值是否为1
	 * 
	 * @return 是返回true，否则返回false
	 */
	public boolean isOne() {
		return this.denominator == this.numerator;
	}

	/**
	 * 判断分数值是否为0
	 * 
	 * @return 是返回true，否则返回false
	 */
	public boolean isZero() {
		return this.numerator == 0;
	}

	/**
	 * 判断分数值是否为-1
	 * 
	 * @return 是返回true，否则返回false
	 */
	public boolean isMinusOne() {
		return this.denominator == -this.numerator;
	}

	/**
	 * 比较两个分数的大小
	 * 
	 * @param o
	 * @return 假如this > o 返回1，假如this = o 返回0，假如this < o 返回-1
	 */
	@Override
	public int compareTo(XLPFraction o) {
		XLPBaseFraction f = (XLPBaseFraction) o;
		if (this.denominator == f.denominator) // 分母相时，比较分子大小
			return this.numerator > f.numerator ? 1
					: (this.numerator == f.numerator ? 0 : -1);
		if (this.numerator == f.numerator) // 分子相同，比较分母大小
			return this.denominator > f.denominator ? -1
					: (this.denominator == f.denominator ? 0 : -1);
		long num = this.numerator * f.denominator;
		long den = this.denominator * f.numerator;
		return num > den ? 1 : (num == den ? 0 : -1);
	}

	@Override
	public String toString() {
		if (this.numerator == 0)
			return "0";
		if (this.denominator == 1)
			return String.valueOf(this.numerator);

		StringBuffer sb = new StringBuffer();

		sb.append(numerator).append("/").append(denominator);
		return sb.toString();
	}

	@Override
	public XLPFraction negate() {
		XLPBaseFraction result = new XLPBaseFraction();
		result.setNumerator(-this.numerator);
		result.setDenominator(this.denominator);
		return result;
	}

	@Override
	public XLPFraction abs() {
		XLPFraction result = (numerator >= 0 && denominator > 0 ? (XLPFraction) this
				.clone() : negate());
		return result;
	}

	/**
	 * 计算this的n次幂（n =0,1,2,3,....）
	 * 
	 * @param n
	 *            n次幂
	 * @return this的n次幂
	 * @throws IllegalArgumentException
	 *             假如n<0，则抛出该异常
	 */
	@Override
	public XLPFraction pow(int n) {
		if (n < 0)
			throw new IllegalArgumentException("参数值应取【0,1,2,3,4...】中的一个值");
		XLPFraction result = null;
		if (n == 0) {
			result = FRACTION_VALUE_OF_1;
		} else if (isZero() || isOne()) {
			result = (XLPFraction) this.clone();
		} else if (isMinusOne()) {
			if (n % 2 == 0)
				result = negate();
			else
				result = (XLPFraction) this.clone();
		} else {
			result = new XLPBaseFraction((long) Math.pow(numerator, n),
					(long) Math.pow(denominator, n));
		}
		return returnLowestF ? result.getFraction() : result;
	}

	/**
	 * 这个函数得到的值是不准确值
	 * 
	 * @param n
	 * @param accury
	 *            要保留的小数位数，假如此值小于0，则重新设置成0
	 * @return
	 */
	public XLPBaseFraction pow(double n, int accury) {
		if (accury < 0) {
			accury = 0;
		}
		XLPBaseFraction result = null;
		if (n == 0) {
			result = FRACTION_VALUE_OF_1;
		} else if (isZero() || isOne()) {
			result = (XLPBaseFraction) this.clone();
		} else if (numerator < 0) {
			throw new RuntimeException("数据处理是产生异常，产生异常的数据是[" + this.toString()
					+ "]");
		} else {
			String rs = new BigDecimal(String.valueOf(Math.pow(numerator, n)))
					.divide(new BigDecimal(String.valueOf(Math.pow(denominator,
							n))), accury, RoundingMode.HALF_UP).toPlainString();

			result = valueOf0(rs.split("\\."));
		}
		return returnLowestF ? (XLPBaseFraction) result.getFraction() : result;
	}

	/**
	 * 用字符串数组构造分数对象，数组存储的是小数的整数部分与小数部分
	 * 
	 * @param num
	 * @return
	 */
	private static XLPBaseFraction valueOf0(String[] num) {
		XLPBaseFraction fraction = new XLPBaseFraction();
		if (num.length == 1 || Long.parseLong(num[1]) == 0) {
			fraction.setNumerator(Long.parseLong(num[0]));
			fraction.setDenominator(1l);
		} else {
			int len = num[1].length();
			if ("".equals(num[0])) {
				num[0] = "0";
			}
			long des1 = Long.parseLong(num[0]);
			long mis1 = Long.parseLong(num[1]);
			fraction.setNumerator((long) Math.pow(10, len) * des1 + mis1);
			fraction.setDenominator((long) Math.pow(10, len));
		}
		return fraction;
	}

	/**
	 * 把浮点数转换成分数对象
	 * 
	 * @param num
	 * @return
	 */
	public static XLPBaseFraction valueOf(double num) {
		String[] split = String.valueOf(num).split("\\.");
		return valueOf0(split);
	}

	/**
	 * 把浮点数转换成分数对象
	 * 
	 * @param num
	 * @return
	 * @throws IllegalArgumentException
	 *             假如参数为null，抛出此异常
	 */
	public static XLPBaseFraction valueOf(BigDecimal num) {
		if (num == null)
			throw new IllegalArgumentException("参数必须不为null");
		String[] split = num.toPlainString().split("\\.");
		return valueOf0(split);
	}

	/**
	 * 把分数字符串转换成分数对象
	 * 
	 * @param fraction
	 * @return
	 * @throws XLPParamFormatException
	 *             假如参数格式错误，抛出该异常
	 */
	public static XLPBaseFraction valueOf(String fraction) {
		if (!XLPVerifedUtil.isFraction(fraction)) {
			throw new XLPParamFormatException("参数格式错误，param:[" + fraction + "]");
		}

		XLPBaseFraction result = new XLPBaseFraction();
		String[] rs = fraction.trim().split("[/]");
		if (rs.length == 1) {
			result.setNumerator(Long.parseLong(rs[0]));
			result.setDenominator(1l);
		} else if (Long.parseLong(rs[1]) == 0) {
			throw new XLPParamFormatException("参数格式错误，param:[" + fraction + "]");
		} else {
			result.setNumerator(Long.parseLong(rs[0]));
			result.setDenominator(Long.parseLong(rs[1]));
		}

		return result;
	}

	@Override
	public BigDecimal decimalVlaue(int accuracy) {
		if (accuracy < 0)
			accuracy = 0;
		return new BigDecimal(String.valueOf(numerator)).divide(new BigDecimal(
				String.valueOf(denominator)), accuracy, RoundingMode.HALF_UP);
	}

	@Override
	public BigDecimal decimalVlaue() {
		return new BigDecimal(String.valueOf(numerator)).divide(new BigDecimal(
				String.valueOf(denominator)));
	}

	@Override
	public Object getFM() {
		return denominator;
	}

	@Override
	public Object getFZ() {
		return numerator;
	}
}
