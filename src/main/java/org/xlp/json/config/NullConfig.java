package org.xlp.json.config;


/**
 * null数据转换的配置类
 * 
 * @author 徐龙平
 * 
 * @version 1.0
 */
public class NullConfig {
	//默认空值,除数字意外的null值，其余的null->""
	private String none = "\"\"";
	//标记是否开启null转换
	private boolean open = false;
	
	public NullConfig(String none) {
		this.none = none;
	}

	public NullConfig() {
	}
	
	public NullConfig(String none, boolean open) {
		this.none = none;
		this.setOpen(open);
	}

	public NullConfig(boolean open) {
		this.setOpen(open);
	}

	public void setNone(String none) {
		this.none = none;
	}

	public String getNone() {
		return none;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public boolean isOpen() {
		return open;
	}
	
	public String toString(String string){
		return isOpen() && string == null ? none : string;
	}
}
