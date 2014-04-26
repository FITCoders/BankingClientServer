package PKIServices;
/**
 * 
 */
		import java.io.*;
import java.security.*;
/**
 * @author David
 *
 */
public class PKIServices {
	
	KeyPair keyPair;

	public boolean generateKeyPair(){
		System.out.println("Not implemented:PKIServices::generateKeyPair");
		
		try{
			KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
			keyGen.initialize(1024);
			keyPair = keyGen.generateKeyPair();
			
		    File privateKeyFile = new File("c:\\users\\david\\documents\\private.key");
			File publicKeyFile = new File("c:\\users\\david\\documents\\public.key");
			
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
	
	public boolean validateMessage(){
		System.out.println("Not implemented:PKIServices::validateMessage");
		return false;
	}
	
	public boolean encryptMessage(){
		System.out.println("Not implemented:PKIServices::encryptMessage");
		return false;
	}
	
	public boolean hashSHA256(byte []digest, String message){
		MessageDigest localDigest;
		try {
			localDigest = MessageDigest.getInstance("SHA-256");
			digest = localDigest.digest(message.getBytes("UTF-8"));
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
	public PKIServices() {
		System.out.println("Not implemented:PKIServices::Constructor");

		// TODO Auto-generated constructor stub
	}

	public boolean decryptMessage() {
		// TODO Auto-generated method stub
		System.out.println("Not implemented:PKIServices::decryptMessage");
		return false;
	}

}
