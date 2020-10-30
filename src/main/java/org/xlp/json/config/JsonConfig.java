package org.xlp.json.config;

/**
 * json数据转换的配置类
 * 
 * @author 徐龙平
 * 
 * @version 1.0
 */
public class JsonConfig {
	//时间转换格式
	private DateFormatConfig dateFormatConfig = new DateFormatConfig();
	//数字转换格式
	private NumberConfig numberConfig = new NumberConfig();
	//特殊字符处理对象
	private SpecialCharacterConfig specialCharacterConfig = 
			new SpecialCharacterConfig();
	//空值转换格式配置对象
	private NullConfig nullConfig = new NullConfig();
	
	public JsonConfig() {
	}

	public JsonConfig(DateFormatConfig dateFormatConfig,
			NumberConfig numberConfig,
			SpecialCharacterConfig specialCharacterConfig,
			NullConfig nullConfig) {
		setDateFormatConfig(dateFormatConfig);
		setNumberConfig(numberConfig);
		setSpecialCharacterConfig(specialCharacterConfig);
		setNullConfig(nullConfig); 
	}

	public JsonConfig(NullConfig nullConfig) {
		setNullConfig(nullConfig);
	}

	public JsonConfig(DateFormatConfig dateFormatConfig) {
		setDateFormatConfig(dateFormatConfig); 
	}

	public JsonConfig(NumberConfig numberConfig) {
		setNumberConfig(numberConfig);
	}

	public JsonConfig(SpecialCharacterConfig specialCharacterConfig) {
		setSpecialCharacterConfig(specialCharacterConfig); 
	}

	public void setDateFormatConfig(DateFormatConfig dateFormatConfig) {
		if(dateFormatConfig != null)
			this.dateFormatConfig = dateFormatConfig;
	}

	public DateFormatConfig getDateFormatConfig() {
		return dateFormatConfig;
	}

	public void setNumberConfig(NumberConfig numberConfig) {
		if(numberConfig != null)
			this.numberConfig = numberConfig;
	}

	public NumberConfig getNumberConfig() {
		return numberConfig;
	}

	public void setSpecialCharacterConfig(SpecialCharacterConfig specialCharacterConfig) {
		if(specialCharacterConfig != null) 
			this.specialCharacterConfig = specialCharacterConfig;
	}

	public SpecialCharacterConfig getSpecialCharacterConfig() {
		return specialCharacterConfig;
	}

	public void setNullConfig(NullConfig nullConfig) {
		if(nullConfig != null)
			this.nullConfig = nullConfig;
	}

	public NullConfig getNullConfig() {
		return nullConfig;
	}
}
