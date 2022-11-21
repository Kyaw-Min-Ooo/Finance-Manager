package ui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import model.BankAccount;
import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

// Class credit to the TellerApp application example
// This class serve as an application interface for operating different features included in the user stories
public class BankApp {
    private static final String JSON_STORE = "./data/bankaccount.json";
    private Scanner input;
    private BankAccount bank;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private GUI ui;

    //Effects: Run the Bank Application
    public BankApp() throws FileNotFoundException {
        bank = new BankAccount();
        input = new Scanner(System.in);
        input.useDelimiter("\n");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        ui = new GUI(this); // Allow GUI class to have access to all info
        ui.run();
    }

    //Modifies: this
    //Effects: Help establish a smooth control flow for user operations on the app
    private void runBankApp()  {
        boolean keepGoing = true;
        String command = null;

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

    //Effects: return this Bank Account reference
    public BankAccount getBank() {
        return this.bank;
    }

    //Requires: non-zero length String
    //Modifies: this
    //Effects: Get username from keyboard and store in bank username
    private void askAccName() {
        System.out.println("What is your name?");
        bank.setAccName(input.nextLine());
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
        System.out.println("\tg --> set saving goals");
        System.out.println("\tp ->  print balance and active financial goals");
        System.out.println("\tt ->  print the statistics of all your spending");
        System.out.println("\tl ->  load bank account from file");
        System.out.println("\tf ->  save bank account to file");
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
        } else if (command.equals("g")) {
            updateSavingGoals();
        } else if (command.equals("p")) {
            displayOverallBalance();
        } else if (command.equals("t")) {
            performStats();
        } else if (command.equals("l")) {
            loadBankAccount();
        } else if (command.equals("f")) {
            saveBankAccount();
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
    //Effects: Ask for saving amount, check if amount entered is valid, store in net balance after calculation
    public void updateSavingGoals() {
        System.out.print("How much money do you want to save this month? $");
        double savingsAmount = input.nextDouble();

        boolean validSavings = bank.getBalance() > savingsAmount;
        if (validSavings && bank.getBalance() != 0) {
            bank.getMyFinGoals().setSavingAmount(savingsAmount);
            bank.setNetBalance(bank.getBalance() - bank.getMyFinGoals().getSavingAmount());
            bank.getMyFinGoals().setIsSaving(true);
            displayOverallBalance();
        } else {
            System.out.println("Invalid saving input! Please try again");
            updateSavingGoals(); // Self reference to allow one more input
        }
    }

    //Effects: Display all purchases made by the user in the whole month
    public void displayAllPurchases() {
        for (Purchase items: this.bank.getMySpendingList()) {
            System.out.println(items.itemName() + " | $" + items.value());
        }
        System.out.println("\n");
        displayOverallBalance(); // Overall Display
    }

    //Modifies: this
    //Effects: Take purchase amount and name. Then, check if user has enough spendable balance after considering
    // saving goals (or other financial goals in the future). Then, print balance after the purchase
    public void makePurchase() {
        System.out.print("How much is this item? $");
        double value = input.nextDouble();

        if (bank.getNetBalance() < value || bank.getBalance() < value) {
            System.out.println("Not enough balance!");
            if (bank.getNetBalance() < value && bank.getMyFinGoals().getIsSaving()) {
                System.out.println("Purchase goes against saving quota!");
            }
        } else {
            String itemName;
            System.out.println("What is the name of the item/transaction? ");
            itemName = input.next();
            bank.withdraw(value);
            bank.updateNetBalance(-1 * value);
            bank.getMySpendingList().add(new Purchase(itemName,value));

            System.out.println("Purchase successful!");
            int itemIndex = bank.getMySpendingTracker().searchItem(itemName);
            System.out.println(bank.getMySpendingList().get(itemIndex).displayTransaction());
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

    // Effects: Print item with the highest spending, sum of total spending list's value,
    // visualize the spending pattern based on spending amount
    public void performStats() {
        if (bank.getMySpendingList().isEmpty()) {
            System.out.println("No purchase data present to display statistics. Please try again later...");
        } else {
            bank.getMySpendingTracker().calculateMaxSpending();
            System.out.print("Item with highest spending: ");

            // Local highestIndex created since too long chain method call
            int highestIndex = bank.getMySpendingTracker().getMaxPurchaseIndex();
            System.out.println(bank.getMySpendingList().get(highestIndex).itemName()
                    + " | $" + bank.getMySpendingList().get(highestIndex).value());

            System.out.println("Total spending: $" + bank.getMySpendingTracker().getTotalSpending() + "\n");

            //Visualise spending amounts based on spending ratio!
            int multiplier;
            for (Purchase item: this.bank.getMySpendingList()) {
                multiplier = 0;
                multiplier = (int) (item.value() / 5.0);
                System.out.println(item.itemName());
                String histogram = "";
                for (int i = 0; i < multiplier; i++) {
                    histogram += "#";
                }
                histogram = (multiplier < 1) ? "#" : histogram; // In case iem costs lower than 5 bucks
                System.out.println(histogram + "\n");
            }
        }
    }

    // EFFECTS: saves the BankAccount to file
    // Code credit to Thingy example project
    public void saveBankAccount() {
        try {
            jsonWriter.open();
            jsonWriter.write(this.bank);
            jsonWriter.close();
            System.out.println("Saved " + this.bank.getAccName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads BankAccount from file
    // Code credit to Thingy example project
    public void loadBankAccount() {
        try {
            this.bank = jsonReader.read();
            System.out.println("Loaded " + this.bank.getAccName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }


}
