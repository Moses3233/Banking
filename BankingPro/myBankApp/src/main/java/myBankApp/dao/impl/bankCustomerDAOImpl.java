package myBankApp.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import org.apache.log4j.Logger;

import myBankApp.dao.bankCustomerDAO;
import myBankApp.dao.dbutil.postgresqlConnection;
import myBankApp.exception.BusinessException;
import myBankApp.model.accounts;
import myBankApp.model.transactions;

public class bankCustomerDAOImpl implements bankCustomerDAO{
	
	Logger log = Logger.getLogger(bankCustomerDAOImpl.class);
	Scanner sc = new Scanner(System.in);
	Random rand = new Random();

	public int customerLogin(String usernameX, String passwordX) throws BusinessException {
		int loginAcctNum = 0;
		List<Integer> acctNumList = new ArrayList<>();

		try {		
			try(Connection connection=postgresqlConnection.getConnection()){
				String sql = "SELECT u.username, u.\"password\", u.role FROM \"bankApp\".users u where role = 'Customer' AND u.username = ? AND u.\"password\" = ? ;";
				PreparedStatement preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setString(1, usernameX);
				preparedStatement.setString(2, passwordX);
				ResultSet resultSet = preparedStatement.executeQuery();
				
				try {	
					if(resultSet.next()) {
					log.info("Login succeeded! Welcome, " + usernameX);		
					
					String sql1 = "SELECT acctnum FROM \"bankApp\".accounts where username = ? ;";
					PreparedStatement preparedStatement1 = connection.prepareStatement(sql1);
					preparedStatement1.setString(1, usernameX);
					ResultSet resultSet1 = preparedStatement1.executeQuery();
			
					while(resultSet1.next()) {
						log.info(resultSet1.getInt("acctnum"));
						Integer acctNum = (Integer) resultSet1.getInt("acctnum");
						acctNumList.add(acctNum);
					}		
					log.info("Which account are you looking into?");
					int anAcctNum = Integer.parseInt(sc.nextLine());
					
					if(acctNumList.contains((Integer)anAcctNum)){
						loginAcctNum = anAcctNum;
						log.info("");
					}
					
					}else {
						throw new BusinessException("No customer with these credentials exist in the database");
					}
				} catch (BusinessException e) {log.info("There may be a system error...");
				}	
				
			} catch (ClassNotFoundException | SQLException e) {
				throw new BusinessException("An Internal error has occured");
			}
		} catch (BusinessException e) {
		}
		return loginAcctNum;
	}

	public void postTransfer(transactions transEntry, int accountNumber) throws BusinessException {

		int c = 0;
		double acctBalance = 0.0, sumSent =0.0;
		
		try(Connection connection=postgresqlConnection.getConnection()){	
		
		String sql = "select * from \"bankApp\".accounts where acctnum = ?";
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setInt(1, accountNumber);
		ResultSet resultSet = preparedStatement.executeQuery();
		acctBalance = resultSet.getDouble("balance");
		 
		do {
		log.info("What is the amount being sent?");
		sumSent = Double.parseDouble(sc.nextLine());
		if(sumSent<=0)
		log.info("The amount being sent cannot be lower than or equal to 0");
		if(acctBalance < sumSent)
		log.info("You don't have enough in the account to send this");	
		}while(sumSent<0 && acctBalance < sumSent);


			String sql1 = "insert into \"bankApp\".transactions(type, sender, recipient, amount, date, status) values(?, ?, ?, ?, ?, ?, ?)";
			 PreparedStatement preparedStatement1= connection.prepareStatement(sql1);
			 
			 preparedStatement1.setString(1, transEntry.getType());
			 preparedStatement1.setInt(2, transEntry.getSender());
			 preparedStatement1.setInt(3, transEntry.getRecipient());
			 preparedStatement1.setDouble(4, sumSent);
			 preparedStatement1.setDate(5, transEntry.getDate());
			 preparedStatement1.setString(6, transEntry.getStatus());
			 
			 c=preparedStatement1.executeUpdate();
			 
			 if(c==1) {
					log.info("The transfer has been initiated. It will be waiting for acceptance.");
			 }
			 
		} catch(ClassNotFoundException | SQLException e) {
			throw new BusinessException("An Internal error has occured");
		}
		
return;	

	}

