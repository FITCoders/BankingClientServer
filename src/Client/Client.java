package Client;
/**
 * 
 */

import messages.*;
import PKIServices.*;

/**
 * @author David
 *
 */
public class Client {
	
	PKIServices pkiServices;
	
	public boolean connectToServer(){
		System.out.println("Not implemented:Client::connectToServer");
		pkiServices.generateKeyPair();
		return false;
	}
	
	public boolean receive(){
		System.out.println("Not implemented:Client::receive");
		
		pkiServices.decryptMessage();
		
		return false;
	}

	/**
	 * 
	 */
	public Client() {
		System.out.println("Not implemented:Client::Constructor");
		this.pkiServices = new PKIServices();
		// TODO Auto-generated constructor stub
	}
	public void requestBalance() {
		System.out.println("Not implemented:Client::requestBalance");
		
		BalanceRequest requestMessage = new BalanceRequest();
		pkiServices.signMessage();
		

		// TODO Auto-generated method stub
		
	}

}
