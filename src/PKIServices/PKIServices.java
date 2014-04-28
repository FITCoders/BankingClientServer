package PKIServices;
/**
 * 
 */
		import java.io.*;
import java.security.*;
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
	
	public boolean signMessage(){
		System.out.println("Not implemented:PKIServices::signMessage");
		return false;
	}
	
	public boolean verifyMessage(){
		System.out.println("Not implemented:PKIServices::verifyMessage");
		return false;
	}
	
	public boolean encryptMessage(){
		System.out.println("Not implemented:PKIServices::encryptMessage");
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
			// TODO Auto-generated catch block
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
