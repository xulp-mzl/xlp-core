package org.xlp.encryption;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.security.auth.DestroyFailedException;

import org.xlp.assertion.AssertUtils;
import org.xlp.assertion.IllegalObjectException;
import org.xlp.utils.XLPCharsetUtil;
import org.xlp.utils.XLPStringUtil;
import org.xlp.utils.io.XLPIOUtil;

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
	/**
	 * 公钥对象
	 */
	private RSAPublicKey publicKey;

	/**
	 * 私钥对象
	 */
	private RSAPrivateKey privateKey;

	/**
	 * 默认秘钥长度
	 */
	private static final int DEFAULT_KEY_LEN = 1024;

	/**
	 * 加密算法
	 */
	private static final String KEY_ALGORITHM = "RSA";

	/**
	 * 字符串转换字节数组的编码格式
	 */
	private String charsetName = XLPCharsetUtil.UTF8;

	/**
	 * @throws EncryptException
	 *             假如秘钥初始化失败，则抛出该异常
	 */
	public EncryptRSA() throws EncryptException {
		// 初始化密钥对生成器，密钥大小为1024位
		this(DEFAULT_KEY_LEN);
	}

	/**
	 * @param charsetName
	 *            字符串转换字节数组的编码格式
	 * @throws EncryptException
	 *             假如秘钥初始化失败，则抛出该异常
	 */
	public EncryptRSA(String charsetName) throws EncryptException {
		// 初始化密钥对生成器，密钥大小为1024位
		this(DEFAULT_KEY_LEN, charsetName);
	}

	/**
	 * @param length
	 *            密匙长度(length>=512)
	 * @throws EncryptException
	 *             假如秘钥初始化失败，则抛出该异常
	 */
	public EncryptRSA(int length) throws EncryptException {
		this(length, null);
	}

	/**
	 * @param length
	 *            密匙长度(length>=512)
	 * @param charsetName
	 *            字符串转换字节数组的编码格式
	 * @throws EncryptException
	 *             假如秘钥初始化失败，则抛出该异常
	 */
	public EncryptRSA(int length, String charsetName) throws EncryptException {
		if (!XLPStringUtil.isEmpty(charsetName)) {
			this.charsetName = charsetName.trim();
		}

		// KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
		KeyPairGenerator keyPairGen = null;
		try {
			keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
		} catch (NoSuchAlgorithmException e) {
			throw new EncryptException("生成生成公钥和私钥对失败", e);
		}
		// 初始化密钥对生成器，密钥大小为length位
		keyPairGen.initialize(length);
		// 生成一个密钥对，保存在keyPair中
		KeyPair keyPair = keyPairGen.generateKeyPair();
		privateKey = (RSAPrivateKey) keyPair.getPrivate();
		publicKey = (RSAPublicKey) keyPair.getPublic();
	}

	/**
	 * 加密
	 * 
	 * @param str
	 *            要加密的字符串
	 * 
	 * @return 加密后的字符串(通过base64转码后的字符串), 如str=null，返回null
	 * @throws EncryptException
	 *             假如加密失败抛出该异常
	 */
	public String encryptByPublicKey(String str) throws EncryptException {
		byte[] data = encryptToByteArrayPublicKey(str);
		return data == null ? null : Base64.getEncoder().encodeToString(data);
	}

	/**
	 * 加密
	 * 
	 * @param str
	 *            要加密的字符串
	 * 
	 * @return 加密后的字符串, 如str=null，返回null
	 * @throws EncryptException
	 *             假如加密失败抛出该异常
	 */
	public byte[] encryptToByteArrayPublicKey(String str) throws EncryptException {
		if (publicKey != null && str != null) {
			return encrypt(str, publicKey);
		}
		return null;
	}

	/**
	 * 加密
	 * 
	 * @param str
	 *            要加密的字符串
	 * 
	 * @return 加密后的字符串(通过base64转码后的字符串), 如str=null，返回null
	 * @throws EncryptException
	 *             假如加密失败抛出该异常
	 */
	public String encryptByPrivateKey(String str) throws EncryptException {
		byte[] data = encryptToByteArrayPrivateKey(str);
		return data == null ? null : Base64.getEncoder().encodeToString(data);
	}

	/**
	 * 加密
	 * 
	 * @param str
	 *            要加密的字符串
	 * 
	 * @return 加密后的字符串, 如str=null，返回null
	 * @throws EncryptException
	 *             假如加密失败抛出该异常
	 */
	public byte[] encryptToByteArrayPrivateKey(String str) throws EncryptException {
		if (privateKey != null && str != null) {
			return encrypt(str, privateKey);
		}
		return null;
	}

	/**
	 * 加密字符串
	 * 
	 * @param str
	 * @param key
	 * @return
	 * @throws EncryptException
	 *             假如加密失败抛出该异常
	 */
	private byte[] encrypt(String str, Key key) throws EncryptException {
		// Cipher负责完成加密或解密工作，基于RSA
		try {
			Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
			// 根据公钥，对Cipher对象进行初始化
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] resultBytes = cipher.doFinal(str.getBytes(charsetName));
			return resultBytes;
		} catch (Exception e) {
			throw new EncryptException("加密字符串失败", e);
		}
	}

	/**
	 * 解密
	 * 
	 * @param encrytStr
	 *            要解密的字符串(通过base64转码形成的字符串)
	 * 
	 * @return 解密后的字符串, 如encrytStr=null，返回null
	 * @throws EncryptException
	 *             假如解密失败抛出该异常
	 */
	public String decryptByPrivateKey(String encrytStr) throws EncryptException {
		if (encrytStr != null) {
			byte[] srcBytes = Base64.getDecoder().decode(encrytStr);
			return decryptByPrivateKey(srcBytes);
		}
		return null;
	}

	/**
	 * 解密
	 * 
	 * @param encrytBytes
	 *            要解密的字节数组
	 * 
	 * @return 解密后的字符串, 如encrytBytes=null，返回null
	 * @throws EncryptException
	 *             假如解密失败抛出该异常
	 */
	public String decryptByPrivateKey(byte[] encrytBytes) throws EncryptException {
		if (privateKey != null && encrytBytes != null) {
			return decrypt(encrytBytes, privateKey);
		}
		return null;
	}

	/**
	 * 解密
	 * 
	 * @param encrytStr
	 *            要解密的字符串(通过base64转码形成的字符串)
	 * 
	 * @return 解密后的字符串, 如encrytStr=null，返回null
	 * @throws EncryptException
	 *             假如解密失败抛出该异常
	 */
	public String decryptByPublicKey(String encrytStr) throws EncryptException {
		if (encrytStr != null) {
			byte[] srcBytes = Base64.getDecoder().decode(encrytStr);
			return decryptByPublicKey(srcBytes);
		}
		return null;
	}

	/**
	 * 解密
	 * 
	 * @param encrytBytes
	 *            要解密的字节数组
	 * 
	 * @return 解密后的字符串, 如encrytBytes=null，返回null
	 * @throws EncryptException
	 *             假如解密失败抛出该异常
	 */
	public String decryptByPublicKey(byte[] encrytBytes) throws EncryptException {
		if (publicKey != null && encrytBytes != null) {
			return decrypt(encrytBytes, publicKey);
		}
		return null;
	}

	/**
	 * 解密
	 * 
	 * @param encrytBytes
	 *            要解密的字节数组
	 * @param key
	 * 
	 * @return 解密后的字符串
	 * @throws EncryptException
	 *             假如解密失败抛出该异常
	 */
	private String decrypt(byte[] encrytBytes, Key key) throws EncryptException {
		try {
			// Cipher负责完成加密或解密工作，基于RSA
			Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
			// 根据公钥，对Cipher对象进行初始化
			cipher.init(Cipher.DECRYPT_MODE, key);
			byte[] resultBytes = cipher.doFinal(encrytBytes);
			return new String(resultBytes, charsetName);
		} catch (Exception e) {
			throw new EncryptException("解密失败", e);
		}
	}

	/**
	 * 获取公钥对象
	 * 
	 * @return
	 */
	public RSAPublicKey getPublicKey() {
		return publicKey;
	}

	/**
	 * 获取私钥对象
	 * 
	 * @return
	 */
	public RSAPrivateKey getPrivateKey() {
		return privateKey;
	}

	/**
	 * 获取公钥字节数组
	 * 
	 * @return
	 */
	public byte[] publicKeyToByteArray() {
		return publicKey.getEncoded();
	}

	/**
	 * 获取私钥字节数组
	 * 
	 * @return
	 */
	public byte[] privateKeyToByteArray() {
		return privateKey.getEncoded();
	}

	/**
	 * 销毁私钥
	 */
	public void destoryPrivateKey() {
		if (privateKey != null) {
			try {
				privateKey.destroy();
			} catch (DestroyFailedException e) {
			}
		}
	}

	/**
	 * 把私钥写入输出流中
	 * 
	 * @param outputStream
	 * @throws EncryptException
	 *             假如私钥写入输出流中失败，则抛出该异常
	 * @throws NullPointerException
	 *             假如参数为null，则抛出该异常
	 */
	public void privateKeyToOutputStream(OutputStream outputStream) throws EncryptException {
		AssertUtils.isNotNull(outputStream, "outputStream param is null!");
		try {
			outputStream.write(privateKeyToByteArray());
		} catch (IOException e) {
			throw new EncryptException(e);
		}
	}

	/**
	 * 把私钥写入指定的文件中
	 * 
	 * @param file
	 * @throws EncryptException
	 *             假如私钥写入指定的文件中失败，则抛出该异常
	 * @throws NullPointerException
	 *             假如参数为null，则抛出该异常
	 * @throws IllegalArgumentException
	 *             假如给定的文件是目录，则抛出该异常
	 */
	public void privateKeyToFile(File file) throws EncryptException {
		AssertUtils.isNotNull(file, "file param is null!");
		mkdirsAndCheckFile(file);
		FileOutputStream outputStream = null;
		try {
			outputStream = new FileOutputStream(file);
			outputStream.write(privateKeyToByteArray());
		} catch (IOException e) {
			throw new EncryptException("秘钥写入指定的文件中失败", e);
		} finally {
			XLPIOUtil.closeOutputStream(outputStream);
		}
	}

	/**
	 * 假如给定文件的父目录不存在，则创建目录，假如给定的文件是目录则抛出IllegalArgumentException异常
	 * 
	 * @param file
	 * @throws IllegalArgumentException
	 *             假如给定的文件是目录,则抛出该异常
	 */
	private void mkdirsAndCheckFile(File file) {
		if (!file.exists()) {
			file.getParentFile().mkdirs();
		}
		if (file.isDirectory()) {
			throw new IllegalArgumentException("给定的文件是目录，秘钥写入失败！");
		}
	}

	/**
	 * 把公钥写入输出流中
	 * 
	 * @param outputStream
	 * @throws EncryptException
	 *             假如公钥获写入输出流中失败，则抛出该异常
	 * @throws NullPointerException
	 *             假如参数为null，则抛出该异常
	 */
	public void publicKeyToOutputStream(OutputStream outputStream) throws EncryptException {
		AssertUtils.isNotNull(outputStream, "outputStream param is null!");
		try {
			outputStream.write(publicKeyToByteArray());
		} catch (IOException e) {
			throw new EncryptException(e);
		}
	}

	/**
	 * 把公钥写入指定的文件中
	 * 
	 * @param file
	 * @throws EncryptException
	 *             假如公钥获写入指定的文件中失败，则抛出该异常
	 * @throws NullPointerException
	 *             假如参数为null，则抛出该异常
	 * @throws IllegalArgumentException
	 *             假如给定的文件是目录，则抛出该异常
	 */
	public void publicKeyToFile(File file) throws EncryptException {
		AssertUtils.isNotNull(file, "file param is null!");
		mkdirsAndCheckFile(file);
		FileOutputStream outputStream = null;
		try {
			outputStream = new FileOutputStream(file);
			outputStream.write(publicKeyToByteArray());
		} catch (IOException e) {
			throw new EncryptException("秘钥写入指定的文件中失败", e);
		} finally {
			XLPIOUtil.closeOutputStream(outputStream);
		}
	}

	/**
	 * 设置公钥
	 * 
	 * @param publicKeyBytes
	 *            公钥字节数组
	 * @throws EncryptException
	 *             假如公钥设置失败，则抛出该异常
	 * @throws NullPointerException
	 *             假如参数为null，则抛出该异常
	 */
	public void setPublicKey(byte[] publicKeyBytes) throws EncryptException {
		AssertUtils.isNotNull(publicKeyBytes, "publicKeyBytes param is null!");
		try {
			// 实例化密钥工厂
			KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
			// 初始化公钥
			// 密钥材料转换
			X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(publicKeyBytes);
			// 产生公钥
			publicKey = (RSAPublicKey) keyFactory.generatePublic(x509KeySpec);
		} catch (NoSuchAlgorithmException e) {
			throw new EncryptException("加密方式无效，公钥初始化失败", e);
		} catch (InvalidKeySpecException e) {
			throw new EncryptException("公钥初始化失败", e);
		}
	}

	/**
	 * 设置公钥
	 * 
	 * @param base64PublicKey
	 *            公钥字节数组通过base64转码形成的字符串
	 * @throws EncryptException
	 *             假如公钥设置失败，则抛出该异常
	 * @throws NullPointerException
	 *             假如参数为null或空，则抛出该异常
	 */
	public void setPublicKey(String base64PublicKey) throws EncryptException {
		AssertUtils.isNotNull(base64PublicKey, "base64PublicKey param is null or empty!");
		setPublicKey(Base64.getDecoder().decode(base64PublicKey));
	}

	/**
	 * 设置私钥
	 * 
	 * @param privateKeyBytes
	 *            私钥字节数组
	 * @throws EncryptException
	 *             假如私钥设置失败，则抛出该异常
	 * @throws NullPointerException
	 *             假如参数为null，则抛出该异常
	 */
	public void setPrivateKey(byte[] privateKeyBytes) throws EncryptException {
		AssertUtils.isNotNull(privateKeyBytes, "privateKeyBytes param is null!");
		try {
			// 取得私钥
			PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
			KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
			// 生成私钥
			privateKey = (RSAPrivateKey) keyFactory.generatePrivate(pkcs8KeySpec);
		} catch (NoSuchAlgorithmException e) {
			throw new EncryptException("加密方式无效，私钥初始化失败", e);
		} catch (InvalidKeySpecException e) {
			throw new EncryptException("私钥初始化失败", e);
		}
	}

	/**
	 * 设置私钥
	 * 
	 * @param inputStream
	 *            私钥输入流
	 * @throws EncryptException
	 *             假如私钥设置失败，则抛出该异常
	 * @throws NullPointerException
	 *             假如参数为null，则抛出该异常
	 */
	public void setPrivateKey(InputStream inputStream) throws EncryptException {
		AssertUtils.isNotNull(inputStream, "inputStream param is null!");
		try {
			setPrivateKey(XLPIOUtil.IOToByteArray(inputStream, false));
		} catch (IOException e) {
			throw new EncryptException("根据输入流私钥生成失败", e);
		}
	}

	/**
	 * 设置私钥
	 * 
	 * @param inputFile
	 *            私钥存储文件
	 * @throws EncryptException
	 *             假如私钥设置失败，则抛出该异常
	 * @throws NullPointerException
	 *             假如参数为null，则抛出该异常
	 * @throws IllegalObjectException
	 *             假如给定的文件是目录或不存在，则抛出该异常
	 */
	public void setPrivateKey(File inputFile) throws EncryptException {
		AssertUtils.assertFile(inputFile);
		try {
			setPrivateKey(XLPIOUtil.IOToByteArray(inputFile));
		} catch (IOException e) {
			throw new EncryptException("根据输入文件私钥生成失败", e);
		}
	}

	/**
	 * 设置私钥
	 * 
	 * @param base64PublicKey
	 *            私钥字节数组通过base64转码形成的字符串
	 * @throws EncryptException
	 *             假如私钥设置失败，则抛出该异常
	 * @throws NullPointerException
	 *             假如参数为null或空，则抛出该异常
	 */
	public void setPrivateKey(String base64PrivateKey) throws EncryptException {
		AssertUtils.isNotNull(base64PrivateKey, "base64PrivateKey param is null or empty!");
		setPrivateKey(Base64.getDecoder().decode(base64PrivateKey));
	}

	/**
	 * 设置公钥
	 * 
	 * @param inputStream
	 *            公钥输入流
	 * @throws EncryptException
	 *             假如公钥设置失败，则抛出该异常
	 * @throws NullPointerException
	 *             假如参数为null，则抛出该异常
	 */
	public void setPublicKey(InputStream inputStream) throws EncryptException {
		AssertUtils.isNotNull(inputStream, "inputStream param is null!");
		try {
			setPublicKey(XLPIOUtil.IOToByteArray(inputStream, false));
		} catch (IOException e) {
			throw new EncryptException("根据输入流私钥生成失败", e);
		}
	}

	/**
	 * 设置公钥
	 * 
	 * @param inputFile
	 *            公钥存储文件
	 * @throws EncryptException
	 *             假如公钥设置失败，则抛出该异常
	 * @throws NullPointerException
	 *             假如参数为null或空，则抛出该异常
	 * @throws IllegalObjectException
	 *             假如给定的文件是目录或不存在，则抛出该异常
	 */
	public void setPublicKey(File inputFile) throws EncryptException {
		AssertUtils.assertFile(inputFile);
		try {
			setPublicKey(XLPIOUtil.IOToByteArray(inputFile));
		} catch (IOException e) {
			throw new EncryptException("根据输入文件私钥生成失败", e);
		}
	}

	/**
	 * 解密文件
	 * 
	 * @param srcFile
	 *            要解密的文件
	 * @param destFile
	 *            解密后目标文件
	 * @throws EncryptException
	 *             假如文件解密失败，则抛出该异常
	 * @throws NullPointerException
	 *             假如参数为null，则抛出该异常
	 * @throws IllegalArgumentException
	 *             假如给定的解密后，存放目标文件是目录，则抛出该异常
	 * @throws IllegalObjectException
	 *             假如给定的解密文件是目录或不存在，则抛出该异常
	 */
	public void decryptFileByPrivateKey(File srcFile, File destFile) throws EncryptException {
		decryptFile(srcFile, destFile, privateKey);
	}
	
	/**
	 * 解密文件
	 * 
	 * @param srcFile
	 *            要解密的文件
	 * @param destFile
	 *            解密后目标文件
	 * @throws EncryptException
	 *             假如文件解密失败，则抛出该异常
	 * @throws NullPointerException
	 *             假如参数为null，则抛出该异常
	 * @throws IllegalArgumentException
	 *             假如给定的解密后，存放目标文件是目录，则抛出该异常
	 * @throws IllegalObjectException
	 *             假如给定的解密文件是目录或不存在，则抛出该异常
	 */
	public void decryptFileByPublicKey(File srcFile, File destFile) throws EncryptException {
		decryptFile(srcFile, destFile, publicKey);
	}

	/**
	 * 解密文件
	 * 
	 * @param srcFile
	 *            要解密的文件
	 * @param destFile
	 *            解密后目标文件
	 * @param key
	 * @throws EncryptException
	 *             假如文件解密失败，则抛出该异常
	 * @throws NullPointerException
	 *             假如参数为null，则抛出该异常
	 * @throws IllegalArgumentException
	 *             假如给定的解密后，存放目标文件是目录，则抛出该异常
	 * @throws IllegalObjectException
	 *             假如给定的解密文件是目录或不存在，则抛出该异常
	 */
	protected void decryptFile(File srcFile, File destFile, 
			Key key) throws EncryptException {
		AssertUtils.assertFile(srcFile);
		AssertUtils.isNotNull(destFile, "解密后目标存储文件路径为null！");
		mkdirsAndCheckFile(destFile);
		InputStream in = null;
		OutputStream out = null;
		try {
			in = new FileInputStream(srcFile);
			out = new FileOutputStream(destFile);
			decryptInputStream(in, out, key);
		} catch (IOException e) {
			throw new EncryptException("解密失败", e);
		} finally {
			XLPIOUtil.closeInputStream(in);
			XLPIOUtil.closeOutputStream(out);
		}
	}

	/**
	 * 解密文件流
	 * 
	 * @param srcIn
	 *            要解密的输入流
	 * @param destOut
	 *            解密后数据输出流
	 * @throws EncryptException
	 *             假如解密失败，则抛出该异常
	 * @throws NullPointerException
	 *             假如参数为null，则抛出该异常
	 */
	public void decryptInputStreamByPrivateKey(InputStream srcIn, OutputStream destOut) throws EncryptException {
		decryptInputStream(srcIn, destOut, privateKey);
	}

	/**
	 * 解密文件流
	 * 
	 * @param srcIn
	 *            要解密的输入流
	 * @param destOut
	 *            解密后数据输出流
	 * @throws EncryptException
	 *             假如解密失败，则抛出该异常
	 * @throws NullPointerException
	 *             假如参数为null，则抛出该异常
	 */
	public void decryptInputStreamByPublicKey(InputStream srcIn, OutputStream destOut) throws EncryptException {
		decryptInputStream(srcIn, destOut, publicKey);
	}
	
	/**
	 * 解密文件流
	 * 
	 * @param srcIn
	 *            要解密的输入流
	 * @param destOut
	 *            解密后数据输出流
	 * @param key
	 * @throws EncryptException
	 *             假如解密失败，则抛出该异常
	 * @throws NullPointerException
	 *             假如参数为null，则抛出该异常
	 */
	protected void decryptInputStream(InputStream srcIn, OutputStream destOut, 
			Key key) throws EncryptException {
		AssertUtils.isNotNull(srcIn, "srcIn param is null！");
		AssertUtils.isNotNull(destOut, "destOut param is null！");
		CipherOutputStream cos = null;
		try {
			// 生成Cipher对象,指定其支持的DES算法
			Cipher c = Cipher.getInstance(KEY_ALGORITHM);
			// 根据密钥，对Cipher对象进行初始化，DECRYPT_MODE表示加密模式
			c.init(Cipher.DECRYPT_MODE, key);
			cos = new CipherOutputStream(destOut, c);
			XLPIOUtil.copy(srcIn, cos);
		} catch (Exception e) {
			throw new EncryptException("解密失败", e);
		} finally {
			XLPIOUtil.closeOutputStream(cos);
		}
	}
	
	/**
	 * 加密文件
	 *
	 * @param srcFile
	 *            要加密的文件
	 * @param destFile
	 *            加密后存放的文件
	 * @param key
	 * @throws EncryptException
	 *             假如文件加密失败，则抛出该异常
	 * @throws NullPointerException
	 *             假如参数为null，则抛出该异常
	 * @throws IllegalArgumentException
	 *             假如给定的加密后，存放目标文件是目录，则抛出该异常
	 * @throws IllegalObjectException
	 *             假如给定的加密文件是目录或不存在，则抛出该异常
	 */
	protected void encryptFile(File srcFile, File destFile, Key key) throws EncryptException {
		AssertUtils.assertFile(srcFile);
		AssertUtils.isNotNull(destFile, "加密后目标存储文件路径为null！");
		mkdirsAndCheckFile(destFile);
		InputStream in = null;
		OutputStream out = null;
		try {
			in = new FileInputStream(srcFile);
			out = new FileOutputStream(destFile);
			encryptInputStream(in, out, key);
		} catch (IOException e) {
			throw new EncryptException("加密失败", e);
		} finally {
			XLPIOUtil.closeInputStream(in);
			XLPIOUtil.closeOutputStream(out);
		}
	}

	/**
	 * 加密文件
	 *
	 * @param srcFile
	 *            要加密的文件
	 * @param destFile
	 *            加密后存放的文件
	 * @throws EncryptException
	 *             假如文件加密失败，则抛出该异常
	 * @throws NullPointerException
	 *             假如参数为null，则抛出该异常
	 * @throws IllegalArgumentException
	 *             假如给定的加密后，存放目标文件是目录，则抛出该异常
	 * @throws IllegalObjectException
	 *             假如给定的加密文件是目录或不存在，则抛出该异常
	 */
	public void encryptFileByPublicKey(File srcFile, File destFile) throws EncryptException {
		encryptFile(srcFile, destFile, publicKey); 
	}
	
	/**
	 * 加密文件
	 *
	 * @param srcFile
	 *            要加密的文件
	 * @param destFile
	 *            加密后存放的文件
	 * @throws EncryptException
	 *             假如文件加密失败，则抛出该异常
	 * @throws NullPointerException
	 *             假如参数为null，则抛出该异常
	 * @throws IllegalArgumentException
	 *             假如给定的加密后，存放目标文件是目录，则抛出该异常
	 * @throws IllegalObjectException
	 *             假如给定的加密文件是目录或不存在，则抛出该异常
	 */
	public void encryptFileByPrivateKey(File srcFile, File destFile) throws EncryptException {
		encryptFile(srcFile, destFile, privateKey); 
	}
	
	/**
	 * 加密文件流
	 * 
	 * @param srcIn
	 *            要解密的输入流
	 * @param destOut
	 *            解密后数据输出流
	 * @throws EncryptException
	 *             假如加密失败，则抛出该异常
	 * @throws NullPointerException
	 *             假如参数为null，则抛出该异常
	 */
	public void encryptInputStreamByPrivateKey(InputStream srcIn, OutputStream destOut) throws EncryptException {
		encryptInputStream(srcIn, destOut, privateKey); 
	}
	
	/**
	 * 加密文件流
	 * 
	 * @param srcIn
	 *            要解密的输入流
	 * @param destOut
	 *            解密后数据输出流
	 * @throws EncryptException
	 *             假如加密失败，则抛出该异常
	 * @throws NullPointerException
	 *             假如参数为null，则抛出该异常
	 */
	public void encryptInputStreamByPublicKey(InputStream srcIn, OutputStream destOut) throws EncryptException {
		encryptInputStream(srcIn, destOut, publicKey); 
	}
	
	/**
	 * 加密文件流
	 * 
	 * @param srcIn
	 *            要解密的输入流
	 * @param destOut
	 *            解密后数据输出流
	 * @param key
	 * @throws EncryptException
	 *             假如加密失败，则抛出该异常
	 * @throws NullPointerException
	 *             假如参数为null，则抛出该异常
	 */
	protected void encryptInputStream(InputStream srcIn, OutputStream destOut, 
			Key key) throws EncryptException {
		AssertUtils.isNotNull(srcIn, "srcIn param is null！");
		AssertUtils.isNotNull(destOut, "destOut param is null！");
		CipherInputStream cin = null;
		try {
			// 生成Cipher对象,指定其支持的DES算法
			Cipher c = Cipher.getInstance(KEY_ALGORITHM);
			// 根据密钥，对Cipher对象进行初始化，DECRYPT_MODE表示加密模式
			c.init(Cipher.ENCRYPT_MODE, key);
			cin = new CipherInputStream(srcIn, c);
			XLPIOUtil.copy(cin, destOut);
		} catch (Exception e) {
			throw new EncryptException("解密失败", e);
		} finally {
			XLPIOUtil.closeInputStream(cin);
		}
	}
}
