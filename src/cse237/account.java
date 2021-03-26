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
	
	/**
	 * Deposits money into an account
	 * @param amount Amount of money for depositing
	 */
	public void depositMoney(double amount) {
		this.balance += amount;
	}
	
	/**
	 * Withdraws money from an account, if there are sufficient funds for given amount
	 * @param amount Amount of money intended for withdrawal
	 * @return true if amount has been successfully withdrawn
	 */
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
