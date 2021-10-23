package com.pki.xmlutil;

import java.io.File;
import java.security.PublicKey;

import javax.crypto.SecretKey;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class CreateXmlPayload {

	public static  String xmlFilePath = null;

	static PublicKey recPublic;
	static SecretKey seckey;

	public static void generateXMLPayload(String encryptedData, String encryptedSymmetricKey) {

		try {

			DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();

			DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();

			Document document = documentBuilder.newDocument();

			// root element
			Element root = document.createElement("UaReq");
			
			Attr ua_id = document.createAttribute("UA-ID");
			ua_id.setValue("UA123");
			root.setAttributeNode(ua_id);
			
			Attr po_id = document.createAttribute("PO-ID");
			po_id.setValue("PO345");
			root.setAttributeNode(po_id);
			
			
			
			document.appendChild(root);

			// message element (1st child element)
			Element message = document.createElement("message");

			// set an attribute to element
			Attr attr = document.createAttribute("encoding");
			attr.setValue("Base64");
			message.setAttributeNode(attr);

			root.appendChild(message);

//			// Generate secret key to encrypt message
//
//			System.out.println("Secret key: " + Base64.getEncoder().encodeToString(seckey.getEncoded()));
//
//			// encrypt message using sec key
//
//			byte[] encryptedMsg = AESUtil.encrypt(inputMsg, Base64.getEncoder().encodeToString(seckey.getEncoded()));
//
//			// convert byte to String

			// String cipherMessage = Base64.getEncoder().encodeToString(encryptedMsg);

			// append data to encMessage element
			message.appendChild(document.createTextNode(encryptedData));
			// append encMessage node to message tag

			// sk(symmetric key) element (2nd child element)
			Element Sk = document.createElement("Sk");

			root.appendChild(Sk);

			Attr attr2 = document.createAttribute("type");
			attr2.setValue("AES256");
			Sk.setAttributeNode(attr2);

			// append encrypt symmtric key

			Sk.appendChild(document.createTextNode(encryptedSymmetricKey));

			// create the xml file
			// transform the DOM Object to an XML File
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource domSource = new DOMSource(document);
			StreamResult streamResult = new StreamResult(new File(xmlFilePath));

			transformer.transform(domSource, streamResult);

			System.out.println("Done creating XML File");

		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		}

	}

	public static void main(String argv[]) throws Exception {

	}
}