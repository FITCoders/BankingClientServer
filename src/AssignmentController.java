/**
 * 
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;

import Client.*;
import PKIServices.*;
import Server.*;

/**
 * @author David 
 * 
 */
public class AssignmentController {
	/**
	 * 
	 */
	static String choice;
	static String accountNum;
	static String serverAddress;
	Server server;

	public AssignmentController() {
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.print("Enter C to run client or S to run server : ");
		BufferedReader userInput = new BufferedReader(new InputStreamReader(
				System.in));
		try {
			choice = userInput.readLine();
		} catch (IOException e) {
            System.err.println("Caught exception " + e.toString());
			e.printStackTrace();
		}
//		Account account;
/*		String message = "Test";
		byte[] cipherText = new byte[128];
		byte[] plainText = new byte[50];
		PKIServices pkiServices = new PKIServices("AssignmentController");
		pkiServices.encryptMessage(message.getBytes(), cipherText);
		pkiServices.decryptMessage(cipherText, plainText);
		String decryptedMessage = new String(plainText);
/*		pkiServices.test();
		pkiServices.signMessage(message.getBytes(), cipherText);
		pkiServices.verifyMessage(message.getBytes(), cipherText);
		PublicKey key = null;
*//*		try {
			key = pkiServices.caGetPublicKey("Server");
		} 
		catch (InvalidKeySpecException e1) {
			e1.printStackTrace();
		}
*/		if (choice.equalsIgnoreCase("S")) {
			Server server = new Server();
		} else if (choice.equalsIgnoreCase("C")){
			try {
				System.out.print("Enter Account Number : ");
				accountNum = userInput.readLine();
				System.out.print("Enter Server IP Address or just enter for localhost : ");
				serverAddress = userInput.readLine();
				if (serverAddress.length() == 0) {
					serverAddress = "localhost";
				}
			} catch (IOException e) {
	            System.err.println("Caught exception " + e.toString());
				e.printStackTrace();
			}
			Client client = new Client(serverAddress,accountNum);
			client.connectToServer();
			client.requestBalance();
			//client.receive();
		}
		else {
			System.out.println("It was a simple request...S or C. Great work. I'm gone.");
		}

	}

}
