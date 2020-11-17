package org.xlp.encryption;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.SecureRandom;
import java.security.Security;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.xlp.assertion.AssertUtils;
import org.xlp.assertion.IllegalObjectException;
import org.xlp.utils.XLPCharsetUtil;
import org.xlp.utils.XLPStringUtil;
import org.xlp.utils.io.XLPIOUtil;

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
public class SymmetricEncryption {
	/**
	 * 密钥长度
	 */
	private int keySize = 0;

	/**
	 * 字符串转换字节数组的编码格式
	 */
	private String charsetName = XLPCharsetUtil.UTF8;

	/**
	 * 存储秘钥对象
	 */
	private SecretKey key;

	/**
	 * 加密算法
	 */
	private EncryptType encryptType = EncryptType.DES;

	/**
	 * 默认provider 提供商
	 */
	@SuppressWarnings("restriction")
	public static final Provider DEFAULT_PROVIDER = new com.sun.crypto.provider.SunJCE();

	/**
	 * 构造函数
	 */
	public SymmetricEncryption() {
	}

	/**
	 * 设置加密方式是DES，还是3DES,还是ASE
	 * 
	 * @param encryptType
	 *            其值取（DES|DESede|AES）
	 */
	public SymmetricEncryption(EncryptType encryptType) {
		setEncryptType(encryptType);
	}

	/**
	 * 构造函数
	 * 
	 * @param encryptType
	 *            加密方式
	 * @param provider
	 *            提供商
	 * @param provider
	 */
	public SymmetricEncryption(EncryptType encryptType, Provider provider) {
		this(encryptType);
		if (provider != null) {
			Security.addProvider(provider);
		}
	}

	/**
	 * 构造函数
	 * 
	 * @param provider
	 *            提供商
	 */
	public SymmetricEncryption(Provider provider) {
		this(null, provider);
	}

	/**
	 * 初始化数据
	 * 
	 * @throws EncryptException
	 */
	protected void init() throws EncryptException {
		KeyGenerator keygen = null;
		try {
			keygen = KeyGenerator.getInstance(encryptType.getEncryptName());
		} catch (NoSuchAlgorithmException e) {
			throw new EncryptException("加密方式无效，秘钥生成器初始化失败", e);
		}
		if (keySize == 0) {
			keySize = encryptType.getKeySize();
		}
		keygen.init(keySize, new SecureRandom());
		// 生成随机密钥
		key = keygen.generateKey();
	}

	/**
	 * 获取字符串转换字节数组时用的字符编码
	 * 
	 * @return
	 */
	public String getCharsetName() {
		return charsetName;
	}

	/**
	 * 设置字符串转换字节数组时用的字符编码
	 * 
	 * @param charsetName
	 */
	public void setCharsetName(String charsetName) {
		if (!XLPStringUtil.isEmpty(charsetName)) {
			this.charsetName = charsetName.trim();
		}
	}

	/**
	 * 获取秘钥长度
	 * 
	 * @return
	 */
	public int getKeySize() {
		return keySize > 0 ? keySize : encryptType.getKeySize();
	}

	/**
	 * 设置秘钥长度（该值不同的加密算法，值得取值不同）
	 * 
	 * @param keySize
	 * @throws EncryptException
	 *             假如设置的值小于0，则抛出该异常
	 */
	public void setKeySize(int keySize) throws EncryptException {
		setKeySize(keySize, false);
	}

	/**
	 * 设置秘钥长度（该值不同的加密算法，值得取值不同）
	 * 
	 * @param keySize
	 * @param reInit
	 *            是否重新初始化key
	 * @throws EncryptException
	 *             假如设置的值小于0或初始化key时，则抛出该异常
	 */
	public void setKeySize(int keySize, boolean reInit) throws EncryptException {
		if (keySize < 0) {
			throw new EncryptException("key size must more than zero!");
		}
		this.keySize = keySize;
		if (reInit) {
			init();
		}
	}

	/**
	 * 获取加密方式
	 * 
	 * @return
	 */
	public EncryptType getEncryptType() {
		return encryptType;
	}

	/**
	 * 设置加密方式
	 * 
	 * @param encryptType
	 */
	public void setEncryptType(EncryptType encryptType) {
		if (encryptType != null) {
			this.encryptType = encryptType;
		}
	}

