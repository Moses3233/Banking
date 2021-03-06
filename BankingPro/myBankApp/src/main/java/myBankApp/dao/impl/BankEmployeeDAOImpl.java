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

import myBankApp.dao.BankEmployeeDAO;
import myBankApp.dao.dbutil.PostgresqlConnection;
import myBankApp.exception.BusinessException;
import myBankApp.model.Accounts;
import myBankApp.model.Transactions;
import myBankApp.model.Users;

public class BankEmployeeDAOImpl implements BankEmployeeDAO{


	Logger log = Logger.getLogger(BankEmployeeDAOImpl.class);
	Scanner sc = new Scanner(System.in);
	Random rand = new Random();
	
	public void employeeLogin(String usernameX, String passwordX) throws BusinessException {

		try(Connection connection=PostgresqlConnection.getConnection()){
				
			String sql = "select * from \"bankApp\".users where role = 'Employee' AND username = ? AND password = ?";
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

	public int createUser(Users newUser) throws BusinessException {
		int c = 0;
		
		try(Connection connection=PostgresqlConnection.getConnection()){
			String sql = "INSERT INTO \"bankApp\".users(username, password, email, role, fname, lname, gender, dob, address, city, state, zip, country) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
			 PreparedStatement preparedStatement= connection.prepareStatement(sql);
			 
			 preparedStatement.setString(1, newUser.getUsername());
			 preparedStatement.setString(2, newUser.getPassword());
			 preparedStatement.setString(3, newUser.getEmail());
			 preparedStatement.setString(4, newUser.getRole());
			 preparedStatement.setString(5, newUser.getFname());
			 preparedStatement.setString(6, newUser.getLname());
			 preparedStatement.setString(7, newUser.getGender());
			 preparedStatement.setDate(8, newUser.getDob());
			 preparedStatement.setString(9, newUser.getAddress());
			 preparedStatement.setString(10, newUser.getCity());
			 preparedStatement.setString(11, newUser.getState());
			 preparedStatement.setString(12, newUser.getZip());
			 preparedStatement.setString(13, newUser.getCountry());
			 
			 c=preparedStatement.executeUpdate();
			 
			 if(c==1)
				 log.info("Congratulations! You successfully created an account!");
			 
		} catch(ClassNotFoundException | SQLException e) {
			log.info(e);
			throw new BusinessException("An Internal error has occured");
		}
return c;		
	}
	
	public int deleteUser(String username) throws BusinessException {
		int c = 0;
		int c1 = 0;
		try(Connection connection=PostgresqlConnection.getConnection()){
			String sql = "delete from \"bankApp\".users where username = ?";
			 PreparedStatement preparedStatement= connection.prepareStatement(sql);
			 preparedStatement.setString(1, username);
			 c=preparedStatement.executeUpdate();

				try {
					String sql1 = "delete from \"bankApp\".accounts where username = ?";
					PreparedStatement preparedStatement1= connection.prepareStatement(sql1);
					preparedStatement1.setString(1, username);
					c1=preparedStatement1.executeUpdate();

				} catch(SQLException e) {
					throw new BusinessException("An Internal error has occured");
				}
			 
			 
		if(c==1)
				log.info("You successfully deleted the user.");
		if(c1==1) {
				log.info("The account(s) under the user has been deleted");
					 }
		
		} catch(ClassNotFoundException | SQLException e) {
			log.info(e);
			throw new BusinessException("An Internal error has occured");
		}
return c;		
	}

	public int createAccount(Accounts newAccount) throws BusinessException {
		int c = 0;

		try(Connection connection=PostgresqlConnection.getConnection()){
			String sql = "insert into \"bankApp\".accounts(username, type, balance, status) values(?, ?, ?, ?)";
			 PreparedStatement preparedStatement= connection.prepareStatement(sql);
			 
			 preparedStatement.setString(1, newAccount.getUsername());
			 preparedStatement.setString(2, newAccount.getType());
			 preparedStatement.setDouble(3, newAccount.getBalance());
			 preparedStatement.setString(4, newAccount.getStatus());
			 
			 c=preparedStatement.executeUpdate();
			 
			 if(c==1) {
					log.info("The account has been created. It will be waiting for approval by an employee.");
			 		log.info("Your account number is: " + newAccount.getAcctnum());
			 }
		} catch(ClassNotFoundException | SQLException e) {
			log.info(e);
			throw new BusinessException("An Internal error has occured");
		}
		
return c;	
	}

	public int deleteAccount(int accountNumber) throws BusinessException {
		int c = 0;

		try(Connection connection=PostgresqlConnection.getConnection()){
			String sql = "delete from \"bankApp\".accounts where acctnum = ?";
			PreparedStatement preparedStatement= connection.prepareStatement(sql);
			preparedStatement.setInt(1, accountNumber);
			c=preparedStatement.executeUpdate();
			 
			 if(c==1) {
					log.info("The account has been deleted");
			 }
		} catch(ClassNotFoundException | SQLException e) {
			throw new BusinessException("An Internal error has occured");
		}
		
return c;	
	}

	public int approveRejectAccount(int accountNumber) throws BusinessException {
		
		int pendingChoice = 0;
		int c;
		Accounts pendingAccount = null;

		try(Connection connection=PostgresqlConnection.getConnection()){
			String sql = "select * from \"bankApp\".accounts where acctnum = ? AND status = 'PENDING'";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, accountNumber);
			ResultSet resultSet = preparedStatement.executeQuery();
			
			if(resultSet.next()) {
			pendingAccount = new Accounts();
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
					String sql1 = "update \"bankApp\".accounts set status='ACTIVE' where acctnum = ?";
					PreparedStatement preparedStatement1= connection.prepareStatement(sql1);
					preparedStatement1.setInt(1, accountNumber);
					c = preparedStatement1.executeUpdate();
					if (c==1)
					log.info("Account has been approved!");
			}
			
			else{
				String sql1 = "delete from \"bankApp\".accounts where acctnum = ?";
				PreparedStatement preparedStatement1= connection.prepareStatement(sql1);
				preparedStatement1.setInt(1, accountNumber);
				c = preparedStatement1.executeUpdate();
				if (c==1)
				log.info("Account has been rejected and removed!");
			}
			
			}else {
				throw new BusinessException("No pending account with the number: " + accountNumber);
			}
			
		} catch (ClassNotFoundException | SQLException e) {
			throw new BusinessException("An Internal error has occured");
		}
			
			return c;
	}

