package messages;

import java.io.Serializable;

import transactionServices.ClientInfo;

public class BalanceRequest extends BankingMessage implements Serializable {

	public BalanceRequest(String clientId) {
		System.out.println("BalanceRequest::Constructor");
		command = "REQUEST_BALANCE";
		client = new ClientInfo(clientId);
	}

	public String getCommand() {
		return command;
	}

	public String getClientId() {
		return client.getClientId();
	}

	public void setClientId(String accountId) {
		
	    this.accountId = accountId;
	}

	public boolean send(){
		System.out.println("Not implemented:BalanceRequest::send");
		return false;
	}
}
