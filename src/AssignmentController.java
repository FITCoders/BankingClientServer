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
	Server server;

	public AssignmentController() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Not implemented:main ()");
		System.out.println("Enter C to run client or S to run server : ");
		BufferedReader userInput = new BufferedReader(new InputStreamReader(
				System.in));
		try {
			choice = userInput.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Account account = new Account();
		if (choice.equals("S")) {
			Server server = new Server();
			server.addAccount(account);
//		    server.start();
//			server.receive();
		} else {
			Client client = new Client();
			client.connectToServer();
			client.requestBalance();
			client.receive();
		}

	}

}
