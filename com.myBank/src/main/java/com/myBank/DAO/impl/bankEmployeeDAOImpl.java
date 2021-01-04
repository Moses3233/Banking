package com.myBank.DAO.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.myBank.DAO.db.postgreConnection;
import com.myBank.exceptions.BusinessException;
import com.myBank.main.bankAppMain;
import com.myBank.model.accounts;
import com.myBank.model.transactions;
import com.myBank.model.users;

public class bankEmployeeDAOImpl {

	private static Logger log=Logger.getLogger(bankAppMain.class);
	Scanner sc = new Scanner(System.in);

	
	public String createUser() throws BusinessException {
		users newUser = new users();
		
		log.info("What is your new username?");
		newUser.setUsername(sc.nextLine());
		log.info("What is your new password?");
		newUser.setPassword(sc.nextLine());
		log.info("What is your first name?");
		newUser.setfName(sc.nextLine());
		log.info("What is your last name?");
		newUser.setlName(sc.nextLine());
		
		newUser.setRole("Customer");
		
		log.info("What is your email?");
		newUser.setEmail(sc.nextLine());
		
		try {
			Connection connection= postgreConnection.getConnection();
			String sql = "insert into bankapp.users(username,password,fname,lname,role,email) values(?,?,?,?,?,?)";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);

			preparedStatement.setString(1, newUser.getUsername());
			preparedStatement.setString(2, newUser.getPassword());
			preparedStatement.setString(3, newUser.getfName());
			preparedStatement.setString(4, newUser.getlName());
			preparedStatement.setString(5, newUser.getRole());
			preparedStatement.setString(6, newUser.getEmail());

			int c = preparedStatement.executeUpdate();
		if(c!=0)
			log.info("User added to Bank Registry");
		} catch (ClassNotFoundException | SQLException e) {
		throw new BusinessException("An error has occured");
		}
		return newUser.getUsername();
	}	
	
	
	public int createAccount(String username) throws BusinessException{
		int c,acctChoice;
		double startBalance = 0.0;
		String acctType = "";
		
		do {
		log.info("Is this a Savings or Checking? Enter 1 for \"Savings\" or 2 for \"Checking\" ");
		acctChoice = Integer.parseInt(sc.nextLine());
		
		switch(acctChoice) {
		case 1:
			acctType = "Savings";
			break;
		case 2:
			acctType = "Checking";
			break;
			
		default:
			log.info("Invalid input. Try again...\n");
			break;
		}
		}while(acctChoice!=1 && acctChoice!=2);
		
		
		do {
		log.info("What is your account's balance?");
		startBalance = Double.parseDouble(sc.nextLine());
		
		if(startBalance<300.0)
			log.info("The account cannot be opened with a starting value less than $300.");
			
		}while(startBalance<300.00);
		

		
		try {
			Connection connection= postgreConnection.getConnection();
			String sql = "insert into bankapp.accounts(username,type,balance,status) values(?,?,?,?)";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);

			preparedStatement.setString(2, username);
			preparedStatement.setString(3, acctType);
			preparedStatement.setDouble(4, startBalance);
			preparedStatement.setString(5, "PENDING");

			c = preparedStatement.executeUpdate();
			
			if(c!=0)
				log.info("Account has been entered into the system! A bank employee will review to approve the account.");
		
		} catch (ClassNotFoundException | SQLException e) {
		throw new BusinessException("An error has occured");
		}
		
		return c;
	}
	
	public int approveRejectAccount()throws BusinessException {
		int AR =0;
		
		int acctNumber = 0;
		
		log.info("What is your account number?");
		acctNumber = Integer.parseInt(sc.nextLine());
		
		try {
			Connection connection= postgreConnection.getConnection();
			String sql = "select number, username, type, balance, status from bankapp.accounts where number = ? AND status = 'PENDING' ";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, acctNumber);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				int judge;
				do {
				log.info("Enter \"1\" to Approve or \"2\" to Reject");
				
				 judge = Integer.parseInt(sc.nextLine());
				
				switch(judge) {
				case 1:
					Connection connection1= postgreConnection.getConnection();
					String sql1 = "update bankapp.accounts set status = ACTIVE where number = acctNumber";
					PreparedStatement preparedStatement1 = connection1.prepareStatement(sql1);

					AR = preparedStatement1.executeUpdate();
					break;
					
				case 2:
					Connection connection2= postgreConnection.getConnection();
					String sql2 = "delete from bankapp.accounts where number = acctNumber";
					PreparedStatement preparedStatement2 = connection2.prepareStatement(sql2);

					AR = preparedStatement2.executeUpdate();
					break;
					
				default:
					log.info("Not a valid option. Try again.");
				}
				
				}while(judge!=1 && judge!=2);
				
					return AR;
					
			} else {
				throw new BusinessException("No Employee found with entered credentials");
			}
		
		} catch (ClassNotFoundException | SQLException e) {
		throw new BusinessException("An error has occured");
		}
		
	}
	
	
	public accounts viewAccount()throws BusinessException {
	
		int acctNumber = 0;
		
		log.info("What is the account number?");
		acctNumber = Integer.parseInt(sc.nextLine());
		
	accounts Account = null;
	try (Connection connection = postgreConnection.getConnection()) {
		String sql="select number, username, type, balance, status from bankapp.accounts where number=?";
		PreparedStatement preparedStatement=connection.prepareStatement(sql);
		preparedStatement.setInt(1, acctNumber);
		ResultSet resultSet=preparedStatement.executeQuery();
		if(resultSet.next()) {
			Account =new accounts();
			Account.setNumber(acctNumber);
			Account.setUser(resultSet.getString("username"));
			Account.setType(resultSet.getString("type"));
			Account.setBalance(resultSet.getDouble("balance"));
			Account.setStatus(resultSet.getString("status"));
		}else {
			throw new BusinessException("No Player found with Account No: "+acctNumber);
		}
	}catch (ClassNotFoundException | SQLException e) {
		throw new BusinessException("Internal error occured contact SYSADMIN ");
	}
	return Account;
	}
	
	public void employeeLogin() throws BusinessException {
		
		String usernameX = "";
		String passwordX = "";
		
		log.info("What is your username?");
		usernameX = sc.nextLine();
		log.info("What is your password?");
		passwordX = sc.nextLine();
		
		try {
			Connection connection= postgreConnection.getConnection();
			String sql = "select users.username, users.password, accounts.username, accounts.number from bankapp.users join bankapp.accounts on accounts.username = users.username where users.role = 'Employee' AND user.username = ? AND user.password = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, usernameX);
			preparedStatement.setString(2, passwordX);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				log.info("Login succeeded");
			} else {
				throw new BusinessException("No Employee found with entered credentials");
			}
		
		} catch (ClassNotFoundException | SQLException e) {
		throw new BusinessException("An error has occured");
		}
		
	}
	
	public int customerLogin() throws BusinessException {
		
		String usernameX = "";
		String passwordX = "";
		
		log.info("What is your username?");
		usernameX = sc.nextLine();
		log.info("What is your password?");
		passwordX = sc.nextLine();
		
		try {
			Connection connection= postgreConnection.getConnection();
			String sql = "select users.username, users.password, accounts.username, accounts.number from bankapp.users join bankapp.accounts on accounts.username = users.username where users.role = 'Customer' AND username = ? AND password = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			
			preparedStatement.setString(1, usernameX);
			preparedStatement.setString(2, passwordX);
			
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				log.info("Login Succeeded");
				return resultSet.getInt("number");
			} else {
				throw new BusinessException("No Customer found with entered credentials");
			}
		
		} catch (ClassNotFoundException | SQLException e) {
		throw new BusinessException("An error has occured");
		}
		
		
	}


	
	public List<transactions> viewTransactions(int accountNumber) throws BusinessException {
		List<transactions> transactionLog = new ArrayList<>();
	
		try (Connection connection = postgreConnection.getConnection()) {
			String sql="select transnum, sender, recipient, amount, status from bankapp.transactions where sender=? OR recipient =?";
			PreparedStatement preparedStatement=connection.prepareStatement(sql);
			preparedStatement.setInt(1, accountNumber);
			preparedStatement.setInt(2, accountNumber);
			ResultSet resultSet=preparedStatement.executeQuery();
			
			while(resultSet.next()) {
				transactions transEntry =new transactions();
				transEntry.setTransnum(resultSet.getInt("transnum"));
				transEntry.setSender(resultSet.getInt("sender"));
				transEntry.setRecipient(resultSet.getInt("recipient"));
				transEntry.setAmount(resultSet.getDouble("amount"));
				transEntry.setStatus(resultSet.getString("status"));
				transactionLog.add(transEntry);
			}
			if(transactionLog.size()==0)
			{
				throw new BusinessException("No transactions done by the customer so far");
			}
		}catch (ClassNotFoundException | SQLException e) {
			throw new BusinessException("Internal error occured contact SYSADMIN ");
		}
		return transactionLog;
	}
	
	}
	
	
