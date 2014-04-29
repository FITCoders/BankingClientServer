package transactionServices;

import java.io.Serializable;

public class ClientInfo implements Serializable {

	public String clientId;
	public String clientName;
	public double clientBalance;

	public ClientInfo(String clientId) {
		System.out.println("ClientInfo::Constructor");
		this.clientId = clientId;
	}
	
	public ClientInfo(String clientId, String clientName) {
		System.out.println("ClientInfo::Constructor");
		this.clientId = clientId;
		this.clientName = clientName;
	}

	public ClientInfo(String clientId, String clientName, double clientBalance) {
		System.out.println("ClientInfo::Constructor");
		this.clientId = clientId;
		this.clientName = clientName;
		this.clientBalance = clientBalance;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		
	    this.clientId = clientId;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		
	    this.clientName = clientName;
	}

	public double getClientBalance(){
		return clientBalance;
	}

	public void setClientBalance(double clientBalance){
		
		this.clientBalance = clientBalance;
	}



	
}
