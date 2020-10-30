package org.xlp.utils.snumber;

import java.util.ArrayList;
import java.util.List;

import org.xlp.utils.XLPStringUtil;

/**
 * @version 1.0
 * @author 徐龙平
 *         <p>
 *         2017-4-29
 *         </p>
 * 
 *         判断数字是否是特殊数字的工具类。
 *         <p>
 *         此类主要功能有： 判断数字是否是水仙花数，回文数，素数，三角形数
 */
public class XLPSpecialNumericUtil {
	/**
	 * 判断指定数字是否是回文数
	 * 
	 * @param num
	 *            要判断的数字
	 * @return 如果该数字是回文数返回true,否则返回false
	 * @throws IllegalArgumentException
	 *             假如传进的参数小于0时，抛出此异常
	 */
	public static boolean isPlalindrome(long num) {
		if (num < 0)
			throw new IllegalArgumentException("传进的数字必须大于或等于0");
		return XLPStringUtil.isPlalindrome(String.valueOf(num));
	}

	/**
	 * 判断指定数字是否是水仙花数。
	 * <p>
	 * 水仙花数是指一个 n 位正整数 ( n≥3 )，它的每个位上的数字的 n 次幂之和等于它本身。（例如：1^3 + 5^3+ 3^3 = 153）。
	 * 
	 * @param num
	 *            要判断的数字
	 * @return 如果该数字是水仙花数返回true,否则返回false
	 * @throws IllegalArgumentException
	 *             假如传进的参数<=100时，抛出此异常
	 */
	public static boolean isNarcissus(long num) {
		if (num <= 100)
			throw new IllegalArgumentException("传进的数字必须大于100");
		// 存储各个位上的数字
		List<Integer> ns = new ArrayList<Integer>();
		long param = num;
		while (num >= 10) {// 拆分数字得到各个位上的数字
			ns.add((int) (num % 10));
			num /= 10;
		}
		ns.add((int) num);
		int size = ns.size(); // 得到参数的位数

		long sum = 0;// 和
		for (Integer n : ns) {
			sum += Math.pow(n, size);
		}

		if (sum == param)
			return true;
		return false;
	}

	/**
	 * 判断指定数字是否是四叶玫瑰数(3个)。
	 * <p>
	 * 四叶玫瑰数是指一个 4 位正整数,它的每个位上的数字的4次幂之和等于它本身。（例如：1^4 + 6^4+ 3^4 + 4^4 = 1634）。
	 * 
	 * @param num
	 *            要判断的数字
	 * @return 如果该数字是四叶玫瑰数返回true,否则返回false
	 * @throws IllegalArgumentException
	 *             假如传进的参数<=1000 || > 9999时，抛出此异常
	 */
	public static boolean isFourRosesNum(long num) {
		if (num <= 1000 || num > 9999)
			throw new IllegalArgumentException("传进的数字必须大于1000并且小于10000");
		return isNarcissus(num);
	}

	/**
	 * 判断指定数字是否是五角星数（3个）。
	 * <p>
	 * 五角星数是指一个 5 位正整数,它的每个位上的数字的5次幂之和等于它本身。（例如：5^5 + 4^5+ 7^5 + 4^5 + 8^5 =
	 * 54748）。
	 * 
	 * @param num
	 *            要判断的数字
	 * @return 如果该数字是五角星数返回true,否则返回false
	 * @throws IllegalArgumentException
	 *             假如传进的参数<=10000 || > 99999时，抛出此异常
	 */
	public static boolean isStarsNum(long num) {
		if (num <= 10000 || num > 99999)
			throw new IllegalArgumentException("传进的数字必须大于10000并且小于100000");
		return isNarcissus(num);
	}

	/**
	 * 判断指定数字是否是六合数（1个）。
	 * <p>
	 * 六合数是指一个 6 位正整数,它的每个位上的数字的6次幂之和等于它本身。（例如：5^6 + 4^6+ 8^6 + 8^6 + 3^6 + 4^6
	 * = 548834）。
	 * 
	 * @param num
	 *            要判断的数字
	 * @return 如果该数字是六合数返回true,否则返回false
	 * @throws IllegalArgumentException
	 *             假如传进的参数<=100000 || > 999999时，抛出此异常
	 */
	public static boolean isLiuheNum(long num) {
		if (num <= 100000 || num > 999999)
			throw new IllegalArgumentException("传进的数字必须大于100000并且小于1000000");
		return isNarcissus(num);
	}

	/**
	 * 判断指定数字是否是北斗七星数（4个）。
	 * <p>
	 * 北斗七星数是指一个 7 位正整数,它的每个位上的数字的7次幂之和等于它本身。
	 * 
	 * @param num
	 *            要判断的数字
	 * @return 如果该数字是北斗七星数返回true,否则返回false
	 * @throws IllegalArgumentException
	 *             假如传进的参数<=1000000 || > 9999999时，抛出此异常
	 */
	public static boolean isBigDipperNum(long num) {
		if (num <= 1000000 || num > 9999999)
			throw new IllegalArgumentException("传进的数字必须大于1000000并且小于10000000");
		return isNarcissus(num);
	}

