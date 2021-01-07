package myBankApp.dao;

import java.util.List;

import myBankApp.exception.BusinessException;
import myBankApp.model.accounts;
import myBankApp.model.transactions;

public interface bankEmployeeDAO {

	public void employeeLogin();
	public void createUser() throws BusinessException;
	public void createAccount() throws BusinessException;
	public void approveRejectAccount() throws BusinessException;
	public accounts viewAccount() throws BusinessException;
	public List<transactions> viewTransactions() throws BusinessException;
}
