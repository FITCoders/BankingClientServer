package messages;
import java.io.Serializable;

import transactionServices.ClientInfo;
/**
 * 
 */

/**
 * @author David
 *
 */
public abstract class BankingMessage implements Serializable {
	
	String command;
	ClientInfo client; // = new ClientInfo;
	String accountId;
	abstract boolean send();
	
	/**
	 * 
	 */
	public BankingMessage() {
		
	}

	public BankingMessage(String clientId) {
		System.out.println("BankingMessage::Constructor");
		client.setClientId(clientId);

		// TODO Auto-generated constructor stub
	}

}
