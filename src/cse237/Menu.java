package cse237;

import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class Menu {
	private static Scanner keyBoardIn;
	private FileWriter userList;
	private File userDatabase;
	private UserRepo trackUsers;

	public Menu() {
		trackUsers = new UserRepo("balance.txt", "accounts.txt", "usernames.txt");
		keyBoardIn = new Scanner(System.in);
		userDatabase = new File("usernames.txt");
		try {
			userList = new FileWriter("usernames.txt", true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Logs in the user, creates an account, takes them to a new interface for
	 * account actions.
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		Menu loginScreen = new Menu();
		loginScreen.login();
	}

	/**
	 * Checking user input to either login or create new profile, validating input,
	 * & allowing them to exit.
	 * 
	 * @throws IOException
	 */
	private void login() throws IOException {
		String loginOption = "";
		while (!loginOption.equals("exit")) {
			displayMainMenu();
			loginOption = getInput();
			if (!loginOption.equals("exit")) {
				try {
					int input = checkLoginNumber(loginOption);
					loginOrCreate(this, input);
					closeBoard();
					break;
				} catch (NumberFormatException e) {
					System.out.println("Please enter a number.");
				}
			}
		}
	}

	/**
	 * Checks that an input can be parsed and is either 1 or 2.
	 * 
	 * @param loginScreen
	 * @return
	 */
	private static int checkLoginNumber(String loginOption) {
		int input = Integer.parseInt(loginOption);
		User currUser = null;
		while (input < 1 || input > 2) {
			System.out.println("Invalid input. Please enter 1 or 2:");
			input = Integer.parseInt(getInput());
		}
		return input;
	}

	/**
	 * Depending on user input, either log the user in or create a new account.
	 * 
	 * @param loginScreen
	 * @param input
	 * @throws IOException
	 */
	private static void loginOrCreate(Menu loginScreen, int input) throws IOException {
		User currUser;
		if (input == 1) {
			// existing user login
			loginScreen.existingUserLogin();
		} else if (input == 2) {
			// new user;
			currUser = null;
			loginScreen.createNewUserPrompt();
		} else {
			System.out.println("Invalid Input. Please enter 1 or 2:");
		}
	}

	/**
	 * Logs in a current user.
	 * 
	 * @throws IOException
	 */
	private void existingUserLogin() throws IOException {
		User currUser;
		currUser = userLogin();
		Account newAccount = new Account(currUser);
		newAccount.getAccountNum();
		ActionPage action = new ActionPage(newAccount);
		action.main(null);
	}

	/**
	 * When the user chooses to login, they are brought to this set of steps. When
	 * they choose a current username that exists in file, success.
	 * 
	 * @return User object, assuming successful login
	 */
	User userLogin() {
		// already have a profile
		User currUser = null;
		System.out.println("Please enter your username below:");
		String username = getInput();
		System.out.println("Please enter your password below:");
		String password = getInput();
		if (trackUsers.userExists(username) && trackUsers.correctPassword(username, password)) {
			System.out.println("Login successful!");
			currUser = new User(username, password);
			return currUser;
		} else {
			System.out.println("Login failed. Username or password is incorrect");
			while (currUser == null) {
				System.out.println("Retry Login");
				System.out.println("Please enter your username below:");
				username = getInput();
				System.out.println("Please enter your password below:");
				password = getInput();
				if (trackUsers.userExists(username) && trackUsers.correctPassword(username, password)) {
					currUser = new User(username, password);
					System.out.println("Login successful!");
					return currUser;
				} else {
					System.out.println("Login failed");
				}
			}
		}
		return null;
	}

	/**
	 * If the person chooses to create a new profile, they are asked to input a
	 * username and password. Makes sure that username doesn't already exist.
	 * 
	 * @throws IOException
	 */
	private void createNewUserPrompt() throws IOException {
		User currUser = null;
		System.out.println("Please Enter a Username");
		String createUsername = getInput();
		System.out.println("Please Enter a Password");
		String createPassword = getInput();
		while (currUser == null) {
			currUser = createUser(createUsername, createPassword);
			if (currUser == null) {
				System.out.println("Please Enter a Username");
				createUsername = getInput();
				System.out.println("Please Enter a Password");
				createPassword = getInput();
			}
		}
		Account newAccount = new Account(currUser);
		try {
			int accountNum = newAccount.generateAccountNum();
			System.out.println(
					"Account Creation Successful. Please restart the application to login. Your account number is "
							+ accountNum);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Displays welcome message and asks for login vs. profile creation.
	 */
	private static void displayMainMenu() {
		System.out.println("Welcome to the piggyBank!");
		System.out.println("To finish your session please type 'exit' on the main menu.");
		System.out.println("To start, please enter 1. to login or 2. to create a new profile");
	}

	/**
	 * Creates a user from their desired username & password, unless the username
	 * already exists on file
	 * 
	 * @param username
	 *            Username of new user to create
	 * @param password
	 *            Password of new user
	 * @return the User object if user creation is successful, null otherwise
	 */
	public User createUser(String username, String password) {
		try {
			// Reference: https://www.baeldung.com/java-append-to-file
			if (trackUsers.userExists(username)) {
				System.out.println("Username is already taken");
				return null;
			}
			userList.write(username);
			userList.write(",");
			userList.write(password);
			userList.append("\n");
			User newUser = new User(username, password);
			userList.close();
			return newUser;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	private static String getInput() {
		return keyBoardIn.nextLine();
	}

	private void closeBoard() {
		keyBoardIn.close();
	}
}