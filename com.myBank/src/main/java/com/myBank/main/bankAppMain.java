package com.myBank.main;

import java.util.Scanner;

import org.apache.log4j.Logger;

import com.myBank.DAO.impl.bankEmployeeDAOImpl;
import com.myBank.exceptions.BusinessException;
import com.myBank.model.users;

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
				
				
				
				break;
			case 2:
				log.info("Welcome Customer! Login:");
				
				
				
				
				
				break;
			default:
				log.info("Not a valid option. Try again.");
			}
			
			}while(loginOption!=1&&loginOption!=2);
			
			break;
			
		case 2:
			log.info("This is to create a new account. Just need to ask a few questions:");
			users newUser = new users();
			
			log.info("What is your new username?");
			newUser.setUsername(sc.nextLine());
			log.info("What is your new password?");
			newUser.setPassword(sc.nextLine());
			log.info("What is your first name?");
			newUser.setfName(sc.nextLine());
			log.info("What is your last name?");
			
			newUser.setlName(sc.nextLine());
			
			newUser.setRole("Customer");
			
			log.info("What is your email?");
			newUser.setEmail(sc.nextLine());
			
			bankEmployeeDAOImpl signUp = new bankEmployeeDAOImpl();
			
			try {
				signUp.createUser(newUser);
				log.info("Account created successfully");
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
	}

}
