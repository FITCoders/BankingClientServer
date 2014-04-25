
/**
 * 
 */
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
	public AssignmentController() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Not implemented:main() test change");
		Server server = new Server();
		Client client = new Client();
		Account account = new Account();
		
		server.addAccount(account);
		client.connectToServer();
		client.requestBalance();
		server.receive();
		client.receive();
	}

}

