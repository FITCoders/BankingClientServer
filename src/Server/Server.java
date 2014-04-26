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
    private static final int PORT = 2348;
    private static int clientNumber = 0;

    public void run() {
        System.out.println("Waiting for client connection at the port: " + PORT);
        try {
            while (true) {
 
                new ConnectionListener(serverSocket.accept(), clientNumber++).start();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
    class ConnectionListener extends Thread {
    	 
        private Socket socket;
 
        public ConnectionListener(Socket socketValue, int clientNumber) {
            socket = socketValue;
            clientNumber = clientNumber;
        }
 
        public void run() {
 
            try {
                PrintWriter pw = null;
                BufferedReader br = null;
                String data = null;
                try {
                        br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        pw = new PrintWriter(socket.getOutputStream());
                        data = br.readLine();
 
                        pw.println(data);
                        pw.flush();
 
                        System.out.println("Client " + clientNumber + " Data: " + data);
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
                pw.close();
                socket.close();
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
	
	public void addAccount(Account account){
		System.out.println("Not implemented:Server::addAccount");
		return;
	}

	public boolean receive(){
		System.out.println("Not implemented:Server::receive");
		pkiServices.validateMessage();
		
		BalanceResponse response = new BalanceResponse();
		pkiServices.encryptMessage();
		
		return false;
	}
	
	
	public Server() {
		System.out.println("Not implemented:Server::Constructor");
//		this.pkiServices = new PKIServices();
		accounts = null;
		try {
			serverSocket = new ServerSocket(PORT);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    start();

	}

}
