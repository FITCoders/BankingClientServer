package messages;
/**
 * 
 */

/**
 * @author David
 *
 */
public abstract class BankingMessage {
	
	abstract boolean send();
	
	/**
	 * 
	 */
	public BankingMessage() {
		System.out.println("Not implemented:BankingMessage::Constructor");

		// TODO Auto-generated constructor stub
	}

}
