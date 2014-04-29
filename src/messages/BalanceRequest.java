package messages;

import java.io.Serializable;

import transactionServices.ClientInfo;

public class BalanceRequest extends BankingMessage implements Serializable {

	public byte []signature;
	public BalanceRequest(String clientId) {
		command = "REQUEST_BALANCE";
		client = new ClientInfo(clientId);
		signature = new byte[50];
		this.clearSignature();
	}
	
	public void clearSignature(){
		for (int i = 0; i < 50; i++) signature[i] = 0;
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
