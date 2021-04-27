package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import cse237.Menu;
import cse237.User;
import cse237.UserRepo;

class changePassword {
	@Test
	void changePasswordSuccess() throws IOException {
		Menu mainmenu = new Menu(); 
		UserRepo userRepo = new UserRepo("balance.txt", "accounts.txt", "usernames.txt");
		double randomUsername = Math.random()*Math.random()*100000000; 
		String username = String.valueOf(randomUsername);
		User newUser = mainmenu.createUser(username, "password"); 	
		userRepo.changePassword(username, "newPassword");
		boolean newpassword = userRepo.correctPassword(username, "newPassword");
		assertTrue(newpassword);
	}
}
