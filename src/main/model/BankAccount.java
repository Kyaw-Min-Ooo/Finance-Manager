package model;

import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

// This BankAccount class stores the user's accName, balance and net balance (calculated with regard to saving goals).
// It also contains a list of purchases made from the account's balance and financial goals the user might commit to
public class BankAccount implements Writable {
    private double balance;
    private String accName;
    private double netBalance;

    //Integrating Spending Tracking and Saving Goals features into user bank account
    private SpendingTracker mySpendingTracker;
    private FinanceGoals myFinGoals;

    public BankAccount() {
        this.accName = "";
        this.balance = 0;
        this.netBalance = 0;
        this.mySpendingTracker = new SpendingTracker();
        this.myFinGoals = new FinanceGoals();
    }

    //Requires: accName has non-zero length
    //Modifies: this
    //Effects: Checks if balance is zero or under. Yes --> Set balance and net balance to zero
    // otherwise,add the balance amount and initialize spening tracker and financial goals
    public BankAccount(String accName, double balance) {
        this.accName = accName;
        if (balance <= 0) {
            this.balance = 0;
            this.netBalance += 0;
        } else {
            this.balance = balance;
            this.netBalance += balance;
        }
        this.mySpendingTracker = new SpendingTracker();
        this.myFinGoals = new FinanceGoals();
    }

    //Requires: non-negative balance and net balance
    //Modifies: this
    //Effects: Set all fields in BankAccount according to
    public BankAccount(String accName, double balance, double netBalance) {
        this.accName = accName;
        this.balance = balance;
        this.netBalance = netBalance;
        this.mySpendingTracker = new SpendingTracker();
        this.myFinGoals = new FinanceGoals();
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

    @Override
    // Effects: extract data from class field and store as JSON objects
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("accName", this.accName);
        json.put("balance", this.balance);
        json.put("netBalance", this.netBalance);
        json.put("spendingTracker", this.mySpendingTracker.toJson());
        json.put("financeGoals", this.myFinGoals.toJson());
        return json;
    }


}
