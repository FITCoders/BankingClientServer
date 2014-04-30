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
	String status;
	ClientInfo client; // = new ClientInfo;
	String accountId;
	abstract boolean send();
	
	/**
	 * 
	 */
	public BankingMessage() {
		
	}

	public void setStatus(String status) {
		this.status = status;
	}
	public BankingMessage(String clientId) {
		client.setClientId(clientId);

		// TODO Auto-generated constructor stub
	}

}
