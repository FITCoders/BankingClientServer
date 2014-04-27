/**
 * 
 */
package transactionServices;

import transactionServices.ClientInfo;

/**
 * @author David
 *
 */
public class Account {

	ClientInfo client;

	/**
	 * 
	 */
	public Account(String clientId, String clientName, double clientBalance) {
		System.out.println("Account::Constructor");
		client = new ClientInfo(clientId, clientName, clientBalance);

		// TODO Auto-generated constructor stub
	}

}
