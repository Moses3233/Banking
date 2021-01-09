package myBankApp.main;

import java.util.Random;
import java.util.Scanner;

import org.apache.log4j.Logger;

import myBankApp.dao.bankCustomerDAO;
import myBankApp.dao.bankEmployeeDAO;
import myBankApp.dao.impl.bankCustomerDAOImpl;
import myBankApp.dao.impl.bankEmployeeDAOImpl;
import myBankApp.exception.BusinessException;
import myBankApp.model.accounts;
import myBankApp.model.users;

public class bankAppMain {
	
	private static Logger log = Logger.getLogger(bankAppMain.class);

	
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		Random rand = new Random();
			
		int welcomeOption = 0;
		int userOption = 0;
		int customerOption=0;
		int employeeOption = 0;
		
		bankEmployeeDAO bankEmployeeService = new bankEmployeeDAOImpl();
		bankCustomerDAO bankCustomerService = new bankCustomerDAOImpl();

						
		do {
				log.info("Welcome to the BankApp! Do you already have an account with us?");
				log.info("Enter \"1\" for yes, \"2\" for no, or \"3\" to Exit");
				welcomeOption = Integer.parseInt(sc.nextLine());
				
		switch(welcomeOption) {
		
		case 1://has an account with the bank
			log.info("Are you an employee or customer? Press \"1\" for employee or Press \"2\" for customer");
			do {
				switch(userOption) {
				
				case 1://Is a customer
					
					String usernameX = "";
					String passwordX = "";
					int customerAcctNumber = 0;
					
					log.info("What is your username?");
					usernameX = sc.nextLine();
					log.info("What is your password going to be");
					passwordX = sc.nextLine();
					
					try {
						customerAcctNumber = bankCustomerService.customerLogin(usernameX,passwordX);
					} catch (BusinessException e) {
					}
					
					do {
						switch(customerOption) {
						
						case 1:
							break;
							
						case 2:
							break;
							
						case 3:
							break;
							
						case 4:
							break;
							
						default:
							log.info("Not a valid option. Try again");
							break;
						}
					}while(customerOption!=5);
					
					
					break;
					
				case 2://Is an Employee
					
					String usernameE = "";
					String passwordE = "";
								
						log.info("What is your username?");
						usernameX = sc.nextLine();
						log.info("What is your password?");
						passwordX = sc.nextLine();
						
					try {
						bankEmployeeService.employeeLogin(usernameE, passwordE);
					} catch (BusinessException e2) {
					}
					
					do {
						switch(employeeOption) {
						
						case 1://Create User
							
							users newUser = new users();
							log.info("What is your username going to be?");
							newUser.setUsername(sc.nextLine());
							log.info("What is your password going to be");
							newUser.setPassword(sc.nextLine());
							log.info("What is your E-mail address?");
							newUser.setEmail(sc.nextLine());
							newUser.setRole("Customer");
							log.info("What is your first name?");
							newUser.setFname(sc.nextLine());
							log.info("What is your last name?");
							newUser.setLname(sc.nextLine());
							log.info("What is your gender?");
							newUser.setGender(sc.nextLine());
							log.info("How old are you?");
							newUser.setAge(Integer.parseInt(sc.nextLine()));
							log.info("What is your address?");
							newUser.setAddress(sc.nextLine());
							log.info("What city do you live in?");
							newUser.setCity(sc.nextLine());
							log.info("What state do you live in?");
							newUser.setState(sc.nextLine());
							log.info("What is your Zip Code?");
							newUser.setZip(sc.nextLine());
							log.info("What Country do you reside in?");
							newUser.setCountry(sc.nextLine());
							
							try {
								bankEmployeeService.createUser(newUser);
							} catch (BusinessException e5) {}
							break;
							
						case 2://Create Account
							
							int acctType = 0;
							double startingBalance = 0.0;
							accounts newAccount = new accounts();
							
							newAccount.setAcctnum(rand.nextInt(1000000)+1);
							
							log.info("What is the username the account is linked to?");
							newAccount.setUsername(sc.nextLine());
							
							do {
							log.info("What type of account is this? Enter \"1\" for Checking or \"2\" for Savings");
							acctType = Integer.parseInt(sc.nextLine());
							if(acctType ==1)
								newAccount.setType("Checking");
							if(acctType == 2)
								newAccount.setType("Saving");
							}while(acctType!=1 && acctType!=2);

							do {
							log.info("What is the starting balance?");
							startingBalance = Double.parseDouble(sc.nextLine());
							if(startingBalance<300)
							log.info("The starting balance cannot be lower than 300.00");
							}while(startingBalance<300.0);
							
							newAccount.setBalance(startingBalance);
							newAccount.setStatus("PENDING");
							
							try {
								bankEmployeeService.createAccount(newAccount);
							} catch (BusinessException e4) {}
							break;
							
							
						case 3://Delete User
							
							log.info("The account(s) you are looking for are under which username?");
							String username = sc.nextLine();
							
							try {
								bankEmployeeService.deleteUser(username);
							} catch (BusinessException e3) {
							}
							break;
								
						case 4://Delete Account

							log.info("What is the account number of the account we're deleting?");
							int accountNumber = Integer.parseInt(sc.nextLine());
							
							try {
								bankEmployeeService.deleteAccount(accountNumber);
							} catch (BusinessException e2) {}
							break;
							
						case 5: //View Accounts
							
							log.info("The account(s) you are looking for are under which username?");
							String acctUsername = sc.nextLine();
							try {
								bankEmployeeService.viewAccounts(acctUsername);
							} catch (BusinessException e1) {
							}
							
							break;
							
						case 6:// Accept/Reject Pending Accounts
						
							log.info("What is the pending account number?");
							int pendingAcctNumber = Integer.parseInt(sc.nextLine());
							
							try {
								bankEmployeeService.approveRejectAccount(pendingAcctNumber);
							} catch (BusinessException e) {
							}
							break;
							
						case 7://View Transaction(s)
							
							log.info("The transaction(s) you are looking for are under which account?");
							int acctNumber = Integer.parseInt(sc.nextLine());
							
							try {
								bankEmployeeService.viewTransactions(acctNumber);
							} catch (BusinessException e) {
							}
							break;
							
						default:
							log.info("Not a valid option. Try again");
							break;
						}
						
					}while(employeeOption!=8);
					
					break;
					
				default:
					log.info("Not a valid option. Try again");
					break;
				}
				
			}while(userOption!=1 && userOption!=2);
			
			
			break;
			
		case 2://doesn't have an account
			
			log.info("Let's get an account set up for you");
			
			users newUser = new users();
			
			log.info("What is your username going to be?");
			newUser.setUsername(sc.nextLine());
			log.info("What is your password going to be");
			newUser.setPassword(sc.nextLine());
			log.info("What is your E-mail address?");
			newUser.setEmail(sc.nextLine());
			newUser.setRole("Customer");
			log.info("What is your first name?");
			newUser.setFname(sc.nextLine());
			log.info("What is your last name?");
			newUser.setLname(sc.nextLine());
			log.info("What is your gender?");
			newUser.setGender(sc.nextLine());
			log.info("How old are you?");
			newUser.setAge(Integer.parseInt(sc.nextLine()));
			log.info("What is your address?");
			newUser.setAddress(sc.nextLine());
			log.info("What city do you live in?");
			newUser.setCity(sc.nextLine());
			log.info("What state do you live in?");
			newUser.setState(sc.nextLine());
			log.info("What is your Zip Code?");
			newUser.setZip(sc.nextLine());
			log.info("What Country do you reside in?");
			newUser.setCountry(sc.nextLine());
			
			try {
				bankEmployeeService.createUser(newUser);
			} catch (BusinessException e) {
			}
			
			log.info("While we're at it, let's set up an account for you as well.");
			
			int acctType = 0;
			double startingBalance = 0.0;
			accounts newAccount = new accounts();
			
			newAccount.setAcctnum(rand.nextInt(1000000)+1);
			
			log.info("What is the username the account is linked to?");
			newAccount.setUsername(sc.nextLine());
			
			do {
			log.info("What type of account is this? Enter \"1\" for Checking or \"2\" for Savings");
			acctType = Integer.parseInt(sc.nextLine());
			if(acctType ==1)
				newAccount.setType("Checking");
			if(acctType == 2)
				newAccount.setType("Saving");
			}while(acctType!=1 && acctType!=2);

			do {
			log.info("What is the starting balance?");
			startingBalance = Double.parseDouble(sc.nextLine());
			if(startingBalance<300)
			log.info("The starting balance cannot be lower than 300.00");
			}while(startingBalance<300.0);
			
			newAccount.setBalance(startingBalance);
			newAccount.setStatus("PENDING");
			try {
				bankEmployeeService.createAccount(newAccount);
			} catch (BusinessException e) {
			}
			
			break;
			
		case 3:
			log.info("Exiting... Have a good day!...");
			System.exit(0);
			break;
			
		default:
			log.info("Not a valid option. Try again");
			break;
		}
		
		
		}while(welcomeOption!=3);
		
		sc.close();
	}

}
