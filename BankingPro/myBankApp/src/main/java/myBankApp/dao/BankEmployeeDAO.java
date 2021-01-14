package myBankApp.dao;

import java.util.List;

import myBankApp.exception.BusinessException;
import myBankApp.model.Accounts;
import myBankApp.model.Transactions;
import myBankApp.model.Users;

public interface BankEmployeeDAO {

	public void employeeLogin(String username, String password) throws BusinessException;
	public int createUser(Users newUser) throws BusinessException;
	public int deleteUser(String username) throws BusinessException;
	public int createAccount(Accounts newAccount) throws BusinessException;
	public int deleteAccount(int accountNumber) throws BusinessException;
	public int approveRejectAccount(int accountNumber) throws BusinessException;
	public List<Accounts> viewAccounts(String username) throws BusinessException;
	public List<Transactions> viewTransactions(int accountNumber) throws BusinessException;
	public List<Transactions> viewAllTransactions() throws BusinessException;
	
}
