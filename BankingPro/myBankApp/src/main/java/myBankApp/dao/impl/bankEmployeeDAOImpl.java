package myBankApp.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import org.apache.log4j.Logger;

import myBankApp.dao.bankEmployeeDAO;
import myBankApp.dao.dbutil.postgresqlConnection;
import myBankApp.exception.BusinessException;
import myBankApp.model.accounts;
import myBankApp.model.transactions;
import myBankApp.model.users;

public class bankEmployeeDAOImpl implements bankEmployeeDAO{


	Logger log = Logger.getLogger(bankEmployeeDAOImpl.class);
	Scanner sc = new Scanner(System.in);
	Random rand = new Random();
	
	public void employeeLogin() throws BusinessException {
		String usernameX = "";
		String passwordX = "";
		
		try(Connection connection=postgresqlConnection.getConnection()){
			
			log.info("What is your username?");
			usernameX = sc.nextLine();
			log.info("What is your password?");
			passwordX = sc.nextLine();
			
			String sql = "select * from \"bankApp\".users where role = EMPLOYEE AND username = ? AND password = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, usernameX);
			preparedStatement.setString(2, passwordX);
			ResultSet resultSet = preparedStatement.executeQuery();
			
			if(resultSet.next()) {
			log.info("Login succeeded! Welcome, " + usernameX);
			}else {
				throw new BusinessException("No employee with these credentials exist in the database");
			}
			
		} catch (ClassNotFoundException | SQLException e) {
			throw new BusinessException("An Internal error has occured");
		}
			
			return;
	}

	public void createUser(users newUser) throws BusinessException {
		int c = 0;
		
		try(Connection connection=postgresqlConnection.getConnection()){
			String sql = "INSERT INTO \"bankApp\".users(username, password, email, role, fname, lname, gender, age, address, city, state, zip, country) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			 PreparedStatement preparedStatement= connection.prepareStatement(sql);
			 
			 preparedStatement.setString(1, newUser.getUsername());
			 preparedStatement.setString(2, newUser.getPassword());
			 preparedStatement.setString(3, newUser.getEmail());
			 preparedStatement.setString(4, newUser.getRole());
			 preparedStatement.setString(5, newUser.getFname());
			 preparedStatement.setString(6, newUser.getLname());
			 preparedStatement.setString(7, newUser.getGender());
			 preparedStatement.setInt(8, newUser.getAge());
			 preparedStatement.setString(9, newUser.getAddress());
			 preparedStatement.setString(10, newUser.getCity());
			 preparedStatement.setString(11, newUser.getState());
			 preparedStatement.setString(12, newUser.getZip());
			 preparedStatement.setString(13, newUser.getCountry());
			 
			 c=preparedStatement.executeUpdate();
			 
			 if(c==1)
				 log.info("Congratulations! You successfully created an account!");
			 
		} catch(ClassNotFoundException | SQLException e) {
			throw new BusinessException("An Internal error has occured");
		}
return;		
	}

	public void createAccount(accounts newAccount) throws BusinessException {
		int c = 0;

		try(Connection connection=postgresqlConnection.getConnection()){
			String sql = "insert into \"bankApp\".accounts(acctnum, username, type, balance, status) values(?, ?, ?, ?, ?)";
			 PreparedStatement preparedStatement= connection.prepareStatement(sql);
			 
			 preparedStatement.setInt(1, newAccount.getAcctnum());
			 preparedStatement.setString(2, newAccount.getUsername());
			 preparedStatement.setString(3, newAccount.getType());
			 preparedStatement.setDouble(4, newAccount.getBalance());
			 preparedStatement.setString(5, newAccount.getStatus());
			 
			 c=preparedStatement.executeUpdate();
			 
			 if(c==1) {
					log.info("The account has been created. It will be waiting for approval by an employee.");
			 		log.info("Your account number is: " + newAccount.getAcctnum());
			 }
		} catch(ClassNotFoundException | SQLException e) {
			throw new BusinessException("An Internal error has occured");
		}
		
return;	
	}

