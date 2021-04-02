package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import cse237.UserRepo;
import cse237.Menu;
import cse237.User;

class userlogintests {

	@Test
	void userLoginSuccess() {
		User userLogin = new User("username","password");
		boolean match = userLogin.checkPassword("password"); 
		assertTrue(match); 
	}
	@Test
	void userLoginFailure() {
		User userLogin = new User("username","password1");
		boolean match = userLogin.checkPassword("password"); 
		assertFalse(match); 
	}
	
	@Test
	void userCreation() {
		Menu mainmenu = new Menu(); 
		double randomUsername = Math.random()*Math.random()*100000000; 
		String username = String.valueOf(randomUsername);
		User newUser = mainmenu.createUser(username, "password"); 
		boolean created = newUser!=null ; 
		assertTrue(created); 
	}
	
	@Test
	void userExists() {
		UserRepo userRepo = new UserRepo("balance.txt", "accounts.txt", "usernames.txt");
		Menu mainmenu = new Menu(); 
		int username = (int) (Math.random() * 1000000*Math.random()); 
		int password = (int) (Math.random() * 1000000*Math.random()); 
		User userTest = new User(Integer.toString(username), Integer.toString(password)); 
		mainmenu.createUser(userTest.getUsername(), userTest.getPassword()); 
		boolean found = userRepo.userExists(userTest.getUsername());
		assertTrue(found); 
	}
	
	
}