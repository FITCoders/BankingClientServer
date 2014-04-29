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

	/**
	 * 
	 */
	public BalanceResponse() {
		System.out.println("Not implemented:BalanceResponse::Constructor");
		// TODO Auto-generated constructor stub
	}

	public BalanceResponse(String accountId) {
		System.out.println("Not implemented:BalanceResponse::Constructor");
		client = new ClientInfo(accountId);
	}

	public double getClientBalance() {
		return client.clientBalance;
	}
	public void setClientBalance(double balance) {
		client.clientBalance = balance;
	}
	public String getClientId() {
		return client.getClientId();
	}
	
	public boolean send(){
		System.out.println("Not implemented:BalanceResponse::send");
		return false;
	}

}
