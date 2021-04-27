package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.LinkedList;

import org.junit.jupiter.api.Test;

import cse237.Account;
import cse237.ActionPage;
import cse237.Menu;
import cse237.User;
import cse237.UserRepo;

class multipleUserTests {

	@Test
	void testSuccessfullyAddedUser() throws IOException {
		Menu mainmenu = new Menu(); 
		double randomUsername = Math.random()*Math.random()*100000000; 
		String username = String.valueOf(randomUsername);
		String username_2 = "personB";
		User main_user = mainmenu.createUser(username, "password"); 
		UserRepo userRepo = new UserRepo("balance.txt", "accounts.txt", "usernames.txt");
		Account main_user_account = new Account(main_user); 
		main_user_account.generateAccountNum(); 
		boolean succesful = userRepo.addApprovedUser(main_user_account, username_2); 
		assertTrue(succesful); 
	}
	
	@Test
	void testFailedAddingUserFakePerson() throws IOException {
		Menu mainmenu = new Menu(); 
		double randomUsername = Math.random()*Math.random()*100000000; 
		String username = String.valueOf(randomUsername);
		String username_2 = "randompersonwhodoesnotexist12345678910111213";
		User main_user = mainmenu.createUser(username, "password"); 
		UserRepo userRepo = new UserRepo("balance.txt", "accounts.txt", "usernames.txt");
		Account main_user_account = new Account(main_user); 
		main_user_account.generateAccountNum(); 
		boolean succesful = userRepo.addApprovedUser(main_user_account, username_2); 
		assertFalse(succesful); 
	}
	

	
	
}
