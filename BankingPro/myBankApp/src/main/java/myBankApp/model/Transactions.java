package myBankApp.model;

import java.sql.Date;

public class Transactions {

	private int transnum;
	private String type;
	private int sender;
	private int recipient;
	private double amount;
	private Date date;
	private String status;
	
	public Transactions() {
		
		
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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Override
	public String toString() {
		
	return "Transaction [Transaction Number: " + transnum +", Transaction Type: " + type + ", Sender: " + sender + ", Recipient: " +recipient+ ", Amount: " + amount +", Date: "+date+", Status: "+status+" ]\n";

	}

}
