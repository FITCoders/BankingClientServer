package PKIServices;
/**
 * 
 */
import java.io.*;
import java.security.*;

import javax.crypto.Cipher;
/**
 * 
 *
 */
public class PKIServices {
	
	KeyPair keyPair;
	String name;

	private boolean generateKeyPair(){
		System.out.println("Not implemented:PKIServices::generateKeyPair");
		
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
	
	public boolean signMessage(byte[] message, byte[] digSig){
		System.out.println("Not debugged:PKIServices::signMessage");
		try {
			Signature dsa = Signature.getInstance("SHA1withDSA", "SUN");
			dsa.initSign(keyPair.getPrivate());
		    dsa.update(message);
			byte[] realSig = dsa.sign();
			System.arraycopy(digSig, 0, realSig, 0, realSig.length);
		} 
		catch (NoSuchAlgorithmException | NoSuchProviderException | InvalidKeyException | SignatureException e) {
            System.err.println("Caught exception " + e.toString());
			e.printStackTrace();
		} 
		return false;
	}
	
	public boolean verifyMessage(byte []message, byte[]signature){
		try {
			Signature dsa = Signature.getInstance("SHA1withDSA", "SUN");
			dsa.initVerify(keyPair.getPublic());
			dsa.update(message);
			dsa.verify(signature);
		} 
		catch (NoSuchAlgorithmException | NoSuchProviderException | InvalidKeyException | SignatureException e) {
            System.err.println("Caught exception " + e.toString());
			e.printStackTrace();
		}
		System.out.println("Not implemented:PKIServices::verifyMessage");
		return false;
	}
	
	public boolean encryptMessage(byte[] message){
		System.out.println("Not implemented:PKIServices::encryptMessage");
		byte[] cipherText = null;
		try {
			// get an RSA cipher object and print the provider
			final Cipher cipher = Cipher.getInstance("RSA");
			// encrypt the plain text using the public key
			cipher.init(Cipher.ENCRYPT_MODE, keyPair.getPublic());
			cipherText = cipher.doFinal(message);
		} 
		catch (Exception e) {
            System.err.println("Caught exception " + e.toString());
			e.printStackTrace();
		}


		return false;
	}
	
	public boolean hashSHA(byte []digest, String message, String algorithm){
		MessageDigest localDigest;
		byte []tempDigest = new byte[50];
		try {
			localDigest = MessageDigest.getInstance(algorithm);
			tempDigest = localDigest.digest(message.getBytes("UTF-8"));
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

		// TODO Auto-generated constructor stub
	}

	public boolean decryptMessage() {
		// TODO Auto-generated method stub
		System.out.println("Not implemented:PKIServices::decryptMessage");
		return false;
	}

}
