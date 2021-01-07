package myBankApp.main;

import java.util.Scanner;

import org.apache.log4j.Logger;

public class bankAppMain {

	public static void main(String[] args) {

		Logger log = Logger.getLogger(bankAppMain.class);
		Scanner sc = new Scanner(System.in);
		log.info("");
		log.info("");
		log.info("");
		log.info("");
		log.info("");
		log.info("");
		log.info("");
		log.info("");
		log.info("");
		log.info("");
		log.info("");
		log.info("");
		
		int welcomeOption = 0;
		int userOption = 0;
		int customerOption=0;
		int employeeOption = 0;
						
		do {
				log.info("Welcome to the BankApp! Do you already have an account with us?");
				log.info("Enter \"1\" for yes, \"2\" for no, or \"3\" to Exit");
				
		switch(welcomeOption) {
		
		case 1://has an account with the bank
			
			do {
				switch(userOption) {
				
				case 1://Is a customer
					
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
		
		
	}

}
