package Client;
/**
 * 
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import messages.*;
import PKIServices.*;

/**
 * @author David
 *
 */
public class Client {
    private static final String HOST = "localhost";
    private static final int PORT = 2349;
    String accountNum;
    BufferedReader serverResponse;
    PrintWriter clientRequest;
	PKIServices pkiServices;
	
	public boolean connectToServer(){
		System.out.println("Client::connectToServer");
        Socket socket = null;
		try {
			socket = new Socket(HOST, PORT);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        try {
			serverResponse = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	        clientRequest = new PrintWriter(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
        System.out.println("Connected to the server");
 		return false;
	}
	
	public boolean receive() {
		System.out.println("Not implemented:Client::receive");
        try {
			System.out.println("Server response: " + serverResponse.readLine());
		} catch (IOException e) {
			e.printStackTrace();
		}
	
		pkiServices.decryptMessage();
		
		return false;
	}

	/**
	 * 
	 */
	public Client(String accountNum) {
		System.out.println("Client::Constructor");
		this.accountNum = accountNum;
		this.pkiServices = new PKIServices("testClient");
	}
	public void requestBalance() {
		System.out.println("Client::requestBalance");
		
		BalanceRequest requestMessage = new BalanceRequest(accountNum);
//        clientRequest.println(requestMessage.getCommand() + " " + requestMessage.getClientId());
        clientRequest.println(requestMessage.getClientId());
        clientRequest.flush();
//		pkiServices.signMessage();
		
	}

}



