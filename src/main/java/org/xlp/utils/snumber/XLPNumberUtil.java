package org.xlp.utils.snumber;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.xlp.utils.XLPVerifedUtil;
import org.xlp.utils.exception.XLPParamFormatException;


/**
 * @version 1.0
 * @author 徐龙平
 *         <p>
 *         2017-4-29
 *         </p>
 * 
 *         解决大数计算问题工具类。
 *         <p>
 *         此类主要功能有：大数据四则运算，以及四舍五入值，得到最简单分数，转换成百分数
 */
public class XLPNumberUtil {
	public final static char PLUS_SIGN = '+';
	public final static char MINUS = '-';
	public final static char TIMES_SIGN = '*';
	public final static char DIVISION_SIGN = '/';

	/**
	 * 对两个大数的大小进行比较
	 * 
	 * @param num1
	 *            数1
	 * @param num2
	 *            数2
	 * @return 假如数1大于数2返回1，假如数1小于数2返回-1，两数相等返回0
	 *         <p>
	 *         特别的：假如两数都为null返回0，假如数1为null数2不为null返回-1，假如数1不为null数2为null返回1
	 */
	public static int compare(Number num1, Number num2) {
		if (num1 == null && num2 == null)
			return 0;
		if (num1 == null)
			return -1;
		if (num2 == null)
			return 1;
		return compare0(String.valueOf(num1), String.valueOf(num2));
	}

	/**
	 * 对两个为数字格式的字符串的大小进行比较
	 * 
	 * @param num1
	 *            字符串1
	 * @param num2
	 *            字符串2
	 * @return 假如字符串1大于字符串2返回1，假如字符串1小于字符串2返回-1，两字符串相等返回0
	 * @throws XLPParamFormatException
	 *             假如字符串1为null或字符串2为null，抛出该异常，假如两字符串不符合数字格式，抛出该异常
	 */
	public static int compare(String num1, String num2) {
		throwParamFormatException(num1, num2);
		return compare0(num1.trim(), num2.trim());
	}

	/**
	 * @param num1
	 * @param num2
	 */
	private static void throwParamFormatException(String num1, String num2) {
		if (!XLPVerifedUtil.isNumber(num1))
			throw new XLPParamFormatException("传进去的参数格式不正确！param: [" + num1
					+ "]");
		if (!XLPVerifedUtil.isNumber(num2))
			throw new XLPParamFormatException("传进去的参数格式不正确！param: [" + num2
					+ "]");
	}

	/**
	 * 对两个为数字格式的字符串的大小进行比较
	 * 
	 * @param num1
	 *            字符串1
	 * @param num2
	 *            字符串2
	 * @return 假如字符串1大于字符串2返回1，假如字符串1小于字符串2返回-1，两字符串相等返回0
	 */
	private static int compare0(String num1, String num2) {
		BigDecimal bg1 = new BigDecimal(num1);
		BigDecimal bg2 = new BigDecimal(num2);
		return bg1.compareTo(bg2);
	}

	/**
	 * 无精度缺失运算
	 * 
	 * @param number1
	 *            操作数1
	 * @param number2
	 *            操作数2
	 * @param operator
	 *            操作符（+|-|/|*）
	 * @return 操作结果Number对象
	 * @throws IllegalArgumentException
	 *             假如操作符不是（+|-|/|*）中的一个，抛出该异常，假如操作数1或操作数2为null，抛出该异常
	 */
	public static Number calculate(Number number1, Number number2, char operator) {
		return calculate(number1, number2, operator, -1);
	}

