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
    	 
            try {
                socket = new Socket("localHost", 4446);
                System.out.println("Connected");
                outputStream = new ObjectOutputStream(socket.getOutputStream());
                BalanceRequest balanceRequest = new BalanceRequest(accountNum);
                System.out.println("Object to be written = " + balanceRequest.getClientId());
                outputStream.writeObject(balanceRequest);
 
 
            } catch (SocketException se) {
                se.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
    public boolean connectToServerOld(){
		System.out.println("Client::connectToServer");
        Socket socket = null;
		try {
			socket = new Socket(HOST, PORT);
		} catch (UnknownHostException e) {
            System.err.println("Caught exception " + e.toString());
			e.printStackTrace();
		} catch (IOException e) {
            System.err.println("Caught exception " + e.toString());
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
	
	/**
	 * 
	 */
	public Client(String accountNum) {
		this.accountNum = accountNum;
		this.pkiServices = new PKIServices("testClient");
	}
	public void requestBalance() {
//		pkiServices.signMessage();
		BalanceRequest requestMessage = new BalanceRequest(accountNum);
	}
}



