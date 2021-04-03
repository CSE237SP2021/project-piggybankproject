package cse237;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class UserRepo {
public File balanceTracker; 
public File accountTracker; 
public File usernameTracker; 

public UserRepo(String balanceFile, String accountFile, String usernameFile) {
	// TODO Auto-generated constructor stub
	this.balanceTracker = new File(balanceFile); 
	this.accountTracker = new File(accountFile); 
	this.usernameTracker = new File(usernameFile); 
}

/**
 * Checks to see if an entered username already exists on file.
 * @param username Username to see if it exists already in database
 * @return true if username already exists, false otherwise 
 */
public boolean userExists(String username) {
	//https://www.w3schools.com/java/java_files_read.asp
	Scanner readUsers;
	try {
		//go through file of existing usernames and see if it exists
		readUsers = new Scanner(this.usernameTracker);
		while(readUsers.hasNextLine()) {
			if(readUsers.nextLine().split(",")[0].equals(username)) {
				readUsers.close(); 
				return true; 
			} else {
				//keep going
			}
		}
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		
		return false; 
	} 
	readUsers.close(); 
	return false; 
}
public boolean correctPassword(String username, String password) {
	Scanner readUsers;
	try {
		//go through file of existing usernames and see if it exists
		readUsers = new Scanner(this.usernameTracker);
		while(readUsers.hasNextLine()) {
			String [] userInfo = readUsers.nextLine().split(","); 
			if(userInfo[0].equals(username)) {
				readUsers.close(); 
				return password.equals(userInfo[1]); 
			} else {
				//keep going
			}
		}
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		
		return false; 
	} 
	readUsers.close(); 
	return false; 
}
/**
 * This simply updates the balance in the balance.txt file
 * @throws IOException
 */
public void updateBalance(Account Account) throws IOException { 
	String fileInfo = getFileContents("balance.txt"); 
	Scanner scan = new Scanner(fileInfo); 
	String oldLine = ""; 
	String newLine =""; 
	while(scan.hasNextLine()) {
		String accountBalanceInfo = scan.nextLine(); 
		String [] accountInfoArray = accountBalanceInfo.split(","); 
		if(Integer.parseInt(accountInfoArray[0])==Account.getAccountNum()) {
			oldLine = accountBalanceInfo; 
			accountInfoArray[1] = Double.toString(Account.getBalance());
			newLine = accountInfoArray[0] + "," + accountInfoArray[1]; 
		}
	}

	fileInfo = fileInfo.replace(oldLine, newLine); 
	FileWriter updateBalance = new FileWriter("balance.txt",false); 
	updateBalance.write(fileInfo);
	updateBalance.close(); 
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


}
