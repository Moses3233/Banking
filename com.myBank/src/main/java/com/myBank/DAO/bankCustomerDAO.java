package com.myBank.DAO;

import com.myBank.model.accounts;
import com.myBank.model.transactions;

public interface bankCustomerDAO {


	public accounts viewAccount(int acctNumber);
	public int accountWithdrawl(int acctNumber, double amount);
	public int accountDeposit(int acctNumber, double amount);
	public int postTransfer(transactions newT);
	public int acceptTransfer(int transNum);
	
	
}
