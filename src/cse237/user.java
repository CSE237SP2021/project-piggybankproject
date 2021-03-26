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
