/**
 * 
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import messages.*;
import Client.*;
import PKIServices.*;
import Server.*;
import transactionServices.*;

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
	Server server;

	public AssignmentController() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out
				.println("Not implemented:main () finally we have a round trip...and now at long last I think it works");
		System.out.println("Enter C to run client or S to run server : ");
		BufferedReader userInput = new BufferedReader(new InputStreamReader(
				System.in));
		try {
			choice = userInput.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		Account account;
		String message = "Test";
		byte[] cipherText = new byte[128];
		byte[] plainText = new byte[50];
		PKIServices pkiServices = new PKIServices("AssignmentController");
		pkiServices.encryptMessage(message.getBytes(), cipherText);
		pkiServices.decryptMessage(cipherText, plainText);
		String decryptedMessage = new String(plainText);
		if (choice.equals("S")) {
			Server server = new Server();
//			server.addAccount(account);
//		    server.start();
//			server.receive();
		} else {
			try {
				System.out.println("Enter Account Number : ");
				accountNum = userInput.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Client client = new Client(accountNum);
			client.connectToServer();
			client.requestBalance();
			client.receive();
		}

	}

}
