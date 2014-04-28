package Server;

/**
 * 
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
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

	List<Account> accounts;
	PKIServices pkiServices;
	private static ServerSocket serverSocket;
	private static final int PORT = 2349;
	private static int connectionNumber = 0;

	public void run() {
		System.out
				.println("Waiting for client connection at the port: " + PORT);
		try {
			while (true) {

				new ConnectionListener(serverSocket.accept(), connectionNumber++)
						.start();
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	class ConnectionListener extends Thread {

		private Socket socket;

		public ConnectionListener(Socket socketValue, int connectionNumber) {
			socket = socketValue;
			connectionNumber = connectionNumber;
		}

		public void run() {

			PrintWriter pw = null;
			BufferedReader br = null;
			try {
				String data = null;
				try {
					br = new BufferedReader(new InputStreamReader(
							socket.getInputStream()));
					pw = new PrintWriter(socket.getOutputStream());
					String accountId = br.readLine();

					pw.println(accountId);
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
			}
		}
	}

	public void addAccount(Account account) {
		System.out.println("Not implemented:Server::addAccount");
		return;
	}

	public boolean receive() {
		System.out.println("Not implemented:Server::receive");
		pkiServices.validateMessage();

		BalanceResponse response = new BalanceResponse();
		pkiServices.encryptMessage();

		return false;
	}

	public Server() {
		System.out.println("Server::Constructor");
		accounts = new ArrayList<Account>();
		accounts.add(new Account("00001","John Wiggins",32.60));
		accounts.add(new Account("00002","John Smith",32.60));
		accounts.add(new Account("00003","John Jones",32.60));
		accounts.add(new Account("00004","John Williams",32.60));
		accounts.add(new Account("00005","John Jabaar",32.60));
		// this.pkiServices = new PKIServices();
		try {
			serverSocket = new ServerSocket(PORT);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		start();
	}

}
