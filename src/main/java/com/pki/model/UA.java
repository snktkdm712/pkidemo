package com.pki.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UA {
	
	
	private String data;
	@JsonProperty("secretKey")
	private String secretKeyFilePath;
	private String privateKeyFilePath;
	private String publicKeyFilePath;
	private String xmlFilePath;
	private String signedXmlFilePath;
	
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getSecretKeyFilePath() {
		return secretKeyFilePath;
	}
	public void setSecretKeyFilePath(String secretKeyFilePath) {
		this.secretKeyFilePath = secretKeyFilePath;
	}
	public String getPrivateKeyFilePath() {
		return privateKeyFilePath;
	}
	public void setPrivateKeyFilePath(String privateKeyFilePath) {
		this.privateKeyFilePath = privateKeyFilePath;
	}
	public String getPublicKeyFilePath() {
		return publicKeyFilePath;
	}
	public void setPublicKeyFilePath(String publicKeyFilePath) {
		this.publicKeyFilePath = publicKeyFilePath;
	}
	public String getXmlFilePath() {
		return xmlFilePath;
	}
	public void setXmlFilePath(String xmlFilePath) {
		this.xmlFilePath = xmlFilePath;
	}
	public String getSignedXmlFilePath() {
		return signedXmlFilePath;
	}
	public void setSignedXmlFilePath(String signedXmlFilePath) {
		this.signedXmlFilePath = signedXmlFilePath;
	}
	@Override
	public String toString() {
		return "UA [data=" + data + ", secretKeyFilePath=" + secretKeyFilePath + "]";
	}
	
	
	
	
	
	
	
	

}
