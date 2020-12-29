package com.myBank.DAO;

import com.myBank.model.accounts;

public interface bankCustomerDAO {


	public accounts viewAccount(int acctNumber);
	public int accountWithdrawl(int acctNumber, double amount);
	public int accountDeposit(int acctNumber, double amount);
	public int postTransfer(String sender, String recipient);
	public int acceptTransfer(int transNum);
	
	
}
