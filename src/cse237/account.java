package cse237;

import java.util.Scanner;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class account {
	private user user;
	private int accountNumber;
	private double balance;
	private FileWriter accountGenerator; 
	private FileWriter balanceUpdater; 

	private File accountList;
	private File balanceList; 
	
	public account(user user) throws IOException {
		this.user=user;
		this.accountNumber = 0; 
		this.balance=0; 
		this.accountList = new File("accounts.txt"); 
		this.balanceList = new File("balance.txt"); 
		try {
			this.accountGenerator = new FileWriter("accounts.txt", true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		
	}
	
	/**
	 * Deposits money into an account
	 * @param amount Amount of money for depositing
	 * @throws IOException 
	 */
	public void depositMoney(double amount) throws IOException {
		 
		this.balance += amount;
		updateBalance();
	}
	
	/**
	 * Withdraws money from an account, if there are sufficient funds for given amount
	 * @param amount Amount of money intended for withdrawal
	 * @return true if amount has been successfully withdrawn
	 * @throws IOException 
	 */
	public boolean withdrawMoney(double amount) throws IOException {
		if (amount > this.balance) {
			return false;
		}
		else {
			this.balance -= amount;
			updateBalance(); 
			return true;
		}
	}
	/**
	 * This will set the balance of a user when they log in (it uses the file balance.txt to get the historical amount)
	 * @returnthe value of the balance or 0 if the account is new. 
	 * @throws IOException
	 */
	public double setBalance() throws IOException {
		String accountInfo = getFileContents("balance.txt"); 
		Scanner scan = new Scanner(accountInfo); 
		while(scan.hasNextLine()) {
			String accountLine = scan.nextLine(); 
			String [] accountInfoArray = accountLine.split(","); 
			if(Integer.parseInt(accountInfoArray[0])==accountNumber) {
				this.balance = Double.parseDouble(accountInfoArray[1]); 
				return Double.parseDouble(accountInfoArray[1]); 
			}
		}
		return 0; 
	}
	public double getBalance() {
		
		return this.balance;
	}
	public int getAccountNum() {
		Scanner readAccounts;
		try {
			readAccounts = new Scanner(accountList);
			while(readAccounts.hasNextLine()) {
				String [] accountAndUser = readAccounts.nextLine().split(","); 
				String username = accountAndUser[0]; 
				String accountNumberString = accountAndUser[1]; 
						
				if(username.equals(user.getUsername())) {
					int accountNum = Integer.parseInt(accountNumberString); 
					this.accountNumber = accountNum; 
					return accountNum; 
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		return 0; 
		
	}
	/**
	 * This function will randomly generate an account number for a new account
	 * @return a randomly generated account Number
	 * @throws IOException
	 */
	public int generateAccountNum() throws IOException {
		int accountNum = (int) (Math.random()*1000000); 
		
		accountGenerator.write(this.user.getUsername() + "," + accountNum); 
		accountGenerator.append("\n");
		accountGenerator.close(); 
		
		String newAccountList = getFileContents("balance.txt") + accountNum +"," + this.balance + "\n"; 
		FileWriter addNewAccount = new FileWriter("balance.txt",false); 
		addNewAccount.write(newAccountList);
		addNewAccount.close(); 
		return accountNum; 
	}
	/**
	 *  This function intends to help clean up the process of getting the contents of a file
	 * @param filename
	 * @return the contents of a file as a string
	 * @throws IOException
	 */
	public String getFileContents(String filename) throws IOException {
		File f = new File(filename); 
		String fileContents = ""; 
		Scanner scan = new Scanner(f);
		while(scan.hasNextLine()) {
			fileContents = fileContents + scan.nextLine() + "\n"; 
		}
		
		scan.close(); 
	
		return fileContents; 
	}
	/**
	 * This simply updates the balance in the balance.txt file
	 * @throws IOException
	 */
	public void updateBalance() throws IOException { 
		String fileInfo = getFileContents("balance.txt"); 
		Scanner scan = new Scanner(fileInfo); 
		String oldLine = ""; 
		String newLine =""; 
		while(scan.hasNextLine()) {
			String accountBalanceInfo = scan.nextLine(); 
			String [] accountInfoArray = accountBalanceInfo.split(","); 
			if(Integer.parseInt(accountInfoArray[0])==accountNumber) {
				oldLine = accountBalanceInfo; 
				accountInfoArray[1] = Double.toString(this.balance);
				newLine = accountInfoArray[0] + "," + accountInfoArray[1]; 
			}
		}

		fileInfo = fileInfo.replace(oldLine, newLine); 
		FileWriter updateBalance = new FileWriter("balance.txt",false); 
		updateBalance.write(fileInfo);
		updateBalance.close(); 
	}



}
