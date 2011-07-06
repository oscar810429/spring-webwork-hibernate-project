/*
 * @(#)AzDGCrypt.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.webapp.security.discuz;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.math.RandomUtils;

/**
 * <p>
 * <a href="AzDGCrypt.java.html"><i>View Source</i></a>
 * </p>
 * 
 * @author Zhang Songfu
 * @version $Id: AzDGCrypt.java 38 2010-06-01 02:18:07Z zhangsf $
 */
public class AzDGCrypt {
	
	public static String encrypt(String key, String input) {
		return encrypt(key, input, null);
	}
	
	public static String encrypt(String key, String input, String charset) {
		byte[] inputBytes = null;
		if (charset != null) {
			try {
				inputBytes = input.getBytes(charset);
			} catch (UnsupportedEncodingException e) {}
		}
		if (inputBytes == null) {
			inputBytes = input.getBytes();
		}
		
		byte[] result = encrypt(key.getBytes(), inputBytes);
		
		return new String(result);
	}
	
	public static String decrypt(String key, String input) {
		return decrypt(key, input, null);
	}
	
	public static String decrypt(String key, String input, String charset) {
		byte[] inputBytes = null;
		
		if (charset != null) {
			try {
				inputBytes = input.getBytes(charset);
			} catch (UnsupportedEncodingException e) {}
		}
		
		if (inputBytes == null) {
			inputBytes = input.getBytes();
		}
		
		byte[] result = decrypt(key.getBytes(), inputBytes);
		
		if (charset != null) {
			try {
				return new String(result, charset);
			} catch (UnsupportedEncodingException e) {}
		}
		
		return new String(result);
	}
	
	public static byte[] encrypt(byte[] key, byte[] input) {
		byte[] encryptKey = DigestUtils.md5Hex(String.valueOf(RandomUtils.nextInt(32000)).getBytes()).getBytes();
		
		byte[] result = new byte[input.length * 2];
		
		for (int i = 0, j = 0, k = 0; i < input.length && k < result.length; i++, j++, k++) {
			if (j == encryptKey.length) j = 0;
			result[k] = encryptKey[j];
			result[++k] = (byte) (input[i] ^ encryptKey[j]);
		}
		
		return Base64.encodeBase64(encodeKey(key, result));
	}
	
	public static byte[] decrypt(byte[] key, byte[] input) {
		byte[] txt = encodeKey(key, Base64.decodeBase64(input));
		
		byte[] result = new byte[txt.length / 2];
		
		for (int i = 0, j = 0; i < result.length && j < txt.length - 1; i++, j++) {
			result[i] = (byte) (txt[j] ^ txt[++j]);
		}
		
		return result;
	}

	
	private static byte[] encodeKey(byte[] key, byte[] input) {
		byte[] encryptKey = DigestUtils.md5Hex(key).getBytes();
		
		byte[] result = new byte[input.length];
		
		for (int i = 0, j = 0; i < input.length; i++, j++) {
			if (j == encryptKey.length) j = 0;
			result[i] = (byte) (input[i] ^ encryptKey[j]);
		}
		
		return result;
	}
	
	public static void main(String[] args) throws Exception {
		String key = "songfu.zhang@gmail.com";
		String source = "username=zola&nickname=zhangsf810429";
		
		//source = URLDecoder.decode(source, "UTF-8");
		
		System.out.println(encrypt(key, source));
		//System.out.println(decrypt(key, encrypt(key, source)));
		System.out.println(decrypt(key, encrypt(key, source, "UTF-8"), "UTF-8"));		
	}
}
