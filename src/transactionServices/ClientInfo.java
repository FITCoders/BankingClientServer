package transactionServices;

public class ClientInfo {

	public int clientId;
	public String clientName;
	public int clientBalance;

	public int getClientId() {
		clientId = 1;
		return clientId;
	}

	public void setClientId(int clientId) {
		
	    this.clientId = clientId;
	}

	public String getClientName() {
		clientName = "Sami";
		return clientName;
	}

	public void setClientName(String clientName) {
		
	    this.clientName = clientName;
	}

	public int getClientBalance(){
		clientBalance = 4500;
		return clientBalance;
	}

	public void setClientBalance(int clientBalance){
		
		this.clientBalance = clientBalance;
	}



	
}
