package org.xlp.utils.snumber;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * @version 1.0
 * @author 徐龙平
 *         <p>
 *         2017-4-29
 *         </p>
 * 
 *         解决数学中一些问题工具类。
 *         <p>
 *         此类主要功能有： 求出一个和数的所有质因数，最小公倍数，最大公约数
 */
public class XLPMathUtil {
	/**
	 * 常量
	 */
	private static final BigInteger INTEGER_VALUE_0 = new BigInteger("0");
	private static final BigInteger INTEGER_VALUE_1 = new BigInteger("1");
	
	
	/**
	 * 求出一个和数的所有质因数
	 * 
	 * @param num
	 *            指定的和数
	 * @return 假如指定的数不是和数返回大小为0的集合，否则返回其质因数的集合
	 */
	public static List<Long> getAllPrimeFactor(long num) {
		List<Long> factors = new ArrayList<Long>();
		if (num <= 1 || XLPSpecialNumericUtil.isPrimeNum(num))
			return factors;
		for (long i = 2; !XLPSpecialNumericUtil.isPrimeNum(num);) {
			if (!XLPSpecialNumericUtil.isPrimeNum(i)) {// 判断数是否是质数
				i++;
				continue;
			}
			if (num % i == 0) {
				num /= i;
				factors.add(i);
			} else {
				++i;
			}
		}
		factors.add(num);
		return factors;
	}

	/**
	 * 求两个正整数的最大公约数
	 * 
	 * @param num1
	 *            数1
	 * @param num2
	 *            数2
	 * @return 返回最大公约数，假如数1为0数2不为0返回数2，假如数2为0数1不为0返回数1
	 * @throws IllegalArgumentException
	 *             假如传进去的数1数2都等于0或数1小于0或数2小于0，抛出该异常
	 */
	public static long getMaxCommonDivisor(long num1, long num2) {
		if (num1 < 0 || num2 < 0 || (num1 == 0 && num2 == 0))
			throw new IllegalArgumentException("传进去的数1数2都等于0或数1小于0或数2小于0");

		if (num1 == num2)
			return num1;
		if (num1 == 1 || num2 == 1)
			return num1 < num2 ? num1 : num2;

		return getMCD(num1, num2);
	}

	/**
	 * 求两个正整数的最大公约数主要实现函数（递归实现）
	 * 
	 * @param num1
	 *            数1
	 * @param num2
	 *            数2
	 * @return 返回最大公约数
	 */
	private static long getMCD(long num1, long num2) {
		if (num1 < num2)
			return getMCD(num2, num1);
		if (num2 == 0)
			return num1;
		if (isEven(num1)) {
			if (isEven(num2)) {
				return getMCD(num1 >> 1, num2 >> 1) << 1;
			}
			return getMCD(num1 >> 1, num2);
		} else {
			if (isEven(num2)) {
				return getMCD(num1, num2 >> 1);
			}
			return getMCD(num2, num1 - num2);
		}
	}

	/**
	 * 判断指定的数是否是偶数
	 * 
	 * @param num
	 * @return
	 */
	public static boolean isEven(long num) {
		return num % 2 == 0;
	}

	/**
	 * 求两个正整数的最小公倍数
	 * 
	 * @param num1
	 *            数1
	 * @param num2
	 *            数2
	 * @return 返回最小公倍数,假如数1或数2小于1，返回0
	 *         <p>
	 *         假如最小公倍数超出<code>Long.MAX_VALUE</code>返回-1
	 */
	public static long getMinCommonMultiple(long num1, long num2) {
		BigInteger mcm = getMinCommonMultipleB(num1, num2);
		return mcm.compareTo(new BigInteger(Long.MAX_VALUE + "")) > 0 ? -1
				: mcm.longValue();
	}

