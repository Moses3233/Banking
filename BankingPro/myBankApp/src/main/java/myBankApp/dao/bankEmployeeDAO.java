package myBankApp.dao;

import java.util.List;

import myBankApp.exception.BusinessException;
import myBankApp.model.transactions;

public interface bankEmployeeDAO {

	public void employeeLogin();
	public void createUser();
	public void createAccount();
	public void approveRejectAccount();
	public void viewAccount();
	public List<transactions> viewTransactions() throws BusinessException;
}
