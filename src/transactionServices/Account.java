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

	/**
	 * 
	 */
	public Account(String clientId, String clientName, double clientBalance) {
		System.out.println("Account::Constructor");
		client = new ClientInfo(clientId, clientName, clientBalance);

		// TODO Auto-generated constructor stub
	}

}
