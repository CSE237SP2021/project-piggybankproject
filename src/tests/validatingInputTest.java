package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import cse237.Account;
import cse237.ActionPage;
import cse237.User;

class validatingInputTest {

	@Test
	void passesValidInput() throws IOException {
		User userLogin = new User("username", "password");
		Account account = new Account(userLogin);
		ActionPage action = new ActionPage(account);
		String input = "123";
		assertTrue(action.validDollarAmount(input));
	}

	@Test
	void failsValidInputLetters() throws IOException {
		User userLogin = new User("username", "password");
		Account account = new Account(userLogin);
		ActionPage action = new ActionPage(account);
		String input = "abc";
		assertFalse(action.validDollarAmount(input));
	}

	@Test
	void failsValidInputLettersAndNumbers() throws IOException {
		User userLogin = new User("username", "password");
		Account account = new Account(userLogin);
		ActionPage action = new ActionPage(account);
		String input = "abc123";
		assertFalse(action.validDollarAmount(input));
	}

	@Test
	void failsValidInputNegativeNumber() throws IOException {
		User userLogin = new User("username", "password");
		Account account = new Account(userLogin);
		ActionPage action = new ActionPage(account);
		String input = "-10";
		assertFalse(action.validDollarAmount(input));
	}
}
