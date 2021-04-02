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
		String userInput = "";
		while(!userInput.equals("exit")) {
			System.out.println(actionText); 
			userInput = getInput(); 
			System.out.println(userInput);
			if(!userInput.equals("exit")) {
				checking(userInput);
			}
		}
	}
	
	/**
	 * Checks to make sure entry is a positive number, either 1/2/or 3, and sends to decision method for next action.
	 * @param userInput
	 * @throws IOException
	 */
	private static void checking(String userInput) throws IOException {
		try {
			int attempt = Integer.parseInt(userInput);
			attempt = checkWrongNumber(attempt);
			decideAction(attempt);
		}
		catch (NumberFormatException e) {
			System.out.println("Invalid input. Please enter 1,2, or 3");
		}
	}
	
	/**
	 * Checks to see that someone returns a number that is not 1, 2, or 3. 
	 * @param attempt
	 * @return
	 */
	private static int checkWrongNumber(int attempt) {
		while (attempt < 0 || attempt >3) {
			System.out.println("Invalid input. Please enter 1,2, or 3");
			attempt = Integer.parseInt(getInput());
			}
		return attempt;
	}
	
	/**
	 * Deposits, withdraws, or views balance depending on user input to the question
	 * @param userChoice
	 * @throws IOException
	 */
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
	 * Print out current user balance.
	 */
	private static void viewBalance() {
		System.out.println("Your current account balance is: $" + account.getBalance());
	}
	
	private static String getInput() {
		return keyBoardIn.nextLine(); 
	} 
}
