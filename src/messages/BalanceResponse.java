package messages;

import transactionServices.ClientInfo;

/**
 * 
 */

/**
 * @author David
 *
 */
public class BalanceResponse extends BankingMessage {

	byte byte01 = '1';
	/**
	 * 
	 */
	public BalanceResponse() {
		// TODO Auto-generated constructor stub
	}

	public BalanceResponse(String accountId) {
		client = new ClientInfo(accountId);
	}

	public double getClientBalance() {
		return client.clientBalance;
	}
	public void setClientBalance(double balance) {
		client.clientBalance = balance;
	}
	public void setClientName(String name) {
		client.clientName = name;
	}
	public String getClientId() {
		return client.getClientId();
	}
	public String toString(){
		return client.toString() + " Status : " + status;
	}
	public boolean send(){
		return false;
	}

}
