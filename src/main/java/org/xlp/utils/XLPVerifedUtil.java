package org.xlp.utils;

/**
 * @version 1.0
 * @author 徐龙平
 *         <p>
 *         2017-4-29
 *         </p>
 * 
 *         解决验证格式问题的工具类。
 *         <p>
 *         此类主要功能有：验证是否是数字，身份证号码，手机号，邮箱地址，密码格式是否真确等
 */
public class XLPVerifedUtil {
	/**
	 *  身份证位数
	 */
	public final static int IDCARD_NUM_LENGTH = 18;
	
	/**
	 * 小数验证正则表达式
	 */
	public final static String DECIMAL_REGEX = "^[-+]{0,1}(([\\d]*\\.[\\d]+)|([\\d]+\\.[\\d]*)|([\\d]+[\\.][Ee][\\d]+))$";
	
	/**
	 * 非负小数验证正则表达式
	 */
	public final static String UNSIGNED_DECIMAL_REGEX = "^([\\d]*\\.[\\d]+)|([\\d]+\\.[\\d]*)|([\\d]+[\\.][Ee][\\d]+)$";
	
	/**
	 * 实数验证正则表达式
	 */
	public final static String REAL_NUMBER_REGEX = "^[-+]{0,1}(([\\d]*\\.[\\d]+)|([\\d]+[\\.]{0,1}[\\d]*)|([\\d]+[\\.]{0,1}[Ee][\\d]+))$";
	
	/**
	 * 非负实数验证正则表达式
	 */
	public final static String UNSIGNED_REAL_NUMBER_REGEX = "([\\d]*\\.[\\d]+)|([\\d]+[\\.]{0,1}[\\d]*)|([\\d]+[\\.]{0,1}[Ee][\\d]+)$";
	
	/**
	 * 邮箱验证正则表达式
	 */
	public final static String EMAIL_REGEX = "^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
	
	/**
	 * 纯小数验证正则表达式
	 */
	public final static String PURE_DECIMAL_REGEX = "^[-+]{0,1}([0]?\\.[\\d]*[1-9]+[\\d]*)$";
	
	/**
	 * 非负纯小数验证正则表达式
	 */
	public final static String UNSIGNED_PURE_DECIMAL_REGEX = "^[0]?\\.[\\d]*[1-9]+[\\d]*$";
	
	/**
	 * 18位身份证简单验证正则表达式
	 */
	public final static String SIMPLE_ID_NO_REGEX = "^\\d{17}[0-9X]$";
	
	/**
	 * 分数验证正则表达式
	 */
	public final static String FRACTION_REGEX = "^[+-]{0,1}([\\d]+|([\\d]+[/][\\d]*[1-9]+[\\d]*))$";
	
	/**
	 * 非负分数验证正则表达式
	 */
	public final static String UNSIGNED_FRACTION_REGEX = "^[\\d]+|([\\d]+[/][\\d]*[1-9]+[\\d]*)$";
	
	/**
	 * 整数验证正则表达式
	 */
	public final static String INT_REGEX = "^[-+]{0,1}[\\d]+$";
	
	/**
	 * 非负整数验证正则表达式
	 */
	public final static String UNSIGNED_INT_REGEX = "^[\\d]+$";
	
	/**
	 * 中文验证正则表达式
	 */
	public final static String CHINESE_REGEX = "^[\u4E00-\u9FA5\uF900-\uFA2D]+$";
	
	/**
	 * 中文验证正则表达式
	 */
	public final static String CHINESE_BETWEEN_LENGTH_REGEX = "^[\u4E00-\u9FA5\uF900-\uFA2D]{%1$d,%2$d}$";
	
	/**
	 * 手机号验证正则表达式
	 */
	public final static String PHONE_REGEX = "^((13[0-9])|(14[57])|(15([0-3]|[5-9]))|(18[0-9])|(17[678]))\\d{8}$";
	