	public void approveRejectAccount() throws BusinessException {
		int pendingChoice = 0;
		int accountNumber = 0;
		int c;
		accounts pendingAccount = null;
		
		log.info("What is the pending account number?");
		accountNumber = Integer.parseInt(sc.nextLine());
		
		try(Connection connection=postgresqlConnection.getConnection()){
			
			String sql = "select * from \"bankApp\".accounts where acctnum = ? AND status = 'PENDING'";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, accountNumber);
			ResultSet resultSet = preparedStatement.executeQuery();
			
			if(resultSet.next()) {
			pendingAccount = new accounts();
			pendingAccount.setAcctnum(accountNumber);
			pendingAccount.setUsername(resultSet.getString("username"));
			pendingAccount.setType(resultSet.getString("type"));
			pendingAccount.setBalance(resultSet.getDouble("balance"));
			pendingAccount.setStatus(resultSet.getString("status"));
			
			do {
			log.info("Is this account to be approved(Enter 1) or rejected(Enter 2)?");
			pendingChoice = Integer.parseInt(sc.nextLine());
			}while(pendingChoice!=1 && pendingChoice!=2);
			
			if(pendingChoice == 1) {
				Connection connection1=postgresqlConnection.getConnection();
					String sql1 = "update \"bankApp\".accounts set status='ACTIVE' where acctnum = ?";
					PreparedStatement preparedStatement1= connection1.prepareStatement(sql);
					preparedStatement1.setInt(1, accountNumber);
					c = preparedStatement1.executeUpdate(sql1);
					if (c==1)
					log.info("Account has been approved!");
			}
			
			else{
				Connection connection1=postgresqlConnection.getConnection();
				String sql1 = "delete from \"bankApp\".accounts where acctnum = ?";
				PreparedStatement preparedStatement1= connection1.prepareStatement(sql);
				preparedStatement1.setInt(1, accountNumber);
				c = preparedStatement1.executeUpdate(sql1);
				if (c==1)
				log.info("Account has been rejected and removed!");
			}
			
			}else {
				throw new BusinessException("No pending account with the number: " + accountNumber);
			}
			
		} catch (ClassNotFoundException | SQLException e) {
			throw new BusinessException("An Internal error has occured");
		}
			
			return;
	}

	public accounts viewAccount() throws BusinessException {
		
		int accountNumber = 0;
		accounts oneAccount = null;
		
		try(Connection connection=postgresqlConnection.getConnection()){
			
			String sql = "select * from \"bankApp\".accounts where acctnum = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, accountNumber);
			ResultSet resultSet = preparedStatement.executeQuery();
			
			if(resultSet.next()) {
			oneAccount = new accounts();
			oneAccount.setAcctnum(accountNumber);
			oneAccount.setUsername(resultSet.getString("username"));
			oneAccount.setType(resultSet.getString("type"));
			oneAccount.setBalance(resultSet.getDouble("balance"));
			oneAccount.setStatus(resultSet.getString("status"));
			oneAccount.toString();
			
			}else {
				throw new BusinessException("No account with the number: " + accountNumber);
			}
			
		} catch (ClassNotFoundException | SQLException e) {
			throw new BusinessException("An Internal error has occured");
		}
			
			return oneAccount;	
	}

	public List<transactions> viewTransactions() throws BusinessException {

		List<transactions> TransactionList = new ArrayList<>();
		try(Connection connection=postgresqlConnection.getConnection()){
			String sql = "select * from \"bankApp\".transactions where sender = ? or recipient = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()) {
				transactions tranEntry = new transactions();
				tranEntry.setTransnum(resultSet.getInt("transnum"));
				tranEntry.setType(resultSet.getString("type"));
				tranEntry.setSender(resultSet.getInt("sender"));
				tranEntry.setRecipient(resultSet.getInt("recipient"));
				tranEntry.setAmount(resultSet.getInt("amount"));
				tranEntry.setDate(resultSet.getDate("date"));
				TransactionList.add(tranEntry);
			}
			if(TransactionList.size()==0){
				throw new BusinessException("No Transactions in the database");
			}
			} catch (ClassNotFoundException | SQLException e) {
				throw new BusinessException("An Internal error has occured");
			}
	
		return TransactionList;
	}
}
