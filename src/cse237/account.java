package cse237;

public class account {
	private user user;
	private int accountNum;
	private double balance;
	
	public account(user user) {
		this.user=user;
		this.accountNum = (int)(Math.random()*1000);
		this.balance=user.getBalance(); 
	}
	
	public void depositMoney(double amount) {
		this.balance += amount;
	}
	
	public boolean withdrawMoney(double amount) {
		if (amount > this.balance) {
			return false;
		}
		else {
			this.balance -= amount;
			return true;
		}
	}
	
	public double getBalance() {
		return this.balance;
	}
}
