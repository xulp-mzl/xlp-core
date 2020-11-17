package org.xlp.encryption;

/**
 * 解密类型
 * 
 * @author xlp
 * @date 2020-05-16
 */
public enum EncryptType {
	DES("DES", 56, 8), 
	DESede_168("DESede", 168, 24), 
	DESede_112("DESede", 112, 24), 
	AES_128("AES", 128, 16), 
	AES_192("AES", 192, 16), 
	AES_256("AES", 256, 16), 
	RC2("RC2", 128, 128), 
	RC4("RC4", 128, 128),
	BLOW_FISH("Blowfish", 128, 56), 
	RIJNDAEL_128("Rijndael", 128, 16), 
	RIJNDAEL_192("Rijndael", 192, 16), 
	RIJNDAEL_256("Rijndael", 256, 16);

	/**
	 * 加密方式
	 */
	private String encryptName;

	/**
	 * 秘钥大小
	 */
	private int keySize;

	/**
	 * 用字符串构造秘钥时，字符串形成的字节数组的大小
	 */
	private int byteLength;

	/**
	 * 构造函数
	 * 
	 * @param encryptName
	 *            加密方式
	 * @param keySize
	 *            秘钥大小
	 * @param byteLength
	 *            用字符串构造秘钥时，字符串形成的字节数组的大小
	 */
	private EncryptType(String encryptName, int keySize, int byteLength) {
		this.encryptName = encryptName;
		this.keySize = keySize;
		this.byteLength = byteLength;
	}

	public String getEncryptName() {
		return encryptName;
	}

	/**
	 * 秘钥长度
	 * 
	 * @return
	 */
	public int getKeySize() {
		return keySize;
	}

	/**
	 * 获取用字符串构造秘钥时，字符串形成的字节数组的大小
	 * 
	 * @return
	 */
	public int getByteLength() {
		return byteLength;
	}
}
