package myBankApp;

import static org.junit.jupiter.api.Assertions.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.Test;

import myBankApp.dao.impl.bankEmployeeDAOImpl;
import myBankApp.exception.BusinessException;
import myBankApp.model.accounts;
import myBankApp.model.users;

class bankAppEmployeeTests {

	bankEmployeeDAOImpl testEmployee = new bankEmployeeDAOImpl();

	
	@Test
	void testCreateUser() throws BusinessException {
		users newUser = new users();
		newUser.setUsername("Fake");
		newUser.setPassword("User");
		newUser.setEmail("Unreal");
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

		accounts newAccount = new accounts();
		
		newAccount.setUsername("Rex");
		newAccount.setType("Checking");
		newAccount.setBalance(333.00);
		newAccount.setStatus("PENDING");
		
		assertEquals(1, testEmployee.createAccount(newAccount));
	}

	@Test
	void testApproveRejectAccount() throws BusinessException {
		assertEquals(1, testEmployee.approveRejectAccount(6));
	}


	@Test
	void testDeleteAccount() throws BusinessException {
		assertEquals(1, testEmployee.deleteAccount(6));
	}
		
	@Test
	void testDeleteUser() throws BusinessException {
		
		assertEquals(1,testEmployee.deleteUser("Fake"));
		
	}

}
