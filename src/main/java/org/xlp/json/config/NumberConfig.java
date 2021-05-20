package org.xlp.json.config;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 数字转换字符串是格式配置
 * 
 * @author 徐龙平
 * 
 * @version 1.0
 */
public class NumberConfig {
	//整形转换字符串是保留的小数位数
	private int intDigit = 0;
	//浮点数保留的小数位数
	private int decimalDigit = 2;
	//浮点数保留取舍方式
	private RoundingMode roundingMode = RoundingMode.HALF_UP;
	//标记是否启用该功能，默认关闭
	private boolean open = false;
	
	public NumberConfig(){
		
	}
	
	public NumberConfig(boolean open){
		this.open = open;
	}
	
	public NumberConfig(int intDigit, int decimalDigit,  
			RoundingMode roundingMode) {
		this(intDigit, decimalDigit, roundingMode, true);
	}

	public NumberConfig(int intDigit, int decimalDigit,  
			RoundingMode roundingMode, boolean open) {
		setIntDigit(intDigit);
		setDecimalDigit(decimalDigit);
		setRoundingMode(roundingMode);
		this.open = open;
	}
	
	/**
	 * 整形转换字符串是保留的小数位数
	 * @param intDigit
	 */
	public void setIntDigit(int intDigit) {
		intDigit = intDigit < 0 ? 0 : intDigit;
		this.intDigit = intDigit;
	}
	
	public int getIntDigit() {
		return intDigit;
	}
	
	/**
	 * 浮点数保留的小数位数
	 * @param decimalDigit
	 */
	public void setDecimalDigit(int decimalDigit) {
		decimalDigit = decimalDigit < 0 ? 0 : decimalDigit;
		this.decimalDigit = decimalDigit;
	}
	
	public int getDecimalDigit() {
		return decimalDigit;
	}

	/**
	 * 浮点数保留取舍方式
	 * @param roundingMode
	 */
	public void setRoundingMode(RoundingMode roundingMode) {
		if(roundingMode != null)
			this.roundingMode = roundingMode;
	}

	public RoundingMode getRoundingMode() {
		return roundingMode;
	}
	
	/**
	 * 把整形数据转换成相对应形式的字符串
	 * 
	 * @param intNumber
	 * @return
	 */
	public String toIntString(Number intNumber){
		if(open){
			StringBuilder sb = new StringBuilder();
			if(intDigit > 0)
				sb.append(".");
			for (int i = 0; i < intDigit; i++) 
				sb.append("0");
			if(intNumber == null)
				return "0" + sb.toString();
			return intNumber.longValue() + sb.toString();
		}
		if(intNumber == null)
			return null;
		return intNumber.longValue() + "";
	}
	
	/**
	 * 把浮点型形数据转换成相对应形式的字符串
	 * 
	 * @param intNumber
	 * @return
	 */
	public String toDecimalString(Number decimalNumber){
		if(decimalNumber == null){
			if(!open)
				return null;
			StringBuilder sb = new StringBuilder();
			if(decimalDigit > 0)
				sb.append(".");
			for (int i = 0; i < decimalDigit; i++) 
				sb.append("0");
			return "0" + sb.toString();
		}
		if(!open)
			return new BigDecimal(decimalNumber.toString()).toPlainString();
		return new BigDecimal(decimalNumber.toString())
			.setScale(decimalDigit, roundingMode).toPlainString();
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public boolean isOpen() {
		return open;
	}
}
