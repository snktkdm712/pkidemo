package com.pki.rsautil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;

import com.fasterxml.jackson.databind.deser.Deserializers.Base;
import com.pki.aesutil.*;

public class RSAEncryption {
	
	/*openSSL commands*/
	
	/*
	 * generate rsa private key :  openssl genrsa -out private_key.pem 2048
	 * 
	 * Convert private Key to PKCS#8 format (so Java can read it):-
	 * openssl pkcs8 -topk8 -inform PEM -outform DER -in private_key.pem -out private_key.der -nocrypt
	 * 
	 * Output public key portion in DER format (so Java can read it):-
	 * 
	 * openssl rsa -in private_key.pem -pubout -outform DER -out public_key.der
	 * */

	// encrypt data with public key

	public static byte[] encrypt(PublicKey recPubKey, byte[] secKey) throws IOException, GeneralSecurityException {

		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");

		cipher.init(Cipher.ENCRYPT_MODE, recPubKey);

		return cipher.doFinal(secKey);

	}

	// decrypt data with private key

	public static byte[] decrypt(byte[] data, PrivateKey privateKey) throws NoSuchPaddingException,
			NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		//return new String(cipher.doFinal(data));
		return cipher.doFinal(data);
	}

	// read private key from file

	public static PrivateKey getPrivate(String filename) throws Exception {

		byte[] keyBytes = Files.readAllBytes(Paths.get(filename));

		PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory kf = KeyFactory.getInstance("RSA");
		return kf.generatePrivate(spec);
	}

	// read public key from file

	public static PublicKey getPublic(String filename) throws Exception {
		
		//System.out.println("file "+filename);

		byte[] keyBytes = Files.readAllBytes(Paths.get(filename));

		X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
		KeyFactory kf = KeyFactory.getInstance("RSA");
		 PublicKey generatePublic = kf.generatePublic(spec);
		// System.out.println(Base64.getEncoder().encodeToString(generatePublic.getEncoded()));
		 return generatePublic;
	}
	
	public static PublicKey getSenderPublicKey(String publicKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
		
		byte[] keyBytes = publicKey.getBytes();
		
		X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
		KeyFactory kf = KeyFactory.getInstance("RSA");
		 PublicKey generatePublic = kf.generatePublic(spec);
		// System.out.println(Base64.getEncoder().encodeToString(generatePublic.getEncoded()));
		 return generatePublic;
	}

	
}
