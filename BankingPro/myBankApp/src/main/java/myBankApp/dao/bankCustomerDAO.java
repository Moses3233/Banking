package myBankApp.dao;

import myBankApp.exception.BusinessException;
import myBankApp.model.transactions;

public interface bankCustomerDAO {

public int customerLogin(String username, String password) throws BusinessException;	
public int postTransfer(transactions transEntry, int accountNumber) throws BusinessException;
public int acceptTransfer(int transactionNumber) throws BusinessException;
public int accountWithdrawl(int accountNumber, double amount) throws BusinessException;
public int accountDeposit(int accountNumber, double amount) throws BusinessException;

}
