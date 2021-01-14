package myBankApp;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.Test;

import myBankApp.dao.impl.BankEmployeeDAOImpl;
import myBankApp.exception.BusinessException;
import myBankApp.model.Accounts;
import myBankApp.model.Users;

class bankAppEmployeeTests {

	BankEmployeeDAOImpl testEmployee = new BankEmployeeDAOImpl();

	
	@Test
	void testCreateUser() throws BusinessException {
		Users newUser = new Users();
		newUser.setUsername("Fake");
		newUser.setPassword("User");
		newUser.setEmail("Unreal@email.com");
		newUser.setRole("Customer");
		newUser.setFname("Fake");
		newUser.setLname("Person");
		newUser.setGender("???");
		newUser.setDob(null);
		newUser.setAddress("009 Memory Lane");
		newUser.setCity("Disney");
		newUser.setState("Fl");
		newUser.setZip("78900");
		newUser.setCountry("Magic Kingdom");
		
		assertEquals(1,testEmployee.createUser(newUser));
		
	}

	@Test
	void testCreateAccount() throws BusinessException {

		Accounts newAccount = new Accounts();
		
		newAccount.setUsername("Rex");
		newAccount.setType("Checking");
		newAccount.setBalance(333.00);
		newAccount.setStatus("PENDING");
		
		assertEquals(1, testEmployee.createAccount(newAccount));
	}

	@Test
	void testApproveRejectAccount() throws BusinessException {
		assertEquals(1, testEmployee.approveRejectAccount(1));
	}


	@Test
	void testDeleteAccount() throws BusinessException {
		assertEquals(1, testEmployee.deleteAccount(20));
	}
		
	@Test
	void testDeleteUser() throws BusinessException {
		
		assertEquals(1,testEmployee.deleteUser("Fake"));
		
	}

}