	/**
	 * 数字，下划线，字母组成字符串验证正则表达式
	 */
	public final static String NUMBER_UNDERLINE_CASE_BETWEEN_LENGTH_REGEX = "^[a-zA-Z_0-9]{%1$d,%2$d}$";
	
	/**
	 * 数字，下划线，字母组成字符串验证正则表达式
	 */
	public final static String NUMBER_UNDERLINE_CASE_REGEX = "^[a-zA-Z_0-9]+$";
	
	/**
	 * qq验证正则表达式
	 */
	public final static String QQ_REGEX = "^[1-9][0-9]{4,9}$";
	
	/**
	 * 下划线
	 */
	public final static String UNDERLINE = "_";
	
	/**
	 * 大写字母正则表达式
	 */
	public final static String LOWER_CASE_REGEX = "[A-Z]";
	
	/**
	 * 小写字母正则表达式
	 */
	public final static String UPPER_CASE_REGEX = "[a-z]";
	
	/**
	 * 数字正则表达式
	 */
	public final static String NUMBER_REGEX = "[0-9]";
	
	/**
	 * 字母正则表达式
	 */
	public final static String CASE_REGEX = "[a-zA-Z]";

	/**
	 * 邮政编码验证正则表达式
	 */
	public final static String POSTAL_CODE_REGEX = "^[0-9]{6}$";
	
	/**
	 * 固定电话验证正则表达式
	 */
	public final static String FIXED_TELEPHONE_REGEX = "^(0\\d{2,3}[-]?)?\\d{3,8}([-]?\\d{3,8})?([-]?\\d{1,7})?$";
	
	/**
	 * ipv4验证正则表达式
	 */
	public final static String IP_V4_REGEX = "^((?:(?:25[0-5]|2[0-4]\\d|[01]?\\d?\\d)\\.){3}(?:25[0-5]|2[0-4]\\d|[01]?\\d?\\d))$";
	
	/**
	 * 数字，下划线，字母最少两种字符组成的字符串验证正则表达式
	 */
	public final static String NUMBER_OR_UNDERLINE_OR_CASE_BETWEEN_LENGTH_REGEX = "^(?=.*[A-Za-z_])(?=.*[0-9])[0-9A-Za-z_]{%1$d,%2$d}|(?=.*[A-Za-z])(?=.*[0-9_])[0-9A-Za-z_]{%1$d,%2$d}$";
	
