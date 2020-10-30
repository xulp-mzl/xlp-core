package org.xlp.utils.snumber;

import java.math.BigDecimal;
import java.text.NumberFormat;

import org.xlp.utils.XLPVerifedUtil;
import org.xlp.utils.exception.XLPParamFormatException;

/**
 * @version 1.0
 * @author 徐龙平
 *         <p>
 *         2017-4-29
 *         </p>
 * 
 *         解决浮点数计算时精度缺失的问题工具类。
 *         <p>
 *         此类主要功能有：浮点数无精度缺失的四则运算，以及四舍五入值，得到最简单分数，转换成百分数
 */
public class XLPDecimalUtil {
	/**
	 * 对两个浮点数的大小进行比较
	 * 
	 * @param num1
	 *            数1
	 * @param num2
	 *            数2
	 * @return 假如数1大于数2返回1，假如数1小于数2返回-1，两数相等返回0
	 */
	public static int compare(double num1, double num2) {
		return XLPNumberUtil.compare(num1, num2);
	}

	/**
	 * 得到指定小数位数的浮点数
	 * <p>
	 * 特别的：无论如何始终小数见后至少有一位小数，假如要保留的小数位数大于已有的小数位数，保持已有的小数位数
	 * <p>
	 * 如果要按实际效果来显示请用<code>XLPDecimalUtil.getDecimalDigitsToStr(...)<code>
	 * 
	 * @param num
	 * @param accuracy
	 *            要保留的小数位数
	 * @return 返回以四舍五入处理后的结果
	 * @throws XLPParamFormatException
	 *             假如精度小于0，抛出该异常
	 */
	public static double getDecimalDigits(double num, int accuracy) {
		return XLPNumberUtil.getAppointedDecimalDigits(num, accuracy)
				.doubleValue();
	}

	/**
	 * 得到指定小数位数的浮点数, 以字符串格式返回
	 * <p>
	 * 按实际效果来显示
	 * 
	 * @param num
	 * @param accuracy
	 *            要保留的小数位数
	 * @return 返回以四舍五入处理后的结果
	 * @throws XLPParamFormatException
	 *             假如精度小于0，抛出该异常
	 */
	public static String getDecimalDigitsToStr(double num, int accuracy) {
		return XLPNumberUtil.getAppointedDecimalDigits(num, accuracy)
				.toString();
	}

	/**
	 * 无精度缺失浮点数运算
	 * 
	 * @param number1
	 *            操作数1
	 * @param number2
	 *            操作数2
	 * @param operator
	 *            操作符（+|-|/|*）
	 * @return 操作结果
	 * @throws IllegalArgumentException
	 *             假如操作符不是（+|-|/|*）中的一个，抛出该异常
	 */
	public static double calculate(double number1, double number2, char operator) {
		return XLPNumberUtil.calculate(number1, number2, operator)
				.doubleValue();
	}

	/**
	 * 无精度缺失浮点数加法运算
	 * 
	 * @param number1
	 *            操作数1
	 * @param number2
	 *            操作数2
	 * @param accuracy
	 *            要保留的小数位数
	 * @return 操作结果,假如要保留的小数位数小于0时，返回保留0位小数后的结果
	 *         <p>
	 *         add(0.3, 0.1,-1) 返回0.0
	 *         <p>
	 *         add(2.5, 0.1,-1) 返回3.0
	 *         <p>
	 *         add(2.5, 0.10,2) 返回2.6 如果要以2.60的格式返回，要用字符串格式处理输出
	 *         <p>
	 *         add(2.5, 0.11,2) 返回2.61
	 */
	public static double add(double number1, double number2, int accuracy) {
		if (accuracy < 0)
			accuracy = 0;
		return XLPNumberUtil.calculate(number1, number2,
				XLPNumberUtil.PLUS_SIGN, accuracy).doubleValue();
	}

