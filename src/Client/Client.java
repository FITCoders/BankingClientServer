package Client;
/**
 * 
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

import transactionServices.ClientInfo;
import messages.*;
import PKIServices.*;

/**
 * @author David
 *
 */
public class Client {
    private static final String HOST = "localhost";
    private static final int PORT = 2349;
    private Socket socket = null;
    private ObjectInputStream inputStream = null;
    private ObjectOutputStream outputStream = null;
    String accountNum;
    BufferedReader serverResponse;
    PrintWriter clientRequest;
	PKIServices pkiServices;
	
    public void connectToServer() {
    	 
 //       while (!isConnected) {
            try {
                socket = new Socket("localHost", 4446);
                System.out.println("Connected");
 //               isConnected = true;
                outputStream = new ObjectOutputStream(socket.getOutputStream());
                BalanceRequest balanceRequest = new BalanceRequest(accountNum);
                System.out.println("Object to be written = " + balanceRequest.getClientId());
                outputStream.writeObject(balanceRequest);
 
 
            } catch (SocketException se) {
                se.printStackTrace();
                // System.exit(0);
            } catch (IOException e) {
                e.printStackTrace();
            }
//        }
    }
    public boolean connectToServerOld(){
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
	
/*	public boolean receive() {
		System.out.println("Not implemented:Client::receive");
        try {
			System.out.println("Server response: " + serverResponse.readLine());
		} catch (IOException e) {
			e.printStackTrace();
		} 
	
//		pkiServices.decryptMessage();
		
		return false;
	} */

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
//        clientRequest.println(requestMessage.getClientId());
//        clientRequest.flush();
//		pkiServices.signMessage();
		
	}

}



