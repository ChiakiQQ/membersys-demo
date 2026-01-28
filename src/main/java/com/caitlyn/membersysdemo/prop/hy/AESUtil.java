package com.caitlyn.membersysdemo.prop.hy;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class AESUtil {

	// AES 加解密用的演算法
	private static final String ALGORITHM = "AES";

	/**
	 * AES 加密
	 * @param key 密鑰（長度必須是 16/24/32 位元）
	 * @param data 要加密的明文
	 * @return 加密後的字串（Base64 編碼）
	 */
	public static String AESEncode(String key, String data) {
		try {
			SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), ALGORITHM);
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
			byte[] encryptedBytes = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
			return Base64.getEncoder().encodeToString(encryptedBytes);
		} catch (Exception e) {
			throw new RuntimeException("AES 加密失敗", e);
		}
	}

	/**
	 * AES 解密
	 * @param key 密鑰（長度必須是 16/24/32 位元）
	 * @param encryptedData 已加密的字串（Base64 編碼）
	 * @return 解密後的明文
	 */
	public static String AESDecode(String key, String encryptedData) {
		try {
			SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), ALGORITHM);
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
			byte[] decodedBytes = Base64.getDecoder().decode(encryptedData);
			byte[] decryptedBytes = cipher.doFinal(decodedBytes);
			return new String(decryptedBytes, StandardCharsets.UTF_8);
		} catch (Exception e) {
			throw new RuntimeException("AES 解密失敗", e);
		}
	}

	// 測試用 main
	public static void main(String[] args) {
		String key = "1234567890abcdef"; // 16 位元密鑰
		String data = "hello123";

		String encrypted = AESEncode(key, data);
		System.out.println("加密後(Base64): " + encrypted);

		String decrypted = AESDecode(key, encrypted);
		System.out.println("解密後明文: " + decrypted);
	}
}
