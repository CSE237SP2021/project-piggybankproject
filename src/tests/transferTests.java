package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import cse237.Account;
import cse237.User;
import cse237.UserRepo;

class transferTests {

	@Test
	void testSuccessfulTransfer() throws IOException {
		UserRepo userRepo = new UserRepo("balance.txt", "accounts.txt", "usernames.txt"); 
		User sender = new User("personA", "personA"); 
		User receiver = new User("personB", "personB"); 
		Account senderAccount = new Account(sender); 
		double initialSendAmount = senderAccount.getBalance(); 
		senderAccount.depositMoney(200);
		Account receiveAccount = new Account(receiver); 
		receiveAccount.getAccountNum();
		receiveAccount.setBalance();
		double initialReceiveAmount = receiveAccount.getBalance(); 
		senderAccount.sendMoney(200, receiveAccount);
		assertTrue(senderAccount.getBalance()==initialSendAmount); 
		assertTrue(initialReceiveAmount+200 == receiveAccount.getBalance()); 

	}

}
