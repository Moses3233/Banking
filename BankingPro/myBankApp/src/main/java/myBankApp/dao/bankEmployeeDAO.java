package myBankApp.dao;

import java.util.List;

import myBankApp.exception.BusinessException;
import myBankApp.model.accounts;
import myBankApp.model.transactions;
import myBankApp.model.users;

public interface bankEmployeeDAO {

	public void employeeLogin(String username, String password) throws BusinessException;
	public int createUser(users newUser) throws BusinessException;
	public int deleteUser(String username) throws BusinessException;
	public int createAccount(accounts newAccount) throws BusinessException;
	public int deleteAccount(int accountNumber) throws BusinessException;
	public int approveRejectAccount(int accountNumber) throws BusinessException;
	public List<accounts> viewAccounts(String username) throws BusinessException;
	public List<transactions> viewTransactions(int accountNumber) throws BusinessException;
	public List<transactions> viewAllTransactions() throws BusinessException;
	
}