	public void acceptTransfer(int transactionNum) throws BusinessException {

		try(Connection connection=postgresqlConnection.getConnection()){	
		
		//Transaction information	
		String sql = "select * from \"bankApp\".transactions where transnum = ?";
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setInt(1, transactionNum);
		ResultSet resultSet = preparedStatement.executeQuery();
		
		//Recipient balance
		Connection connection0=postgresqlConnection.getConnection();
		String sql0 = "select balance from \"bankApp\".accounts where acctnum = ?";
		PreparedStatement preparedStatement0 = connection0.prepareStatement(sql0);
		preparedStatement0.setInt(1, resultSet.getInt("recipient") );
		ResultSet resultSet0 = preparedStatement0.executeQuery();	
		
		//Query run
		if(resultSet.next() && resultSet0.next()) {
			
			Connection connection1=postgresqlConnection.getConnection();
			String sql1 = "update \"bankApp\".accounts set balance = ? where acctnum = ?";
			PreparedStatement preparedStatement1= connection1.prepareStatement(sql1);
			preparedStatement1.setDouble(1,resultSet0.getDouble("balance") + resultSet.getDouble("amount"));
			preparedStatement1.setInt(2, resultSet.getInt("sender"));
			int c = preparedStatement1.executeUpdate(sql1);
			if (c==1)
			log.info("Sender account has been updated!");
			
			Connection connection2=postgresqlConnection.getConnection();
			String sql2 = "update \"bankApp\".accounts set balance = ? where acctnum = ?";
			PreparedStatement preparedStatement2= connection2.prepareStatement(sql2);
			preparedStatement2.setDouble(1,resultSet0.getDouble("balance") - resultSet.getDouble("amount"));
			preparedStatement2.setInt(2, resultSet.getInt("recipient"));
			int c1 = preparedStatement2.executeUpdate(sql2);
			if (c1==1)
			log.info("Recipient account has been updated!");
			
			log.info("What is the date of this transaction being accepted? (Format: YYYY-MM-DD)");
			String todayDate = sc.nextLine();
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			sdf.setLenient(false);
			Date theDate = null;
			try {
			 theDate = sdf.parse(todayDate);
			} catch (ParseException e6) {
				log.info("Parsing Error");
			}
			
			//Transaction information	
			String sql3 = "update \"bankApp\".transactions set status = ?, date = ? where transnum = ?";
			PreparedStatement preparedStatement3 = connection.prepareStatement(sql3);
			 preparedStatement3.setInt(1,transactionNum);
			 preparedStatement3.setDate(2, (java.sql.Date) theDate);
			 preparedStatement3.setString(3, "Completed");
			 int c2 = preparedStatement3.executeUpdate();
			
			if (c2==1)
				log.info("Transfer has been completed!");
			
	}
		else {log.info("No transfer was found with this number.");}
		} catch(ClassNotFoundException | SQLException e) {
			throw new BusinessException("An Internal error has occured");
		}
		
	}

