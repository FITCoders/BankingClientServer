package PKIServices;
/**
 * 
 */
import java.io.*;
import java.security.*;
import javax.crypto.Cipher;
import java.security.spec.*;

/**
 * This class encapsulates the PKI services required to complete this assignment...
 * 1. Key pair generation (1024 bit RSA keys)
 * 2. Digital Signatures (using proprietary algorithm instead of DSA to reduce key management)
 * 3. Secure Hash Algorithms (SHA-256)
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
 * signMessage uses a proprietary algorithm, which uses RSA keys to encrypt SHA-256 hashes instead of DSA
 * keys that encrypt SHA-1 or SHA-256 hashes.
 * 
 * */	
	
	public boolean signMessage(byte[] message, byte[] digSig){
		byte[] digest = new byte[50];
		byte[] tempDigSig = new byte[128];
		
		try {	
			this.hashSHA(digest, message,  "SHA-256");
			this.encryptMessage(digest, tempDigSig);
			System.arraycopy(tempDigSig, 0, digSig, 0, tempDigSig.length);
		} 
		catch (Exception e) {
            System.err.println("Caught exception " + e.toString());
			e.printStackTrace();
		} 
		return true;
	}
	
/*
 * 
 * verifyMessage reverses the proprietary algorithm, using RSA keys to encrypt SHA-256 hashes instead of DSA
 * keys that decrypt SHA-1 or SHA-256 hashes.
 * 
 * */	
	
	
	public boolean verifyMessage(byte []message, byte[]digSig){
		boolean returnVal = true;
		byte[] sigHash = new byte[128];
		byte[] tempBuf = new byte[128];
		try {
			this.decryptMessage(digSig, sigHash);
			this.hashSHA(tempBuf, message, "SHA-256");
			for (int i = 0; i < 32; i++){
				if (tempBuf[i] != sigHash[i]){
					returnVal = false;
					break;
				}
			}
		} 
		catch (Exception e) {
            System.err.println("Caught exception " + e.toString());
			e.printStackTrace();
		}
		return returnVal;
	}

/*
 * Encrypt using 1024 bit RSA public key
 * the plaintext is passed by the caller
 * ...the ciphertext is copied into a byte array passed in by the caller...
 * 
 * */	
	
	public boolean encryptMessage(byte[] message, byte[] cipherText){
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
		return true;
	}
	
	/**
	 * 
	 */
	public PKIServices(String name) {
		this.name = name;
		this.generateKeyPair();
	}

/*
 * 
 * decryptMessage with 1024 bit RSA private key...
 * the ciphertext is sent by the caller
 * the plaintext is returned in a parameter passed by the caller
 * 
 * */	
	
	public boolean decryptMessage(byte[] cipherText, byte[] message) {
		byte []tempBuf = new byte[128];
		try {
			final Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.DECRYPT_MODE, keyPair.getPrivate());
			tempBuf = cipher.doFinal(cipherText);
			System.arraycopy(tempBuf, 0, message, 0, tempBuf.length);
		} 
		catch (Exception e) {
            System.err.println("Caught exception " + e.toString());
			e.printStackTrace();
		}
		return true;
	}

/*
* 
* caGetPublicKey emulates in a very simplistic fashion transfer of a public key...
* 
* */	
	
	public static PublicKey caGetPublicKey(String name) throws InvalidKeySpecException {
		File publicKeyFile = new File("c:\\KeyStore\\"+name+"\\public.key");
		FileInputStream fileInStream = null;
		byte[] keyBytes = null;
	    KeyFactory kf = null;
	    X509EncodedKeySpec spec = null;
		try {
			fileInStream = new FileInputStream(publicKeyFile);
		    DataInputStream dataInputStream = new DataInputStream(fileInStream);
		    keyBytes = new byte[(int)publicKeyFile.length()];
		    dataInputStream.readFully(keyBytes);
		    dataInputStream.close();
		    spec = new X509EncodedKeySpec(keyBytes);
			kf = KeyFactory.getInstance("RSA");
		} 
		catch (IOException | NoSuchAlgorithmException e) {
	        System.err.println("Caught exception " + e.toString());
			e.printStackTrace();
		}
	    return kf.generatePublic(spec);
	}
	
	
}
