package cse237;

import java.util.Scanner;

public class actionPage {
	private static account account;
	private static Scanner keyBoardIn;
	
	public actionPage(account account) {
		this.account=account;
		this.keyBoardIn= new Scanner(System.in);
	}
	
	public static void main(String[]args) {
		actionPage activePage = new actionPage(account);
		System.out.println("Press 1 for depositing, Press 2 for withdrawal");
		String userInput = activePage.getInput();
		while(!userInput.equals("exit")) {
		int userChoice = Integer.parseInt(userInput);
		if (userChoice == 1) {
			deposit();
		}
		else {
			withdraw();
		}
		System.out.println("Press 1 for depositing, Press 2 for withdrawal");
		userInput = activePage.getInput();
	}
}
	/**
	 * Asks user to enter withdrawal amount and withdraws it if there are sufficient funds in account.
	 */
	private static void withdraw() {
		System.out.println("How much money would you like to withdraw?");
		double amount = Double.parseDouble(getInput());
		boolean successfulWithdrawal = account.withdrawMoney(amount);
		if (successfulWithdrawal == false) {
			System.out.println("Insufficient Funds for this Withdrawal Amount");
		}
		else {
			System.out.println("You have successfully withdrawn $"+ amount + "from your account!");
		}
	}
	
	/**
	 * Deposits desired amount into account. 
	 */
	private static void deposit() {
		System.out.println("How much money would you like to deposit?");
		double amount = Double.parseDouble(getInput());
		account.depositMoney(amount);
	}
	
	private static String getInput() {
		return keyBoardIn.nextLine(); 
	} 
}
