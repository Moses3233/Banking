package myBankApp.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;

import myBankApp.dao.BankEmployeeDAO;
import myBankApp.dao.impl.BankEmployeeDAOImpl;
import myBankApp.exception.BusinessException;
import myBankApp.model.Accounts;
import myBankApp.model.Transactions;
import myBankApp.model.Users;
import myBankApp.service.BankEmployeeServiceDAO;

public class BankEmployeeServiceDAOImpl implements BankEmployeeServiceDAO{

	private BankEmployeeDAO employeeDAO = new BankEmployeeDAOImpl();
	
	Logger log = Logger.getLogger(BankEmployeeServiceDAOImpl.class);
	Scanner sc = new Scanner(System.in);
	
	public void employeeLogin(String usernameX, String passwordX) throws BusinessException {

		employeeDAO.employeeLogin(usernameX, passwordX);
			
			return;
	}

	public int createUser(Users newUser) throws BusinessException {
		int c;
		c = employeeDAO.createUser(newUser);
		return c;		
	}
	
	public int deleteUser(String username) throws BusinessException {
		int c = 0;
		
		c = employeeDAO.deleteUser(username);
		
		return c;	
	}

	public int deleteAccount(int accountNumber) throws BusinessException {
		int c = 0;

	c = employeeDAO.deleteAccount(accountNumber);
		
return c;	
	}

	public int approveRejectAccount(int accountNumber) throws BusinessException {

		int c;
	c = employeeDAO.approveRejectAccount(accountNumber);
			return c;
	}

	public List<Accounts> viewAccounts(String username) throws BusinessException {
		
		List<Accounts> AccountList = new ArrayList<>();
		
			AccountList = employeeDAO.viewAccounts(username);

			
			return AccountList;	
	}

	public List<Transactions> viewTransactions(int accountNumber) throws BusinessException {

        List<Transactions> TransactionList = employeeDAO.viewTransactions(accountNumber);
	
		return TransactionList;
	}
	
	public List<Transactions> viewAllTransactions() throws BusinessException {

		List<Transactions> TransactionList = new ArrayList<>();
		
		TransactionList = employeeDAO.viewAllTransactions();
	
		return TransactionList;
	}

	@Override
	public int createAccount(Accounts newAccount) throws BusinessException {
		int c = 0;

	c = employeeDAO.createAccount(newAccount);
		
return c;
	}
}