	/**
	 * 必须包含数字，大小写字母的字符串验证正则表达式
	 */
	public final static String NUMBER_AND_CASE_BETWEEN_LENGTH_REGEX = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])[0-9A-Za-z]{%1$d,%2$d}$";
	
	/**
	 * 必须包含数字，字母和特殊字符（!"#$%&'()*+,-./:;<=>?@[\]^_`空格{|}~）的字符串验证正则表达式
	 */
	public final static String NUMBER_AND_CASE_AND_SPECIAL_CASE_BETWEEN_LENGTH_REGEX = "^(?=.*[A-Za-z])(?=.*[\\p{Punct} ])(?=.*[0-9])[0-9A-Za-z\\p{Punct} ]{%1$d,%2$d}$";
	
	/**
	 * 验证指定的字符串是否是小数包含正负号
	 * 
	 * @param num
	 *            要验证的字符串
	 * @return 假如是小数返回true， 否则返回false
	 */
	public static boolean isDecimal(String num) {
		return (!XLPStringUtil.isEmpty(num) && num.matches(DECIMAL_REGEX));
	}
	
	/**
	 * 验证指定的字符串是否是小数不包含正负号
	 * 
	 * @param num
	 *            要验证的字符串
	 * @return 假如是小数返回true， 否则返回false
	 */
	public static boolean isUnsignedDecimal(String num) {
		return (!XLPStringUtil.isEmpty(num) && num.matches(UNSIGNED_DECIMAL_REGEX));
	}

	/**
	 * 验证指定的字符串是否是实数包含正负号
	 * 
	 * @param num
	 *            要验证的字符串
	 * @return 假如是实数返回true， 否则返回false
	 */
	public static boolean isNumber(String num) {
		return (!XLPStringUtil.isEmpty(num) && num.matches(REAL_NUMBER_REGEX));
	}
	
	/**
	 * 验证指定的字符串是否是实数不包含正负号
	 * 
	 * @param num
	 *            要验证的字符串
	 * @return 假如是实数返回true， 否则返回false
	 */
	public static boolean isUnsignedNumber(String num) {
		return (!XLPStringUtil.isEmpty(num) && num.matches(UNSIGNED_REAL_NUMBER_REGEX));
	}

	/**
	 * 验证指定的字符串是否是纯小数包含正负号
	 * 
	 * @param num
	 *            要验证的字符串
	 * @return 假如是纯小数返回true， 否则返回false
	 */
	public static boolean isPureDecimal(String num) {
		return (!XLPStringUtil.isEmpty(num) && num.matches(PURE_DECIMAL_REGEX));
	}

	/**
	 * 验证指定的字符串是否是纯小数不包含正负号
	 * 
	 * @param num
	 *            要验证的字符串
	 * @return 假如是纯小数返回true， 否则返回false
	 */
	public static boolean isUnsignedPureDecimal(String num) {
		return (!XLPStringUtil.isEmpty(num) && num.matches(UNSIGNED_PURE_DECIMAL_REGEX));
	}
	
	/**
	 * 验证指定的字符串是否是分数格式包含正负号
	 * 
	 * @param num
	 *            要验证的字符串
	 * @return 假如是分数格式返回true， 否则返回false
	 */
	public static boolean isFraction(String num) {
		return (!XLPStringUtil.isNullOrEmpty(num) && num.matches(FRACTION_REGEX));
	}
	
	/**
	 * 验证指定的字符串是否是分数格式包不含正负号
	 * 
	 * @param num
	 *            要验证的字符串
	 * @return 假如是分数格式返回true， 否则返回false
	 */
	public static boolean isUnsignedFraction(String num) {
		return (!XLPStringUtil.isNullOrEmpty(num) && num.matches(UNSIGNED_FRACTION_REGEX ));
	}

	/**
	 * 验证指定的字符串是否是整数格式包含正负号
	 * 
	 * @param num
	 *            要验证的字符串
	 * @return 假如是整数格式返回true， 否则返回false
	 */
	public static boolean isInteger(String num) {
		return (!XLPStringUtil.isNullOrEmpty(num) && num.matches(INT_REGEX));
	}
	
	/**
	 * 验证指定的字符串是否是整数格式包不含正负号
	 * 
	 * @param num
	 *            要验证的字符串
	 * @return 假如是整数格式返回true， 否则返回false
	 */
	public static boolean isUnsignedInteger(String num) {
		return (!XLPStringUtil.isNullOrEmpty(num) && num.matches(UNSIGNED_INT_REGEX));
	}

	/**
	 * 验证身份证格式是否正确
	 * <p>
	 * 1、将前面的身份证号码17位数分别乘以不同的系数。从第一位到第十七位的系数分别为: 7 9 10 5 8 4 2 1 6 3 7 9 10 5 8
	 * 4 2 ;
	 * 
	 * <p>
	 * 2、将这17位数字和系数相乘的结果相加;
	 * 
	 * <p>
	 * 3、用加出来和除以11，看余数是多少;
	 * 
	 * <p>
	 * 4、余数只可能有0 1 2 3 4 5 6 7 8 9 10这11个数字。其分别对应的最后一位身 份证的号码为1 0 X 9 8 7 6 5 4
	 * 3 2;
	 * 
	 * <p>
	 * 5、通过上面得知如果余数是2，就会在身份证的第18位数字上出现罗马数字的Ⅹ。 如果余数是10，身份证的最后一位号码就是2;
	 * 
	 * <p>
	 * 例如:某男性的身份证号码是34052419800101001X。我们要看看这个身份 证是不是合法的身份证。
	 * 
	 * <p>
	 * 首先:我们计算3*7+4*9+0*10+5*5+...+1*2，前17位的乘积和是189
	 * 
	 * <p>
	 * 然后:用189除以11得出的结果是商17余2
	 * 
	 * <p>
	 * 最后:通过对应规则就可以知道余数2对应的数字是x。
	 * <p>
	 * 所以， 这是一个合格的身份证号码。
	 * 
	 * @param id
	 *            省份证号码
	 * @return 假如是返回true，否则返回false，判断时去掉前后空格
	 */
	public static boolean isIdCardNumber(String idNo) {
		boolean isSuccess = !XLPStringUtil.isEmpty(idNo) && idNo.matches(SIMPLE_ID_NO_REGEX);
		if (!isSuccess){
			return isSuccess;
		}
		char[] checkBit = {'1','0','X','9','8','7','6','5','4','3','2'};
		// 7 9 10 5 8 4 2 1 6 3 7 9 10 5 8 4 2 ;
		int[] weight = {7,9,10,5,8,4, 2,1,6,3,7,9,10,5,8,4,2};
		char[] idNoArr = idNo.toCharArray();
		int sum = 0;
		for (int i = 0; i < 17; i++){
			sum += weight[i] * (idNoArr[i] - '0');
		}
		if (idNoArr[17] != checkBit[sum % 11]){
			isSuccess = false;
		}
		return isSuccess;
	}

	/**
	 * 判断是否是邮箱地址
	 * 
	 * @param email
	 * @return 假如是返回true，否则返回false
	 */
	public static boolean isEmail(String email) {
		return (!XLPStringUtil.isEmpty(email) && email.matches(EMAIL_REGEX));
	}

	/**
	 * 判断是否全是中文
	 * 
	 * @param name
	 * @return 假如是返回true，否则返回false
	 */
	public static boolean isChinese(String chinese) {
		return (!XLPStringUtil.isEmpty(chinese) && chinese.matches(CHINESE_REGEX));
	}

	/**
	 * 判断是否由minLength--maxLength个字符组成的汉字字符串
	 * 
	 * @param name
	 * @param minLength
	 * @param maxLength
	 * @return
	 */
	public static boolean isChinese(String chinese, int minLength, int maxLength) {
		if (!XLPStringUtil.isEmpty(chinese) && minLength < maxLength) {
			String chineseRegex = String.format(CHINESE_BETWEEN_LENGTH_REGEX, minLength, maxLength);
			return chinese.matches(chineseRegex);
		}

		return false;
	}

	/**
	 * 判断是否手机号
	 * 
	 * @param phone
	 * @return 假如是返回true，否则返回false
	 */
	public static boolean isPhone(String phone) {
		return !XLPStringUtil.isEmpty(phone) && phone.matches(PHONE_REGEX);
	}

	/**
	 * 判断是否由数字，下划线，字母中至少两种组成
	 * 
	 * @param password
	 *            源字符串
	 * @param minLength
	 *            最小长度
	 * @param maxLength
	 *            最大程度
	 * @return 假如是返回true，否则返回false
	 */
	public static boolean isLegalPassword(String password, int minLength, int maxLength) {
		if (!XLPStringUtil.isEmpty(password) && minLength < maxLength) {
			String regex = String.format(NUMBER_OR_UNDERLINE_OR_CASE_BETWEEN_LENGTH_REGEX, minLength, maxLength);
			return password.matches(regex);
		}
		return false;
	}
	
	/**
	 * 给定的字符串必须含有数字，大小写字母
	 * 
	 * @param s
	 * @param minLength
	 * @param maxLength
	 * @return 假如含有，返回true,否则返回false
	 */
	public static boolean isContainCaseAndNumber(String s, int minLength, int maxLength) {
		if (!XLPStringUtil.isEmpty(s) && minLength < maxLength) {
			String regex = String.format(NUMBER_AND_CASE_BETWEEN_LENGTH_REGEX, minLength, maxLength);
			return s.matches(regex);
		}
		return false;
	}
	
	/**
	 * 给定的字符串必须包含数字，字母和特殊字符（!"#$%&'()*+,-./:;<=>?@[\]^_`空格{|}~）
	 * 
	 * @param s
	 * @param minLength
	 * @param maxLength
	 * @return 假如含有，返回true,否则返回false
	 */
	public static boolean isContainCaseAndNumberAndSpecialCase(String s, int minLength, int maxLength) {
		if (!XLPStringUtil.isEmpty(s) && minLength < maxLength) {
			String regex = String.format(NUMBER_AND_CASE_AND_SPECIAL_CASE_BETWEEN_LENGTH_REGEX, minLength, maxLength);
			return s.matches(regex);
		}
		return false;
	}
	
	/**
	 * 判断是否由数字，下划线，字母中至少两种组成
	 * 
	 * @param password
	 *            源字符串
	 * @return 假如是返回true，否则返回false
	 */
	public static boolean isSimpleString(String string) {
		return !XLPStringUtil.isEmpty(string) && string.matches(NUMBER_UNDERLINE_CASE_REGEX);
	}

	/**
	 * 判断字符串是否包含大写字母
	 * 
	 * @param string
	 * @return 假如是返回true，否则返回false
	 */
	public static boolean isContainUpperCase(String string) {
		return string != null && XLPStringUtil.containSubString(string, UPPER_CASE_REGEX);
	}

	/**
	 * 判断字符串是否包含小写字母
	 * 
	 * @param string
	 * @return 假如是返回true，否则返回false
	 */
	public static boolean isContainLowerCase(String string) {
		return string != null && XLPStringUtil.containSubString(string, LOWER_CASE_REGEX);
	}

	/**
	 * 判断字符串是否包含字母
	 * 
	 * @param string
	 * @return 假如是返回true，否则返回false
	 */
	public static boolean isContainCase(String string) {
		return string != null && XLPStringUtil.containSubString(string, CASE_REGEX);
	}

	/**
	 * 判断字符串是否包含数字
	 * 
	 * @param string
	 * @return 假如是返回true，否则返回false
	 */
	public static boolean isContainNumber(String string) {
		return string != null && XLPStringUtil.containSubString(string, NUMBER_REGEX);
	}

	/**
	 * 判断字符串是否包含下划线
	 * 
	 * @param string
	 * @return 假如是返回true，否则返回false
	 */
	public static boolean isContainUnderline(String string) {
		return string != null && string.contains(UNDERLINE);
	}
	
	/**
	 * 验证是否是qq格式字符串
	 * 
	 * @param qq
	 * @return 假如是返回true，否则返回false
	 */
	public static boolean isQQ(String qq){
		return (!XLPStringUtil.isEmpty(qq) && qq.matches(QQ_REGEX));
	}
	
	/**
	 * 验证是否是邮政编码格式字符串
	 * 
	 * @param postalCode
	 * @return 假如是返回true，否则返回false
	 */
	public static boolean isPostalCode(String postalCode){
		return (!XLPStringUtil.isEmpty(postalCode) && postalCode.matches(POSTAL_CODE_REGEX)
				&& !"000000".equals(postalCode));
	}
	
	/**
	 * 验证是否是固定电话格式字符串
	 * 
	 * @param fixedTelephone
	 * @return 假如是返回true，否则返回false
	 */
	public static boolean isFixedTelephone(String fixedTelephone){
		return (!XLPStringUtil.isEmpty(fixedTelephone) && fixedTelephone.matches(FIXED_TELEPHONE_REGEX));
	}
	
	/**
	 * 验证是否是IPV4格式字符串
	 * 
	 * @param ipv4
	 * @return 假如是返回true，否则返回false
	 */
	public static boolean isIPV4(String ipv4){
		return (!XLPStringUtil.isEmpty(ipv4) && ipv4.matches(IP_V4_REGEX));
	}
}
