/**  
 * @project_name yjd_comm
 * @Title: SecurityCode.java
 * @Package com.yjd.comm.util
 * @author renbangjie renbangjie@126.com  
 * @date 2014-8-17 上午9:59:14
 * @version V1.0  
 * <b>Copyright (c)</b> 2014医信金融信息服务（上海）有限公司-版权所有<br/>
 */
package cn.springmvc.util;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @ClassName: SecurityCode
 * @author renbangjie renbangjie@126.com
 * @date 2014-8-17 上午9:59:14
 */
public class SecurityCode {

	// 加密算法
	private static final String KEY_ALGORITHM = "DES";
	// 加密类型
	public static final String ECB_CIPHER_ALGORITHM = "DES/ECB/PKCS5Padding";
	public static final String CBC_CIPHER_ALGORITHM = "DES/CBC/PKCS5Padding";

	private static final String BASE_CHECK_CODE = "0123456789abcdefghijklnmopqrstuvwxyz";

	/**
	 * @Title: getRandomCode
	 * @Description: 获取随机数字符串
	 * @param length
	 *            生成字符串的长度
	 * @return String
	 */
	public static String getRandomCode(int length) {
		if (length <= 0) {
			return null;
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			sb.append(BASE_CHECK_CODE.charAt((int) Math.floor(Math.random()
					* BASE_CHECK_CODE.length())));
		}
		return sb.reverse().toString().toUpperCase();
	}

