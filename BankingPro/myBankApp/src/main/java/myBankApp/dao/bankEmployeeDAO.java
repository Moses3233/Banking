package myBankApp.dao;

import java.util.List;

import myBankApp.exception.BusinessException;
import myBankApp.model.accounts;
import myBankApp.model.transactions;
import myBankApp.model.users;

public interface bankEmployeeDAO {

	public void employeeLogin(String username, String password) throws BusinessException;
	public void createUser(users newUser) throws BusinessException;
	public void deleteUser(String username) throws BusinessException;
	public void createAccount(accounts newAccount) throws BusinessException;
	public void deleteAccount(int accountNumber) throws BusinessException;
	public void approveRejectAccount(int accountNumber) throws BusinessException;
	public List<accounts> viewAccounts(String username) throws BusinessException;
	public List<transactions> viewTransactions(int accountNumber) throws BusinessException;
}
