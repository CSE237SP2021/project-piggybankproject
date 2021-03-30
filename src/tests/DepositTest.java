package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import cse237.account;
import cse237.user;

class DepositTest {

	@Test
	void testDepositIntoAccount() throws IOException {
		user user_ex = new user("username", "password");
		account account_1 = new account(user_ex);
		account_1.depositMoney(100);
		assertEquals(100, account_1.getBalance());
	}
	
	@Test
	void testWithdrawFromAccount_Success() throws IOException {
		user user_ex = new user("username", "password");
		account account_1 = new account(user_ex);
		account_1.depositMoney(100);
		account_1.withdrawMoney(99);
		assertEquals(1, account_1.getBalance());
	}
	
	@Test
	void testWithdrawFromAccount_Failure() throws IOException {
		user user_ex = new user("username", "password");
		account account_1 = new account(user_ex);
		account_1.depositMoney(100);
		boolean if_successful = account_1.withdrawMoney(101);
		assertFalse(if_successful);
	}
	
}


