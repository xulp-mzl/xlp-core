package org.xlp.json.config;

import org.xlp.json.utils.JsonUtil;

/**
 * json特殊字符配置类配置类
 * 
 * @author 徐龙平
 * 
 * @version 1.0
 */
public class SpecialCharacterConfig {
	//标记是否启用该功能,默认启用
	private boolean open = true;
	
	private static final String DOUBLE_QUO = "\\\"";
	
	public String toString(String original){
		if(original == null)
			return null;
		return open ? original.replace("\\", "\\\\")
				.replace(JsonUtil.DOUBLE_QUOTES, DOUBLE_QUO) : original; 
	}
	
	/**
	 * 还原字符串原始格式
	 * 
	 * @param optString
	 * @return
	 */
	public String toRawString(String optString){
		if(optString == null)
			return null;
		return open ? optString.replace("\\\\", "\\")
				.replace(DOUBLE_QUO, JsonUtil.DOUBLE_QUOTES) : optString; 
	}
	
	public SpecialCharacterConfig() {
	}

	public SpecialCharacterConfig(boolean open) {
		this.open = open;
	}
}
