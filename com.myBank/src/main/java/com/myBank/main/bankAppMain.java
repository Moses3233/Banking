package com.myBank.main;

import java.util.Scanner;

import org.apache.log4j.Logger;

import com.myBank.DAO.impl.bankCustomerDAOImpl;
import com.myBank.DAO.impl.bankEmployeeDAOImpl;
import com.myBank.exceptions.BusinessException;

public class bankAppMain {

	private static Logger log=Logger.getLogger(bankAppMain.class);

	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
	
		int welcomeOption;
		
		log.info("Welcome to the Bank! Do you already have an account with us?");
		
		do {
			
		log.info("\nEnter \"1\" for yes or \"2\" for no.\nEnter \"3\" to exit now");
		
			welcomeOption = Integer.parseInt(sc.nextLine()) ;
		
		
		switch(welcomeOption) {
		case 1:
			int loginOption;
			
			do {
			log.info("Logging into an existing account...\nAre you an employee(Enter \"1\") or a customer(Enter \"2\")?");

			loginOption = Integer.parseInt(sc.nextLine()) ;
			
			switch(loginOption) {
			case 1:
				log.info("You're an employee? Got it");
				
				
				bankEmployeeDAOImpl daoE = new bankEmployeeDAOImpl();
				int employeeOption;
				String newUsername;
				
				try {
					daoE.employeeLogin();
					do {
						log.info("Welcome Employee! Another day, another dollar! What would you like to do?");
						log.info("1) View An Account\n2) Handle Pending Account\n3) Create Account\n4) View Transactions\n5) Exit");
						employeeOption = Integer.parseInt(sc.nextLine()) ;
						
					int customAcctNum;
					
					switch(employeeOption) {
					case 1:
						log.info("Opening viewer...");
						daoE.viewAccount();
						break;
						
					case 2:
						log.info("Ok. Focusing on pending accounts");
						daoE.approveRejectAccount();
						break;
						
					case 3:
						log.info("Ok. Let's create the account. What is their username?");
						newUsername = (sc.nextLine());
						daoE.createAccount(newUsername);
						break;

					case 4:
						log.info("What is the customer's account number?");
						customAcctNum = Integer.parseInt(sc.nextLine()) ;
						log.info("Ok. Let's take a look at the transactions.");
						log.info(daoE.viewTransactions(customAcctNum));
						break;
						
					case 5:
						log.info("Ok. Logging out...");
						break;
						
					default:
						log.info("Not a valid choice. Try again.");
						break;
					
					
					}
					
					}while(employeeOption!=5);
					
				}catch(BusinessException e) {
					log.info("Error!");
				}
				
				break;
				
			case 2:
				log.info("Welcome Customer! Login:");
				
				bankEmployeeDAOImpl dao = new bankEmployeeDAOImpl();
				
				try {
					dao.customerLogin();
					
					bankCustomerDAOImpl daoC = new bankCustomerDAOImpl();
					int customAcctNum= dao.customerLogin();
					int customerOption, amount;
					
					//log.info("These options need implementation");
					
					do {
						log.info("Welcome valued Customer! What would you like to do?");
						log.info("1) View Account\n2) Deposit\n3) Withdraw\n4) Post Transfer\n5) Accept Transfer\n6) Exit");
						customerOption = Integer.parseInt(sc.nextLine()) ;
						
					switch(customerOption) {
					case 1:
						log.info("Ok. Viewing Account...");
						daoC.viewAccount(customAcctNum);
						break;
						
					case 2:
						log.info("Ok. How much are you depositing?");
						amount = (Integer.parseInt(sc.nextLine()));
						daoC.accountDeposit(customAcctNum, amount);
						break;
						
					case 3:
						log.info("Ok. How much are you withdrawing?");
						amount = (Integer.parseInt(sc.nextLine()));
						daoC.accountWithdrawl(customAcctNum, amount);
						break;
						
					case 4:
						log.info("Ok. Let's start making the transfer");
						daoC.postTransfer(customAcctNum);
						break;
						
					case 5:
						log.info("Ok. Let's start on getting the transfer.");
						daoC.acceptTransfer();
						break;
						
					case 6:
						log.info("Ok. Logging out...");
						break;
					default:
						break;
					
					
					}
					
					}while(customerOption!=6);
					
				} catch (BusinessException e) {
					log.info("Error!");
				}
				
				
				
				break;
			default:
				log.info("Not a valid option. Try again.");
			}
			
			}while(loginOption!=1&&loginOption!=2);
			
			break;
			
		case 2:
			log.info("This is to create a new account. Just need to ask a few questions:");
			
			bankEmployeeDAOImpl signUp = new bankEmployeeDAOImpl();
			
			try {
				if(signUp.createUser()!=null) {
				log.info("User created successfully. But you will need an account");
				signUp.createAccount(signUp.createUser());
				}
			
			} catch (BusinessException e) {
			log.info("An error occured while making your account");
			}
			break;
			
		case 3:
			log.info("Exiting... Have a good day.");
			System.exit(0);
			break;
			
		default:
			log.info("Not a valid option. Try again");
		
		}
		
		}while(welcomeOption!=3);
		
		sc.close();
	}

}
