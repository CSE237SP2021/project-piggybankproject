package cse237;
import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException; 

public class menu {
	private Scanner keyBoardIn;
	private FileWriter userList; 
	private File userDatabase; 
	public menu() {
		keyBoardIn = new Scanner(System.in); 
		userDatabase = new File("usernames.txt"); 
		try {
			userList = new FileWriter("usernames.txt",true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Logs in the user, creates an account, takes them to a new interface for account actions.
	 * @param args
	 */
	public static void main(String[]args) {
		menu loginScreen = new menu(); 
		loginScreen.displayMainMenu();
		int option1 = Integer.parseInt(loginScreen.getInput()); 
		user currUser = null; 
		if(option1==1) {
			//existing user login
			currUser = loginScreen.userLogin();
			account newAccount = new account(currUser);
			actionPage action = new actionPage(newAccount);
			action.main(null);
		} else {
			//new user; 
			currUser = null; 
			loginScreen.createNewUserPrompt();
		}
		loginScreen.closeBoard(); 
	}
	
	/**
	 * When the user chooses to login, they are brought to this set of steps. When they choose a current username that exists in file, success.  
	 * @return User object, assuming successful login
	 */
	private user userLogin() {
		//already have a profile
		user currUser = null; 
		System.out.println("Please enter your username below:");
		String username = getInput(); 
		if(userExists(username)) {
			System.out.println("Login successful!");
			currUser = new user(username, "password"); 
			return currUser;
		} else {
			System.out.println("Login failed"); 
			while(currUser==null) {
				System.out.println("Retry Login");
				username = getInput(); 
					if(userExists(username)) { 
						currUser = new user(username, "password"); 
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
	 * If the person chooses to create a new profile, they are asked to input a username and password. Makes sure that username doesn't already exist. 
	 */
	private void createNewUserPrompt() {
		user currUser = null; 
		System.out.println("Please Enter a Username");
		String createUsername = getInput(); 
		System.out.println("Please Enter a Password");
		String createPassword = getInput(); 
		while(currUser==null) { 
			currUser = createUser(createUsername,createPassword);
			if(currUser==null) {
				System.out.println("Please Enter a Username");
				createUsername = getInput(); 
				System.out.println("Please Enter a Password");
				createPassword = getInput(); 
			}
		}
		System.out.println("Account Creation Successful");
	}
	
	/**
	 * Displays welcome message and asks for login vs. profile creation.
	 */
	private static void displayMainMenu() {
		System.out.println("Welcome to the piggyBank!");
		System.out.println("Please enter 1. to login or 2. to create a new profile");
	}
	
	private String getInput() {
		return keyBoardIn.nextLine(); 
	}
	
	private void closeBoard() {
		keyBoardIn.close(); 
	}
	
	/**
	 * Creates a user from their desired username & password, unless the username already exists on file
	 * @param username Username of new user to create
	 * @param password Password of new user
	 * @return the User object if user creation is successful, null otherwise
	 */
	public user createUser(String username, String password) {
		try {
			//https://www.baeldung.com/java-append-to-file
			if(this.userExists(username)) {
				System.out.println("Username is already taken"); 
				return null; 
			}
				userList.write(username); 
				userList.append("\n"); 
				user newUser = new user(username,password); 
				userList.close(); 
				return newUser; 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null; 
		} 
	}
		
	/**
	 * Checks to see if an entered username already exists on file.
	 * @param username Username to see if it exists already in database
	 * @return true if username already exists, false otherwise 
	 */
	public boolean userExists(String username) {
		//https://www.w3schools.com/java/java_files_read.asp
		Scanner readUsers;
		try {
			//go through file of existing usernames and see if it exists
			readUsers = new Scanner(this.userDatabase);
			while(readUsers.hasNextLine()) {
				if(readUsers.nextLine().equals(username)) {
					return true; 
				} else {
					//keep going
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false; 
		} 
		return false; 
	}
}
