package com.caitlyn.membersysdemo.util;

import com.caitlyn.membersysdemo.prop.hy.AESUtil;
import com.caitlyn.membersysdemo.prop.hy.Properties;

import java.util.Base64;


public class DecodeUtil {

	/**
	 * 加密方法（前端模擬用）
	 * 1. 判斷是否有鹽值
	 * 2. 密碼 + 鹽值（如果有）
	 * 3. 用 AES 加密
	 * 4. 再用 Base64 編碼
	 */
	public static String encodePassword(String plainPassword, String salt) {
		String toEncrypt;
		if (salt == null || salt.isEmpty()) {
			toEncrypt = plainPassword;
		} else {
			toEncrypt = plainPassword + salt;
		}

		// 先做 AES 加密
		String aesEncrypted = AESUtil.AESEncode(Properties.AESRule, toEncrypt);

		// 再轉成 Base64
		return Base64.getEncoder().encodeToString(aesEncrypted.getBytes());
	}

	/**
	 * 反解密碼（後端驗證用）
	 * 1. Base64 解碼
	 * 2. AES 加密（依規則 + 鹽值）
	 */
	public static String decodePassword(String base64Encoded, String salt) {
		// 1. Base64 解碼成原始密碼字串
		String password = new String(Base64.getDecoder().decode(base64Encoded));

		// 2. 判斷是否有鹽值，決定要不要加在密碼後再加密
		if (salt == null || salt.isEmpty()) {
			return AESUtil.AESEncode(Properties.AESRule, password);
		} else {
			return AESUtil.AESEncode(Properties.AESRule, password + salt);
		}
	}

	public static void main(String[] args) {
		// 測試用明文密碼
		String plainPwd = "123456";
		String salt = "abc";

		// 加密流程（模擬前端傳輸）
		String encodedPwd = encodePassword(plainPwd, salt);
		System.out.println("加密後傳給後端的字串(Base64): " + encodedPwd);

		// 反解流程（後端驗證）
		String decodedPwd = decodePassword(encodedPwd, salt);
		System.out.println("後端反解再加密的結果: " + decodedPwd);
	}

}