	/**
	 * 无精度缺失浮点数减法运算
	 * 
	 * @param number1
	 *            操作数1
	 * @param number2
	 *            操作数2
	 * @param accuracy
	 *            要保留的小数位数
	 * @return 操作结果,假如要保留的小数位数小于0时，返回保留0位小数后的结果
	 *         <p>
	 *         subtract(0.3, 0.1,-1) 返回0.0
	 *         <p>
	 *         subtract(2.5, 0.1,-1) 返回2.0
	 *         <p>
	 *         subtract(2.5, 0.10,2) 返回2.4 如果要以2.40的格式返回，要用字符串格式处理输出
	 *         <p>
	 *         subtract(2.5, 0.11,2) 返回2.39
	 */
	public static double subtract(double number1, double number2, int accuracy) {
		if (accuracy < 0)
			accuracy = 0;
		return XLPNumberUtil.calculate(number1, number2, XLPNumberUtil.MINUS,
				accuracy).doubleValue();
	}

	/**
	 * 无精度缺失浮点数乘法运算
	 * 
	 * @param number1
	 *            操作数1
	 * @param number2
	 *            操作数2
	 * @param accuracy
	 *            要保留的小数位数
	 * @return 操作结果,假如要保留的小数位数小于0时，返回保留0位小数后的结果
	 *         <p>
	 *         multiply(0.3, 0.1,-1) 返回0.0
	 *         <p>
	 *         multiply(2.5, 0.1,-1) 返回0.0
	 *         <p>
	 *         multiply(2.5, 0.10,3) 返回0.25 如果要以0.250的格式返回，要用字符串格式处理输出
	 *         <p>
	 *         multiply(2.5, 0.1,2) 返回0.25
	 */
	public static double multiply(double number1, double number2, int accuracy) {
		if (accuracy < 0)
			accuracy = 0;
		return XLPNumberUtil.calculate(number1, number2,
				XLPNumberUtil.TIMES_SIGN, accuracy).doubleValue();
	}

	/**
	 * 无精度缺失浮点数除法运算
	 * 
	 * @param number1
	 *            操作数1
	 * @param number2
	 *            操作数2
	 * @param accuracy
	 *            要保留的小数位数
	 * @return 操作结果,假如要保留的小数位数小于0时，返回保留0位小数后的结果
	 *         <p>
	 *         divide(0.3, 0.1,-1) 返回3.0
	 *         <p>
	 *         divide(2.5, 1,-1) 返回3.0
	 *         <p>
	 *         divide(2.5, 0.10,3) 返回25 如果要以25.000的格式返回，要用字符串格式处理输出
	 *         <p>
	 *         divide(2.5, 1,2) 返回2.5
	 */
	public static double divide(double number1, double number2, int accuracy) {
		if (accuracy < 0)
			accuracy = 0;
		return XLPNumberUtil.calculate(number1, number2,
				XLPNumberUtil.DIVISION_SIGN, accuracy).doubleValue();
	}

	/**
	 * 小数转换成指定小数位数的百分数
	 * 
	 * @param number
	 *            要处理的数
	 * @param accuracy
	 *            小数位数
	 * @return 百分数,假如要保留的小数位数小于0时，返回保留0位小数后的结果
	 */
	public static String decimalToPercentage(double number, int accuracy) {
		return decimalToPercentage(String.valueOf(number), accuracy);
	}

	/**
	 * 小数转换成指定小数位数的百分数
	 * 
	 * @param number
	 *            要处理的字符串
	 * @param accuracy
	 *            小数位数
	 * @return 百分数,假如要保留的小数位数小于0时，返回保留0位小数后的结果，假如要处理的字符串是整数格式时，accuracy值无效
	 * @throws XLPParamFormatException
	 *             当指定要处理的字符串为null或格式错误时，抛出该异常
	 */
	public static String decimalToPercentage(String number, int accuracy) {
		if (!XLPVerifedUtil.isNumber(number))
			throw new XLPParamFormatException("要处理的字符串格式错误！param：[" + number
					+ "]");
		if (accuracy < 0)
			accuracy = 0;
		// 获取格式化对象
		NumberFormat nt = NumberFormat.getPercentInstance();
		BigDecimal bg = new BigDecimal(number.trim());
		if (bg.doubleValue() == bg.longValue()) {// 假如是整数是重新设置精确度，既不保留小数位数
			accuracy = 0;
		}
		// 设置百分数精确度accuracy即保留accuracy位小数
		nt.setMinimumFractionDigits(accuracy);
		// 最后格式化并输出
		return nt.format(bg);
	}
}
