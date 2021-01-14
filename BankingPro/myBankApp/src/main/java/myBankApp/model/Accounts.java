package myBankApp.model;

public class Accounts {


	private int acctnum;
	private String username;
	private String type;
	private double balance;
	private String status;
	
	public Accounts() {
		
		
	}

	public int getAcctnum() {
		return acctnum;
	}

	public void setAcctnum(int acctnum) {
		this.acctnum = acctnum;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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
	
	@Override
	public String toString() {
		
	return "Account [Account Number: " + acctnum +", Username: " + username + ", Account Type: " + type +  ", Balance: " + balance +", Status: " +status+" ]\n";

	}
	
	
}
