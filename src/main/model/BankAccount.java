package model;

import java.util.ArrayList;

public class BankAccount {
    private double balance;
    private String name;

    //Integrating features into bank account
    private SpendingTracker mySpendingList;

    public BankAccount() {
        this.name = "";
        this.balance = 0;
        this.mySpendingList = new SpendingTracker(); //Is it good practice?
    }

    public BankAccount(String name, double balance) {
        this.name = name;
        this.balance = balance;
        this.mySpendingList = new SpendingTracker(); //Is this good practice?
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBalance() {
        return this.balance;
    }

    public String getAccName() {
        return this.name;
    }

    public ArrayList<Purchase> getMySpendingList() {
        return this.mySpendingList.getSpendingList();
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

}
