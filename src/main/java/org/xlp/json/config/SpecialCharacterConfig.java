package org.xlp.json.config;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * json特殊字符配置类配置类
 * 
 * @author 徐龙平
 * 
 * @version 1.0
 */
public class SpecialCharacterConfig {
	/**
	 * 标记是否启用该功能,默认启用
	 */
	private boolean open = true;
	
	/**
	 * 存储转义字符映射关系，key：要转义的字符，value：转义后的字符
	 */
	private static final Map<String, String> ESCAPE_CHARACTER_MAP = new LinkedHashMap<String, String>();
	
	static {
		//转义字符映射关系
		ESCAPE_CHARACTER_MAP.put("\\","\\\\");
		ESCAPE_CHARACTER_MAP.put("\"","\\\"");
		ESCAPE_CHARACTER_MAP.put("\n","\\n");
		ESCAPE_CHARACTER_MAP.put("\r","\\r");
		ESCAPE_CHARACTER_MAP.put("\t","\\t");
	}
	
	public String toString(String original){
		if(original == null)
			return null;
		if(open){
			for (Entry<String, String> entry : ESCAPE_CHARACTER_MAP.entrySet()) {
				original = original.replace(entry.getKey(), entry.getValue());
			}
		}
		return original;
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
		
		if(open){
			for (Entry<String, String> entry : ESCAPE_CHARACTER_MAP.entrySet()) {
				optString = optString.replace(entry.getValue(), entry.getKey());
			}
		}
		return optString; 
	}
	
	public SpecialCharacterConfig() {
	}

	public SpecialCharacterConfig(boolean open) {
		this.open = open;
	}
	
	/**
	 * 添加转义字符映射关系
	 * 
	 * @param soucre 要转义的字符
	 * @param target 转义后的字符
	 */
	public static void add(String soucre, String target) {
		if (soucre != null && target != null) {
			ESCAPE_CHARACTER_MAP.put(soucre, target);
		}
	}
}
