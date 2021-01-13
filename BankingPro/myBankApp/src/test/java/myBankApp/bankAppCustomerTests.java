package myBankApp;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;
import myBankApp.dao.impl.bankCustomerDAOImpl;
import myBankApp.exception.BusinessException;
import myBankApp.model.transactions;

public class bankAppCustomerTests {
	
	
		bankCustomerDAOImpl testCustomer = new bankCustomerDAOImpl();
		
	@Test
	void testCustomerLogin() throws BusinessException {
		
		assertEquals(995100, testCustomer.customerLogin("Rex","Happy"));
}

	@Test
	void testPostTransfer() throws BusinessException {
		
		transactions dummyEntry = new transactions();

		dummyEntry.setAmount(100);
		dummyEntry.setSender(0);
		dummyEntry.setRecipient(0);
		dummyEntry.setStatus("Pending");
		dummyEntry.setType("Transfer");
		dummyEntry.setDate(null);
		
		
		assertEquals(1, testCustomer.postTransfer(dummyEntry, 0));
	
	}

	@Test
	void testAcceptTransfer() throws BusinessException {

		assertEquals(1, testCustomer.acceptTransfer(4));

	}

	@Test
	void testAccountWithdrawl() throws BusinessException {

		assertEquals(1, testCustomer.accountWithdrawl(0, 100.00));
	}

	@Test
	void testAccountDeposit() throws BusinessException {
	
		assertEquals(1, testCustomer.accountDeposit(0,100.00));

	}

}
