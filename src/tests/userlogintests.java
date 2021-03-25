package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import cse237.user;

class userlogintests {

	@Test
	void userLoginSuccess() {
		user userLogin = new user("username","password");
		boolean match = userLogin.checkPassword("password"); 
		assertTrue(match); 
		assertTrue(userLogin.getBalance()==0);
	}
	void userLoginFailure() {
		user userLogin = new user("username","password1");
		boolean match = userLogin.checkPassword("password"); 
		assertFalse(match); 
	}
}
