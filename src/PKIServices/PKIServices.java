package PKIServices;
/**
 * 
 */
import java.io.*;
import java.security.*;

import javax.crypto.Cipher;
/**
 * This class encapsulates the PKI services required to complete this assignment...
 * 1. Key pair generation (1024 bit RSA keys)
 * 2. Digital Signature
 * 3. Secure Hash Algorithms (SHA-1 and SHA-256)
 * 4. RSA encryption and decryption
 */
public class PKIServices {
	
	KeyPair keyPair;
	String name;

/*
 * 
 * generateKeyPair generates 1024-bit RSA public and private keys...the keys are saved in a keystore 
 * for future feature expansion...
 * 
 * */	
	
	private boolean generateKeyPair(){
		System.out.println("Not finished...implement keystore:PKIServices::generateKeyPair");
		
		try{
			KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
			keyGen.initialize(1024);
			keyPair = keyGen.generateKeyPair();
			
		    File privateKeyFile = new File("c:\\KeyStore\\"+this.name+"\\private.key");
			File publicKeyFile = new File("c:\\KeyStore\\"+this.name+"\\public.key");
			
			// Create files to store public and private key
			if (privateKeyFile.getParentFile() != null) {
			  privateKeyFile.getParentFile().mkdirs();
			}
			privateKeyFile.createNewFile();
			
			if (publicKeyFile.getParentFile() != null) {
			  publicKeyFile.getParentFile().mkdirs();
			}
			publicKeyFile.createNewFile();
			
		    // Saving the Public key in a file
		    ObjectOutputStream publicKeyOS = new ObjectOutputStream(
		        new FileOutputStream(publicKeyFile));
		    publicKeyOS.writeObject(keyPair.getPublic());
		    publicKeyOS.close();
	
		    // Saving the Private key in a file
		    ObjectOutputStream privateKeyOS = new ObjectOutputStream(
		        new FileOutputStream(privateKeyFile));
		    privateKeyOS.writeObject(keyPair.getPrivate());
		    privateKeyOS.close();
		} 
		catch (Exception e) {
            System.err.println("Caught exception " + e.toString());
        }
		
		return true;
	}
	
/*
 * 
 * signMessage signs a message using the 1024 bit RSA public key and SHA1 with DSA
 * 
 * */
	
	public boolean signMessage(byte[] message, byte[] digSig){
		System.out.println("Not debugged:PKIServices::signMessage");
		byte[] digest = new byte[50];
		byte[] tempDigSig = new byte[128];
		
		try {
/*			Signature dsa = Signature.getInstance("SHA1withDSA", "SUN");
			dsa.initSign(keyPair.getPrivate());
		    dsa.update(message);
			byte[] realSig = dsa.sign();
*/			
			this.hashSHA(digest, message,  "SHA-256");
			this.encryptMessage(digest, tempDigSig);
			System.arraycopy(tempDigSig, 0, digSig, 0, tempDigSig.length);
		} 
		catch (Exception e) {
            System.err.println("Caught exception " + e.toString());
			e.printStackTrace();
		} 
		return false;
	}
	
	public boolean verifyMessage(byte []message, byte[]digSig){
		System.out.println("Not debugged:PKIServices::verifyMessage");
/*		try {
			Signature dsa = Signature.getInstance("SHA1withDSA", "SUN");
			dsa.initVerify(keyPair.getPublic());
			dsa.update(message);
			dsa.verify(signature);
		} 
		catch (NoSuchAlgorithmException | NoSuchProviderException | InvalidKeyException | SignatureException e) {
            System.err.println("Caught exception " + e.toString());
			e.printStackTrace();
		}*/
		return false;
	}

/*
 * Encrypt using 1024 bit RSA public key
 * ...the result is copied into a byte array passed in by the sender...
 * 
 * */	
	
	public boolean encryptMessage(byte[] message, byte[] cipherText){
		System.out.println("Not debugged:PKIServices::encryptMessage");
		byte[] tempBuf = new byte[128];
		try {
			final Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.ENCRYPT_MODE, keyPair.getPublic());
			tempBuf = cipher.doFinal(message);
			System.arraycopy(tempBuf, 0,cipherText, 0, tempBuf.length);
		} 
		catch (Exception e) {
            System.err.println("Caught exception " + e.toString());
			e.printStackTrace();
		}
		return true;
	}
	
	public boolean hashSHA(byte []digest, byte[] message, String algorithm){
		MessageDigest localDigest;
		byte []tempDigest = new byte[50];
		try {
			localDigest = MessageDigest.getInstance(algorithm);
			tempDigest = localDigest.digest(message);
			System.arraycopy(tempDigest, 0, digest, 0, tempDigest.length);
		} catch (NoSuchAlgorithmException e) {
            System.err.println("Caught exception " + e.toString());
			e.printStackTrace();
		} 
		catch (Exception e) {
            System.err.println("Caught exception " + e.toString());
        }
		return false;
	}
	
	/**
	 * 
	 */
	public PKIServices(String name) {
		System.out.println("Not finished:PKIServices::Constructor");
		this.name = name;
		this.generateKeyPair();
	}

/*
 * 
 * decryptMessage with 1024 bit RSA private key...
 * 
 * */	
	
	public boolean decryptMessage(byte[] cipherText, byte[] message) {
		byte []tempBuf = new byte[128];
		try {
			// get an RSA cipher object and print the provider
			final Cipher cipher = Cipher.getInstance("RSA");
			// encrypt the plain text using the public key
			cipher.init(Cipher.DECRYPT_MODE, keyPair.getPrivate());
			tempBuf = cipher.doFinal(cipherText);
			System.arraycopy(tempBuf, 0, message, 0, tempBuf.length);
		} 
		catch (Exception e) {
            System.err.println("Caught exception " + e.toString());
			e.printStackTrace();
		}
		return false;
	}

}
