package com.pki.controller;

import java.io.File;
import java.io.IOException;
import java.util.Base64;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pki.aesutil.AESUtil;
import com.pki.model.UA;

@RestController
@RequestMapping("ua")
@CrossOrigin("*")
public class UAController {

	@PostMapping("/encrypt-data")
	public ResponseEntity<String> encryptData(@RequestBody UA ua) throws IOException {

//		String secretKey = ua.getSecretKeyFilePath();
//		System.out.println("Secret key  file: " + secretKey);
		
		String secretKeyPath = "D:\\seckey".concat(File.separator+ua.getSecretKeyFilePath());
		
		String secretKey = Base64.getEncoder().encodeToString(AESUtil.readSecKey(secretKeyPath).getEncoded());

		System.out.println("Secret Key: "+secretKey);
		
		//encrypt data
		String encryptedData = Base64.getEncoder().encodeToString(AESUtil.encrypt(ua.getData(), secretKey));

		String decryptedData = AESUtil.decrypt(Base64.getDecoder().decode(encryptedData.getBytes()), secretKey);
		
		System.out.println("decrypted data: "+decryptedData);
		
				return ResponseEntity.ok(encryptedData);
	}

}
