package ui;

import java.util.ArrayList;
import java.util.Scanner;
import model.BankAccount;
import model.*;


//Class credit to the TellerApp application
public class BankInterface {
    private Scanner input;
    private BankAccount bank;

    public BankInterface() {
        runBankApp();
    }

    //Need more editing based on TellerApp
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

        System.out.println("\nGoodbye!");
    }

    private void askAccName() {
        System.out.println("What is your name?");
        bank.setName(input.nextLine());
    }

    private void init() {
        bank = new BankAccount();
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    public void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\td -> deposit");
        System.out.println("\tw -> withdraw");
        System.out.println("\tp --> make purchase");
        System.out.println("\ts ->  print all purchases/spending");
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
        } else {
            System.out.println("Selection not valid...");
        }
    }

    //Used from TellerApp project
    private void doDeposit() {
        System.out.print("Enter amount to deposit: $");
        double amount = input.nextDouble();

        if (amount >= 0.0) {
            bank.deposit(amount);
        } else {
            System.out.println("Cannot deposit negative amount...\n");
        }
        System.out.println(bank.displayBalance());
    }

    private void doWithdrawal() {
        System.out.print("Enter amount to withdraw: $");
        double amount = input.nextDouble();

        if (amount < 0.0) {
            System.out.println("Cannot withdraw negative amount...\n");
        } else if (bank.getBalance() < amount) {
            System.out.println("Insufficient balance on account...\n");
        } else {
            bank.withdraw(amount);
        }
        System.out.println(bank.displayBalance());
    }

    // Check if you have enough balance
    // If yes
    // If no

    // Check if this purchase meets the spending limiters
    // If yes

    public int searchItem(String inputName) {
        for (Purchase itemInList: bank.getMySpendingList()) {
            if (itemInList.itemName().equals(inputName)) {
                return bank.getMySpendingList().indexOf(itemInList);
            }
        }
        return -1;
    }

    public void displayAllPurchases() {
        for (Purchase items: this.bank.getMySpendingList()) {
            System.out.println(items.itemName() + " | $" + items.value());
        }
    }


    public void makePurchase() {
        ArrayList<Purchase> spendingList = bank.getMySpendingList();

        input = new Scanner(System.in);
        System.out.println("What is the name of the item/transaction?");
        String itemName = input.nextLine();
        System.out.println("How much is this item?");
        double value = input.nextDouble();

        bank.withdraw(value);
        spendingList.add(new Purchase(itemName,value));

        System.out.println("Purchase successful!");
        int itemIndex = searchItem(itemName);
        System.out.println(spendingList.get(itemIndex).displayTransaction());
        System.out.println(bank.displayBalance());
    }
}
