package cn.bizspring.cloud.common.core.util;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * Utils - 安全
 * 
 */
public final class SecurityUtils {

	/**
	 * RSA密钥签名算法
	 */
	public static final String RSA_KEY_ALGORITHM = "RSA";

	/**
	 * 安全服务提供者
	 */
	public static final Provider PROVIDER = new BouncyCastleProvider();


	/**
	 * 获取用户
	 */
	public static String getUsername()
	{
		String username = ServletUtils.getRequest().getHeader("userName");
		return ServletUtils.urlDecode(username);
	}

	/**
	 * 获取用户ID
	 */
	public static String getUserId()
	{
		return String.valueOf(ServletUtils.getRequest().getHeader("UserId"));
	}

	/**
	 * 获取请求token
	 */
	public static String getToken()
	{
		return getToken(ServletUtils.getRequest());
	}

	/**
	 * 根据request获取请求token
	 */
	public static String getToken(HttpServletRequest request)
	{
		String token = ServletUtils.getRequest().getHeader("Authorization");
		if (StringUtils.isNotEmpty(token) && token.startsWith("Bearer"))
		{
			token = token.replace("Bearer", "");
		}
		return token;
	}

	/**
	 * 是否为管理员
	 *
	 * @param userId 用户ID
	 * @return 结果
	 */
	public static boolean isAdmin(Long userId)
	{
		return userId != null && 1L == userId;
	}

	/**
	 * 不可实例化
	 */
	private SecurityUtils() {
	}

	/**
	 * 生成私钥
	 * 
	 * @param encodedKey
	 *            密钥编码
	 * @param algorithm
	 *            签名算法
	 * @return 私钥
	 */
	public static PrivateKey generatePrivateKey(byte[] encodedKey, String algorithm) {
		try {
			KeyFactory keyFactory = KeyFactory.getInstance(algorithm, PROVIDER);
			return keyFactory.generatePrivate(new PKCS8EncodedKeySpec(encodedKey));
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e.getMessage(), e);
		} catch (InvalidKeySpecException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	/**
	 * 生成私钥
	 * 
	 * @param keyString
	 *            密钥字符串(BASE64编码)
	 * @param algorithm
	 *            签名算法
	 * @return 私钥
	 */
	public static PrivateKey generatePrivateKey(String keyString, String algorithm) {
		return generatePrivateKey(Base64.decodeBase64(keyString), algorithm);
	}

	/**
	 * 生成公钥
	 * 
	 * @param encodedKey
	 *            密钥编码
	 * @param algorithm
	 *            签名算法
	 * @return 公钥
	 */
	public static PublicKey generatePublicKey(byte[] encodedKey, String algorithm) {
		try {
			KeyFactory keyFactory = KeyFactory.getInstance(algorithm, PROVIDER);
			return keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e.getMessage(), e);
		} catch (InvalidKeySpecException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	/**
	 * 获取密钥字符串
	 * 
	 * @param key
	 *            密钥
	 * @return 密钥字符串(BASE64编码)
	 */
	public static String getKeyString(Key key) {
		return Base64.encodeBase64String(key.getEncoded());
	}

	/**
	 * 获取加密密钥和证书的存储容器
	 * 
	 * @param type
	 *            类型
	 * @param inputStream
	 *            输入流
	 * @param password
	 *            密码
	 * @return 加密密钥和证书的存储容器
	 */
	public static KeyStore getKeyStore(String type, InputStream inputStream, String password) {
		try {
			KeyStore keyStore = KeyStore.getInstance(type, PROVIDER);
			keyStore.load(inputStream, password != null ? password.toCharArray() : null);
			return keyStore;
		} catch (KeyStoreException e) {
			throw new RuntimeException(e.getMessage(), e);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e.getMessage(), e);
		} catch (CertificateException e) {
			throw new RuntimeException(e.getMessage(), e);
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	/**
	 * 获取密钥
	 * 
	 * @param type
	 *            类型
	 * @param inputStream
	 *            输入流
	 * @param password
	 *            密码
	 * @return 密钥
	 */
	public static Key getKey(String type, InputStream inputStream, String password) {
		try {
			KeyStore keyStore = KeyStore.getInstance(type, PROVIDER);
			keyStore.load(inputStream, password != null ? password.toCharArray() : null);
			String alias = keyStore.aliases().hasMoreElements() ? keyStore.aliases().nextElement() : null;
			return keyStore.getKey(alias, password != null ? password.toCharArray() : null);
		} catch (KeyStoreException e) {
			throw new RuntimeException(e.getMessage(), e);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e.getMessage(), e);
		} catch (CertificateException e) {
			throw new RuntimeException(e.getMessage(), e);
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		} catch (UnrecoverableKeyException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	/**
	 * 生成签名
	 * 
	 * @param algorithm
	 *            签名算法
	 * @param privateKey
	 *            私钥
	 * @param data
	 *            数据
	 * @return 签名
	 */
	public static byte[] sign(String algorithm, PrivateKey privateKey, byte[] data) {
		try {
			Signature signature = Signature.getInstance(algorithm, PROVIDER);
			signature.initSign(privateKey);
			signature.update(data);
			return signature.sign();
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e.getMessage(), e);
		} catch (InvalidKeyException e) {
			throw new RuntimeException(e.getMessage(), e);
		} catch (SignatureException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	/**
	 * 加密
	 * 
	 * @param key
	 *            密钥
	 * @param data
	 *            数据
	 * @param algorithm
	 *            签名算法
	 * @param algorithmParameterSpec
	 *            加密参数
	 * @return 密文
	 */
	public static byte[] encrypt(Key key, byte[] data, String algorithm, AlgorithmParameterSpec algorithmParameterSpec) {
		try {
			Cipher cipher = Cipher.getInstance(algorithm, PROVIDER);
			cipher.init(Cipher.ENCRYPT_MODE, key, algorithmParameterSpec);
			return cipher.doFinal(data);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e.getMessage(), e);
		} catch (NoSuchPaddingException e) {
			throw new RuntimeException(e.getMessage(), e);
		} catch (InvalidKeyException e) {
			throw new RuntimeException(e.getMessage(), e);
		} catch (IllegalBlockSizeException e) {
			throw new RuntimeException(e.getMessage(), e);
		} catch (BadPaddingException e) {
			throw new RuntimeException(e.getMessage(), e);
		} catch (InvalidAlgorithmParameterException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	/**
	 * 解密
	 * 
	 * @param key
	 *            密钥
	 * @param data
	 *            数据
	 * @param algorithm
	 *            签名算法
	 * @param algorithmParameterSpec
	 *            加密参数
	 * @return 明文
	 */
	public static byte[] decrypt(Key key, byte[] data, String algorithm, AlgorithmParameterSpec algorithmParameterSpec) {
		try {
			Cipher cipher = Cipher.getInstance(algorithm, PROVIDER);
			cipher.init(Cipher.DECRYPT_MODE, key, algorithmParameterSpec);
			return cipher.doFinal(data);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e.getMessage(), e);
		} catch (NoSuchPaddingException e) {
			throw new RuntimeException(e.getMessage(), e);
		} catch (InvalidKeyException e) {
			throw new RuntimeException(e.getMessage(), e);
		} catch (IllegalBlockSizeException e) {
			throw new RuntimeException(e.getMessage(), e);
		} catch (BadPaddingException e) {
			throw new RuntimeException(e.getMessage(), e);
		} catch (InvalidAlgorithmParameterException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

}