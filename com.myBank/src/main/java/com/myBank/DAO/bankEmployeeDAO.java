package com.myBank.DAO;

import com.myBank.exceptions.BusinessException;
import com.myBank.model.accounts;
import com.myBank.model.users;

public interface bankEmployeeDAO {

public int createUser(users user) throws BusinessException;	
public int createAccount(accounts account);
public void approveRejectAccount(int acctNumber);
public users viewAccount(String first, String last);
public void employeeLogin(String username, String password);
public void customerLogin(String username, String password);

}