	public List<Accounts> viewAccounts(String username) throws BusinessException {
		
		List<Accounts> AccountList = new ArrayList<>();
		
		try(Connection connection=PostgresqlConnection.getConnection()){
			
			String sql = "select * from \"bankApp\".accounts where username = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, username);
			ResultSet resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()) {
			Accounts oneAccount = new Accounts();
			oneAccount.setAcctnum(resultSet.getInt("acctnum"));
			oneAccount.setUsername(username);
			oneAccount.setType(resultSet.getString("type"));
			oneAccount.setBalance(resultSet.getDouble("balance"));
			oneAccount.setStatus(resultSet.getString("status"));
			oneAccount.toString();
			}
			
			if(AccountList.size()==0) {
				throw new BusinessException("No account(s) under the username: " + username);
			}
			
		}catch (ClassNotFoundException | SQLException e) {
			throw new BusinessException("An Internal error has occured");
		}
			
			return AccountList;	
	}

	public List<Transactions> viewTransactions(int accountNumber) throws BusinessException {

		List<Transactions> TransactionList = new ArrayList<>();
		try(Connection connection=PostgresqlConnection.getConnection()){
			String sql = "select * from \"bankApp\".transactions where sender = ? or recipient = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, accountNumber);
			preparedStatement.setInt(2, accountNumber);
			ResultSet resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()) {
				Transactions tranEntry = new Transactions();
				tranEntry.setTransnum(resultSet.getInt("transnum"));
				tranEntry.setType(resultSet.getString("type"));
				tranEntry.setSender(resultSet.getInt("sender"));
				tranEntry.setRecipient(resultSet.getInt("recipient"));
				tranEntry.setAmount(resultSet.getInt("amount"));
				tranEntry.setDate(resultSet.getDate("date"));
				tranEntry.setStatus(resultSet.getString("status"));
				TransactionList.add(tranEntry);
			}
			if(TransactionList.size()==0){
				throw new BusinessException("No Transactions in the database for the account number: " + accountNumber);
			}
			} catch (ClassNotFoundException | SQLException e) {
				throw new BusinessException("An Internal error has occured");
			}
	
		return TransactionList;
	}
	
	public List<Transactions> viewAllTransactions() throws BusinessException {

		List<Transactions> TransactionList = new ArrayList<>();
		try(Connection connection=PostgresqlConnection.getConnection()){
			String sql = "select * from \"bankApp\".transactions";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()) {
				Transactions tranEntry = new Transactions();
				tranEntry.setTransnum(resultSet.getInt("transnum"));
				tranEntry.setType(resultSet.getString("type"));
				tranEntry.setSender(resultSet.getInt("sender"));
				tranEntry.setRecipient(resultSet.getInt("recipient"));
				tranEntry.setAmount(resultSet.getInt("amount"));
				tranEntry.setDate(resultSet.getDate("date"));
				tranEntry.setStatus(resultSet.getString("status"));
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
