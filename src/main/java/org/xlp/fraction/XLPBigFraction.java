package org.xlp.fraction;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

import org.xlp.utils.XLPVerifedUtil;
import org.xlp.utils.exception.XLPParamFormatException;
import org.xlp.utils.snumber.XLPMathUtil;

/**
 * @version 1.0
 * @author 徐龙平
 *         <p>
 *         2017-5-8
 *         </p>
 * 
 *         分数实现，功能主要有分数的四则运算，分数化简，主要针对大分子与大母组成的分数
 */
public class XLPBigFraction extends XLPFraction {
	private static final long serialVersionUID = -789050339844470065L;

	/**
	 * 分子
	 */
	private volatile BigInteger numerator;
	/**
	 * 分母
	 */
	private volatile BigInteger denominator;
	private volatile boolean returnLowestF = false; // 计算结果是否以最简分数返回

	private volatile BigInteger realNumerator; // 真正的分子
	private volatile BigInteger realDenominator; // 真正的分母

	// 常量
	private final static BigInteger INTEGER_1 = new BigInteger("1");
	private final static BigInteger INTEGER_0 = new BigInteger("0");
	private final static BigInteger INTEGER_NRGATE_1 = new BigInteger("-1");

	// 值为0的分数常量
	public static final XLPBigFraction FRACTION_VALUE_OF_0 = new XLPBigFraction(
			INTEGER_0, INTEGER_1);
	// 值为1的分数常量
	public static final XLPBigFraction FRACTION_VALUE_OF_1 = new XLPBigFraction(
			INTEGER_1, INTEGER_1);
	// 值为-1的分数常量
	public static final XLPBigFraction FRACTION_VALUE_OF_NRGATE_1 = new XLPBigFraction(
			INTEGER_NRGATE_1, INTEGER_1);

	private XLPBigFraction() {
	};

