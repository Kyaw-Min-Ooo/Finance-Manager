package ui;

import java.util.Scanner;
import model.BankAccount;
import model.*;

//Class credit to the TellerApp application
// This class serve as an application interface for operating different features included in the user stories
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

        init(); // Initialize scanner and bank account object
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

    //Requires: non-zero length String
    //Modifies: this
    //Effects: Get username from keyboard and store in bank username
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

    //Effects: Displays the menu of the application to the user
    public void displayMenu() {
        System.out.println("--------------------------");
        System.out.println(this.bank.getAccName() + "'s Bank Options");
        System.out.println("--------------------------");
        System.out.println("Select from:");
        System.out.println("\td -> deposit");
        System.out.println("\tw -> withdraw");
        System.out.println("\tm --> make purchase");
        System.out.println("\ts ->  print all purchases/spending");
        System.out.println("\tsg --> set saving goals");
        System.out.println("\tp ->  print balance and active financial goals");
        System.out.println("\tst ->  print the statistics of all your spending");
        System.out.println("\tq -> quit");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("d")) {
            doDeposit();
        } else if (command.equals("w")) {
            doWithdrawal();
        } else if (command.equals("m")) {
            makePurchase();
        } else if (command.equals("s")) {
            displayAllPurchases();
        } else if (command.equals("sg")) {
            updateSavingGoals();
        } else if (command.equals("p")) {
            displayOverallBalance();
        } else if (command.equals("st")) {
            performStats();
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
        displayOverallBalance();
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
        displayOverallBalance();
    }

    //Modifies: this
    //Effects: Ask for saving amount, check if amount entered is valid then store in net balance after calculation
    public void updateSavingGoals() {
        System.out.println("How much money do you want to save this month?");
        double savingsAmount = input.nextDouble();

        Boolean validSavings = bank.getBalance() > savingsAmount;

        if (validSavings && bank.getBalance() != 0) {
            bank.getMyFinGoals().setSavingAmount(savingsAmount);
            bank.setNetBalance(bank.getBalance() - bank.getMyFinGoals().getSavingAmount());
            bank.getMyFinGoals().setIsSaving(true);
            displayOverallBalance();
        } else {
            System.out.println("Invalid saving input! Please try again");
            updateSavingGoals();
        }
    }

    //Effects: Display all purchases made by the user in the whole month
    public void displayAllPurchases() {
        for (Purchase items: this.bank.getMyPurchaseList()) {
            System.out.println(items.itemName() + " | $" + items.value());
        }
        System.out.println("\n");
        displayOverallBalance(); // Overall Display
    }

    //Modifies: this
    //Effects: Take purchase amount and name. Then, check if user has enough spendable balance after considering
    // saving goals (or other financial goals in the future). Then, print balance after the purchase
    public void makePurchase() {
        String itemName;
        System.out.println("What is the name of the item/transaction?");
        itemName = input.next();
        System.out.println("How much is this item?");
        double value = input.nextDouble();

        if (bank.getNetBalance() < value || bank.getNetBalance() < value) {
            System.out.println("Not enough balance!");
            if (bank.getNetBalance() < value && bank.getMyFinGoals().getIsSaving()) {
                System.out.println("Purchase goes against saving quota!");
            }
        } else {
            bank.withdraw(value);
            bank.updateNetBalance(-1 * value);
            bank.getMyPurchaseList().add(new Purchase(itemName,value));

            System.out.println("Purchase successful!");
            int itemIndex = bank.getMySpendingTracker().searchItem(itemName);
            System.out.println(bank.getMyPurchaseList().get(itemIndex).displayTransaction());
            displayOverallBalance(); // Overall Display
        }
    }

    //Effects: Prints the current balance, spendable and any target savings
    public void displayOverallBalance() {
        System.out.println(bank.displayBalance());
        System.out.println("Spendable balance : $" + bank.getNetBalance());
        if (this.bank.getMyFinGoals().getIsSaving()) {
            System.out.println("Your targeted saving amounts: $" + bank.getMyFinGoals().getSavingAmount());
        } else {
            System.out.println("There are currently no active saving goals.");
        }
    }

    // Print item with the highest spending, sum of total spending list's value, visualize based on spending amount
    public void performStats() {
        bank.getMySpendingTracker().calculateMaxSpending();
        System.out.println("Item with highest spending: ");
        int highestIndex = bank.getMySpendingTracker().getMaxPurchaseIndex();
        System.out.println(bank.getMyPurchaseList().get(highestIndex).itemName()
                + " | $" + bank.getMyPurchaseList().get(highestIndex).value());

        System.out.println("Total amount of all items spent: $" + bank.getMySpendingTracker().getTotalSpending());

        //Visualise data
        int multiplier;
        for (Purchase item: this.bank.getMyPurchaseList()) {
            multiplier = 0;
            multiplier = (int) (item.value() / 5.0);
            System.out.println(item.itemName());
            String histogram = "";
            for (int i = 0; i < multiplier; i++) {
                histogram += "#";
            }
            histogram = (multiplier < 1) ? "#" : histogram; // In case iem costs lower than 5 bucks
            System.out.println(histogram);
        }
    }
}
