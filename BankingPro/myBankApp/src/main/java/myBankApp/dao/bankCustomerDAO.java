package myBankApp.dao;

import myBankApp.exception.BusinessException;

public interface bankCustomerDAO {

public void customerLogin() throws BusinessException;	
public void postTransfer(int accountNumber) throws BusinessException;
public void acceptTransfer() throws BusinessException;
public void accountWithdrawl(int accountNumber, double amount) throws BusinessException;
public void accountDeposit(int accountNumber, double amount) throws BusinessException;

}
