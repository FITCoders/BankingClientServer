package Server;

/**
 * 
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import messages.*;
import transactionServices.*;
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
//	private static final int PORT = 2346;
	private static int connectionNumber = 0;

	public void run() {
		System.out
				.println("Waiting for client connection at the port: " );
		while (true) {

			try {
				new ConnectionListener(serverSocket.accept(), connectionNumber++)
						.start();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	class ConnectionListener extends Thread {

		private Socket socket;
	    private ObjectInputStream inStream = null;

		public ConnectionListener(Socket socketValue, int connectionNumber) {
			socket = socketValue;
			connectionNumber = connectionNumber;
		}

	    public void communicate() {
	        try {
//	            serverSocket = new ServerSocket(4446);
//	            socket = serverSocket.accept();
	            System.out.println("Connected");
	            inStream = new ObjectInputStream(socket.getInputStream());
	 
	            ClientInfo clientInfo = (ClientInfo) inStream.readObject();
	            System.out.println("Object received = " + clientInfo.getClientId());
	            socket.close();
	 
	        } catch (SocketException se) {
	            System.exit(0);
	        } catch (IOException e) {
	            e.printStackTrace();
	        } catch (ClassNotFoundException cn) {
	            cn.printStackTrace();
	        }
	    }


		public void run() {

			PrintWriter pw = null;
			BufferedReader br = null;
			communicate();
/*			try {
				String data = null;
				try {
					br = new BufferedReader(new InputStreamReader(
							socket.getInputStream()));
					pw = new PrintWriter(socket.getOutputStream());
					String accountId = br.readLine();
					
					if (isValidAccount(accountId)) {
						pw.println(accountId);
					} else {
						pw.println(accountId + " is not a valid account ID");
					}
					pw.flush();

					System.out.println("Client " + connectionNumber + " Data: "
							+ data);
				} catch (IOException ioe) {
					ioe.printStackTrace();
				}
				pw.close();
				br.close();
				socket.close();
			} catch (IOException ex) {
				Logger.getLogger(Server.class.getName()).log(Level.SEVERE,
						null, ex);
				pw.close();
				try {
					br.close();
					socket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} */
		}
	}

	public boolean isValidAccount(String accountId) {
		for (int i=0;i<5;i++) {
			if (accounts[i].getId().equals(accountId)) {
				return true;
			}
		}
//		for (int i;i<accounts.size();i++) {
//			if (accounts[i].)
		return false;
	}
	
	public void addAccount(Account account) {
		System.out.println("Not implemented:Server::addAccount");
		return;
	}

	public boolean receive() {
		System.out.println("Not implemented:Server::receive");
//		pkiServices.validateMessage();

		BalanceResponse response = new BalanceResponse();
//		pkiServices.encryptMessage();

		return false;
	}

	public Server() {
		System.out.println("Server::Constructor");
//		accounts = new ArrayList<Account>();
		accounts = new Account[5];
		accounts[0] = new Account("00001","John Wiggins",32.60);
		accounts[1] = new Account("00002","John Smith",32.60);
		accounts[2] = new Account("00003","John Jones",32.60);
		accounts[3] = new Account("00004","John Williams",32.60);
		accounts[4] = new Account("00005","John Jabaar",32.60);
		// this.pkiServices = new PKIServices();
		try {
			serverSocket = new ServerSocket(4446);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		start();
	}

}