	/**
	 * 判断指定数字是否是八仙花数（3个）。
	 * <p>
	 * 八仙花数是指一个 8 位正整数,它的每个位上的数字的8次幂之和等于它本身。
	 * 
	 * @param num
	 *            要判断的数字
	 * @return 如果该数字是八仙花数返回true,否则返回false
	 * @throws IllegalArgumentException
	 *             假如传进的参数<=10000000 || > 99999999时，抛出此异常
	 */
	public static boolean isHydrangeasNum(long num) {
		if (num <= 10000000 || num > 99999999)
			throw new IllegalArgumentException("传进的数字必须大于10000000并且小于100000000");
		return isNarcissus(num);
	}

	/**
	 * 判断指定数字是否是九九重阳数（4个）。
	 * <p>
	 * 九九重阳数是指一个 9 位正整数,它的每个位上的数字的9次幂之和等于它本身。
	 * 
	 * @param num
	 *            要判断的数字
	 * @return 如果该数字是九九重阳数返回true,否则返回false
	 * @throws IllegalArgumentException
	 *             假如传进的参数<=100000000 || > 999999999时，抛出此异常
	 */
	public static boolean isDoubleNinthNum(long num) {
		if (num <= 100000000 || num > 999999999)
			throw new IllegalArgumentException(
					"传进的数字必须大于100,000,000并且小于100,000,000,0");
		return isNarcissus(num);
	}

	/**
	 * 判断指定数字是否是十全十美数（1个）。
	 * <p>
	 * 十全十美数是指一个 10 位正整数,它的每个位上的数字的10次幂之和等于它本身。
	 * 
	 * @param num
	 *            要判断的数字
	 * @return 如果该数字是十全十美数返回true,否则返回false
	 * @throws IllegalArgumentException
	 *             假如传进的参数<=1000000000 || > 9999999999时，抛出此异常
	 */
	public static boolean isExcellentNum(long num) {
		if (num <= 1000000000 || num > 9999999999l)
			throw new IllegalArgumentException(
					"传进的数字必须大于100,000,000,0并且小于100,000,000,00");
		return isNarcissus(num);
	}

	/**
	 * 判断指定数字是否是素数。
	 * 
	 * @param num
	 *            要判断的数字
	 * @return 如果该数字是素数返回true,否则返回false
	 */
	public static boolean isPrimeNum(long num) {
		if (num < 2)
			return false;
		int sqrt = (int) Math.sqrt(num);
		for (int i = 2; i <= sqrt; i++) {
			if (num % i == 0) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 判断指定数字是否是完数。
	 * <p>
	 * 完全数(Perfect
	 * number)，又称完美数或完备数，是一些特殊的自然数。它所有的真因子(即除了自身以外的约数)的和(即因子函数)，恰好等于它本身。
	 * 如果一个数恰好等于它的因子之和，则称该数为"完全数"。
	 * 
	 * @param num
	 *            要判断的数字
	 * @return 如果该数字是完数返回true,否则返回false
	 */
	public static boolean isPerfectNum(long num) {
		if (num <= 1)
			return false;
		return factorSum(num) == num;
	}

	/**
	 * 计算一个正整数真因子(即除了自身以外的约数)的和
	 * 
	 * @param num
	 *            要处理数
	 * @return 这个数的真因子和
	 * @throws IllegalArgumentException
	 *             假如传进去的参数小于1，抛出此异常
	 */
	public static long factorSum(long num) {
		if (num < 1)
			throw new IllegalArgumentException("传进去的参数值必须大于0");
		long sum = 0;
		for (long i = 1; i < num; i++) {
			if (num % i == 0) {
				sum += i;
			}
		}
		return sum;
	}

	/**
	 * 判断指定数字是否是亏数。
	 * <p>
	 * 亏数是:它所有的真因子(即除了自身以外的约数)的和(即因子函数)，小于它本身。
	 * 
	 * @param num
	 *            要判断的数字
	 * @return 如果该数字是亏数返回true,否则返回false
	 */
	public static boolean isDeficiencyNum(long num) {
		if (num <= 1)
			return false;
		return factorSum(num) < num ? true : false;
	}

	/**
	 * 判断指定数字是否是盈数。
	 * <p>
	 * 盈数是:它所有的真因子(即除了自身以外的约数)的和(即因子函数)，大于它本身。
	 * 
	 * @param num
	 *            要判断的数字
	 * @return 如果该数字是盈数返回true,否则返回false
	 */
	public static boolean isAbundanceNum(long num) {
		if (num <= 1)
			return false;
		return factorSum(num) > num ? true : false;
	}

	/**
	 * 判断指定数字是否是三角形数。
	 * <p>
	 * 古希腊科学家把数1，3，6，10，15，21……这些数量的（石子），都可以排成三角形，像这样的数称为三角形数。
	 * <p>
	 * s(n)=n（n+1）/2
	 * 
	 * @param num
	 *            要判断的数字
	 * @return 如果该数字是三角形数返回true,否则返回false
	 */
	public static boolean isTriangleNum(long num) {
		if (num <= 0)
			return false;
		long f = 1;
		for (long i = 1; f <= num; i++) {
			f = i * (i + 1) / 2;
			if (f == num) {
				return true;
			}
		}
		return false;
	}
}
