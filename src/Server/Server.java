package Server;
/**
 * 
 */
import java.util.List;
import messages.*;
import transactionServices.*;
import PKIServices.*;

/**
 * @author David
 *
 */
public class Server {
	/**
	 * 
	 */
	
	List<Account> accounts;
	PKIServices pkiServices;
	
	public void addAccount(Account account){
		System.out.println("Not implemented:Server::addAccount");
		return;
	}

	public boolean receive(){
		System.out.println("Not implemented:Server::receive");
		pkiServices.validateMessage();
		
		BalanceResponse response = new BalanceResponse();
		pkiServices.encryptMessage();
		
		return false;
	}
	
	
	public Server() {
		System.out.println("Not implemented:Server::Constructor");
		this.pkiServices = new PKIServices();
		accounts = null;

	}

}
