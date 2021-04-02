package cse237;

import java.io.IOException;
import java.util.Scanner;

public class ActionPage {
	private static Account account;
	private static Scanner keyBoardIn;
	
	public ActionPage(Account account) {
		this.account=account;
		this.keyBoardIn= new Scanner(System.in);
	}
	
	public static void main(String[]args) throws IOException {
		ActionPage activePage = new ActionPage(account);
		account.setBalance(); 
		String actionText = "Enter: \n1 for depositing \n2 for withdrawal \n3 to see current balance";
		System.out.println(actionText);
		String userInput = "";
		boolean valid = false;
		checking(activePage, actionText, userInput, valid);
	}

	private static void checking(ActionPage activePage, String actionText, String userInput, boolean valid) throws IOException {
		while(!userInput.equals("exit")) {
			while(!valid) {
				try {
					int attempt = Integer.parseInt(getInput());
					while (attempt < 0) {
						System.out.println("you entered a negative number");
						attempt = Integer.parseInt(getInput());
					}
					valid = true;
					System.out.println("you entered positive number");
					decideAction(attempt);
					System.out.println(actionText);
					userInput = activePage.getInput();
					System.out.println("option 1");
					//checking(activePage,actionText,userInput, valid);
				}
				catch (NumberFormatException e) {
					System.out.println("you entered a non number");
				}
			}
			System.out.println("option 2");
			checking(activePage,actionText,userInput, valid);
		}
	}

	private static void decideAction(int userChoice) throws IOException {
		if (userChoice == 1) {
			deposit();
		}
		else if (userChoice == 2) {
			withdraw();
		}
		else if (userChoice == 3) {
			viewBalance();
		}
		else {
			System.out.println("Invalid entry");
		}
	}
	
	/**
	 * Asks user to enter withdrawal amount and withdraws it if there are sufficient funds in account.
	 * @throws IOException 
	 */
	private static void withdraw() throws IOException {
		System.out.println("How much money would you like to withdraw?");
		double amount = Double.parseDouble(getInput());
		boolean successfulWithdrawal = account.withdrawMoney(amount);
		if (successfulWithdrawal == false) {
			System.out.println("Insufficient Funds for this Withdrawal Amount");
		}
		else {
			System.out.println("You have successfully withdrawn $"+ amount + " from your account!");
		}
	}
	
	/**
	 * Deposits desired amount into account. 
	 * @throws IOException 
	 */
	private static void deposit() throws IOException {
		System.out.println("How much money would you like to deposit?");
		boolean validInput = false; 
		while(!validInput) {
			try {
				double amount = Double.parseDouble(getInput());
				while (amount < 0) {
					System.out.println("Please enter a positive amount of money:");
					amount = Double.parseDouble(getInput());
				}
				validInput = true;
				account.depositMoney(amount);
			}
			catch (NumberFormatException e){
				System.out.println("Please enter a positive amount of money:");
			}
		}
	}

	/**
	 * Print out current user balance
	 */
	private static void viewBalance() {
		System.out.println("Your current account balance is: $" + account.getBalance());
	}
	
	private static String getInput() {
		return keyBoardIn.nextLine(); 
	} 
}