	/**
	 * 对字符串加密
	 * 
	 * @param str
	 *            加密字符串
	 * @return 加密后的字符串（通过base64转码）, 如str=null，返回null
	 * @throws EncryptException
	 *             假如加密失败抛出该异常
	 */
	public String encrytor(String str) throws EncryptException {
		byte[] data = encryptToByteArray(str);
		return data == null ? null : Base64.getEncoder().encodeToString(data);
	}

	/**
	 * 对字符串加密
	 * 
	 * @param str
	 *            加密字符串
	 * @return 加密后的字节数组, 如str=null，返回null
	 * @throws EncryptException
	 *             假如加密失败抛出该异常
	 */
	public byte[] encryptToByteArray(String str) throws EncryptException {
		if (str != null) {
			if (key == null) {
				init();
			}
			try {
				// 生成Cipher对象,指定其支持的DES算法
				Cipher c = Cipher.getInstance(encryptType.getEncryptName());
				// 根据密钥，对Cipher对象进行初始化，ENCRYPT_MODE表示加密模式
				c.init(Cipher.ENCRYPT_MODE, key);
				byte[] src = str.getBytes(charsetName);
				// 加密，结果保存进cipherByte
				byte[] cipherByte = c.doFinal(src);
				return cipherByte;
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
	 *            要解密的字符串（通过base64后的加密字符串）
	 * @return 解密后的字符串， 如encrytStr=null，返回null
	 * @throws EncryptException
	 *             假如解密失败抛出该异常
	 */
	public String decryptor(String encrytStr) throws EncryptException {
		if (encrytStr != null) {
			byte[] buff = Base64.getDecoder().decode(encrytStr);
			return decryptor(buff);
		}
		return null;
	}

	/**
	 * 解密
	 * 
	 * @param encryptBytes
	 *            要解密的字节数组
	 * @return 解密后的字符串， 如encryptBytes=null，返回null
	 * @throws EncryptException
	 *             假如解密失败抛出该异常
	 */
	public String decryptor(byte[] encryptBytes) throws EncryptException {
		if (encryptBytes != null) {
			if (key == null) {
				init();
			}
			try {
				// 生成Cipher对象,指定其支持的DES算法
				Cipher c = Cipher.getInstance(encryptType.getEncryptName());
				// 根据密钥，对Cipher对象进行初始化，DECRYPT_MODE表示加密模式
				c.init(Cipher.DECRYPT_MODE, key);
				byte[] cipherByte = c.doFinal(encryptBytes);
				return new String(cipherByte, charsetName);
			} catch (Exception e) {
				throw new EncryptException("解密失败", e);
			}
		}
		return null;
	}

	/**
	 * 根据给定的字符串，生成key
	 * 
	 * @param strKey
	 * @param charsetName
	 *            字符编码
	 * @throws EncryptException
	 *             假如设置key失败，则抛出该异常
	 * @throws NullPointerException
	 *             假如strKey为null或空，则抛出该异常
	 */
	public void setKey(String strKey, String charsetName) throws EncryptException {
		AssertUtils.isNotNull(strKey, "strKey param is null or empty!");
		charsetName = XLPStringUtil.isEmpty(charsetName) ? this.charsetName : charsetName;
		try {
			setKey(strKey2Bytes(strKey, charsetName));
		} catch (UnsupportedEncodingException e) {
			throw new EncryptException("不支持该字符编码", e);
		}
	}

	/**
	 * 形成秘钥的字符串转换成字节数组
	 * 
	 * @param strKey
	 * @param charsetName
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private byte[] strKey2Bytes(String strKey, String charsetName) throws UnsupportedEncodingException {
		int byteLength = encryptType.getByteLength();
		byte[] tempByteKey = strKey.getBytes(charsetName);
		// Blowfish 的最大秘钥字节数组为56
		if (encryptType == EncryptType.BLOW_FISH) {
			byteLength = tempByteKey.length > 56 ? 56 : tempByteKey.length;
		}
		byte[] byteKey = new byte[byteLength];
		int minLen = Math.min(byteLength, tempByteKey.length);
		for (int i = 0; i < minLen; i++) {
			byteKey[i] = tempByteKey[i];
		}
		return byteKey;
	}

	/**
	 * 根据给定的字符串，生成key
	 * 
	 * @param strKey
	 * @throws EncryptException
	 *             假如设置key失败，则抛出该异常
	 * @throws NullPointerException
	 *             假如strKey为null或空，则抛出该异常
	 */
	public void setKey(String strKey) throws EncryptException {
		setKey(strKey, null);
	}

	/**
	 * 根据给定字节数组，生成key
	 * 
	 * @param keyBytes
	 * @throws NullPointerException
	 *             假如参数为null，则抛出该异常
	 */
	public void setKey(byte[] keyBytes) {
		AssertUtils.isNotNull(keyBytes, "keyBytes param is null!");
		key = new SecretKeySpec(keyBytes, encryptType.getEncryptName());
	}

	/**
	 * 根据给定秘钥输入流，生成key
	 * 
	 * @param inputStream
	 * @throws EncryptException
	 *             假如秘钥生成失败，则抛出该异常
	 * @throws NullPointerException
	 *             假如参数为null，则抛出该异常
	 */
	public void setKey(InputStream inputStream) throws EncryptException {
		AssertUtils.isNotNull(inputStream, "inputStream param is null!");
		try {
			setKey(XLPIOUtil.IOToByteArray(inputStream, false));
		} catch (IOException e) {
			throw new EncryptException("根据输入流秘钥生成失败", e);
		}
	}

	/**
	 * 根据给定秘钥文件，生成key
	 * 
	 * @param inputFile
	 * @throws EncryptException
	 *             假如秘钥生成失败，则抛出该异常
	 * @throws NullPointerException
	 *             假如参数为null，则抛出该异常
	 * @throws IllegalObjectException
	 *             假如给定的文件是目录或不存在，则抛出该异常
	 */
	public void setKey(File inputFile) throws EncryptException {
		AssertUtils.assertFile(inputFile);
		try {
			setKey(XLPIOUtil.IOToByteArray(inputFile));
		} catch (IOException e) {
			throw new EncryptException("根据输入文件秘钥生成失败", e);
		}
	}

	/**
	 * 秘钥经过base64转码后形成的秘钥
	 * 
	 * @param base64Key
	 * @throws NullPointerException
	 *             假如strKey为null或空，则抛出该异常
	 */
	public void setBase64Key(String base64Key) {
		AssertUtils.isNotNull(base64Key, "base64Key param is null or empty!");
		setKey(Base64.getDecoder().decode(base64Key));
	}

	/**
	 * 秘钥通过base64转码成字符串
	 * 
	 * @return
	 * @throws EncryptException
	 *             假如秘钥获取失败，则抛出该异常
	 */
	public String getStrKey() throws EncryptException {
		byte[] keyBytes = getByteArrayKey();
		return Base64.getEncoder().encodeToString(keyBytes);
	}

	/**
	 * 获取秘钥对象
	 * 
	 * @return
	 * @throws EncryptException
	 *             假如秘钥获取失败，则抛出该异常
	 */
	public SecretKey getKey() throws EncryptException {
		if (key == null) {
			init();
		}
		return key;
	}

	/**
	 * 获取秘钥字节数组
	 * 
	 * @return
	 * @throws EncryptException
	 *             假如秘钥获取失败，则抛出该异常
	 */
	public byte[] getByteArrayKey() throws EncryptException {
		return getKey().getEncoded();
	}

	/**
	 * 获取秘钥字符串，通过base64转码后的字符串
	 * 
	 * @return
	 * @throws EncryptException
	 *             假如秘钥获取失败，则抛出该异常
	 */
	public String getBase64Key() throws EncryptException {
		return Base64.getEncoder().encodeToString(getByteArrayKey());
	}

	/**
	 * 把秘钥写入输出流中
	 * 
	 * @param outputStream
	 * @throws EncryptException
	 *             假如秘钥获写入输出流中失败，则抛出该异常
	 * @throws NullPointerException
	 *             假如参数为null，则抛出该异常
	 */
	public void keyToOutputStream(OutputStream outputStream) throws EncryptException {
		AssertUtils.isNotNull(outputStream, "outputStream param is null!");
		try {
			outputStream.write(getByteArrayKey());
		} catch (IOException e) {
			throw new EncryptException(e);
		}
	}

	/**
	 * 把秘钥写入指定的文件中
	 * 
	 * @param file
	 * @throws EncryptException
	 *             假如秘钥获写入指定的文件中失败，则抛出该异常
	 * @throws NullPointerException
	 *             假如参数为null，则抛出该异常
	 * @throws IllegalArgumentException
	 *             假如给定的文件是目录，则抛出该异常
	 */
	public void keyToFile(File file) throws EncryptException {
		AssertUtils.isNotNull(file, "file param is null!");
		mkdirsAndCheckFile(file);
		FileOutputStream outputStream = null;
		try {
			outputStream = new FileOutputStream(file);
			outputStream.write(getByteArrayKey());
		} catch (IOException e) {
			throw new EncryptException("秘钥获写入指定的文件中失败", e);
		} finally {
			XLPIOUtil.closeOutputStream(outputStream);
		}
	}

	/**
	 * 假如给定文件的父目录不存在，则创建目录，假如给定的文件是目录则抛出IllegalArgumentException异常
	 * 
	 * @param file
	 * @throws IllegalArgumentException 假如给定的文件是目录,则抛出该异常
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
	public void decryptFile(File srcFile, File destFile) throws EncryptException {
		AssertUtils.assertFile(srcFile);
		AssertUtils.isNotNull(destFile, "解密后目标存储文件路径为null！");
		mkdirsAndCheckFile(destFile); 
		InputStream in = null;
		OutputStream out = null;
		try {
			in = new FileInputStream(srcFile);
	        out = new FileOutputStream(destFile);
	        decryptInputStream(in, out);  
		} catch (IOException e) {
			throw new EncryptException("解密失败", e);
		}finally {
			XLPIOUtil.closeInputStream(in);
			XLPIOUtil.closeOutputStream(out);
		}
	}

	/**
	 * 解密文件流
	 * 
	 * @param srcIn 要解密的输入流
	 * @param destOut 解密后数据输出流
	 * @throws EncryptException
	 *             假如解密失败，则抛出该异常
	 * @throws NullPointerException
	 *             假如参数为null，则抛出该异常
	 */
	public void decryptInputStream(InputStream srcIn, OutputStream destOut) throws EncryptException {
		AssertUtils.isNotNull(srcIn, "srcIn param is null！");
		AssertUtils.isNotNull(destOut, "destOut param is null！");
		if (key == null) { 
			init();
		}
		CipherOutputStream cos = null;
		try {
			// 生成Cipher对象,指定其支持的DES算法
			Cipher c = Cipher.getInstance(encryptType.getEncryptName());
			// 根据密钥，对Cipher对象进行初始化，DECRYPT_MODE表示加密模式
			c.init(Cipher.DECRYPT_MODE, key);
	        cos = new CipherOutputStream(destOut, c);
	        XLPIOUtil.copy(srcIn, cos);
		} catch (Exception e) {
			throw new EncryptException("解密失败", e);
		}finally {
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
     * @throws EncryptException
	 *             假如文件加密失败，则抛出该异常
	 * @throws NullPointerException
	 *             假如参数为null，则抛出该异常
	 * @throws IllegalArgumentException
	 *             假如给定的加密后，存放目标文件是目录，则抛出该异常
	 * @throws IllegalObjectException
	 *             假如给定的加密文件是目录或不存在，则抛出该异常
     */

    public void encryptFile(File srcFile, File destFile) throws EncryptException {
    	AssertUtils.assertFile(srcFile);
		AssertUtils.isNotNull(destFile, "加密后目标存储文件路径为null！");
		mkdirsAndCheckFile(destFile); 
		InputStream in = null;
		OutputStream out = null;
		try {
			in = new FileInputStream(srcFile);
	        out = new FileOutputStream(destFile);
	        encryptInputStream(in, out);  
		} catch (IOException e) {
			throw new EncryptException("加密失败", e);
		}finally {
			XLPIOUtil.closeInputStream(in);
			XLPIOUtil.closeOutputStream(out);
		}
    }
    
    /**
	 * 加密文件流
	 * 
	 * @param srcIn 要解密的输入流
	 * @param destOut 解密后数据输出流
	 * @throws EncryptException
	 *             假如加密失败，则抛出该异常
	 * @throws NullPointerException
	 *             假如参数为null，则抛出该异常
	 */
	public void encryptInputStream(InputStream srcIn, OutputStream destOut) throws EncryptException {
		AssertUtils.isNotNull(srcIn, "srcIn param is null！");
		AssertUtils.isNotNull(destOut, "destOut param is null！");
		if (key == null) { 
			init();
		}
		CipherInputStream cin = null;
		try {
			// 生成Cipher对象,指定其支持的DES算法
			Cipher c = Cipher.getInstance(encryptType.getEncryptName());
			// 根据密钥，对Cipher对象进行初始化，DECRYPT_MODE表示加密模式
			c.init(Cipher.ENCRYPT_MODE, key);
	        cin = new CipherInputStream(srcIn, c);
	        XLPIOUtil.copy(cin, destOut);
		} catch (Exception e) {
			throw new EncryptException("解密失败", e);
		}finally {
			XLPIOUtil.closeInputStream(cin);
		}
	}
}