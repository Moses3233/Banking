package com.myBank.model;

public class accounts {

	private int number;
	private String user;
	private String type;
	private double balance;
	private String status;
	
	
	public accounts() {}

	public accounts(int number, String user, String type, double balance, String status) {
		this.balance = balance;
		this.status= status;
		this.number = number;
		this.type=type;
		this.user=user;	
	}

	public int getNumber() {
		return number;
	}


	public void setNumber(int number) {
		this.number = number;
	}


	public String getUser() {
		return user;
	}


	public void setUser(String user) {
		this.user = user;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public double getBalance() {
		return balance;
	}


	public void setBalance(double balance) {
		this.balance = balance;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}
	
	public String toString() {
		return "[ Account Number: "+number+" User: "+user+" Type: "+type+" Balance: "+balance+"Status: "+status+" ]";
		
	}
	
}
