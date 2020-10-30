package org.xlp.encryption;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;

import javax.crypto.Cipher;

import org.xlp.utils.XLPStringUtil;

/**
 * RSA 公钥加密算法是1977年由Ron Rivest、Adi Shamirh和LenAdleman在（美国麻省理工学院）开发的。
 * RSA取名来自开发他们三者的名字。RSA是目前最有影响力的公钥加密算法，它能够 抵抗到目前为止已知的
 * 所有密码攻击，已被ISO推荐为公钥数据加密标准。RSA算法基于一个十分简单的数论事实：将两个大素数 相乘十分容易 ，但那时想要对
 * 其乘积进行因式分解却极其困难，因此可以将乘积公开作为加密密钥。
 * 
 * @author 徐龙平
 *         <p>
 *         2017-03-13
 *         </p>
 * @version 1.0
 * 
 */
public class EncryptRSA {
	// 密钥对
	private KeyPair keyPair;
	
	/**
	 * 字符串转换字节数组的编码格式
	 */
	private String charsetName = "UTF-8";

	/**
	 * @throws EncryptException 假如加密失败抛出该异常
	 */
	public EncryptRSA() throws EncryptException {
		// 初始化密钥对生成器，密钥大小为1024位
		this(1024);
	}
	
	/**
	 * @param charsetName 字符串转换字节数组的编码格式
	 * @throws EncryptException 假如加密失败抛出该异常
	 */
	public EncryptRSA(String charsetName) throws EncryptException {
		// 初始化密钥对生成器，密钥大小为1024位
		this(1024, charsetName);
	}


	/**
	 * @param length
	 *            密匙长度(length>=512)
	 * @throws EncryptException 假如加密失败抛出该异常
	 */
	public EncryptRSA(int length) throws EncryptException {
		this(length, null);
	}
	
	/**
	 * @param length
	 *            密匙长度(length>=512)
	 * @param charsetName 字符串转换字节数组的编码格式
	 * @throws EncryptException 假如加密失败抛出该异常
	 */
	public EncryptRSA(int length, String charsetName) throws EncryptException {
		if (!XLPStringUtil.isEmpty(charsetName)) {
			this.charsetName = charsetName;
		}
		
		// KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
		KeyPairGenerator keyPairGen = null;
		try {
			keyPairGen = KeyPairGenerator.getInstance("RSA");
		} catch (NoSuchAlgorithmException e) {
			throw new EncryptException("生成生成公钥和私钥对失败", e);
		}
		// 初始化密钥对生成器，密钥大小为length位
		keyPairGen.initialize(length);
		// 生成一个密钥对，保存在keyPair中
		keyPair = keyPairGen.generateKeyPair();
	}

	/**
	 * 加密
	 * 
	 * @param str
	 *            要加密的字符串
	 * 
	 * @return 加密后的字符串, 如str=null，返回null
	 * @throws EncryptException 假如加密失败抛出该异常
	 */
	public String encrypt(String str) throws EncryptException  {
		// 得到公钥
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();

		if (publicKey != null && str != null) {
			// Cipher负责完成加密或解密工作，基于RSA
			try {
				Cipher cipher = Cipher.getInstance("RSA");
				// 根据公钥，对Cipher对象进行初始化
				cipher.init(Cipher.ENCRYPT_MODE, publicKey);
				byte[] resultBytes = cipher.doFinal(str.getBytes(charsetName));
				return Base64.getEncoder().encodeToString(resultBytes);
			} catch (Exception e) {
				throw new EncryptException("加密字符串失败", e);
			} 
		}
		return null;
	}

	/**
	 * 解密
	 * 
	 * @param encrytStr
	 *            要解密的字符串
	 * 
	 * @return 解密后的字符串, 如encrytStr=null，返回null
	 * @throws EncryptException 假如解密失败抛出该异常
	 */
	public String decrypt(String encrytStr) throws EncryptException {
		// 得到私钥
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
		if (privateKey != null && encrytStr != null) {
			try {
				// Cipher负责完成加密或解密工作，基于RSA
				Cipher cipher = Cipher.getInstance("RSA");
				// 根据公钥，对Cipher对象进行初始化
				cipher.init(Cipher.DECRYPT_MODE, privateKey);
				byte[] srcBytes = Base64.getDecoder().decode(encrytStr);
				byte[] resultBytes = cipher.doFinal(srcBytes);
				return new String(resultBytes, charsetName); 
			} catch (Exception e) {
				throw new EncryptException("解密失败", e);
			} 
		}
		return null;
	}
}
