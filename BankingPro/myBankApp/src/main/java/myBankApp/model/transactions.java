package myBankApp.model;

public class transactions {

	private int transnum;
	private String type;
	private int sender;
	private int recipient;
	private double amount;
	
	public transactions() {
		
		
	}

	public int getTransnum() {
		return transnum;
	}

	public void setTransnum(int transnum) {
		this.transnum = transnum;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getSender() {
		return sender;
	}

	public void setSender(int sender) {
		this.sender = sender;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public int getRecipient() {
		return recipient;
	}

	public void setRecipient(int recipient) {
		this.recipient = recipient;
	}

	@Override
	public String toString() {
		
	return "Transaction [Transaction Number: " + transnum +", Transaction Type: " + type + ", Sender: " + sender + ", Recipient: " +recipient+ ", Amount: " + amount +" ]";

	}
	
}
