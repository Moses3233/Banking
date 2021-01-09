package myBankApp.dao;

import java.util.List;

import myBankApp.exception.BusinessException;
import myBankApp.model.accounts;
import myBankApp.model.transactions;
import myBankApp.model.users;

public interface bankEmployeeDAO {

	public void employeeLogin() throws BusinessException;
	public void createUser(users newUser) throws BusinessException;
	public void createAccount(accounts newAccount) throws BusinessException;
	public void approveRejectAccount() throws BusinessException;
	public accounts viewAccount() throws BusinessException;
	public List<transactions> viewTransactions() throws BusinessException;
}
