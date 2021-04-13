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
		

	 
	@Test
	void testUnsuccessfulTransferInsufficientFunds() throws IOException {
		UserRepo userRepo = new UserRepo("balance.txt", "accounts.txt", "usernames.txt"); 
		User sender = new User("personA", "personA"); 
		User receiver = new User("personB", "personB"); 
		Account senderAccount = new Account(sender); 
		double initialSendAmount = senderAccount.getBalance(); 
		Account receiveAccount = new Account(receiver); 
		receiveAccount.getAccountNum();
		receiveAccount.setBalance();
		double initialReceiveAmount = receiveAccount.getBalance(); 
		boolean success = senderAccount.sendMoney(Double.MAX_VALUE, receiveAccount);
		assertFalse(success);
		assertTrue(senderAccount.getBalance()==initialSendAmount); 
		assertTrue(initialReceiveAmount == receiveAccount.getBalance()); 
	}
	
	@Test
	void testUnsuccessfulTransferFakePerson() throws IOException {
		UserRepo userRepo = new UserRepo("balance.txt", "accounts.txt", "usernames.txt"); 
		User sender = new User("personA", "personA"); 
		User receiver = new User("fakeperson237", "fakeperson237"); 
		Account senderAccount = new Account(sender); 
		senderAccount.depositMoney(1);
		double initialSendAmount = senderAccount.getBalance();
		Account receiveAccount = new Account(receiver); 
		receiveAccount.getAccountNum();
		receiveAccount.setBalance();
		double initialReceiveAmount = receiveAccount.getBalance(); 
		boolean success = senderAccount.sendMoney(1, receiveAccount);
		assertFalse(success);
		assertTrue(senderAccount.getBalance()==initialSendAmount); 
	}
}
