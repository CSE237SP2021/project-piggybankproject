package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import cse237.Account;
import cse237.User;


class transferTests {
	
	@Test
	void testSuccessfulTransfer() throws IOException {
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
		User sender = new User("personA", "personA"); 
		User receiver = new User("fakeperson237", "fakeperson237"); 
		Account senderAccount = new Account(sender); 
		senderAccount.depositMoney(1);
		double initialSendAmount = senderAccount.getBalance();
		Account receiveAccount = new Account(receiver); 
		receiveAccount.getAccountNum();
		receiveAccount.setBalance();
		boolean success = senderAccount.sendMoney(1, receiveAccount);
		assertFalse(success);
		assertTrue(senderAccount.getBalance()==initialSendAmount); 
	}
	
	@Test
	void testUnsuccessfulTransferToSelf() throws IOException {
		User sender = new User("personA", "personA"); 
		User receiver = new User("personA", "personA"); 
		Account senderAccount = new Account(sender); 
		senderAccount.depositMoney(1);
		double initialSendAmount = senderAccount.getBalance();
		Account receiveAccount = new Account(receiver); 
		receiveAccount.getAccountNum();
		receiveAccount.setBalance();
		boolean success = senderAccount.sendMoney(1, receiveAccount);
		assertFalse(success);
		assertTrue(senderAccount.getBalance()==initialSendAmount); 
	}
	
	@Test
	void testUnsuccessfulTransferNegativeAmount() throws IOException{
		User sender = new User("personA", "personA"); 
		User receiver = new User("personB", "personB"); 
		Account senderAccount = new Account(sender); 
		senderAccount.depositMoney(1);
		double initialSendAmount = senderAccount.getBalance();
		Account receiveAccount = new Account(receiver); 
		receiveAccount.getAccountNum();
		receiveAccount.setBalance();
		double initialReceiveAmount = receiveAccount.getBalance(); 
		boolean success = senderAccount.sendMoney(-10, receiveAccount);
		assertFalse(success);
		assertTrue(senderAccount.getBalance()==initialSendAmount); 
		assertTrue(initialReceiveAmount ==receiveAccount.getBalance());
	}
	
	@Test
	void testUnsuccessfulTransferNegativeAndFakePerson() throws IOException{
		User sender = new User("personA", "personA"); 
		User receiver = new User("fakeperson237", "personB"); 
		Account senderAccount = new Account(sender); 
		senderAccount.depositMoney(1);
		double initialSendAmount = senderAccount.getBalance();
		Account receiveAccount = new Account(receiver); 
		receiveAccount.getAccountNum();
		receiveAccount.setBalance();
		double initialReceiveAmount = receiveAccount.getBalance(); 
		boolean success = senderAccount.sendMoney(-10, receiveAccount);
		assertFalse(success);
		assertTrue(senderAccount.getBalance()==initialSendAmount); 
	}
	
	@Test
	void testUnsuccessfulTransferInsufficientBalanceAndFakePerson() throws IOException{
		User sender = new User("personA", "personA"); 
		User receiver = new User("fakeperson237", "personB"); 
		Account senderAccount = new Account(sender); 
		senderAccount.depositMoney(1);
		double initialSendAmount = senderAccount.getBalance();
		Account receiveAccount = new Account(receiver); 
		receiveAccount.getAccountNum();
		receiveAccount.setBalance();
		double initialReceiveAmount = receiveAccount.getBalance(); 
		boolean success = senderAccount.sendMoney(Double.MAX_VALUE, receiveAccount);
		assertFalse(success);
		assertTrue(senderAccount.getBalance()==initialSendAmount); 
	}
	
	@Test
	void testUnsuccessfulTransferInsufficientBalanceAndSelf() throws IOException{
		User sender = new User("personA", "personA"); 
		User receiver = new User("personA", "personA"); 
		Account senderAccount = new Account(sender); 
		senderAccount.depositMoney(1);
		double initialSendAmount = senderAccount.getBalance();
		Account receiveAccount = new Account(receiver); 
		receiveAccount.getAccountNum();
		receiveAccount.setBalance();
		double initialReceiveAmount = receiveAccount.getBalance(); 
		boolean success = senderAccount.sendMoney(Double.MAX_VALUE, receiveAccount);
		assertFalse(success);
		assertTrue(senderAccount.getBalance()==initialSendAmount); 
	}
}
