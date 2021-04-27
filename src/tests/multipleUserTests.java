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
	
	@Test
	
	void testAbilityToDepositSuccessOnOtherApprovedAccount() throws IOException{
		Menu mainmenu = new Menu(); 
		double randomUsername = Math.random()*Math.random()*100000000; 
		String user_to_add = String.valueOf(randomUsername);
		String username_2 = "personB"; 
		mainmenu.createUser(user_to_add, "password"); 
		User main_user = new User(username_2, "password"); 
		User sub_user = new User(user_to_add, "password"); 
		Account main_account = new Account(main_user); 
		main_account.getAccountNum(); 
		UserRepo userRepo = new UserRepo("balance.txt", "accounts.txt", "usernames.txt");
		userRepo.addApprovedUser(main_account, user_to_add); 
		Account person_added_account = new Account(sub_user);
		LinkedList <String> list =  userRepo.approvedAccounts(user_to_add); 
		System.out.println(list.toString()); 
		assertTrue(list.contains(String.valueOf(main_account.getAccountNum()))); 
		
	}

}
