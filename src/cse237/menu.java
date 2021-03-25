package cse237;

import java.util.Scanner;

import java.io.File; 
import java.io.FileWriter;
import java.io.IOException; 

public class menu {
	private Scanner keyBoardIn;
	public menu() {
		keyBoardIn = new Scanner(System.in); 
		
	}
	
	public static void main(String[]args) {
		
		menu loginScreen = new menu(); 
		loginScreen.displayMainMenu();
		int option1 = Integer.parseInt(loginScreen.getInput()); 
		if(option1==1) {
			System.out.println("Please Enter your username below!");
			System.out.println("Please Enter your password below!");
		} else {
			System.out.println("output other");
			String s2 = loginScreen.getInput(); 
			try {
				FileWriter userList = new FileWriter("usernames.txt");
				userList.write(s2); 
				userList.close(); 
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			
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
}
