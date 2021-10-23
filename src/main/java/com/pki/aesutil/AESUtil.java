package com.pki.aesutil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.Scanner;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;import org.bouncycastle.jcajce.provider.symmetric.AES;

import com.fasterxml.jackson.databind.deser.Deserializers.Base;

public class AESUtil {

	private static String ALGORITHM = "AES";

	public static SecretKey getSecretEncryptionKey() throws Exception {
		KeyGenerator generator = KeyGenerator.getInstance(ALGORITHM);
		generator.init(256); // The AES key size in number of bits
		SecretKey secKey = generator.generateKey();
		return secKey;
	}

//	public static byte[] encryptText(String plainText, SecretKey secKey) throws Exception {
//
//		Cipher aesCipher = Cipher.getInstance(ALGORITHM);
//		aesCipher.init(Cipher.ENCRYPT_MODE, secKey);
//		byte[] byteCipherText = aesCipher.doFinal(plainText.getBytes());
//		return byteCipherText;
//	}

//	public static String decryptText(byte[] byteCipherText, String secKey) throws Exception {
//		
//	    // AES defaults to AES/ECB/PKCS5Padding in Java 7
//		SecretKeySpec spec = new SecretKeySpec(secKey.getBytes(), "AES");
//	        Cipher aesCipher = Cipher.getInstance(ALGORITHM);
//	        aesCipher.init(Cipher.DECRYPT_MODE, spec);
//	       // byte[] base64decodedTokenArr = Base64.decodeBase64(byteCipherText);
//	        byte[] bytePlainText = aesCipher.doFinal(byteCipherText);
//	        return new String(bytePlainText);
//	    }
	
	public static void writeToFile(String path, byte[] key) throws IOException {
		File f = new File(path);
		f.getParentFile().mkdirs();

		FileOutputStream fos = new FileOutputStream(f);
		fos.write(key);
		fos.flush();
		fos.close();
	}
	public static SecretKey readSecKey(String filename) throws IOException {
		
		byte []keybyte = new byte[32];
		FileInputStream fin = new FileInputStream(filename);
		fin.read(keybyte);
		SecretKey skey = new SecretKeySpec(keybyte, 0, 32, "AES");
		
		return skey;
		 
	}

	public static byte[] encrypt(String content, String secKey) {
		try {
			KeyGenerator kgen = KeyGenerator.getInstance(ALGORITHM);
			kgen.init(256, new SecureRandom(secKey.getBytes()));
			SecretKey secretKey = kgen.generateKey();
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, ALGORITHM);
			Cipher cipher = Cipher.getInstance(ALGORITHM);// Create a cipher
			byte[] byteContent = content.getBytes("utf-8");
			cipher.init(Cipher.ENCRYPT_MODE, key);// Initialize
			byte[] cipherText = cipher.doFinal(byteContent);
			return cipherText; // encryption
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String decrypt(byte[] content, String seckey) {
		try {
			KeyGenerator kgen = KeyGenerator.getInstance(ALGORITHM);
			kgen.init(256, new SecureRandom(seckey.getBytes()));
			SecretKey secretKey = kgen.generateKey();
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, ALGORITHM);
			Cipher cipher = Cipher.getInstance(ALGORITHM);// Create a cipher
			cipher.init(Cipher.DECRYPT_MODE, key);// Initialize
			byte[] result = cipher.doFinal(content);
			return new String(result); // encryption

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * Convert a binary byte array into readable hex form
	 * 
	 * @param hash
	 * 
	 * @return
	 */
	public static String bytesToHex(byte[] hash) {
		return DatatypeConverter.printHexBinary(hash);
	}

	public static void main(String args[]) throws Exception {

		// call getSecretEncryptionKey() method to generate secret key to encrypt msg

		try (Scanner sc = new Scanner(System.in)) {

			String inputText = "Hello PKI Team";

			//SecretKey secKey = getSecretEncryptionKey();

			//System.out.println("Original Secret key: " + Base64.getEncoder().encodeToString(secKey.getEncoded()));
			
			
			String path = "D:\\seckey".concat("\\secret");
			//AESUtil.writeToFile(path, secKey.getEncoded());
			
			String secretKey = Base64.getEncoder().encodeToString(AESUtil.readSecKey(path).getEncoded());
			
			System.out.println("Secret key: "+secretKey);
			String encryptedText = Base64.getEncoder().encodeToString(AESUtil.encrypt(inputText, secretKey));
//					
			System.out.println("Encrypted Text: "+encryptedText);
//			
			String decryptedText = AESUtil.decrypt(Base64.getDecoder().decode(encryptedText.getBytes()), secretKey);
//			
			System.out.println("Decrypted Text: "+decryptedText);
//			
		}

	}

}
