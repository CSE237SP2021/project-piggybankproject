package cse237;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class ActionPage {
	private static Account account;
	private static Scanner keyBoardIn;
	private static UserRepo userrepo;
	private static ArrayList<String> sessionLog = new ArrayList<String>();

	public ActionPage(Account account) {
		this.account = account;
		this.keyBoardIn = new Scanner(System.in);
		this.userrepo = new UserRepo("balance.txt", "account.txt", "usernames.txt");
	}

	public static void main(String[] args) throws IOException {
		ActionPage activePage = new ActionPage(account);
		account.setBalance();
		String actionText = "Enter: \n1 deposit \n2 withdraw \n3 view current balance"
				+ " \n4 transfer money \n5 view session log";
		String userInput = "";
		while (!userInput.equals("exit")) {
			System.out.println(actionText);
			userInput = getInput();
			if (!userInput.equals("exit")) {
				checking(userInput);
			}
		}
		activePage.keyBoardIn.close();
	}

	/**
	 * Sends money to another account
	 * 
	 * @throws IOException
	 */
	private static boolean transferMoney() throws IOException {
		System.out.println("How much would you like to send?");
		String input = getInput();
		boolean valid = validDollarAmount(input);
		while (valid == false) {
			input = getInput();
			valid = validDollarAmount(input);
		}
		double amount = Double.parseDouble(input);
		System.out.println("Please give the username of who you would like to send this money to");
		String username = getInput();
		User receiver = new User(username, "masterKey");
		Account accountReceive = new Account(receiver);
		boolean success = account.sendMoney(amount, accountReceive);
		while(!success) {
			
			System.out.println("How much would you like to send?");
			amount = Double.parseDouble(getInput());
			System.out.println("Please give the username of who you would like to send this money to");
			username = getInput();
			receiver = new User(username, "masterKey");
			accountReceive = new Account(receiver);
			success = account.sendMoney(amount, accountReceive);
		}
		System.out.println("Transfered $" + amount + " to " + username + " - balance: $" + account.getBalance());
		sessionLog.add("Transfered $" + amount + " to " + username + " - balance: $" + account.getBalance());
		return true;
	}

	/**
	 * Checks that user input is a number between 1-5. 
	 * @param userInput
	 * @throws IOException
	 */
	private static void checking(String userInput) throws IOException {
		try {
			int attempt = Integer.parseInt(userInput);
			while (attempt < 0 || attempt > 5) {
				System.out.println("Invalid input. Please enter a number 1-5");
				attempt = Integer.parseInt(getInput());
			}
			decideAction(attempt);
		} catch (NumberFormatException e) {
			if(userInput.equals("exit")) {
				System.out.println("Returning to Main Menu");
			} else {
				System.out.println("Invalid input. Please enter a number 1-5");
			}
			
		}
	}

	/**
	 * Deposits, withdraws, or views balance depending on user input to the question.
	 * 
	 * @param userChoice
	 * @throws IOException
	 */
	private static void decideAction(int userChoice) throws IOException {
		if (userChoice == 1) {
			deposit();
		} else if (userChoice == 2) {
			withdraw();
		} else if (userChoice == 3) {
			viewBalance();
		} else if (userChoice == 4) {
			transferMoney();
		} else if (userChoice == 5) {
			viewLog();
		} else {
			System.out.println("Invalid entry");
		}
	}

	/**
	 * Asks user to enter withdrawal amount and withdraws it if there are sufficient
	 * funds in account.
	 * 
	 * @throws IOException
	 */
	private static void withdraw() throws IOException {
		boolean validInput = false;
		int amount = 0;
		String input = "";
		while (!validInput) {
			System.out.println("How much money would you like to withdraw?");
			input = getInput();
			validInput = validDollarAmount(input);
		}
		double withdrawAmount = Double.parseDouble(input);
		boolean successfulWithdrawal = account.withdrawMoney(withdrawAmount);
		if (successfulWithdrawal == false) {
			System.out.println("Insufficient Funds for this Withdrawal Amount");
		} else {
			System.out.println("You have successfully withdrawn $" + withdrawAmount + " from your account!");
			sessionLog.add("Withdrew $" + withdrawAmount + " - balance: $" + account.getBalance());
		}
	}

	/**
	 * Deposits desired amount into account.
	 * 
	 * @throws IOException
	 */
	private static void deposit() throws IOException {
		boolean validInput = false;
		int amount = 0;
		String input = "";
		while (!validInput) {
			System.out.println("How much money would you like to deposit?");
			input = getInput();
			validInput = validDollarAmount(input);
		}
		double depositAmount = Double.parseDouble(input);
		account.depositMoney(depositAmount);
		sessionLog.add("deposited $" + depositAmount + " - balance: $" + account.getBalance());
	}
	
	/**
	 * Checks that user input is a positive number.
	 * @param input
	 * @return
	 */
	private static boolean validDollarAmount(String input) {
		try {
			double amount = Double.parseDouble(input);
			if (amount < 0) {
				System.out.println("Invalid Amount. Please enter a positive number.");
				return false;
			}
			else {
				return true;
			}
		} catch (NumberFormatException e) {
			System.out.println("Invalid Amount. Please enter a number.");
			return false;
		}
	}

	/**
	 * Print out current user balance.
	 */
	private static void viewBalance() {
		System.out.println("Your current account balance is: $" + account.getBalance());
		sessionLog.add("checked balance - balance: $" + account.getBalance());
	}

	/**
	 * Retrieves and prints log for current user session. 
	 */
	private static void viewLog() {
		System.out.println("Log for current session:");
		for (int i = 0; i < sessionLog.size(); i++) {
			System.out.println(sessionLog.get(i));
		}
	}
	
	private static String getInput() {
		return keyBoardIn.nextLine();
	}
}