package myBankApp.main;

import java.util.Scanner;

import org.apache.log4j.Logger;

import myBankApp.dao.bankCustomerDAO;
import myBankApp.dao.bankEmployeeDAO;
import myBankApp.dao.impl.bankCustomerDAOImpl;
import myBankApp.dao.impl.bankEmployeeDAOImpl;
import myBankApp.exception.BusinessException;

public class bankAppMain {

	public static void main(String[] args) {

		Logger log = Logger.getLogger(bankAppMain.class);
		Scanner sc = new Scanner(System.in);
		
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
			
			do {
				switch(userOption) {
				
				case 1://Is a customer
					try {
						bankCustomerService.customerLogin();
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
					

					do {
						switch(employeeOption) {
						
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
					}while(employeeOption!=5);
					
					break;
					
				default:
					log.info("Not a valid option. Try again");
					break;
				}
				
			}while(userOption!=1 && userOption!=2);
			
			
			
			
			break;
			
		case 2://doesn't have an account
			
			log.info("Let's get an account set up for you");
			try {
				bankEmployeeService.createUser();
			} catch (BusinessException e) {
			}
			log.info("While we're at it, let's set up an account for you as well.");
			try {
				bankEmployeeService.createAccount();
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
