package cse237;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class ActionPage {
	private static Account account;
	private static Scanner keyBoardIn;
	private static UserRepo userrepo;
	private static Menu menu;
	private static ArrayList<String> sessionLog = new ArrayList<String>();

	public ActionPage(Account account) {
		this.account = account;
		this.keyBoardIn = new Scanner(System.in);
		this.userrepo = new UserRepo("balance.txt", "account.txt", "usernames.txt");
		this.menu = new Menu();
	}

	public static void main(String[] args) throws IOException {
		ActionPage activePage = new ActionPage(account);
		account.setBalance();
		String actionText = "\n*********************\n\nEnter: \n1 deposit \n2 withdraw \n3 view current balance"
				+ " \n4 transfer money \n5 view session log \n6 add a new user to your main account"
				+ " \n7 change your password" + "\n8 delete your account" + "\n'exit' to exit";
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
	 * Checks that user input is a number between 1-9. (9 is surprise function!)
	 * 
	 * @param userInput
	 * @throws IOException
	 */
	private static void checking(String userInput) throws IOException {
		try {
			int attempt = Integer.parseInt(userInput);
			while (attempt < 0 || attempt > 9) {
				System.out.println("Invalid input. Please enter a number 1-8");
				attempt = Integer.parseInt(getInput());
			}
			decideAction(attempt);
		} catch (NumberFormatException e) {
			if (userInput.equals("exit")) {
				System.out.println("Returning to Main Menu");
			} else {
				System.out.println("Invalid input. Please enter a number 1-8");
			}
		}
	}
	
	/**
	 * Deposits, withdraws, or views balance depending on user input to the
	 * question.
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
		} else if(userChoice == 6){
			addApprovedUser(); 
		} else if (userChoice== 7) {
			inputNewPassword();
		} else if (userChoice == 8) {
			deleteAccountPrompt();
		} else if (userChoice == 9) {
			easterEgg();
		}
		else {
			System.out.println("Invalid entry");
		}
	}

	/**
	 * Deposits desired amount into account.
	 * 
	 * @throws IOException
	 */
	private static void deposit() throws IOException {
		Account accountToUse = actionOnOtherAccount(); 
		accountToUse.setBalance(); 
		boolean validInput = false;
		int amount = 0;
		String input = "";
		while (!validInput) {
			System.out.println("How much money would you like to deposit?");
			input = getInput();
			validInput = validDollarAmount(input);
		}
		double depositAmount = Double.parseDouble(input);
		accountToUse.depositMoney(depositAmount);
		System.out.println("You have successfully deposited $" + depositAmount + " to " + accountToUse.getAccountNum());
		sessionLog.add("Deposited $" + depositAmount + " into account " + accountToUse.getAccountNum() + " - balance: $" + accountToUse.getBalance());
	}

	/**
	 * Asks user to enter withdrawal amount and withdraws it if there are sufficient
	 * funds in account.
	 * 
	 * @throws IOException
	 */
	private static void withdraw() throws IOException {
		Account accountToUse = actionOnOtherAccount(); 
		accountToUse.setBalance(); 
		boolean validInput = false;
		int amount = 0;
		String input = "";
		while (!validInput) {
			System.out.println("How much money would you like to withdraw?");
			input = getInput();
			validInput = validDollarAmount(input);
		}
		double withdrawAmount = Double.parseDouble(input); 
		boolean successfulWithdrawal = accountToUse.withdrawMoney(withdrawAmount);
		if (successfulWithdrawal == false) {
			System.out.println("Insufficient Funds for this Withdrawal Amount");
		} else {
			System.out.println("You have successfully withdrawn $" + withdrawAmount + " from " + accountToUse.getAccountNum());
			sessionLog.add("Withdrew $" + withdrawAmount + " from account "+ accountToUse.getAccountNum() +  " - balance: $" + accountToUse.getBalance());
		}
	}

	/**
	 * Prints out current user balance on account.
	 * @throws IOException 
	 */
	private static void viewBalance() throws IOException {
		Account accountToUse = actionOnOtherAccount(); 
		accountToUse.setBalance(); 
		System.out.println("Your current account balance on account " + accountToUse.getAccountNum() + " is: $" + accountToUse.getBalance());
		sessionLog.add("checked balance on account " + accountToUse.getAccountNum() + " - balance: $" + accountToUse.getBalance());
	}

	/**
	 * Sends money to another account
	 * 
	 * @throws IOException
	 */
	private static boolean transferMoney() throws IOException {
		System.out.println("How much would you like to send? Please note you may only do this from your main account.");
		String input = getInput();
		boolean valid = validDollarAmount(input);
		//Checks to see that input for transferring is a valid positive number.  
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
		//Checks to see if transfer was successful. If not successful, asks for new input. 
		while (!success) {
			System.out.println("How much would you like to send?");
			amount = Double.parseDouble(getInput());
			System.out.println("Please give the username of who you would like to send this money to:");
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
	 * Allows user to add another user to an account using this person's username.
	 * @return true if user added successfully 
	 * @throws IOException
	 */
	public static boolean addApprovedUser() throws IOException{
		System.out.println("Please enter the username of the person you would like to add to your account. Please note you may only do this from your main account."); 
		boolean userExists = false; 
		String userToAdd = ""; 
		while(userExists==false) {
			userToAdd = getInput(); 
			userExists = userrepo.userExists(userToAdd); 
		}
		userrepo.addApprovedUser(account, userToAdd);
		System.out.println("User was successfully added."); 
		return true;
	}
	
	/**
	 * Asks user for a new password and updates it in the text files. 
	 * @throws IOException
	 */
	private static void inputNewPassword() throws IOException {
		System.out.println("Please enter a new password:");
		String newPassword = getInput();
		userrepo.changePassword(account.getUsername(), newPassword);
		System.out.println("You have successfully updated your password.");
	}
	
	/**
	 * Asks user if they want to delete their account then calls delete function
	 * @throws IOException
	 */
	private static void deleteAccountPrompt() throws IOException {
		System.out.println("Are you sure you want to delete this account? Type 'yes' to confirm. We ask that you do not delete accounts with usernames of personA, personB, or fakeperson237, since these are used in our testing!");
		String answer  = "";
		answer = getInput();
		if(answer.equals("yes")) {
			userrepo.deleteAccount(account.getUsername());
			System.out.println("Account successfully deleted. Goodbye.");
			System.exit(0);
		} else {
			System.out.print("You did not enter 'yes' so your account was not deleted.");
		}
	}
	
	/**
	 * Retrieves and prints log for current user session in the same run of the program.
	 */
	private static void viewLog() {
		System.out.println("Log for current session:");
		for (int i = 0; i < sessionLog.size(); i++) {
			System.out.println(sessionLog.get(i));
		}
	}

	/**
	 * Checks that user input is a positive number.
	 * 
	 * @param input
	 * @return
	 */
	public static boolean validDollarAmount(String input) {
		try {
			double amount = Double.parseDouble(input);
			if (amount < 0) {
				System.out.println("Invalid Amount. Please enter a positive number.");
				return false;
			} else {
				return true;
			}
		} catch (NumberFormatException e) {
			System.out.println("Invalid Amount. Please enter a number.");
			return false;
		}
	}
	
	/**
	 * Prompts user to choose an account to perform a transaction on, assuming they are an approved user of this account.
	 * @return the account that a user wants to use
	 * @throws IOException
	 */
	private static Account actionOnOtherAccount() throws IOException {
		System.out.println("Please type 0 if you'd like to do this transaction on your main account or 1 if you'd like to do it on an account that you are a subUser");
		int accountChoice = checkBinaryValue(getInput()); 
		if(accountChoice==0) {
			return account; 
		} else {
			LinkedList <String> approvedAccounts = userrepo.approvedAccounts(account.getUsername()); 
			System.out.println("Below is the list of your approved account numbers"); 
			System.out.println(approvedAccounts.toString()); 
			String accountNumberString = ""; 
			int accountNum=0; 
			boolean validAccountNumber = false; 
			while(!validAccountNumber) {
				System.out.println("Please enter the account Number you want to use or enter 0 to use your main account"); 
				accountNumberString=getInput(); 
				if(validDollarAmount(accountNumberString)) {
					accountNum = Integer.parseInt(accountNumberString); 
				}
				if(accountNum!=0) {
					if(userrepo.isAnApprovedUser(accountNum, account.getUsername()) ) {
						validAccountNumber=true; 
					}	
				} else {
					validAccountNumber = true; 
				}
			}
			if(accountNum!=0) {
				System.out.println("Approved make a transaction on account " + accountNum); 
				String usernameOnSubAccount = userrepo.usernameOnAccount(accountNum);
				User userOnSubAccount = new User(usernameOnSubAccount, "masterkey"); 
				Account accountToMakeTransaction = new Account(userOnSubAccount);

				accountToMakeTransaction.getAccountNum(); 
				return accountToMakeTransaction; 
			} else {
				return account; 
			}
		}
	}
	
	/**
	 * Checks that an input can be parsed and is either 0 or 1.
	 * 
	 * @param String to be checked
	 * @return
	 */
	private static int checkBinaryValue(String loginOption) {
		int input = Integer.parseInt(loginOption);
		User currUser = null;
		while (input > 1 || input < 0) {
			System.out.println("Invalid input. Please enter 0 or 1:");
			input = Integer.parseInt(getInput());
		}
		return input;
	}
	
	private static String getInput() {
		return keyBoardIn.nextLine();
	}
	
	/**
	 * Surprise function :)
	 */
	private static void easterEgg() {
		System.out.println("You found a secret!");
	}
}