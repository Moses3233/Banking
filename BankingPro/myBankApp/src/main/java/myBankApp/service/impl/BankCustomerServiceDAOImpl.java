package myBankApp.service.impl;


import org.apache.log4j.Logger;

import myBankApp.dao.BankCustomerDAO;
import myBankApp.dao.impl.BankCustomerDAOImpl;
import myBankApp.exception.BusinessException;
import myBankApp.model.Transactions;
import myBankApp.service.BankCustomerServiceDAO;

public class BankCustomerServiceDAOImpl implements BankCustomerServiceDAO{
	
	private BankCustomerDAO customerDAO = new BankCustomerDAOImpl();
	
	Logger log = Logger.getLogger(BankCustomerServiceDAOImpl.class);

	public int customerLogin(String usernameX, String passwordX) throws BusinessException {
		int loginAcctNum = 0;

		try {
			loginAcctNum = customerDAO.customerLogin(usernameX, passwordX);
		} catch (BusinessException e) {
		}
		return loginAcctNum;
	}

	public int postTransfer(Transactions transEntry, int accountNumber) throws BusinessException {
		int c = 0;
	
		c = customerDAO.postTransfer(transEntry, accountNumber);

return c;	
	}

	public int acceptTransfer(int transactionNum) throws BusinessException {
		int c2 = 0;
	c2 = customerDAO.acceptTransfer(transactionNum);
	return c2;	
	}

	public int accountWithdrawl(int accountNumber, double amount) throws BusinessException {
	
		int c1 = 0;
		c1 = customerDAO.accountWithdrawl(accountNumber, amount);
	return c1;
	}

	public int accountDeposit(int accountNumber, double amount) throws BusinessException {
			int c1 = 0;
c1 = customerDAO.accountDeposit(accountNumber, amount);
		return c1;
	}



}