	/**
	 * 求两个正整数的最小公倍数
	 * 
	 * @param num1
	 *            数1
	 * @param num2
	 *            数2
	 * @return 返回最小公倍数,假如数1或数2小于1，返回0
	 */
	public static BigInteger getMinCommonMultipleB(long num1, long num2) {
		BigInteger mcm = null;
		if (num1 < 1 || num2 < 1)
			return mcm = INTEGER_VALUE_0;
		BigInteger product = new BigInteger(num1 + "").multiply(new BigInteger(
				num2 + ""));
		mcm = product.divide(new BigInteger(getMaxCommonDivisor(num1, num2)
				+ ""));
		return mcm;
	}

	/**
	 * 求两个正整数的最小公倍数,以字符串格式返回
	 * 
	 * @param num1
	 *            数1
	 * @param num2
	 *            数2
	 * @return 返回最小公倍数,假如数1或数2小于1，返回0
	 */
	public static String getMinCommonMultipleS(long num1, long num2) {
		return getMinCommonMultipleB(num1, num2).toString();
	}

	/**
	 * 求两个大正整数的最大公约数
	 * 
	 * @param num1
	 *            数1
	 * @param num2
	 *            数2
	 * @return 返回最大公约数，假如数1为0数2不为0返回数2，假如数2为0数1不为0返回数1
	 * @throws IllegalArgumentException
	 *             假如传进去的数1数2都等于0或数1小于0或数2小于0，抛出该异常,假如数1或数2为null，也抛出该异常
	 */
	public static BigInteger getMaxCommonDivisor(BigInteger num1,
			BigInteger num2) {
		BigInteger bg = INTEGER_VALUE_0;
		if (num1 == null || num2 == null || bg.compareTo(num1) > 0
				|| bg.compareTo(num2) > 0
				|| (bg.compareTo(num2) == 0 && bg.compareTo(num1) == 0))
			throw new IllegalArgumentException("传进去的数1数2都等于0或数1小于0或数2小于0或为null");

		if (num1.compareTo(num2) == 0)
			return num1;
		bg = INTEGER_VALUE_1;
		if (num1.compareTo(bg) == 0 || num2.compareTo(bg) == 0)
			return num1.compareTo(num2) < 0 ? num1 : num2;

		return getMCD(num1, num2);
	}

	/**
	 * 求两个大正整数的最大公约数主要实现函数（循环实现）
	 * <p>
	 * 递归实现很容易出现栈溢出异常
	 * 
	 * @param num1
	 *            数1
	 * @param num2
	 *            数2
	 * @return 返回最大公约数
	 */
	private static BigInteger getMCD(BigInteger num1, BigInteger num2) {
		BigInteger temp = null;
		if (num1.compareTo(num2) < 0) {
			temp = num1;
			num1 = num2;
			num2 = temp;
		}

		while (num2.compareTo(INTEGER_VALUE_0) != 0) {
			temp = num1;
			num1 = num2;
			num2 = temp.subtract(num1);
			if (num1.compareTo(num2) < 0) {
				temp = num1;
				num1 = num2;
				num2 = temp;
			}
		}
		return num1;
	}

	/**
	 * 求两个大正整数的最小公倍数
	 * 
	 * @param num1
	 *            数1
	 * @param num2
	 *            数2
	 * @return 返回最小公倍数,假如数1或数2小于1，返回0
	 * @throws IllegalArgumentException
	 *             假如数1或数2为null，也抛出该异常
	 */
	public static BigInteger getMinCommonMultiple(BigInteger num1,
			BigInteger num2) {
		if (num1 == null || num2 == null)
			throw new IllegalArgumentException("参数必须不为null");
		BigInteger mcm = INTEGER_VALUE_1;
		if (num1.compareTo(mcm) < 0 || num2.compareTo(mcm) < 0)
			return mcm = INTEGER_VALUE_0;
		BigInteger product = num1.multiply(num2);
		mcm = product.divide(getMaxCommonDivisor(num1, num2));
		return mcm;
	}
}
