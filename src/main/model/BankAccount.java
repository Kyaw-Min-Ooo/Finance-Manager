package model;

import java.util.ArrayList;

// This BankAccount class stores the user's name, balance and net balance (calculated with regards to saving goals).
// It also contains a list of purchases made from the account's balance.
public class BankAccount {
    private double balance;
    private String name;
    private double netBalance;

    //Integrating Spending Tracking and Saving Goals features into user bank account
    private SpendingTracker mySpendingTracker;
    private FinanceGoals myFinGoals = new FinanceGoals();

    public BankAccount() {
        this.name = "";
        this.balance = 0;
        this.netBalance = 0;
        this.mySpendingTracker = new SpendingTracker(); //Is it good practice?
    }

    //Requires: name has non-zero length
    //Modifies: this
    //Effects: Checks if balance is zero under if yes, then set baalnce to zero otherwise, add the balance amount into
    // this.balance
    public BankAccount(String name, double balance) {
        this.name = name;
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

    public void setAccName(String name) {
        this.name = name;
    }

    public String getAccName() {
        return this.name;
    }

    //Effects: Return financial class which for now only contains saving goal
    public FinanceGoals getMyFinGoals() {
        return this.myFinGoals;
    }

    public SpendingTracker getMySpendingTracker() {
        return this.mySpendingTracker;
    }

    public ArrayList<Purchase> getMyPurchaseList() {
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
    //Effects: Subtract the amount of purchase from account's balanace
    public double withdraw(double withdrawAmount) {
        this.balance -= withdrawAmount;
        return this.balance; // returns the balance instead of amount withdrawn
    }


    //Effects: Print the account username and remaining balance
    public String displayBalance() {
        String balanceStr = String.format("%.2f", balance); // Credit to TellerApp project
        return (name + "'s Balance: $" + balanceStr);
    }


    //Effects: Takes an input String and search through this.mySpendingTracker for an item with matching name.
    // returns the first stance of the item. Does not account for repeating name.
    public int searchItem(String inputName) {
        for (Purchase itemInList: this.getMyPurchaseList()) {
            if (itemInList.itemName().equals(inputName)) {
                return this.getMyPurchaseList().indexOf(itemInList);
            }
        }
        return -1;
    }

}
