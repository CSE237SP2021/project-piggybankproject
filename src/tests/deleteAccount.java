package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import cse237.Account;
import cse237.Menu;
import cse237.User;
import cse237.UserRepo;

class deleteAccount {

	@Test
	void deleteAccount() throws IOException {
		Menu mainmenu = new Menu(); 
		UserRepo userRepo = new UserRepo("balance.txt", "accounts.txt", "usernames.txt");
		double randomUsername = Math.random()*Math.random()*100000000; 
		String username = String.valueOf(randomUsername);
		User newUser = mainmenu.createUser(username, "password"); 
		boolean found = userRepo.userExists(username);
		assertTrue(found); 
		userRepo.deleteAccount(username);
		found = userRepo.userExists(username);
		assertFalse(found);
	}
	
	@Test
	void deleteAccountList() throws IOException {
		Menu mainmenu = new Menu(); 
		UserRepo userRepo = new UserRepo("balance.txt", "accounts.txt", "usernames.txt");
		double randomUsername = Math.random()*Math.random()*100000000; 
		String username = String.valueOf(randomUsername);
		User newUser = mainmenu.createUser(username, "password"); 
		Account account = new Account(newUser);
		int accountNum = account.generateAccountNum();
		boolean found = userRepo.accountExists(accountNum);
		assertTrue(found); 
		userRepo.deleteAccount(username);
		found = userRepo.accountExists(account.getAccountNum());
		assertFalse(found);
	}
	
}
