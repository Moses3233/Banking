package myBankApp.dao;

import myBankApp.exception.BusinessException;
import myBankApp.model.transactions;

public interface bankCustomerDAO {

public int customerLogin(String username, String password) throws BusinessException;	
public void postTransfer(transactions transEntry, int accountNumber) throws BusinessException;
public void acceptTransfer(int transactionNumber) throws BusinessException;
public void accountWithdrawl(int accountNumber, double amount) throws BusinessException;
public void accountDeposit(int accountNumber, double amount) throws BusinessException;

}
