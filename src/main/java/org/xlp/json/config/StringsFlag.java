package org.xlp.json.config;

/**
 * @author 徐龙平
 * 
 * @version 1.0
 * 
 * <p>该类主要功能是判断字符串中的一些特殊字符是否要进行转换,与<code>SpecialCharacterConfig</code>一起使用
 */
class StringsFlag {
	//标记是否进行转换
	private Boolean change = null;
	//要转换的字符串
	private String changeString;

	public StringsFlag(Boolean change, String changeString) {
		this.change = change;
		this.changeString = changeString;
	}

	public void setChange(Boolean change) {
		this.change = change;
	}
	
	public Boolean getChange() {
		return change;
	}

	public void setChangeString(String changeString) {
		this.changeString = changeString;
	}

	public String getChangeString() {
		return changeString;
	}
}
