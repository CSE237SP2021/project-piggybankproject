package cse237;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

public class UserRepo {
	public File balanceTracker;
	public File accountTracker;
	public File usernameTracker;
	public File approvedUserTracker;

	public UserRepo(String balanceFile, String accountFile, String usernameFile) {
		this.balanceTracker = new File(balanceFile);
		this.accountTracker = new File(accountFile);
		this.usernameTracker = new File(usernameFile);
		this.approvedUserTracker = new File("approvedUsers.txt");
	}

	/**
	 * Goes through the file contents of accounts to see if an account exists
	 * 
	 * @param accountNumber
	 * @return true if the account exists, false otherwise
	 */
	public boolean accountExists(int accountNumber) {
		try {
			String accountList = getFileContents("accounts.txt");
			Scanner readAccountList = new Scanner(accountList);
			boolean found = false;
			while (readAccountList.hasNextLine() && found == false) {
				String accountInfo = readAccountList.nextLine();
				String[] accountInfoArray = accountInfo.split(",");
				if (Integer.parseInt(accountInfoArray[1]) == accountNumber) {
					return true;
				} else {
					// keep searching through next lines
				}
			}
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Checks to see if an entered username already exists on file.
	 * 
	 * @param username Username to see if it exists already in database
	 * @return true if username already exists, false otherwise
	 */
	public boolean userExists(String username) {
		// Reference: https://www.w3schools.com/java/java_files_read.asp
		Scanner readUsers;
		try {
			// go through file of existing usernames and see if it exists
			readUsers = new Scanner(this.usernameTracker);
			while (readUsers.hasNextLine()) {
				if (readUsers.nextLine().split(",")[0].equals(username)) {
					readUsers.close();
					return true;
				} else {
					// keep going
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		}
		readUsers.close();
		return false;
	}

	/**
	 * Checks to see if a password is correct for a specific account
	 * 
	 * @param username
	 * @param password
	 * @return true if the password is correct, false otherwise
	 */
	public boolean correctPassword(String username, String password) {
		Scanner readUsers;
		try {
			// go through file of existing usernames and see if it exists
			readUsers = new Scanner(this.usernameTracker);
			while (readUsers.hasNextLine()) {
				String[] userInfo = readUsers.nextLine().split(",");
				if (userInfo[0].equals(username)) {
					readUsers.close();
					return password.equals(userInfo[1]);
				} else {
					// keep going
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		}
		readUsers.close();
		return false;
	}

	/**
	 * Changes user password by finding their username in the .txt file and updating the entry line.
	 * @param username
	 * @param updatedPassword
	 * @throws IOException
	 */
	public void changePassword(String username, String updatedPassword) throws IOException {
	
		String usernameInfo = getFileContents("usernames.txt"); 
		Scanner scan = new Scanner(usernameInfo);
		String oldLine = "";
		String newLine = "";
		while (scan.hasNextLine()) {
			String usernamePasswordEntry= scan.nextLine();
			String[] accountInfoArray = usernamePasswordEntry.split(",");
			if (accountInfoArray[0].equals(username)) {
				oldLine = usernamePasswordEntry;
				newLine = username + "," + updatedPassword;
			}
		}
			usernameInfo = usernameInfo.replace(oldLine, newLine);
			FileWriter updatePassword = new FileWriter("usernames.txt", false);
			updatePassword.write(usernameInfo);
			updatePassword.close();
		
	}

	/**
	 * Deletes account by finding the username in the .txt files and updating
	 * the entry lines in all respective files.
	 * 
	 * @param username
	 * @param updatedPassword
	 * @throws IOException
	 */
	public void deleteAccount(String username) throws IOException {
		// Updating usernames.txt to replace with a placeholder
		String usernameInfo = getFileContents("usernames.txt");
		Scanner scan = new Scanner(usernameInfo);
		String oldLine = "";
		String newLine = "";
		while (scan.hasNextLine()) {
			String usernameLine = scan.nextLine();
			String[] accountInfoArray = usernameLine.split(",");
			if (accountInfoArray[0].equals(username)) {
				oldLine = usernameLine;
				newLine = "xxx";
			}
			usernameInfo = usernameInfo.replace(oldLine, newLine);
			usernameInfo = usernameInfo.replace("xxx\n", "");
			FileWriter updateUsers = new FileWriter("usernames.txt", false);
			updateUsers.write(usernameInfo);
			updateUsers.close();
		}
		// Updating accounts.txt to replace with a placeholder
		String accountsText = getFileContents("accounts.txt");
		scan = new Scanner(accountsText);
		oldLine = "";
		newLine = "";
		String accountNumber = "";
		while (scan.hasNextLine()) {
			String accountLine = scan.nextLine();
			String[] accountInfoArray = accountLine.split(",");
			if (accountInfoArray[0].equals(username)) {
				accountNumber = accountInfoArray[1];
				oldLine = accountLine;
				newLine = "xxx";
			}
			accountsText = accountsText.replace(oldLine, newLine);
			accountsText = accountsText.replace("xxx\n", "");
			FileWriter updateAccounts = new FileWriter("accounts.txt", false);
			updateAccounts.write(accountsText);
			updateAccounts.close();
		}
		// Updating balance.txt to replace with a placeholder
		String balanceText = getFileContents("balance.txt");
		scan = new Scanner(balanceText);
		oldLine = "";
		newLine = "";
		while (scan.hasNextLine()) {
			String balanceLine = scan.nextLine();
			String[] accountInfoArray = balanceLine.split(",");
			if (accountInfoArray[0].equals(accountNumber)) {
				oldLine = balanceLine;
				newLine = "xxx";
			}
			balanceText = balanceText.replace(oldLine, newLine);
			balanceText = balanceText.replace("xxx\n", "");
			FileWriter updateBalance = new FileWriter("balance.txt", false);
			updateBalance.write(balanceText);
			updateBalance.close();
		}
		// Updating approvedUsers.txt to replace with a placeholder
		String approvedText = getFileContents("approvedUsers.txt");
		scan = new Scanner(approvedText);
		oldLine = "";
		newLine = "";
		while (scan.hasNextLine()) {
			String approvedLine = scan.nextLine();
			String[] accountInfoArray = approvedLine.split(",");
			if (accountInfoArray[0].equals(accountNumber) || accountInfoArray[1].equals(username)) {
				oldLine = approvedLine;
				newLine = "xxx";
				approvedText = approvedText.replace(oldLine, newLine);
				approvedText = approvedText.replace("xxx\n", "");
			}
	
			FileWriter updateApproved = new FileWriter("approvedUsers.txt", false);
			updateApproved.write(approvedText);
			updateApproved.close();
		}
	}

	/**
	 * This simply updates the balance in the balance.txt file
	 * 
	 * @throws IOException
	 */
	public void updateBalance(Account Account) throws IOException {
		String fileInfo = getFileContents("balance.txt");
		Scanner scan = new Scanner(fileInfo);
		String oldLine = "";
		String newLine = "";
		while (scan.hasNextLine()) {
			String accountBalanceInfo = scan.nextLine();
			String[] accountInfoArray = accountBalanceInfo.split(",");
			if (Integer.parseInt(accountInfoArray[0]) == Account.getAccountNum()) {
				oldLine = accountBalanceInfo;
				accountInfoArray[1] = Double.toString(Account.getBalance());
				newLine = accountInfoArray[0] + "," + accountInfoArray[1];
			}
		}
		fileInfo = fileInfo.replace(oldLine, newLine);
		FileWriter updateBalance = new FileWriter("balance.txt", false);
		updateBalance.write(fileInfo);
		updateBalance.close();
	}

	/**
	 * This function intends to help clean up the process of getting the contents of
	 * a file
	 * 
	 * @param filename
	 * @return the contents of a file as a string
	 * @throws IOException
	 */
	public String getFileContents(String filename) throws IOException {
		File f = new File(filename);
		String fileContents = "";
		Scanner scan = new Scanner(f);
		while (scan.hasNextLine()) {
			fileContents = fileContents + scan.nextLine() + "\n";
		}
		scan.close();
		return fileContents;
	}

	/**
	 * Adds an approved user to an account.
	 * @param Account
	 * @param username
	 * @return
	 * @throws IOException
	 */
	public boolean addApprovedUser(Account Account, String username) throws IOException {
		if(userExists(username)) {
			FileWriter fw = new FileWriter(approvedUserTracker, true);
			fw.append(Account.getAccountNum() + "," + username + "," + "\n");
			fw.close();
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Returns username of associate account number from accounts.txt.
	 * @param accountNumber
	 * @return username associated with account
	 * @throws IOException
	 */
	public String usernameOnAccount(int accountNumber) throws IOException {
		String fileInfo = getFileContents("accounts.txt");
		Scanner scan = new Scanner(fileInfo);
		while (scan.hasNextLine()) {
			String line = scan.nextLine();
			String[] lineArray = line.split(",");
			if (Integer.parseInt(lineArray[1]) == accountNumber) {
				return lineArray[0];
			}
		}
		return "";
	}

	/**
	 * Returns a list of all accounts a user has been approved for/added to.
	 * @param username
	 * @return list of all approved accounts for a user
	 * @throws IOException
	 */
	public LinkedList<String> approvedAccounts(String username) throws IOException {
		String fileInfo = getFileContents("approvedUsers.txt");
		int i = 0;
		LinkedList<String> approvedAccounts = new LinkedList<String>();
		Scanner scan = new Scanner(fileInfo);
		while (scan.hasNextLine()) {
			String line = scan.nextLine();
			String[] lineArray = line.split(",");
			if (lineArray[1].equals(username)) {
				i = i + 1;
				approvedAccounts.addLast(i + ": " + lineArray[0]);
			}
		}
		if (i == 0) {
			approvedAccounts.add("You have no other approved accounts.");
		}
		return approvedAccounts;
	}

	/**
	 * Declares whether a user has been approved to use a certain account.
	 * @param accountNum
	 * @return true if user is approved for an account
	 * @throws IOException
	 */
	public boolean isAnApprovedUser(int accountNum, String username) throws IOException {
		String fileInfo = getFileContents("approvedUsers.txt");
		Scanner scan = new Scanner(fileInfo);
		while (scan.hasNextLine()) {
			String line = scan.nextLine();
			String[] lineArray = line.split(",");
			if (Integer.parseInt(lineArray[0]) == accountNum && lineArray[1].equals(username)) {
				return true;
			}
		}
		System.out.println("You are not an approved user for account " + accountNum);
		return false;
	}
}