package model;

import java.util.ArrayList;

// This BankAccount class stores the user's name, balance and net balance (calculated with regards to saving goals).
// It also contains a list of purchases made from the acoount's balance.
public class BankAccount {
    private double balance;
    private String name;
    private double netBalance;

    //Integrating features into bank account
    private SpendingTracker myPurchaseList;
    private FinanceGoals myFinGoals = new FinanceGoals(0);


    public void setNetBalance(double netBalance) {
        this.netBalance = netBalance;
    }

    public double getNetBalance() {
        return this.netBalance;
    }


    public void updateNetBalance(double netBalance) {
        this.netBalance += netBalance;
    }


    public BankAccount() {
        this.name = "";
        this.balance = 0;
        this.netBalance = 0;
        this.myPurchaseList = new SpendingTracker(); //Is it good practice?
    }

    public BankAccount(String name, double balance) {
        this.name = name;
        if (balance <= 0) {
            this.balance = 0;
            this.netBalance += 0;
        } else {
            this.balance = balance;
            this.netBalance += balance;
        }
        this.myPurchaseList = new SpendingTracker(); //Is this good practice?
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setAccName(String name) {
        this.name = name;
    }

    public double getBalance() {
        return this.balance;
    }

    public String getAccName() {
        return this.name;
    }

    public FinanceGoals getMyFinGoals() {
        return this.myFinGoals;
    }

    public ArrayList<Purchase> getMyPurchaseList() {
        return this.myPurchaseList.getSpendingList();
    }

    public double deposit(double depositAmount) {
        this.balance += depositAmount;
        return this.balance; // returns the balance instead of amount deposit
    }

    public double withdraw(double withdrawAmount) {
        this.balance -= withdrawAmount;
        return this.balance; // returns the balance instead of amount withdrawn
    }

    public String displayBalance() {
        String balanceStr = String.format("%.2f", balance); // Credit to TellerApp project
        return (name + "'s Balance: $" + balanceStr);
    }

    public int searchItem(String inputName) {
        for (Purchase itemInList: this.getMyPurchaseList()) {
            if (itemInList.itemName().equals(inputName)) {
                return this.getMyPurchaseList().indexOf(itemInList);
            }
        }
        return -1;
    }

}
