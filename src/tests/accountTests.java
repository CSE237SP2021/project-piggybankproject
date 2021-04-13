package tests;

import static org.junit.jupiter.api.Assertions.*;


import java.io.IOException;

import org.junit.jupiter.api.Test;

import cse237.Account;
import cse237.User;

class accountTests {

	@Test
	
	void generateAccountNumber() throws IOException {
		int username = (int)(Math.random() *100000000 * Math.random());
		int password = (int)(Math.random() * 10000000 * Math.random()); 
		User user = new User(Integer.toString(username), Integer.toString(password)); 
		Account account = new Account(user); 
		try {
			int accountNum = account.generateAccountNum();
			assertEquals(accountNum, account.getAccountNum()); 
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	@Test
	void checkBalanceUpdate() throws IOException {
		int username = (int)(Math.random() *100000000 * Math.random());
		int password = (int)(Math.random() * 10000000 * Math.random()); 
		User user = new User(Integer.toString(username), Integer.toString(password)); 
		Account account = new Account(user); 
		account.depositMoney(10000);
		assertTrue(account.getBalance() == 10000); 
		account.depositMoney(400);
		assertTrue(account.getBalance() == 10400); 
		account.withdrawMoney(200); 
		assertTrue(account.getBalance() == 10200);  
	}
}
