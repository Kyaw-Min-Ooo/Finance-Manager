package model;

import java.util.ArrayList;

// This BankAccount class stores the user's accName, balance and net balance (calculated with regard to saving goals).
// It also contains a list of purchases made from the account's balance.
public class BankAccount {
    private double balance;
    private String accName;
    private double netBalance;

    //Integrating Spending Tracking and Saving Goals features into user bank account
    private SpendingTracker mySpendingTracker;
    private FinanceGoals myFinGoals = new FinanceGoals();

    public BankAccount() {
        this.accName = "";
        this.balance = 0;
        this.netBalance = 0;
        this.mySpendingTracker = new SpendingTracker(); //Is it good practice?
    }

    //Requires: accName has non-zero length
    //Modifies: this
    //Effects: Checks if balance is zero or under. Yes --> Set balance and net balance to zero otherwise,
    // add the balance amount into this.balance. Finally, initialize a SpendingTracker
    public BankAccount(String accName, double balance) {
        this.accName = accName;
        if (balance <= 0) {
            this.balance = 0;
            this.netBalance += 0;
        } else {
            this.balance = balance;
            this.netBalance += balance;
        }
        this.mySpendingTracker = new SpendingTracker(); //Is this good practice?
    }

    public void setNetBalance(double netBalance) {
        this.netBalance = netBalance;
    }

    public double getNetBalance() {
        return this.netBalance;
    }

    //Requires: non-negative double
    //Modifies: this
    //Effects: Increase or decrease the net balance due to deposit or withdrawal
    public void updateNetBalance(double amount) {
        this.netBalance += amount;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getBalance() {
        return this.balance;
    }

    public void setAccName(String accName) {
        this.accName = accName;
    }

    public String getAccName() {
        return this.accName;
    }

    //Effects: Return financial class which for now only contains saving goal
    public FinanceGoals getMyFinGoals() {
        return this.myFinGoals;
    }

    public SpendingTracker getMySpendingTracker() {
        return this.mySpendingTracker;
    }

    public ArrayList<Purchase> getMySpendingList() {
        return this.mySpendingTracker.getSpendingList();
    }

    //Requires: non-negative double amount
    //Modifies: this
    //Effects: Add the amount of purchase into account balance
    public double deposit(double depositAmount) {
        this.balance += depositAmount;
        return this.balance; // returns the balance instead of amount deposit
    }

    //Requires: non-negative double amount
    //Modifies: this
    //Effects: Subtract the amount of purchase from account's balance
    public double withdraw(double withdrawAmount) {
        this.balance -= withdrawAmount;
        return this.balance; // returns the balance instead of amount withdrawn
    }


    //Effects: Print the account username and remaining balance
    public String displayBalance() {
        String balanceStr = String.format("%.2f", balance); // Credit to TellerApp project
        return (accName + "'s Balance: $" + balanceStr);
    }


}
