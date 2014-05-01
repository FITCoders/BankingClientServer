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
	String keyPath;

/*
 * 
 * generateKeyPair generates 1024-bit RSA public and private keys...the keys are saved in a keystore 
 * for future feature expansion...
 * 
 * */	
	
	private boolean generateKeyPair(){
/*		try{
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
		    
		 // Store Public Key.
			PublicKey publicKey = keyPair.getPublic();
			X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(
					publicKey.getEncoded());

			publicKeyOS.write(x509EncodedKeySpec.getEncoded());
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
		
*/
		try {
			 
			KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
 
			keyGen.initialize(1024);
			this.keyPair = keyGen.genKeyPair();
 
			System.out.println("Generated Key Pair");
			this.dumpKeyPair(this.keyPair);
			this.SaveKeyPair(this.keyPath, keyPair);

//////////////////////////////////////////////////////////////////////////////////////////////////////////////
			/**/		String message = "Test";
			byte[] cipherText = new byte[128];
			byte[] plainText = new byte[50];
			this.encryptMessage(message.getBytes(), cipherText, this.keyPair.getPrivate());
			this.decryptMessage(cipherText, plainText, this.keyPair.getPublic());
			String decryptedMessage = new String(plainText);
			System.out.println("GENERATED TEST KEYS Original Plaintext: " + message + " decrypted message: " + decryptedMessage);

//////////////////////////////////////////////////////////////////////////////////////////////////////////////
			
			KeyPair loadedKeyPair = this.LoadKeyPair(keyPath, "RSA");
			System.out.println("Loaded Key Pair");
			this.dumpKeyPair(loadedKeyPair);
			
			
//////////////////////////////////////////////////////////////////////////////////////////////////////////////


for (int i = 0; i < plainText.length; i++)plainText[i] = 0;
this.decryptMessage(cipherText, plainText, loadedKeyPair.getPublic());
decryptedMessage = new String(plainText);
System.out.println("MIXED KEYS Original Plaintext: " + message + " decrypted message: " + decryptedMessage);

//////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		} 
		catch (Exception e) {
	        System.err.println("Caught exception " + e.toString());
			e.printStackTrace();
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
			System.arraycopy(tempDigSig, 0, digSig, 0, digSig.length);
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
			this.decryptMessage(digSig, sigHash, true);
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
 * this is an overload that will accept a private encryption key from the caller
 * 
 * */	
	
	public boolean encryptMessage(byte[] message, byte[] cipherText, PrivateKey privateKey){
		byte[] tempBuf = new byte[128];
		try {
			final Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.ENCRYPT_MODE, privateKey);
			tempBuf = cipher.doFinal(message);
			System.arraycopy(tempBuf, 0,cipherText, 0, tempBuf.length);
		} 
		catch (Exception e) {
            System.err.println("Caught exception " + e.toString());
			e.printStackTrace();
		}
		return true;
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

/*
 * 
 * encryptMessage with 1024 bit RSA private key...
 * the ciphertext is sent by the caller
 * the plaintext is returned in a parameter passed by the caller
 * this is an overloaded function that loads the receivers public key and encrypts with that
 * 
 * */	
		
		public boolean encryptMessage(byte[] message, byte[] cipherText, boolean loadKey) {
			byte []tempBuf = new byte[128];
			try {
				KeyPair loadedKeyPair = null;
				String senderKeyPath = "c:\\KeyStore\\";
					if (this.name.equals("Server"))
						senderKeyPath += "Client\\";
					else
						senderKeyPath += "Server\\";
				loadedKeyPair = LoadKeyPair(senderKeyPath, "RSA");
				final Cipher cipher = Cipher.getInstance("RSA");
				cipher.init(Cipher.DECRYPT_MODE, loadedKeyPair.getPublic());
				tempBuf = cipher.doFinal(cipherText);
				System.arraycopy(tempBuf, 0, message, 0, tempBuf.length);
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
		keyPath = "c:\\KeyStore\\"+this.name+"\\";
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
	 * decryptMessage with 1024 bit RSA private key...
	 * the ciphertext is sent by the caller
	 * the plaintext is returned in a parameter passed by the caller
	 * this is an overloaded function that accepts a public key from the caller
	 * 
	 * */	
		
		public boolean decryptMessage(byte[] cipherText, byte[] message, PublicKey publicKey) {
			byte []tempBuf = new byte[128];
			try {
				final Cipher cipher = Cipher.getInstance("RSA");
				cipher.init(Cipher.DECRYPT_MODE, publicKey);
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
	 * decryptMessage with 1024 bit RSA private key...
	 * the ciphertext is sent by the caller
	 * the plaintext is returned in a parameter passed by the caller
	 * this is an overloaded function that accepts a boolean value indicating that the senders public key should be loaded
	 * 
	 * */	
		
		public boolean decryptMessage(byte[] cipherText, byte[] message, boolean loadPublicKey ) {
			byte []tempBuf = new byte[128];
			KeyPair loadedKeyPair;
			String senderKeyPath = "c:\\KeyStore\\";
			try {
				if (this.name.equals("Server"))
					senderKeyPath += "Client\\";
				else
					senderKeyPath += "Server\\";
				loadedKeyPair = LoadKeyPair(senderKeyPath, "RSA");
				final Cipher cipher = Cipher.getInstance("RSA");
				cipher.init(Cipher.DECRYPT_MODE, loadedKeyPair.getPublic());
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
 * 
 * 
 * 
 * PKI Test
 * 
 
  * 
 * 
 * 
 * */

/*	public  void test() {
		try {
 
			KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
 
			keyGen.initialize(1024);
			KeyPair generatedKeyPair = keyGen.genKeyPair();
 
			System.out.println("Generated Key Pair");
			this.dumpKeyPair(generatedKeyPair);
			this.SaveKeyPair(keyPath, generatedKeyPair);

//////////////////////////////////////////////////////////////////////////////////////////////////////////////
					String message = "Test";
			byte[] cipherText = new byte[128];
			byte[] plainText = new byte[50];
			this.encryptMessage(message.getBytes(), cipherText, generatedKeyPair.getPrivate());
			this.decryptMessage(cipherText, plainText, generatedKeyPair.getPublic());
			String decryptedMessage = new String(plainText);
			System.out.println("GENERATED TEST KEYS Original Plaintext: " + message + " decrypted message: " + decryptedMessage);

//////////////////////////////////////////////////////////////////////////////////////////////////////////////
			
			KeyPair loadedKeyPair = this.LoadKeyPair(keyPath, "RSA");
			System.out.println("Loaded Key Pair");
			this.dumpKeyPair(loadedKeyPair);
			
			
//////////////////////////////////////////////////////////////////////////////////////////////////////////////


for (int i = 0; i < plainText.length; i++)plainText[i] = 0;
this.decryptMessage(cipherText, plainText, loadedKeyPair.getPublic());
decryptedMessage = new String(plainText);
System.out.println("MIXED KEYS Original Plaintext: " + message + " decrypted message: " + decryptedMessage);

//////////////////////////////////////////////////////////////////////////////////////////////////////////////
			
			
			
		} catch (Exception e) {
	        System.err.println("Caught exception " + e.toString());
			e.printStackTrace();
			return;
		}
	}
*/ 
	private void dumpKeyPair(KeyPair keyPair) {
		PublicKey pub = keyPair.getPublic();
		System.out.println("Public Key: " + getHexString(pub.getEncoded()));
 
		PrivateKey priv = keyPair.getPrivate();
		System.out.println("Private Key: " + getHexString(priv.getEncoded()));
	}
 
	private String getHexString(byte[] b) {
		String result = "";
		for (int i = 0; i < b.length; i++) {
			result += Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1);
		}
		return result;
	}
 
	public void SaveKeyPair(String path, KeyPair keyPair) throws IOException {
		PrivateKey privateKey = keyPair.getPrivate();
		PublicKey publicKey = keyPair.getPublic();
 
		// Store Public Key.
		X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(
				publicKey.getEncoded());
		String publicPath = path + "public.key";
		String privatePath = path + "private.key";
		
	    File privateKeyFile = new File(privatePath);
		File publicKeyFile = new File(publicPath);
		
		if (privateKeyFile.getParentFile() != null) {
			  privateKeyFile.getParentFile().mkdirs();
			}
 			privateKeyFile.createNewFile();
			
			if (publicKeyFile.getParentFile() != null) {
			  publicKeyFile.getParentFile().mkdirs();
			}
			publicKeyFile.createNewFile();

		
		FileOutputStream fos = new FileOutputStream(publicKeyFile);
		fos.write(x509EncodedKeySpec.getEncoded());
		fos.close();
 
		// Store Private Key.
		PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(
				privateKey.getEncoded());
		fos = new FileOutputStream(privateKeyFile);
		fos.write(pkcs8EncodedKeySpec.getEncoded());
		fos.close();
	}
 
	public KeyPair LoadKeyPair(String path, String algorithm)
			throws IOException, NoSuchAlgorithmException,
			InvalidKeySpecException {
		// Read Public Key.
		File filePublicKey = new File(path + "/public.key");
		FileInputStream fis = new FileInputStream(path + "/public.key");
		byte[] encodedPublicKey = new byte[(int) filePublicKey.length()];
		fis.read(encodedPublicKey);
		fis.close();
 
		// Read Private Key.
		File filePrivateKey = new File(path + "/private.key");
		fis = new FileInputStream(path + "/private.key");
		byte[] encodedPrivateKey = new byte[(int) filePrivateKey.length()];
		fis.read(encodedPrivateKey);
		fis.close();
 
		// Generate KeyPair.
		KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
		X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(
				encodedPublicKey);
		PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);
		

		PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(
				encodedPrivateKey);
		PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);

		return new KeyPair(publicKey, privateKey);
	}
	
	
	public PublicKey loadPublicKey(String path, String algorithm)
			throws IOException, NoSuchAlgorithmException,
			InvalidKeySpecException {
		// Read Public Key.
		File filePublicKey = new File(path + "/public.key");
		FileInputStream fis = new FileInputStream(path + "/public.key");
		byte[] encodedPublicKey = new byte[(int) filePublicKey.length()];
		fis.read(encodedPublicKey);
		fis.close();
 
		// Generate KeyPair.
		KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
		X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(
				encodedPublicKey);
		PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);
		

		return publicKey;
	}

}

