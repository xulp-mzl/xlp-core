package org.xlp.encryption;

import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import org.xlp.utils.XLPStringUtil;

/**
 * DES 算法为密码体制中的对称密码体制，又被成为美国数据加密标准， 是1972年美国IBM公司研制的 对称密码体制加密算法。
 * 
 * 3DES 又称Triple DES，是DES加密算法的一种模式，它使用3条56位的密钥对3DES 数据进行三次加密，比起最初的DES，3DES更为 安全。
 * 
 * AES 密码学中的高级加密标准（Advanced Encryption Standard，AES），又称 高级加密标准
 * Rijndael加密法，是美国联邦政府采用的一种区块加密标准。这个标准用来替代原先的DES
 * 
 * @author 徐龙平
 *         <p>
 *         2017-03-13
 *         </p>
 * @version 1.0
 * 
 */
public class EncryptDES {

	// KeyGenerator 提供对称密钥生成器的功能，支持各种算法
	private KeyGenerator keygen;
	// SecretKey 负责保存对称密钥
	private SecretKey deskey;
	// Cipher负责完成加密或解密工作
	private Cipher c;
	// 该字节数组负责保存加密的结果
	private byte[] cipherByte;
	/**
	 * 字符串转换字节数组的编码格式
	 */
	private String charsetName = "UTF-8";

	public EncryptDES() throws EncryptException {
		this((String)null);
	}
	
	/**
	 * @param charsetName 字符串转换字节数组的编码格式
	 * @throws EncryptException
	 */
	@SuppressWarnings("restriction")
	public EncryptDES(String charsetName) throws EncryptException {
		if (!XLPStringUtil.isEmpty(charsetName)) {
			this.charsetName = charsetName;
		}
		Security.addProvider(new com.sun.crypto.provider.SunJCE());
		try {
			// 实例化支持DES算法的密钥生成器(算法名称命名需按规定，否则抛出异常)
			keygen = KeyGenerator.getInstance("DESede");
			// 生成密钥
			deskey = keygen.generateKey();
			// 生成Cipher对象,指定其支持的DES算法
			c = Cipher.getInstance("DESede");
		} catch (NoSuchAlgorithmException e) {
			throw new EncryptException("生成生成公钥和私钥对失败", e);
		} catch (NoSuchPaddingException e) {
			throw new EncryptException("生成生成公钥和私钥对失败", e);
		}
	}
	
	/**
	 * 设置加密方式是DES，还是3DES,还是ASE
	 * 
	 * @param encryptType
	 *            其值取（DES|DESede|AES）
	 * @throws EncryptException 
	 */
	public EncryptDES(EncryptType encryptType) throws EncryptException{
		this(encryptType, null);
	}
	
	/**
	 * 设置加密方式是DES，还是3DES,还是ASE
	 * 
	 * @param encryptType
	 *            其值取（DES|DESede|AES）
	 * @param charsetName 字符串转换字节数组的编码格式  
	 * @throws EncryptException 
	 */
	@SuppressWarnings("restriction")
	public EncryptDES(EncryptType encryptType, String charsetName) throws EncryptException{
		if (!XLPStringUtil.isEmpty(charsetName)) {
			this.charsetName = charsetName;
		}
		
		Security.addProvider(new com.sun.crypto.provider.SunJCE());
		try {
			// 实例化支持DES算法的密钥生成器(算法名称命名需按规定，否则抛出异常)
			keygen = KeyGenerator.getInstance(encryptType.getEncryptName());
			// 生成密钥
			deskey = keygen.generateKey();
			// 生成Cipher对象,指定其支持的DES算法
			c = Cipher.getInstance(encryptType.getEncryptName());
		} catch (NoSuchAlgorithmException e) {
			throw new EncryptException("生成生成公钥和私钥对失败", e);
		} catch (NoSuchPaddingException e) {
			throw new EncryptException("生成生成公钥和私钥对失败", e);
		}
	}

	/**
	 * 对字符串加密
	 * 
	 * @param str
	 *            加密字符串
	 * @return 加密后的字符串, 如str=null，返回null
	 * @throws EncryptException 假如加密失败抛出该异常
	 */
	public String encrytor(String str) throws EncryptException {
		if (str != null) {
			try {
				// 根据密钥，对Cipher对象进行初始化，ENCRYPT_MODE表示加密模式
				c.init(Cipher.ENCRYPT_MODE, deskey);
				byte[] src = str.getBytes(charsetName);
				// 加密，结果保存进cipherByte
				cipherByte = c.doFinal(src);
				return Base64.getEncoder().encodeToString(cipherByte);
			} catch (Exception e) {
				throw new EncryptException("加密字符串失败", e);
			} 
		}
		return null;
	}

	/**
	 * 对字符串解密
	 * 
	 * @param encrytStr
	 *            要解密的字符串
	 * @return 解密后的字符串， 如encrytStr=null，返回null
	 * @throws EncryptException  假如解密失败抛出该异常
	 */
	public String decryptor(String encrytStr) throws EncryptException {
		if (encrytStr != null) {
			try {
				// 根据密钥，对Cipher对象进行初始化，DECRYPT_MODE表示加密模式
				c.init(Cipher.DECRYPT_MODE, deskey);
				byte[] buff = Base64.getDecoder().decode(encrytStr);
				cipherByte = c.doFinal(buff);
				return new String(cipherByte, charsetName); 
			} catch (Exception e) {
				throw new EncryptException("解密失败", e);
			} 
		}
		return null;
	}
}