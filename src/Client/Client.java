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

import utilities.Serializer;
import messages.*;
import PKIServices.*;

/**
 * @author David 
 *
 */
public class Client {
    private static final String HOST = "localhost";
	private static final int PORT = 4446;
    private Socket socket = null;
    private ObjectInputStream inputStream = null;
    private ObjectOutputStream outputStream = null;
    String accountNum;
    BufferedReader serverResponse;
    PrintWriter clientRequest;
	PKIServices pkiServices;
	BalanceResponse balanceResponse;
	
    public void connectToServer() {
    	 
		byte[] cipherText = new byte[128];
		byte[] plainText = new byte[50];
            try {
			socket = new Socket("localHost", PORT);
                System.out.println("Connected");
                outputStream = new ObjectOutputStream(socket.getOutputStream());
                BalanceRequest balanceRequest = new BalanceRequest(accountNum);
			pkiServices.signMessage(Serializer.serialize(balanceRequest),
					balanceRequest.signature);
                outputStream.writeObject(balanceRequest);
	            try {
		            inputStream = new ObjectInputStream(socket.getInputStream());
				cipherText = (byte[]) inputStream.readObject();
				pkiServices.decryptMessage(cipherText, plainText);
				balanceResponse = (BalanceResponse) Serializer
						.deserialize(plainText);
				System.out.println("Response received from server "
						+ balanceResponse.toString());
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
 
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
			serverResponse = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
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
		this.pkiServices = new PKIServices("Client");
	}
	
	public void requestBalance() {
		BalanceRequest requestMessage = new BalanceRequest(accountNum);

		try {
			pkiServices.signMessage(Serializer.serialize(requestMessage), requestMessage.signature);
		} 
		catch (IOException e) {
            System.err.println("Caught exception " + e.toString());
			e.printStackTrace();
		}
	}
}



