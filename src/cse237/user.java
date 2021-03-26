package cse237;

public class user {
	private String username; 
	private String password; 
	private double balance; 
	
	public user(String username, String password) {
	this.username=username;
	this.password = password;
	this.balance=0; 
	}

	/**
	 * Makes sure that the inputed password matches their stores password. 
	 * @param passwordAttempt User's password input when promted
	 * @return true if password matches as it should
	 */
	public boolean checkPassword(String passwordAttempt) {
		return passwordAttempt.equals(this.password);
	}

	public double getBalance() {
		return this.balance;
	}
	
	public String getUsername() {
		return this.username; 
	}
}