	/**
	 * 以分子，分母，计算结果是否以最简分数返回标志构造分数对象
	 * 
	 * @param numerator
	 *            分子
	 * @param denominator
	 *            分母
	 * @throws IllegalArgumentException
	 *             假如参数为null分母为0，抛出此异常
	 */
	public XLPBigFraction(BigInteger numerator, BigInteger denominator) {
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
	 * @throws XLPParamFormatException
	 *             假如参数格式错误，抛出此异常
	 */

	public XLPBigFraction(String numerator, String denominator,
			boolean returnLowestF) {
		if (!XLPVerifedUtil.isInteger(numerator))
			throw new XLPParamFormatException("参数格式错误，错误参数是：[" + numerator
					+ "]");
		if ((XLPVerifedUtil.isInteger(denominator) && !numerator
				.matches("^.*[1-9]+.*$"))
				|| !XLPVerifedUtil.isInteger(denominator))
			throw new XLPParamFormatException("参数格式错误，错误参数是：[" + denominator
					+ "]");
		setNumerator(new BigInteger(numerator.trim()));
		setDenominator(new BigInteger(denominator.trim()));
		setReturnLowestF(returnLowestF);
	}

	/**
	 * 以分子，分母，计算结果是否以最简分数返回标志构造分数对象
	 * 
	 * @param numerator
	 *            分子
	 * @param denominator
	 *            分母
	 * @throws XLPParamFormatException
	 *             假如参数格式错误，抛出此异常
	 */
	public XLPBigFraction(String numerator, String denominator) {
		this(numerator, denominator, false);
	}

	/**
	 * 以分子，分母，计算结果是否以最简分数返回标志构造分数对象
	 * 
	 * @param numerator
	 *            分子
	 * @param denominator
	 *            分母
	 * @throws IllegalArgumentException
	 *             假如分母为0，抛出此异常
	 */
	public XLPBigFraction(long numerator, long denominator) {
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
	public XLPBigFraction(long numerator, long denominator,
			boolean returnLowestF) {
		this(new BigInteger(String.valueOf(numerator)), new BigInteger(
				String.valueOf(denominator)), returnLowestF);
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
	 *             假如参数为null分母为0，抛出此异常
	 */
	public XLPBigFraction(BigInteger numerator, BigInteger denominator,
			boolean returnLowestF) {
		setNumerator(numerator);
		setDenominator(denominator);
		this.returnLowestF = returnLowestF;
	}

	/**
	 * 用XLPFraction构造分数对象
	 * 
	 * @param fraction
	 *            XLPFraction对象
	 * @throws IllegalArgumentException
	 *             假如参数为null，抛出此异常
	 */
	public XLPBigFraction(XLPFraction fraction) {
		if (fraction == null)
			throw new IllegalArgumentException("参数必须不为null");
		setReturnLowestF(fraction.isReturnLowestF());
		setNumerator(new BigInteger(fraction.getFZ().toString()));
		setDenominator(new BigInteger(fraction.getFM().toString()));
	}

	/**
	 * 得到分子
	 * 
	 * @return
	 */
	public BigInteger getNumerator() {
		return numerator;
	}

	/**
	 * 设置分子
	 * 
	 * @param numerator
	 * @throws IllegalArgumentException
	 *             假如参数为null，抛出该异常
	 * 
	 */
	public void setNumerator(BigInteger numerator) {
		if (numerator == null)
			throw new IllegalArgumentException("参数必须不为null");
		this.numerator = numerator;
		this.realNumerator = numerator;
	}

	/**
	 * 得到分母
	 * 
	 * @return
	 */
	public BigInteger getDenominator() {
		return denominator;
	}

	/**
	 * 设置分母
	 * 
	 * @param denominator
	 * @throws IllegalArgumentException
	 *             假如参数为null或0，抛出该异常
	 */
	public void setDenominator(BigInteger denominator) {
		if (denominator == null || denominator.compareTo(INTEGER_0) == 0)
			throw new IllegalArgumentException("参数必须不为null或0");
		this.denominator = denominator;
		this.realDenominator = denominator;
		if (this.denominator.compareTo(INTEGER_0) < 0) {
			this.numerator = this.numerator.negate();
			this.denominator = this.denominator.negate();
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
	public BigInteger getRealNumerator() {
		return realNumerator;
	}

	/**
	 * 得到真正的分母
	 * 
	 * @return
	 */
	public BigInteger getRealDenominator() {
		return realDenominator;
	}

	/**
	 * 比较两个分数的大小
	 * 
	 * @param o
	 * @return 假如this > o 返回1，假如this = o 返回0，假如this < o 返回-1
	 */
	@Override
	public int compareTo(XLPFraction o) {
		XLPBigFraction bF = toBigFraction(o);
		if (this.numerator.compareTo(bF.numerator) == 0)
			return this.denominator.compareTo(bF.denominator) > 0 ? -1
					: (this.denominator.compareTo(bF.denominator) == 0 ? 0 : 1);
		if (this.denominator.compareTo(bF.denominator) == 0)
			return this.numerator.compareTo(bF.numerator);
		BigInteger tem1 = this.numerator.multiply(bF.denominator);
		BigInteger tem2 = this.denominator.multiply(bF.numerator);
		return tem1.compareTo(tem2);
	}

	@Override
	public String toString() {
		if (isZero())
			return "0";
		if (this.denominator.compareTo(INTEGER_1) == 0)
			return numerator.toString();

		StringBuffer sb = new StringBuffer();
		sb.append(numerator.toString()).append("/")
				.append(denominator.toString());
		return sb.toString();
	}

	/**
	 * 判断分数值是否为1
	 * 
	 * @return 是返回true，否则返回false
	 */
	public boolean isOne() {
		return this.denominator.compareTo(this.numerator) == 0;
	}

	/**
	 * 判断分数值是否为0
	 * 
	 * @return 是返回true，否则返回false
	 */
	public boolean isZero() {
		return this.numerator.compareTo(INTEGER_0) == 0;
	}

	/**
	 * 判断分数值是否为-1
	 * 
	 * @return 是返回true，否则返回false
	 */
	public boolean isMinusOne() {
		return this.denominator.compareTo(this.numerator.negate()) == 0;
	}

	@Override
	public XLPFraction add(XLPFraction fraction) {
		XLPBigFraction bF = toBigFraction(fraction);
		XLPBigFraction result = new XLPBigFraction();
		if (denominator.compareTo(bF.denominator) == 0) {
			result.setDenominator(denominator);
			result.setNumerator(numerator.add(bF.numerator));
		} else {
			result.setDenominator(denominator.multiply(bF.denominator));
			result.setNumerator(numerator.multiply(bF.denominator).add(
					denominator.multiply(bF.numerator)));
		}
		return returnLowestF ? result.getFraction() : result;
	}

	/**
	 * 分数加法
	 * 
	 * @param num
	 * @return 返回一个新的分数对象
	 */
	public XLPBigFraction add(long num) {
		XLPFraction result = new XLPBigFraction(num, 1);
		result = result.add(this);
		return (XLPBigFraction) (returnLowestF ? result.getFraction() : result);
	}

	/**
	 * 分数加法
	 * 
	 * @param num
	 * @return 返回一个新的分数对象
	 */
	public XLPBigFraction add(double num) {
		XLPFraction result = valueOf(num);
		result = result.add(this);
		return (XLPBigFraction) (returnLowestF ? result.getFraction() : result);
	}

	/**
	 * 分数加法
	 * 
	 * @param num
	 * @return 返回一个新的分数对象
	 */
	public XLPBigFraction add(Number num) {
		XLPFraction result = valueOf(new BigDecimal(num + "").toPlainString(),
				true);
		result = result.add(this);
		return (XLPBigFraction) (returnLowestF ? result.getFraction() : result);
	}

	@Override
	public XLPFraction subtract(XLPFraction fraction) {
		XLPBigFraction bF = toBigFraction(fraction);
		XLPBigFraction result = new XLPBigFraction();
		if (denominator.compareTo(bF.denominator) == 0) {
			result.setDenominator(denominator);
			result.setNumerator(numerator.subtract(bF.numerator));
		} else {
			result.setDenominator(denominator.multiply(bF.denominator));
			result.setNumerator(numerator.multiply(bF.denominator).subtract(
					denominator.multiply(bF.numerator)));
		}
		return returnLowestF ? result.getFraction() : result;
	}

	/**
	 * 分数减法
	 * 
	 * @param num
	 * @return 返回一个新的分数对象
	 */
	public XLPBigFraction subtract(long num) {
		XLPFraction result = (XLPFraction) this.clone();
		result = result.subtract(new XLPBigFraction(num, 1l));
		return (XLPBigFraction) (returnLowestF ? result.getFraction() : result);
	}

	/**
	 * 分数减法
	 * 
	 * @param num
	 * @return 返回一个新的分数对象
	 */
	public XLPBigFraction subtract(double num) {
		XLPFraction result = (XLPFraction) this.clone();
		result = result.subtract(valueOf(num));
		return (XLPBigFraction) (returnLowestF ? result.getFraction() : result);
	}

	/**
	 * 分数减法
	 * 
	 * @param num
	 * @return 返回一个新的分数对象
	 */
	public XLPBigFraction subtract(Number num) {
		XLPFraction result = (XLPFraction) this.clone();
		result = result.subtract(valueOf(
				new BigDecimal(num + "").toPlainString(), true));
		return (XLPBigFraction) (returnLowestF ? result.getFraction() : result);
	}

	@Override
	public XLPFraction multiply(XLPFraction fraction) {
		XLPBigFraction bF = toBigFraction(fraction);
		XLPBigFraction result = null;
		if (bF.isMinusOne()) {
			result = (XLPBigFraction) this.negate();
		} else if (bF.isOne()) {
			result = (XLPBigFraction) this.clone();
		} else if (this.isMinusOne()) {
			result = (XLPBigFraction) bF.negate();
		} else if (this.isOne()) {
			result = (XLPBigFraction) bF.clone();
		} else {
			result = new XLPBigFraction();
			result.setNumerator(this.numerator.multiply(bF.numerator));
			result.setDenominator(this.denominator.multiply(bF.denominator));
		}

		return returnLowestF ? result.getFraction() : result;
	}

	/**
	 * 分数乘法
	 * 
	 * @param num
	 * @return 返回一个新的分数对象
	 */
	public XLPBigFraction multiply(long num) {
		XLPFraction result = new XLPBigFraction(num, 1);
		result = result.multiply(this);
		return (XLPBigFraction) (returnLowestF ? result.getFraction() : result);
	}

	/**
	 * 分数乘法
	 * 
	 * @param num
	 * @return 返回一个新的分数对象
	 */
	public XLPBigFraction multiply(double num) {
		XLPFraction result = valueOf(num);
		result = result.multiply(this);
		return (XLPBigFraction) (returnLowestF ? result.getFraction() : result);
	}

	/**
	 * 分数乘法
	 * 
	 * @param num
	 * @return 返回一个新的分数对象
	 */
	public XLPBigFraction multiply(Number num) {
		XLPFraction result = valueOf(new BigDecimal(num + "").toPlainString(),
				true);
		result = result.multiply(this);
		return (XLPBigFraction) (returnLowestF ? result.getFraction() : result);
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
		XLPBigFraction bF = toBigFraction(fraction);
		if (bF.isZero())
			throw new IllegalArgumentException("除数不能为0");
		XLPBigFraction result = null;
		if (bF.isMinusOne()) {
			result = (XLPBigFraction) this.negate();
		} else if (bF.isOne()) {
			result = (XLPBigFraction) this.clone();
		} else if (this.isMinusOne()) {
			result = (XLPBigFraction) new XLPBigFraction(bF.denominator,
					bF.numerator).negate();
		} else if (this.isOne()) {
			result = new XLPBigFraction(bF.denominator, bF.numerator);
		} else {
			result = new XLPBigFraction();
			result.setNumerator(this.numerator.multiply(bF.denominator));
			result.setDenominator(this.denominator.multiply(bF.numerator));
		}
		return returnLowestF ? result.getFraction() : result;

	}

	/**
	 * 分数除法
	 * 
	 * @param num
	 * @return 返回一个新的分数对象
	 * @throws IllegalArgumentException
	 *             假如除数为0，抛出此异常
	 */
	public XLPBigFraction divide(long num) {
		XLPFraction result = (XLPFraction) this.clone();
		result = result.divide(new XLPBigFraction(num, 1l));
		return (XLPBigFraction) (returnLowestF ? result.getFraction() : result);
	}

	/**
	 * 分数除法
	 * 
	 * @param num
	 * @return 返回一个新的分数对象
	 * @throws IllegalArgumentException
	 *             假如除数为0，抛出此异常
	 */
	public XLPBigFraction divide(double num) {
		XLPFraction result = (XLPFraction) this.clone();
		result = result.divide(valueOf(num));
		return (XLPBigFraction) (returnLowestF ? result.getFraction() : result);
	}

	/**
	 * 分数除法
	 * 
	 * @param num
	 * @return 返回一个新的分数对象
	 * @throws IllegalArgumentException
	 *             假如除数为0，抛出此异常
	 */
	public XLPBigFraction divide(Number num) {
		XLPFraction result = (XLPFraction) this.clone();
		result = result.divide(valueOf(
				new BigDecimal(num + "").toPlainString(), true));
		return (XLPBigFraction) (returnLowestF ? result.getFraction() : result);
	}

	@Override
	public XLPFraction negate() {
		XLPBigFraction result = new XLPBigFraction();
		result.setNumerator(this.numerator.negate());
		result.setDenominator(this.denominator);
		return returnLowestF ? result.getFraction() : result;
	}

	/**
	 * 分数取绝对值
	 * 
	 * @return 返回一个新的分数对象
	 */
	@Override
	public XLPFraction abs() {
		XLPFraction result = numerator.compareTo(INTEGER_0) >= 0
				&& denominator.compareTo(INTEGER_0) > 0 ? (XLPFraction) this
				.clone() : negate();
		return returnLowestF ? result.getFraction() : result;
	}

	/**
	 * 计算this的n次幂（n =0,1,2,3,....）
	 * 
	 * @param n
	 *            n次幂
	 * @return this的n次幂, 新的分数对象
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
			result = new XLPBigFraction(numerator.pow(n), denominator.pow(n));
		}
		return returnLowestF ? result.getFraction() : result;
	}

	@Override
	public XLPFraction getFraction() {
		BigInteger des1 = this.numerator.compareTo(INTEGER_0) < 0 ? this.numerator
				.negate() : this.numerator;
		BigInteger mbc = XLPMathUtil
				.getMaxCommonDivisor(des1, this.denominator);
		this.numerator = this.numerator.divide(mbc);
		this.denominator = this.denominator.divide(mbc);
		return this;
	}

	/**
	 * 把浮点数转换成分数对象
	 * 
	 * @param num
	 * @return
	 */
	public static XLPBigFraction valueOf(double num) {
		return valueOf(new BigDecimal(String.valueOf(num)));
	}

	/**
	 * 把浮点数转换成分数对象
	 * 
	 * @param num
	 * @return
	 * @throws IllegalArgumentException
	 *             假如参数为null，抛出此异常
	 */
	public static XLPBigFraction valueOf(BigDecimal num) {
		if (num == null)
			throw new IllegalArgumentException("参数必须不为null");
		String[] split = num.toPlainString().split("\\.");
		return valueOf0(split);
	}

	/**
	 * 用字符串数组构造分数对象,数组存储的是小数的整数部分与小数部分
	 * 
	 * @param num
	 * @return
	 */
	private static XLPBigFraction valueOf0(String[] num) {
		XLPBigFraction fraction = new XLPBigFraction();
		if (num.length == 1 || INTEGER_0.compareTo(new BigInteger(num[1])) == 0) {
			fraction.setNumerator(new BigInteger(num[0]));
			fraction.setDenominator(INTEGER_1);
		} else {
			int len = num[1].length();
			if ("".equals(num[0])) {
				num[0] = "0";
			}
			BigInteger des1 = new BigInteger(num[0]);
			BigInteger mis1 = new BigInteger(num[1]);
			BigInteger ten = new BigInteger("10");
			fraction.setNumerator(ten.pow(len).multiply(des1).add(mis1));
			fraction.setDenominator(ten.pow(len));
		}
		return fraction;
	}

	/**
	 * 把分数字符串转换成分数对象
	 * 
	 * @param fraction
	 * @return
	 * @throws XLPParamFormatException
	 *             假如参数格式错误，抛出该异常
	 */
	public static XLPBigFraction valueOf(String fraction) {
		if (!XLPVerifedUtil.isFraction(fraction)) {
			throw new XLPParamFormatException("参数格式错误，param:[" + fraction + "]");
		}

		XLPBigFraction result = new XLPBigFraction();
		String[] rs = fraction.trim().split("[/]");
		if (rs.length == 1) {
			result.setNumerator(new BigInteger(rs[0]));
			result.setDenominator(INTEGER_1);
		} else if (new BigInteger(rs[1]).compareTo(INTEGER_0) == 0) {
			throw new XLPParamFormatException("参数格式错误，param:[" + fraction + "]");
		} else {
			result.setNumerator(new BigInteger(rs[0]));
			result.setDenominator(new BigInteger(rs[1]));
		}

		return result;
	}

	/**
	 * 把字符串转换成分数对象
	 * 
	 * @param decimal
	 *            字符串
	 * @param decimalStrToFraction
	 *            标记是否是把小数格式的字符串转换成分数对象，此值设为true，则表示是，false则按分数字符串格式转换
	 * @return
	 * @throws XLPParamFormatException
	 *             假如参数格式错误，抛出该异常
	 */
	public static XLPBigFraction valueOf(String decimal,
			boolean decimalStrToFraction) {
		if (!decimalStrToFraction)
			return valueOf(decimal);
		if (!XLPVerifedUtil.isNumber(decimal))
			throw new XLPParamFormatException("参数格式错误，param:[" + decimal + "]");
		return valueOf0(new BigDecimal(decimal.trim()).toPlainString().split(
				"\\."));
	}

	@Override
	public BigDecimal decimalVlaue(int accuracy) {
		if (accuracy < 0)
			accuracy = 0;
		return new BigDecimal(numerator.toString()).divide(new BigDecimal(
				denominator.toString()), accuracy, RoundingMode.HALF_UP);
	}

	@Override
	public BigDecimal decimalVlaue() {
		return new BigDecimal(numerator.toString()).divide(new BigDecimal(
				denominator.toString()));
	}

	@Override
	public Object getFM() {
		return denominator;
	}

	@Override
	public Object getFZ() {
		return numerator;
	}

	/**
	 * 把给定的分数对象转换成<code>XLPBigFraction</code>对象
	 * 
	 * @param fraction
	 * @return
	 */
	public static XLPBigFraction toBigFraction(XLPFraction fraction) {
		if (fraction == null)
			return null;
		if (fraction instanceof XLPBigFraction)
			return (XLPBigFraction) fraction;
		return new XLPBigFraction(fraction);
	}
}
