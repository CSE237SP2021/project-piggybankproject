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
	}

	/**
	 * Sends money to another account
	 * 
	 * @throws IOException
	 */
	private static void transferMoney() throws IOException {
		System.out.println("How much would you like to send?");
		double amount = Double.parseDouble(getInput());
		System.out.println("Please give the username of who you would like to send this money to");
		String username = getInput();
		if (userrepo.userExists(username)) {
			if (account.getBalance() > amount) {
				User receiver = new User(username, "masterKey");
				Account accountReceive = new Account(receiver);
				account.sendMoney(amount, accountReceive);
				sessionLog.add("Transfered $" + amount + " to " + username + " - balance: $" + account.getBalance());

			} else {
				System.out.println("Insufficient Funds");
			}
		} else {
			System.out.println("Invalid user.");
		}
	}

	private static void checking(String userInput) throws IOException {
		try {
			int attempt = Integer.parseInt(userInput);
			while (attempt < 0 || attempt > 5) {
				System.out.println("Invalid input. Please enter a number 1-5");
				attempt = Integer.parseInt(getInput());
			}
			decideAction(attempt);
		} catch (NumberFormatException e) {
			System.out.println("Invalid input. Please enter a number 1-5");
		}
	}

	//PRETTY SURE WE CAN GET RID OF THIS???
	/**
	 * Checks to see that someone returns a number that is not 1, 2, or 3.
	 * 
	 * @param attempt
	 * @return
	 */
	private static int checkWrongNumber(int attempt) {
		while (attempt < 0 || attempt > 3) {
			System.out.println("Invalid input. Please enter a number 1-5");
			attempt = Integer.parseInt(getInput());
		}
		return attempt;
	}

	/**
	 * Deposits, withdraws, or views balance depending on user input to the question
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
		System.out.println("How much money would you like to withdraw?");
		double amount = Double.parseDouble(getInput());
		boolean successfulWithdrawal = account.withdrawMoney(amount);
		if (successfulWithdrawal == false) {
			System.out.println("Insufficient Funds for this Withdrawal Amount");
		} else {
			System.out.println("You have successfully withdrawn $" + amount + " from your account!");
			sessionLog.add("Withdrew $" + amount + " - balance: $" + account.getBalance());
		}
	}

	/**
	 * Deposits desired amount into account.
	 * 
	 * @throws IOException
	 */
	private static void deposit() throws IOException {
		System.out.println("How much money would you like to deposit?");
		boolean validInput = false;
		while (!validInput) {
			try {
				double amount = Double.parseDouble(getInput());
				while (amount < 0) {
					System.out.println("Please enter a positive amount of money:");
					amount = Double.parseDouble(getInput());
				}
				validInput = true;
				account.depositMoney(amount);
				sessionLog.add("deposited $" + amount + " - balance: $" + account.getBalance());

			} catch (NumberFormatException e) {
				System.out.println("Please enter a positive amount of money:");
			}
		}
	}

	/**
	 * Print out current user balance.
	 */
	private static void viewBalance() {
		System.out.println("Your current account balance is: $" + account.getBalance());
		sessionLog.add("checked balance - balance: $" + account.getBalance());
	}

	private static String getInput() {
		return keyBoardIn.nextLine();
	}

	private static void viewLog() {
		System.out.println("Log for current session:");
		for (int i = 0; i < sessionLog.size(); i++) {
			System.out.println(sessionLog.get(i));
		}
	}
}
