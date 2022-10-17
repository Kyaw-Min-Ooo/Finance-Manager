package ui;

import java.util.ArrayList;
import java.util.Scanner;
import model.BankAccount;
import model.*;


//Class credit to the TellerApp application
// This class serve as an application interface for operating different features included in the user storeiss
public class BankApp {
    private Scanner input;
    private BankAccount bank;


    //Effects: Run the Bank Application
    public BankApp() {
        runBankApp();
    }

    //Modifies: this
    //Effects: Help establish a smooth control flow for user operations on the app
    private void runBankApp() {
        boolean keepGoing = true;
        String command = null;

        init();
        askAccName();

        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("\nThank you for using the Spending Coach program! Until Next Time!");
    }

    //Requires: non zero lenght String
    //Modifies: this
    //Effects: Get user name from keyboard and store in bank user name
    private void askAccName() {
        System.out.println("What is your name?");
        bank.setAccName(input.nextLine());
    }

    //Modifies: this
    //Effects: Initializes BankAccount and the Scanner
    private void init() {
        bank = new BankAccount();
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    //Effects: Displays the menu of the appication to the user
    public void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\td -> deposit");
        System.out.println("\tw -> withdraw");
        System.out.println("\tp --> make purchase");
        System.out.println("\tsg --> set saving goals");
        System.out.println("\ts ->  print all purchases/spending");
        System.out.println("\tpg ->  print all active financial goals");
        System.out.println("\tq -> quit");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("d")) {
            doDeposit();
        } else if (command.equals("w")) {
            doWithdrawal();
        } else if (command.equals("p")) {
            makePurchase();
        } else if (command.equals("s")) {
            displayAllPurchases();
        } else if (command.equals("sg")) {
            activateSavingGoals();
        }  else if (command.equals("pg")) {
            displayActiveFinGoals();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    //Used from TellerApp project
    //Modifies: this
    //Effects: Take user input and perform a money deposit to the account
    private void doDeposit() {
        System.out.print("Enter amount to deposit: $");
        double amount = input.nextDouble();

        if (amount >= 0.0) {
            bank.deposit(amount);
            bank.updateNetBalance(amount);
        } else {
            System.out.println("Cannot deposit negative amount...\n");
        }
        System.out.println(bank.displayBalance());
        System.out.println("Available balance : $" + bank.getNetBalance());
    }

    //Used from TellerApp project
    //Modifies: this
    //Effects: Take user input and perform a money withdrawal from account
    private void doWithdrawal() {
        System.out.print("Enter amount to withdraw: $");
        double amount = input.nextDouble();

        if (amount < 0.0) {
            System.out.println("Cannot withdraw negative amount...\n");
        } else if (bank.getBalance() < amount) {
            System.out.println("Insufficient balance on account...\n");
        } else {
            bank.withdraw(amount);
            bank.updateNetBalance(-1 * amount);
        }
        System.out.println(bank.displayBalance());
        System.out.println("Available balance : $" + bank.getNetBalance());
    }

    //Modifies: this
    //Effects: Ask for saving amount, check if amount entered is valid then store in net balance after calculation
    public void activateSavingGoals() {
        System.out.println("How much money do you want to save this month?");
        double savingsAmount = input.nextDouble();

        Boolean validSavings = bank.getBalance() > savingsAmount;

        if (validSavings && bank.getBalance() != 0) {
            bank.getMyFinGoals().setSavingAmount(savingsAmount);
            bank.setNetBalance(bank.getBalance() - bank.getMyFinGoals().getSavingAmount());
            System.out.println("Your targeted saving amounts: $" + bank.getMyFinGoals().getSavingAmount());
        }
    }

    //Effects: Display user's available balance and current saving amount
    public void displayActiveFinGoals() {
        if (bank.getMyFinGoals().getSavingAmount() != 0) {
            System.out.println("Available balance : $" + bank.getNetBalance());
            System.out.println("Saving goal amount: $" + bank.getMyFinGoals().getSavingAmount());
        } else {
            System.out.println("There are currently no active saving goals.");
        }
    }

    //Effects: Display all purchases made by the user in the whole month
    public void displayAllPurchases() {
        for (Purchase items: this.bank.getMyPurchaseList()) {
            System.out.println(items.itemName() + " | $" + items.value());
            System.out.println("Your targeted saving amounts: $" + bank.getMyFinGoals().getSavingAmount());
        }
    }

    //Modifies: this
    //Effects: Take purchase amount and name. Then, check if user has enough available balance after considering
    // saving goals (or other financial goals in the future). Then, print balance after the purchase
    public void makePurchase() {
        ArrayList<Purchase> purchaseList = bank.getMyPurchaseList();
        String itemName;
        System.out.println("What is the name of the item/transaction?");
        itemName = input.next();
        System.out.println("How much is this item?");
        double value = input.nextDouble();

        if (value > bank.getNetBalance()) {
            System.out.println("Not enough balance! Purchase goes against saving quota!");
        } else {
            bank.withdraw(value);
            bank.updateNetBalance(-1 * value);
            purchaseList.add(new Purchase(itemName,value));

            System.out.println("Purchase successful!\n");
            int itemIndex = bank.searchItem(itemName);
            System.out.println(purchaseList.get(itemIndex).displayTransaction());
            System.out.println(bank.displayBalance());
            System.out.println("\nSpendable balance : $" + bank.getNetBalance());
            System.out.println("Your targeted saving amounts: $" + bank.getMyFinGoals().getSavingAmount());
        }
    }
}
