package com.pki.rsautil;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

public class GenerateKeys {
	
	private KeyPairGenerator keyGen;
	private KeyPair pair;
	private PrivateKey privateKey;
	private PublicKey publicKey;

	public GenerateKeys(int keylength) throws NoSuchAlgorithmException, NoSuchProviderException {
		this.keyGen = KeyPairGenerator.getInstance("RSA");
		this.keyGen.initialize(keylength);
	}

	public void createKeys() {
		this.pair = this.keyGen.generateKeyPair();
		this.privateKey = pair.getPrivate();
		this.publicKey = pair.getPublic();
	}

	public PrivateKey getPrivateKey() {
		return this.privateKey;
	}

	public PublicKey getPublicKey() {
		return this.publicKey;
	}

	public void writeToFile(String path, byte[] key) throws IOException {
		File f = new File(path);
		f.getParentFile().mkdirs();

		FileOutputStream fos = new FileOutputStream(f);
		fos.write(key);
		fos.flush();
		fos.close();
	}

	public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchProviderException, IOException {
		GenerateKeys sender;
		GenerateKeys receiver;
		
		sender = new GenerateKeys(4096);
		sender.createKeys();
		//sender.writeToFile("Sender/publicKey", sender.getPublicKey().getEncoded());
		//sender.writeToFile("Sender/privateKey", sender.getPrivateKey().getEncoded());
		
		System.out.println("Sender private : "+Base64.getEncoder().encodeToString(sender.getPrivateKey().getEncoded()));
		System.out.println("Sender public: "+Base64.getEncoder().encodeToString(sender.getPublicKey().getEncoded()));
			
//		receiver = new GenerateKeys(4096);
//		receiver.createKeys();
//		receiver.writeToFile("Receiver/publicKey", receiver.getPublicKey().getEncoded());
//		receiver.writeToFile("Receiver/privateKey", receiver.getPrivateKey().getEncoded());
		
		System.out.println("keypair generated succesfully...");
		
		//System.out.println(gk_Bob.getPublicKey());
	
	}
}
