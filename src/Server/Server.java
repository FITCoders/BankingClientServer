package Server;

/**
 * 
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import messages.*;
import transactionServices.*;
import utilities.Serializer;
import PKIServices.*;

/**
 * @author David
 * 
 */
public class Server extends Thread {
	/**
	 * 
	 */

	static Account accounts[];
	PKIServices pkiServices;
	private static ServerSocket serverSocket;
	private static final int PORT = 4446;
	private static int connectionNumber = 0;

	public void run() {
		System.out.println("Waiting for client connection on port : " + PORT );
		while (true) {

			try {
				new ConnectionListener(serverSocket.accept(),
						connectionNumber++).start();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	class ConnectionListener extends Thread {

		private int connectionNumber;
		private Socket socket;
	    private ObjectInputStream inStream = null;
	    private ObjectOutputStream outputStream = null;

		public ConnectionListener(Socket socketValue, int connectionNum) {
			socket = socketValue;
		}

	    public void communicate() {
	        try {
//	            serverSocket = new ServerSocket(PORT);
//	            socket = serverSocket.accept();
	            System.out.println("Connected");
	            inStream = new ObjectInputStream(socket.getInputStream());
	 
				BalanceRequest balanceRequest = (BalanceRequest) inStream.readObject();
	            byte[]digest = new byte[50];
	            System.arraycopy(balanceRequest.signature, 0, digest, 0, balanceRequest.signature.length);
	            balanceRequest.clearSignature();
				if (pkiServices.verifyMessage(Serializer.serialize(balanceRequest), digest)) {
					BalanceResponse balanceResponse = new BalanceResponse(balanceRequest.getClientId());
					System.out.println("Message received from client : " + balanceRequest.toString());
		            	if (isValidAccount(balanceRequest.getClientId())) {
			            	setResponseInfo(balanceResponse);
			            	balanceResponse.setStatus("SUCCESS");
			            // Send the BalanceRequest object to the client.
						} 
			            else {
							System.out.println("Invalid account number : "
									+ balanceRequest.getClientId());
			            	balanceResponse.setStatus("INVALID ACCOUNT NUMBER");
			            }
					byte[] cipherText = new byte[128];
					pkiServices.encryptMessage(Serializer.serialize(balanceResponse), cipherText);
					outputStream = new ObjectOutputStream(
							socket.getOutputStream());
					outputStream.writeObject(cipherText);
		            socket.close();
	            }
			} catch (SocketException se) {
	            System.exit(0);
	        } catch (IOException e) {
	            e.printStackTrace();
	        } catch (ClassNotFoundException cn) {
	            cn.printStackTrace();
	        }
	    }


		public void run() {
			communicate();
		}
	}

	public boolean isValidAccount(String accountId) {
		for (int i=0;i<5;i++) {
			if (accounts[i].getId().equals(accountId)) {
				return true;
			}
		}
		return false;
	}

	public boolean setResponseInfo(BalanceResponse response) {
		for (int i=0;i<5;i++) {
			if (accounts[i].getId().equals(response.getClientId())) {
				response.setClientBalance(accounts[i].getBalance());
				;
				response.setClientName(accounts[i].getName());
			}
		}
		return false;
	}
	public Server() {
		this.pkiServices = new PKIServices("Server");
		accounts = new Account[5];
		accounts[0] = new Account("00001","John Wiggins",32.60);
		accounts[1] = new Account("00002","John Smith",33.60);
		accounts[2] = new Account("00003","John Jones",34.60);
		accounts[3] = new Account("00004","John Williams",35.60);
		accounts[4] = new Account("00005","John Jabaar",324444.60);
		// this.pkiServices = new PKIServices();
		try {
			serverSocket = new ServerSocket(PORT);
		} catch (IOException e) {
            System.err.println("Caught exception " + e.toString());
			e.printStackTrace();
		} 
		start();
	}
}
