package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import cse237.menu;
import cse237.user;

class userlogintests {

	@Test
	void userLoginSuccess() {
		user userLogin = new user("username","password");
		boolean match = userLogin.checkPassword("password"); 
		assertTrue(match); 
	}
	@Test
	void userLoginFailure() {
		user userLogin = new user("username","password1");
		boolean match = userLogin.checkPassword("password"); 
		assertFalse(match); 
	}
	
	@Test
	void userCreation() {
		menu mainmenu = new menu(); 
		double randomUsername = Math.random()*Math.random()*100000000; 
		String username = String.valueOf(randomUsername);
		user newUser = mainmenu.createUser(username, "password"); 
		boolean created = newUser!=null ; 
		assertTrue(created); 
	}
	
	@Test
	void userExists() {
		menu mainmenu = new menu(); 
		int username = (int) (Math.random() * 1000000*Math.random()); 
		int password = (int) (Math.random() * 1000000*Math.random()); 
		user userTest = new user(Integer.toString(username), Integer.toString(password)); 
		mainmenu.createUser(userTest.getUsername(), userTest.getPassword()); 
		boolean found = mainmenu.userExists(userTest.getUsername()); 
		assertTrue(found); 
	}
	
	
}