	/**
	 * 无精度缺失浮点数运算,并返回指定保留小数位数Number
	 * 
	 * @param number1
	 *            操作数1
	 * @param number2
	 *            操作数2
	 * @param operator
	 *            操作符合（+|-|/|*）
	 * @param accuracy
	 *            要保留的小数位数(如不指定保留位数,该参数一般取值为小于0的数)
	 * @return 运算后的Number
	 * @throws IllegalArgumentException
	 *             假如操作符不是（+|-|/|*）中的一个，抛出该异常，假如操作数1或操作数2为null，抛出该异常
	 */
	public static Number calculate(Number number1, Number number2,
			char operator, int accuracy) {
		if (number1 == null || number2 == null)
			throw new IllegalArgumentException("传进去的参数不能为null");
		return calculate0(String.valueOf(number1), String.valueOf(number2),
				operator, accuracy);
	}

	/**
	 * 无精度缺失浮点数运算,并返回指定保留小数位数String
	 * 
	 * @param number1
	 *            操作数1
	 * @param number2
	 *            操作数2
	 * @param operator
	 *            操作符合（+|-|/|*）
	 * @param accuracy
	 *            要保留的小数位数(如不指定保留位数,该参数一般取值为小于0的数)
	 * @return 运算后的String
	 * @throws IllegalArgumentException
	 *             假如操作符不是（+|-|/|*）中的一个，抛出该异常
	 * @throws XLPParamFormatException
	 *             假如字符串1为null或字符串2为null，抛出该异常，假如两字符串不符合数字格式，抛出该异常
	 */
	public static String calculate(String number1, String number2,
			char operator, int accuracy) {
		throwParamFormatException(number1, number2);
		return calculate0(number1.trim(), number2.trim(), operator, accuracy)
				.toString();
	}

	/**
	 * 无精度缺失浮点数运算,并返回指定保留小数位数Number
	 * 
	 * @param number1
	 *            操作数1
	 * @param number2
	 *            操作数2
	 * @param operator
	 *            操作符合（+|-|/|*）
	 * @param accuracy
	 *            要保留的小数位数(如不指定保留位数,该参数一般取值为小于0的数)
	 * @return 运算后的Number
	 * @throws IllegalArgumentException
	 *             假如操作符不是（+|-|/|*）中的一个，抛出该异常
	 */
	private static Number calculate0(String number1, String number2,
			char operator, int accuracy) {
		BigDecimal bg1 = new BigDecimal(number1);
		BigDecimal bg2 = new BigDecimal(number2);
		BigDecimal result = null;
		switch (operator) {
		case PLUS_SIGN:
			result = bg1.add(bg2);
			break;
		case MINUS:
			result = bg1.subtract(bg2);
			break;
		case TIMES_SIGN:
			result = bg1.multiply(bg2);
			break;
		case DIVISION_SIGN:
			result = bg1.divide(bg2, accuracy, RoundingMode.HALF_UP);
			break;
		default:
			throw new IllegalArgumentException("输入的操作符错误");
		}
		if (accuracy < 0) {
			return result;
		} else {
			return result.setScale(accuracy, RoundingMode.HALF_UP);
		}
	}

	/**
	 * 得到指定小数位数的浮点数
	 * 
	 * @param number
	 * @param accuracy
	 *            要保留的小数位数
	 * @return 返回以四舍五入处理后的结果
	 * @throws XLPParamFormatException
	 *             假如要处理的数为null或精度小于0，抛出该异常
	 */
	public static Number getAppointedDecimalDigits(Number number, int accuracy) {
		return getAppointedDecimalDigits(String.valueOf(number), accuracy);
	}

	/**
	 * 得到指定小数位数的浮点数
	 * 
	 * @param number
	 * @param accuracy
	 *            要保留的小数位数
	 * @return 返回以四舍五入处理后的结果
	 * @throws XLPParamFormatException
	 *             假如要处理的字符串为null或精度小于0或字符串格式不正确，抛出该异常
	 */
	public static Number getAppointedDecimalDigits(String number, int accuracy) {
		if (accuracy < 0 || !XLPVerifedUtil.isNumber(number)) {
			throw new XLPParamFormatException("要处理的字符串格式错误或指定保留位数小于0");
		}
		return new BigDecimal(number.trim()).setScale(accuracy,
				RoundingMode.HALF_UP);
	}
}
