package cse237;

import java.util.Scanner;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Account {
	private User user;
	private int accountNumber;
	private double balance;
	private FileWriter accountGenerator;
	private File accountList;
	private File balanceList;
	private UserRepo userRepo;

	public Account(User user) throws IOException {
		this.userRepo = new UserRepo("balance.txt", "accounts.txt", "usernames.txt");
		this.user = user;
		this.accountNumber = 0;
		this.balance = 0;
		this.accountList = new File("accounts.txt");
		this.balanceList = new File("balance.txt");
		try {
			this.accountGenerator = new FileWriter("accounts.txt", true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getUsername() {
		return this.user.getUsername(); 
	}

	/**
	 * Deposits money into an account
	 * 
	 * @param amount
	 *            Amount of money for depositing
	 * @throws IOException
	 */
	public boolean depositMoney(double amount) throws IOException {
		if (amount > 0) {
			this.balance += amount;
			this.userRepo.updateBalance(this);
			return true;
		}
		return false;
	}

	/**
	 * Withdraws money from an account, if there are sufficient funds for given
	 * amount
	 * 
	 * @param amount
	 *            Amount of money intended for withdrawal
	 * @return true if amount has been successfully withdrawn
	 * @throws IOException
	 */
	public boolean withdrawMoney(double amount) throws IOException {

		if (amount > this.balance || amount < 0) {
			return false;
		} else {
			this.balance -= amount;
			this.userRepo.updateBalance(this);
			return true;
		}
	}

	/**
	 * Sends a specified amount of money from active user to another
	 * 
	 * @param amount
	 *            Money to be sent to new user
	 * @param username
	 *            user to receive money
	 * @throws IOException
	 */
	public boolean sendMoney(double amount, Account accountReceive) throws IOException {
		boolean sufficientFunds = this.withdrawMoney(amount);
		int receiverAccountNum = accountReceive.getAccountNum();
		//Ensures that there are sufficient funds for transfer, receiver exists (account number is not 0), and not sending money to oneself.
		if (sufficientFunds == true && receiverAccountNum != 0 && receiverAccountNum != this.getAccountNum()) {
			accountReceive.getAccountNum();
			accountReceive.setBalance();
			accountReceive.depositMoney(amount);
			return true;
		}
		//If receiver does not exist, return money back to sender that was originally withdrawn.
		if ((receiverAccountNum == 0 || receiverAccountNum == this.getAccountNum()) && sufficientFunds == true) {
			this.depositMoney(amount);
			if (receiverAccountNum == 0) {
				System.out.println("This user does not exist.");

			} else {
				System.out.println("You cannot transfer money to yourself.");
			}
		}
		if (!sufficientFunds) {
			System.out.println("Insufficient Funds.");
		}
		return false;
	}

	/**
	 * This will set the balance of a user when they log in (it uses the file
	 * balance.txt to get the historical amount)
	 * 
	 * @return the value of the balance or 0 if the account is new.
	 * @throws IOException
	 */
	public double setBalance() throws IOException {
		String accountInfo = userRepo.getFileContents("balance.txt");
		Scanner scan = new Scanner(accountInfo);
		while (scan.hasNextLine()) {
			String accountLine = scan.nextLine();
			String[] accountInfoArray = accountLine.split(",");
			if (Integer.parseInt(accountInfoArray[0]) == accountNumber) {
				this.balance = Double.parseDouble(accountInfoArray[1]);
				return Double.parseDouble(accountInfoArray[1]);
			}
		}
		return 0;
	}

	public double getBalance() {
		return this.balance;
	}

	/**
	 * Retruns the account number for a user's account.
	 * 
	 * @return
	 */
	public int getAccountNum() {
		Scanner readAccounts;
		try {
			readAccounts = new Scanner(accountList);
			while (readAccounts.hasNextLine()) {
				String[] accountAndUser = readAccounts.nextLine().split(",");
				String username = accountAndUser[0];
				String accountNumberString = accountAndUser[1];
				if (username.equals(user.getUsername())) {
					int accountNum = Integer.parseInt(accountNumberString);
					this.accountNumber = accountNum;
					return accountNum;
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * This function will randomly generate an account number for a new account
	 * 
	 * @return a randomly generated account Number
	 * @throws IOException
	 */
	public int generateAccountNum() throws IOException {
		int accountNum = (int) (Math.random() * 1000000);
		accountGenerator.write(this.user.getUsername() + "," + accountNum);
		accountGenerator.append("\n");
		accountGenerator.close();
		String newAccountList = userRepo.getFileContents("balance.txt") + accountNum + "," + this.balance + "\n";
		FileWriter addNewAccount = new FileWriter("balance.txt", false);
		addNewAccount.write(newAccountList);
		addNewAccount.close();
		return accountNum;
	}
	
}