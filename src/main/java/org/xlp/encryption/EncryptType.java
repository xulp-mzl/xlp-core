package org.xlp.encryption;

/**
 * 解密类型
 * 
 * @author xlp
 * @date 2020-05-16
 */
public enum EncryptType {
	DES("DES"), DESede("DESede"), AES("AES");
	
	private String encryptName;
	
	private EncryptType(String encryptName){
		this.encryptName = encryptName;
	}

	public String getEncryptName() {
		return encryptName;
	}
}
