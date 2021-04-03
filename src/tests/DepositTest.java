package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import cse237.Account;
import cse237.User;

class DepositTest {

	@Test
	void testDepositIntoAccount() throws IOException {
		User user_ex = new User("username", "password");
		Account account_1 = new Account(user_ex);
		account_1.depositMoney(100);
		assertEquals(100, account_1.getBalance());
	}
	
	@Test
	void testWithdrawFromAccount_Success() throws IOException {
		User user_ex = new User("username", "password");
		Account account_1 = new Account(user_ex);
		account_1.depositMoney(100);
		account_1.withdrawMoney(99);
		assertEquals(1, account_1.getBalance());
	}
	
	@Test
	void testWithdrawFromAccount_Failure() throws IOException {
		User user_ex = new User("username", "password");
		Account account_1 = new Account(user_ex);
		account_1.depositMoney(100);
		boolean if_successful = account_1.withdrawMoney(101);
		assertFalse(if_successful);
	}
	
}


