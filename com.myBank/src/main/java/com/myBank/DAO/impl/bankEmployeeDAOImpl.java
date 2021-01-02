package com.myBank.DAO.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.myBank.DAO.db.postgreConnection;
import com.myBank.exceptions.BusinessException;
import com.myBank.main.bankAppMain;
import com.myBank.model.accounts;
import com.myBank.model.users;

public class bankEmployeeDAOImpl {

	private static Logger log=Logger.getLogger(bankAppMain.class);
	Scanner sc = new Scanner(System.in);

	
	public int createUser() throws BusinessException {
		int c = 0;
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

			c = preparedStatement.executeUpdate();
		
		} catch (ClassNotFoundException | SQLException e) {
		throw new BusinessException("An error has occured");
		}
		return c;
	}	
	
	
	public int createAccount(accounts account) throws BusinessException{
		int c;
		
		try {
			Connection connection= postgreConnection.getConnection();
			String sql = "insert into bankapp.accounts(number,username,type,balance,status) values(?,?,?,?,?)";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);

			preparedStatement.setInt(1, account.getNumber());
			preparedStatement.setString(2, account.getUser());
			preparedStatement.setString(3, account.getType());
			preparedStatement.setDouble(4, account.getBalance());
			preparedStatement.setString(5, "PENDING");

			c = preparedStatement.executeUpdate();
		
		} catch (ClassNotFoundException | SQLException e) {
		throw new BusinessException("An error has occured");
		}
		
		return c;
	}
	
	public int approveRejectAccount(int acctNumber)throws BusinessException {
		int AR =0;
		try {
			Connection connection= postgreConnection.getConnection();
			String sql = "select number, username, type, balance, status from bankapp.accounts where number = acctNumber AND status = PENDING ";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				int judge;
				Scanner scan = new Scanner(System.in);
				do {
				log.info("Enter \"1\" to Approve or \"2\" to Reject");
				
				 judge = Integer.parseInt(scan.nextLine());
				
				switch(judge) {
				case 1:
					Connection connection1= postgreConnection.getConnection();
					String sql1 = "update bankapp.accounts set status = Approved where number = acctNumber";
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
	
	
	public accounts viewAccount(int acctNumber)throws BusinessException {
	
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
	
	public void employeeLogin(String username, String password) throws BusinessException {
		
		try {
			Connection connection= postgreConnection.getConnection();
			String sql = "select username, password from bankapp.users where role = Employee";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
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
	
	public void customerLogin() throws BusinessException {
		
		String usernameX = "";
		String passwordX = "";
		
		log.info("What is your username?");
		usernameX = sc.nextLine();
		log.info("What is your password?");
		passwordX = sc.nextLine();
		
		try {
			Connection connection= postgreConnection.getConnection();
			String sql = "select username, password from bankapp.users where role = 'Customer' AND username = ? AND password = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			
			preparedStatement.setString(1, usernameX);
			preparedStatement.setString(2, passwordX);
			
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				log.info("Login Succeeded");
			} else {
				throw new BusinessException("No Customer found with entered credentials");
			}
		
		} catch (ClassNotFoundException | SQLException e) {
		throw new BusinessException("An error has occured");
		}
		
	}
	
	
}
