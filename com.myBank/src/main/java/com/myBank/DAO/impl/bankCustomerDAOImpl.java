package com.myBank.DAO.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
	
	public accounts viewAccount(int acctNumber) {
		
	
		
	}
	
	public int accountWithdrawl(int acctNumber, double amount) {
		
		
		
	}
	
	public int accountDeposit(int acctNumber, double amount) {
		
		
		
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
	
	public int acceptTransfer(int transNum) {
	
		
		
	}
	
	
}
