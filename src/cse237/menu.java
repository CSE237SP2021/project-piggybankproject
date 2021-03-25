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
	
	public static void main(String[]args) {
		
		menu loginScreen = new menu(); 
		loginScreen.displayMainMenu();
		int option1 = Integer.parseInt(loginScreen.getInput()); 
		if(option1==1) {
			System.out.println("Please Enter your username below!");
			System.out.println("Please Enter your password below!");
		} else {
			user currUser = null; 
			System.out.println("Please Enter a Username");
			String createUsername = loginScreen.getInput(); 
			String createPassword = loginScreen.getInput(); 
			while(currUser==null) { 
				currUser = loginScreen.createUser(createUsername,createPassword);
				if(currUser==null) {
					createUsername = loginScreen.getInput(); 
					createPassword = loginScreen.getInput(); 
				}
			
				
			}
			System.out.println("Account Creation Successful");
			
		}
		loginScreen.closeBoard(); 
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