	/**
	 * 生成秘钥
	 * 
	 * @param length
	 *            秘钥长度
	 * @return void
	 * @throws NoSuchAlgorithmException
	 */
	public static byte[] generateByteKey(int length)
			throws NoSuchAlgorithmException {
		KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM);
		kg.init(length);
		SecretKey secretKey = kg.generateKey();
		return secretKey.getEncoded();
	}

	/**
	 * 
	 * @param key
	 * @return Key
	 * @throws Exception
	 */
	public static Key keyConvert(byte[] key) throws Exception {
		DESKeySpec dks = new DESKeySpec(key);
		SecretKeyFactory skf = SecretKeyFactory.getInstance(KEY_ALGORITHM);
		return skf.generateSecret(dks);
	}

	/**
	 * 
	 * @param key
	 * @return Key
	 * @throws Exception
	 */
	public static Key keyConvert(String key) throws Exception {
		return keyConvert(key.getBytes());
	}

	/**
	 * 生成秘钥
	 * 
	 * @param length
	 * @throws Exception
	 * @return Key
	 */
	public static Key generateKey(int length) throws Exception {
		byte[] byteKey = generateByteKey(length);
		return keyConvert(byteKey);
	}

	/**
	 * DES ECB方式加密
	 * 
	 * @param data
	 * @param key
	 * @throws Exception
	 * @return byte[]
	 */
	public static byte[] encryptECB(byte[] data, byte[] key) throws Exception {
		return encrypt(data, keyConvert(key), ECB_CIPHER_ALGORITHM);
	}

	/**
	 * DES ECB方式加密
	 * 
	 * @param data
	 * @param key
	 * @throws Exception
	 * @return byte[]
	 */
	public static byte[] encryptECB(byte[] data, Key key) throws Exception {
		return encrypt(data, key, ECB_CIPHER_ALGORITHM);
	}

	/**
	 * 加密
	 * 
	 * @param data
	 *            待加密数据
	 * @param key
	 *            密钥
	 * @param cipherAlgorithm
	 *            加密算法/工作模式/填充方式
	 * @return byte[] 加密数据
	 * @throws Exception
	 */
	public static byte[] encrypt(byte[] data, byte[] key, String cipherAlgorithm)
			throws Exception {
		return encrypt(data, keyConvert(key), cipherAlgorithm);
	}

	/**
	 * 加密
	 * 
	 * @param data
	 *            待加密数据
	 * @param key
	 *            密钥
	 * @param cipherAlgorithm
	 *            加密算法/工作模式/填充方式
	 * @return byte[] 加密数据
	 * @throws Exception
	 */
	public static byte[] encrypt(byte[] data, Key key, String cipherAlgorithm)
			throws Exception {
		// 实例化
		Cipher cipher = initCipher(cipherAlgorithm, Cipher.ENCRYPT_MODE, key);
		// 执行操作
		return cipher.doFinal(data);
	}

	/**
	 * DES ECB方式解密
	 * 
	 * @param data
	 * @param key
	 * @throws Exception
	 * @return byte[]
	 */
	public static byte[] decryptECB(byte[] data, byte[] key) throws Exception {
		return decrypt(data, keyConvert(key), ECB_CIPHER_ALGORITHM);
	}

	/**
	 * DES ECB方式解密
	 * 
	 * @param data
	 * @param key
	 * @throws Exception
	 * @return byte[]
	 */
	public static byte[] decryptECB(byte[] data, Key key) throws Exception {
		return decrypt(data, key, ECB_CIPHER_ALGORITHM);
	}

	/**
	 * 解密
	 * 
	 * @param data
	 *            待解密数据
	 * @param key
	 *            密钥
	 * @param cipherAlgorithm
	 *            加密算法/工作模式/填充方式
	 * @return byte[] 解密数据
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] data, byte[] key, String cipherAlgorithm)
			throws Exception {
		return decrypt(data, keyConvert(key), cipherAlgorithm);
	}

	/**
	 * 解密
	 * 
	 * @param data
	 *            待解密数据
	 * @param key
	 *            密钥
	 * @param cipherAlgorithm
	 *            加密算法/工作模式/填充方式
	 * @return byte[] 解密数据
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] data, Key key, String cipherAlgorithm)
			throws Exception {
		// 实例化
		Cipher cipher = initCipher(cipherAlgorithm, Cipher.DECRYPT_MODE, key);
		// 执行操作
		return cipher.doFinal(data);
	}

	/**
	 * @param cipherAlgorithm
	 *            密码类型
	 * @param mode
	 *            加密模式或解密模式
	 * @param key
	 *            秘钥
	 * @return Cipher
	 * @throws Exception
	 */
	public static Cipher initCipher(String cipherAlgorithm, int mode, Key key)
			throws Exception {
		// 实例化
		Cipher cipher = Cipher.getInstance(cipherAlgorithm);
		// 使用密钥初始化
		if (CBC_CIPHER_ALGORITHM.equals(cipherAlgorithm)) {
			AlgorithmParameterSpec iv = new IvParameterSpec(
					"12345678".getBytes());
			cipher.init(mode, key, iv);
			return cipher;
		}
		cipher.init(mode, key);
		return cipher;
	}

	/**
	 * @Title: getRandomNumber
	 * @Description: 获取数字验证码
	 * @param length
	 * @return String
	 */
	public static String getRandomNumber(int length) {
		if (length <= 0) {
			return null;
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			sb.append((int) Math.floor(Math.random() * 9 + 1));
		}
		return sb.reverse().toString();
	}

	/**
	 * 根据基数来生成随机码
	 * 
	 * @param base
	 * @param length
	 * @return String
	 */
	public static String getRandomNumber(String base, int length) {
		if (length <= 0) {
			return base;
		}
		length = length - base.length();

		StringBuffer sb = new StringBuffer(base);
		for (int i = 0; i < length; i++) {
			sb.append((int) Math.floor(Math.random() * 10));
		}
		return sb.reverse().toString();
	}

	/**
	 * @Title: passwordMD5
	 * @Description: 密码加盐加密
	 * @param password
	 * @param salt
	 * @return String
	 */
	public static String passwordMD5(String password, String salt) {
		return DigestUtils.md5Hex(password + salt);
	}

	/**
	 * @Title: passwordMD5
	 * @Description: MD5(MD5(password)+salt)
	 * @param password
	 * @param salt
	 * @return String
	 */
	public static String passwordMD5_2(String password, String salt) {
		return DigestUtils.md5Hex(DigestUtils.md5Hex(password) + salt);
	}

	/**
	 * 加密
	 * 
	 * @param data
	 * @return
	 */
	public static String md5(String data) {
		return DigestUtils.md5Hex(data);
	}

}
