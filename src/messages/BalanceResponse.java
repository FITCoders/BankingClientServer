package messages;
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
	
	public double getClientBalance() {
		return client.clientBalance;
	}
	
	public boolean send(){
		System.out.println("Not implemented:BalanceResponse::send");
		return false;
	}

}
