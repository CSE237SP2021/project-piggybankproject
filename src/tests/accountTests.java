package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import cse237.account;
import cse237.user;

class accountTests {

	@Test
	
	void generateAccountNumber() {
		user user = new user("username", "password"); 
		account account = new account(user); 
		try {
			int accountNum = account.generateAccountNum();
			assertEquals(accountNum, account.getAccountNum()); 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		
	}

}
