package org.xlp.utils;

import java.util.HashMap;
import java.util.Map;

import org.xlp.javabean.convert.XLPMapBeanUtil;
import org.xlp.json.annotation.FieldName;

public class A {
	@FieldName(name="")
	private int a;
	@FieldName(name="B")
	private int isB;
	@FieldName
	private boolean is;
	private boolean isa;
	private boolean c;
	private Boolean isc;
	public int getA() {
		return a;
	}
	public void setA(int a) {
		this.a = a;
	}
	public boolean isIs() {
		return is;
	}
	public void setIs(boolean is) {
		this.is = is;
	}
	public boolean isIsa() {
		return isa;
	}
	public void setIsa(boolean isa) {
		this.isa = isa;
	}
	public boolean isC() {
		return c;
	}
	public void setC(boolean c) {
		this.c = c;
	}
	public Boolean getIsc() {
		return isc;
	}
	public void setIsc(Boolean isc) {
		this.isc = isc;
	}
	public int getIsB() {
		return isB;
	}
	public void setIsB(int isB) {
		this.isB = isB;
	}
	@Override
	public String toString() {
		return "A [a=" + a + ", isB=" + isB + ", is=" + is + ", isa=" + isa + ", c=" + c + ", isc=" + isc
				+ "]";
	}
	
	public static void main(String[] args) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("a", 2);
		map.put("isB", 4);
		map.put("is", true);
		map.put("isa", true);
		map.put("c", true);
		map.put("isc", true);
		System.out.println(XLPMapBeanUtil.mapToBean(A.class, map));
	}
}