	public void accountWithdrawl(int accountNumber, double amount) throws BusinessException {
		int c = 0;
		int c1 = 0;
		accounts oneAccount = new accounts();
		
		
	try(Connection connection=postgresqlConnection.getConnection()){	
	
		String sql = "select * from \"bankApp\".accounts where acctnum = ?";
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setInt(1, accountNumber);
		ResultSet resultSet = preparedStatement.executeQuery();
		
		if(resultSet.next()) {
			
		oneAccount.setAcctnum(accountNumber);
		oneAccount.setUsername(resultSet.getString("username"));
		oneAccount.setType(resultSet.getString("type"));
		oneAccount.setBalance(resultSet.getDouble("balance") - amount);
		oneAccount.setStatus(resultSet.getString("status"));	
		
		log.info("What is the date of the withdrawl being done? (Format: YYYY-MM-DD)");
		String todayDate = sc.nextLine();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf.setLenient(false);
		Date theDate = null;
		try {
		theDate = sdf.parse(todayDate);
		} catch (ParseException e6) {
			log.info("Parsing Error");
		}	
		
		 java.sql.Date theRealDate = new java.sql.Date(theDate.getTime());
		 
	String sql1 = "update \"bankApp\".accounts set balance = ? where acctnum = ?";
	PreparedStatement preparedStatement1 = connection.prepareStatement(sql1);
	preparedStatement1.setDouble(1, oneAccount.getBalance());
	preparedStatement1.setInt(2, accountNumber);
	c = preparedStatement1.executeUpdate();
		
	if(c==1) {
		log.info("Transaction completed.");
	}
	
	String sql2 = "insert into \"bankApp\".transactions( type, sender, recipient, amount, date, status) values( ?, ?, ?, ?, ?, ?)";
	 PreparedStatement preparedStatement2= connection.prepareStatement(sql2);
	 
	 preparedStatement2.setString(1, "Withdrawl");
	 preparedStatement2.setInt(2, 0);
	 preparedStatement2.setInt(3, accountNumber);
	 preparedStatement2.setDouble(4, amount);
	 preparedStatement2.setDate(5, theRealDate);
	 preparedStatement2.setString(6, "Completed");

	 c1=preparedStatement2.executeUpdate();

	if(c1==1) {
		log.info("Transaction has been logged.");
	}
		}
	} catch(ClassNotFoundException | SQLException e) {
		log.info(e);
		throw new BusinessException("An Internal error has occured");
	}
	
	}

	public void accountDeposit(int accountNumber, double amount) throws BusinessException {
			int c = 0;
			int c1 = 0;
			accounts oneAccount = new accounts();
			
		try(Connection connection=postgresqlConnection.getConnection()){	
		
			
			String sql = "select * from \"bankApp\".accounts where acctnum = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, accountNumber);
			ResultSet resultSet = preparedStatement.executeQuery();
			
			if(resultSet.next()) {
			oneAccount.setAcctnum(accountNumber);
			oneAccount.setUsername(resultSet.getString("username"));
			oneAccount.setType(resultSet.getString("type"));
			oneAccount.setBalance(resultSet.getDouble("balance") + amount);
			oneAccount.setStatus(resultSet.getString("status"));	
			}
			
		String sql1 = "update \"bankApp\".accounts set balance = ? where acctnum = ?";
		PreparedStatement preparedStatement1 = connection.prepareStatement(sql1);
		preparedStatement1.setDouble(1, oneAccount.getBalance());
		preparedStatement1.setInt(2, accountNumber);
		c = preparedStatement1.executeUpdate();
		
		
		log.info("What is the date of the deposit being done? (Format: YYYY-MM-DD)");
		String todayDate = sc.nextLine();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf.setLenient(false);
		Date theDate = null;
		try {
		theDate = sdf.parse(todayDate);
		} catch (ParseException e6) {
			log.info("Parsing Error");
		}	
		
		 java.sql.Date theRealDate = new java.sql.Date(theDate.getTime());
		
		String sql2 = "insert into \"bankApp\".transactions(type, sender, recipient, amount, date, status) values(?, ?, ?, ?, ?, ?)";
		 PreparedStatement preparedStatement2= connection.prepareStatement(sql2);
		 
		 preparedStatement2.setString(1, "Deposit");
		 preparedStatement2.setInt(2, accountNumber);
		 preparedStatement2.setInt(3, 0);
		 preparedStatement2.setDouble(4, amount);
		 preparedStatement2.setDate(5, theRealDate);
		 preparedStatement2.setString(6, "Completed");
		 
		 c1=preparedStatement2.executeUpdate();	 

		if(c==1) {
		log.info("Transaction completed.");	
		}
			
		
		if(c1==1) {
			log.info("Transaction has been logged.");
			log.info("");
		}
		
		} catch(ClassNotFoundException | SQLException e) {
			throw new BusinessException("An Internal error has occured");
		}
		
	}



}
