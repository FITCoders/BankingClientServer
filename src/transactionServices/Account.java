/**
 * 
 */
package transactionServices;

/**
 * @author David
 *
 */
public class Account {

	ClientInfo client;
	public String getId() {
		return client.getClientId();
	}
	public String getName() {
		return client.getClientName();
	}
	public double getBalance() {
		return client.getClientBalance();
	}

	/**
	 * 
	 */
	public Account(String clientId, String clientName, double clientBalance) {
		client = new ClientInfo(clientId, clientName, clientBalance);

		// TODO Auto-generated constructor stub
	}

}
