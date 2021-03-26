package cse237;
//testing testing 123
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
			loginScreen.createNewUser();
		}
		loginScreen.closeBoard(); 
	}

	private user userLogin() {
		//already have an account
		user currUser = null; 
		System.out.println("Please Enter your username below!");
		String username = getInput(); 
		if(userExists(username)) {
			System.out.println("login successful");
			currUser = new user(username, "password"); 
			return currUser;
		} else {
			System.out.println("Login failed"); 
			while(currUser==null) {
				System.out.println("Retry Login");
				username = getInput(); 
					if(userExists(username)) { 
						currUser = new user(username, "password"); 
						System.out.println("login successful");
						return currUser;
					} else {
					System.out.println("Login failed"); 
					}
			}
		}
		return null;
	}

	private void createNewUser() {
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

	
	
	private static void displayMainMenu() {
		System.out.println("Welcome to the piggyBank!");
		System.out.println("Please enter 1. to login or 2. to create a new account");
	}
	
	
	
	
	private String getInput() {
		return keyBoardIn.nextLine(); 
	}
	private void closeBoard() {
		keyBoardIn.close(); 
	}
	/**
	 * 
	 * @param username username of new use to create
	 * @param password password of new user
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
	 * 
	 * @param username username to see if it exists
	 * @return true if user exists, false otherwise 
	 */
	public boolean userExists(String username) {
		//https://www.w3schools.com/java/java_files_read.asp
		Scanner readUsers;
		try {
			// go through file of existing usernames and see if it exists
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
