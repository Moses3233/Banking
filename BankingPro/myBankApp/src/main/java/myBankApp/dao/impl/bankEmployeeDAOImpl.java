package myBankApp.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import myBankApp.dao.bankEmployeeDAO;
import myBankApp.dao.dbutil.postgresqlConnection;
import myBankApp.exception.BusinessException;
import myBankApp.model.transactions;

public class bankEmployeeDAOImpl implements bankEmployeeDAO{

	public void employeeLogin() {
		// TODO Auto-generated method stub
		
	}

	public void createUser() {
		// TODO Auto-generated method stub
		
	}

	public void createAccount() {
		// TODO Auto-generated method stub
		
	}

	public void approveRejectAccount() {
		// TODO Auto-generated method stub
		
	}

	public void viewAccount() {
		// TODO Auto-generated method stub
		
	}

	public List<transactions> viewTransactions() throws BusinessException {

		List<transactions> TransactionList = new ArrayList<>();
		try(Connection connection=postgresqlConnection.getConnection()){
			String sql = "select * from test.player where playedby = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()) {
				transactions tranEntry = new transactions();
				tranEntry.setTransnum(resultSet.getInt("transnum"));
				tranEntry.setType(resultSet.getString("type"));
				tranEntry.setSender(resultSet.getInt("sender"));
				tranEntry.setRecipient(resultSet.getInt("recipient"));
				tranEntry.setAmount(resultSet.getInt("amount"));
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
