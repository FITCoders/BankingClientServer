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

	public boolean generateKeyPair(){
		System.out.println("Not implemented:PKIServices::generateKeyPair");
		
		try{

		KeyPairGenerator keyGen = KeyPairGenerator.getInstance("DSA", "SUN");
		KeyPair pair = keyGen.generateKeyPair();
		@SuppressWarnings("unused")
		PrivateKey priv = pair.getPrivate();
		@SuppressWarnings("unused")
		PublicKey pub = pair.getPublic();
		} catch (Exception e) {
            System.err.println("Caught exception " + e.toString());
        }
		return false;
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
