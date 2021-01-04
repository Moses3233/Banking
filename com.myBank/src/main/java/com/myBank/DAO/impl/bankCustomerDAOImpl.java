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
import com.myBank.model.transactions;

public class bankCustomerDAOImpl {

	private static Logger log=Logger.getLogger(bankAppMain.class);
	Scanner sc = new Scanner(System.in);
	
	public accounts viewAccount(int acctNumber) throws BusinessException {
		accounts thisAccount = null;
		try {
		Connection connection= postgreConnection.getConnection();
		String sql = "select number, username, type, balance, status from bankapp.accounts where number = ?";
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setInt(1, acctNumber);
		ResultSet resultSet = preparedStatement.executeQuery();
		
		if (resultSet.next()) {
			log.info("Account found! Posting details,,,");
			thisAccount = new accounts();
			thisAccount.setNumber(acctNumber);
			thisAccount.setUser(resultSet.getString("username"));
			thisAccount.setType(resultSet.getString("type"));
			thisAccount.setBalance(resultSet.getDouble("balance"));
			thisAccount.setStatus(resultSet.getString("status"));
		} else {
			throw new BusinessException("No Player found with Account Number: " + acctNumber);
		}
		
		} catch (ClassNotFoundException | SQLException e) {
			throw new BusinessException("An error has occured");
			}
		
		return thisAccount;
	}
	
	public void accountWithdrawl(int acctNumber, double amount) throws BusinessException {
		int c;
		double startingBalance;
		try {
			Connection connection= postgreConnection.getConnection();
			String sql = "select number, username, type, balance, status from bankapp.accounts where number = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, acctNumber);
			ResultSet resultSet = preparedStatement.executeQuery();
			
			startingBalance = resultSet.getDouble("balance");
			resultSet.updateDouble("balance", startingBalance-=amount);
			resultSet.updateRow();
			
			Connection connection2= postgreConnection.getConnection();
			String sql2 = "insert into bankapp.transactions(sender,recipient,amount,status) values(?,?,?,?)";
			PreparedStatement preparedStatement2 = connection2.prepareStatement(sql2);

			preparedStatement2.setInt(2, acctNumber);
			preparedStatement2.setInt(3, acctNumber);
			preparedStatement2.setDouble(4, amount);
			preparedStatement2.setString(5, "Completed");

			c = preparedStatement.executeUpdate();
			
			if(c!=0)
				log.info("Successful withdrawl of "+amount+" from the account "+acctNumber+".");
			
			} catch (ClassNotFoundException | SQLException e) {
				throw new BusinessException("An error has occured");
				}
		
	}
	
	public void accountDeposit(int acctNumber, double amount) throws BusinessException {
		int c;
		double startingBalance;
		
		try {
		Connection connection= postgreConnection.getConnection();
		String sql = "select number, username, type, balance, status from bankapp.accounts where number = ?";
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setInt(1, acctNumber);
		ResultSet resultSet = preparedStatement.executeQuery();
		
		startingBalance = resultSet.getDouble("balance");
		resultSet.updateDouble("balance", startingBalance+=amount);
		resultSet.updateRow();
		
		Connection connection2= postgreConnection.getConnection();
		String sql2 = "insert into bankapp.transactions(sender,recipient,amount,status) values(?,?,?,?)";
		PreparedStatement preparedStatement2 = connection2.prepareStatement(sql2);

		preparedStatement2.setInt(2, acctNumber);
		preparedStatement2.setInt(3, acctNumber);
		preparedStatement2.setDouble(4, amount);
		preparedStatement2.setString(5, "Completed");

		c = preparedStatement.executeUpdate();
		
		if(c!=0)
			log.info("Successful deposit of "+amount+" to the account "+acctNumber+".");
	
		} catch (ClassNotFoundException | SQLException e) {
			throw new BusinessException("An error has occured");
			}
		
	}
	
	public int postTransfer(int senderAcct) throws BusinessException {
	
		int c = 0;
		transactions newT = new transactions();
		
		log.info("What is the account number of your intended recipient?");
		newT.setRecipient(Integer.parseInt(sc.nextLine()));
		log.info("How much are you sending?");
		newT.setAmount(Double.parseDouble(sc.nextLine()));

		newT.setStatus("Pending");


		
		try {
			Connection connection= postgreConnection.getConnection();
			String sql = "insert into bankapp.transactions(sender,recipient,amount,status) values(?,?,?,?)";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);

			preparedStatement.setInt(2, senderAcct);
			preparedStatement.setInt(3, newT.getRecipient());
			preparedStatement.setDouble(4, newT.getAmount());
			preparedStatement.setString(5, newT.getStatus());

			c = preparedStatement.executeUpdate();
			
			if(c!=0)
				log.info("Transfer has been completed. The transaction number is: " + newT.getTransnum());
			
		} catch (ClassNotFoundException | SQLException e) {
		throw new BusinessException("An error has occured");
		}
		return c;
		
	}
	
	public void acceptTransfer() throws BusinessException {
		int transferNum, c;
		bankCustomerDAOImpl dao = new bankCustomerDAOImpl();
		log.info("What is the transaction number of your transfer?");
		transferNum = (Integer.parseInt(sc.nextLine()));
		
		try {
		Connection connection= postgreConnection.getConnection();
		String sql = "select sender, recipient, amount from bankapp.transactions where transnum = ?";
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setInt(1, transferNum);

		ResultSet tranInfo = preparedStatement.executeQuery();
		
		dao.accountWithdrawl(tranInfo.getInt("sender"), tranInfo.getDouble("amount"));
		dao.accountDeposit(tranInfo.getInt("recipient"), tranInfo.getDouble("amount"));

		Connection connection2= postgreConnection.getConnection();
		String sql2 = "delete from bankapp.transactions where transnum = ?";
		PreparedStatement preparedStatement2 = connection2.prepareStatement(sql2);
		preparedStatement2.setInt(1, transferNum);

		
		c = preparedStatement2.executeUpdate();
		}catch (ClassNotFoundException | SQLException e) {
			throw new BusinessException("An error has occured");
			}
		
	
	}	
}
