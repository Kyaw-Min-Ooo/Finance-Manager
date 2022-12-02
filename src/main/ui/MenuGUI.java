package ui;

import model.Event;
import model.EventLog;
import model.Purchase;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


// This class serves as a large code base for all the GUI implementations for Phase 3
public class MenuGUI extends JFrame implements ListSelectionListener {
    private BankApp uiBank; // Allow changes to BankAccount object through associations

    // Listeners for button actions
    ActionListener depositListener = null;
    ActionListener withdrawListener = null;
    ActionListener savingListener =  null;
    ActionListener purchaseListener = null;

    // All the components for Bank information panel
    private JLabel heading;
    private JLabel bankBalance;
    private JLabel netBalance;
    private JLabel spendTracker;
    private JLabel savingGoals;
    private JLabel spendingList;

    // All the major Swing Components used for the whole menu split panels
    private JSplitPane menuSplitPane;
    private JTextField field;
    private JTextField fieldPrice;
    private JSplitPane bankInfoAndCurPanel;
    private JList bankMenuList;
    private JPanel currPanel;
    private JPanel bankInfo;
    private String[] menuOptions = {
            "Deposit",
            "Withdraw",
            "Make Purchase",
            "Set saving goals",
            "Load bank account from file",
            "Save bank account to file",
            "Quit"
    };

    //Requires: BankApp reference
    //Modifies: this
    //Effects: Store BankApp reference, set frame title to username, and run the GUI program
    public MenuGUI(BankApp uiBank) {
        super(uiBank.getBank().getAccName() + "'s " + "Finance Manager");
        this.uiBank = uiBank;
        run();
    }

    //Modifies: this
    //Effects: Design and populate the split panels with appropriate format
    public void run() {
        //Design the panels
        bankMenuList = new JList(menuOptions);
        bankMenuList.addListSelectionListener(this);
        bankMenuList.setBackground(Color.decode("#ecf0f1"));

        currPanel =  new JPanel();
        currPanel.setBackground(Color.decode("#ecf0f1"));
        currPanel.setSize(new Dimension(200,200));
        currPanel.setLayout(new GridLayout(0, 1));

        bankInfo = new JPanel();
        buildBankInfo();

        bankInfoAndCurPanel = new JSplitPane(JSplitPane.VERTICAL_SPLIT,currPanel,new JScrollPane(bankInfo));
        bankInfoAndCurPanel.setDividerLocation(250);

        menuSplitPane = new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT,
                new JScrollPane(bankMenuList),
                bankInfoAndCurPanel
        );
        menuSplitPane.setDividerLocation(300);

        buildFrame(); // Compile and build all the frame
    }

    // Modifies: this.frame
    // Effects: Add panels and set up the frame format
    public void buildFrame() {
        add(menuSplitPane);
        setUpListeners();
        setMinimumSize(new Dimension(800,500));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

    //Modifies: this
    //Effects: initialize and populate the bank info panel showing major bank data
    private void buildBankInfo() {
        heading = new JLabel(uiBank.getBank().getAccName() + "'s Bank Info:");
        bankBalance = new JLabel("Current Balance: $" + uiBank.getBank().getBalance());
        netBalance = new JLabel("Spendable Balance: $" + uiBank.getBank().getBalance());
        savingGoals = new JLabel("Current Saving Goal: $" + uiBank.getBank().getMyFinGoals().getSavingAmount());
        spendTracker = new JLabel("Total spending: $ " + uiBank.getBank().getMySpendingTracker().getTotalSpending());
        spendingList = new JLabel("List of purchases: ");

        GridLayout layout = new GridLayout(0,1);
        bankInfo.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

        bankInfo.setLayout(layout);
        bankInfo.add(heading, BorderLayout.CENTER);
        bankInfo.add(new JLabel("------------------"));
        bankInfo.add(bankBalance);
        bankInfo.add(netBalance);
        bankInfo.add(savingGoals,BorderLayout.AFTER_LAST_LINE);
        bankInfo.add(spendTracker);
        bankInfo.add(new JLabel("------------------"));
        bankInfo.add(spendingList);
        bankInfo.add(new JLabel("------------------"));
        setBackground(Color.decode("#ecf0f1"));
    }

    //Effects: Set up actions listeners for withdraw, deposit, and saving goal in the GUI
    public void setUpListeners() {
        depositListener = e -> {
            double amount = Double.parseDouble(field.getText());
            deposit(amount);
        };
        withdrawListener = e -> {
            double amount = Double.parseDouble(field.getText());
            withdraw(amount);
        };
        savingListener = e -> {
            double savingsAmount = Double.parseDouble(field.getText());
            save(savingsAmount);
        };
        setUpListenersPurchase(); // Continue ActionListeners setup in another method due to checkstyle
    }

    //Effects: Set up actions listeners for making purchases in the GUI + exiting and logging all events
    public void setUpListenersPurchase() {
        purchaseListener = e -> {
            String itemName = field.getText();
            double itemPrice = Double.parseDouble(fieldPrice.getText());
            addPurchase(itemName,itemPrice);
        };

        // Ensures all the event logs are also printed on pressing red exit button
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                printAllLog();
            }
        }
        );
    }

    //Requires: non-negative double and non-zero itemName
    //Modifies: this.bank
    //Effects: add a purchase item to the list of items
    private void addPurchase(String itemName, double itemPrice) {
        if (uiBank.getBank().getNetBalance() < itemPrice || uiBank.getBank().getBalance() < itemPrice) {
            System.out.println("Not enough balance!");
            if (uiBank.getBank().getNetBalance() < itemPrice && uiBank.getBank().getMyFinGoals().getIsSaving()) {
                System.out.println("Purchase goes against saving quota!");
            }
        } else {
            uiBank.getBank().withdraw(itemPrice);
            uiBank.getBank().updateNetBalance(-1 * itemPrice);
            uiBank.getBank().getMySpendingList().add(new Purchase(itemName,itemPrice)); // Adding X to Y

            System.out.println("Purchase successful!");
            int itemIndex = uiBank.getBank().getMySpendingTracker().searchItem(itemName);
            System.out.println(uiBank.getBank().getMySpendingList().get(itemIndex).displayTransaction());
            uiBank.displayOverallBalance(); // Overall Display
            updateBankInfo();
            addPurchaseInPanel();
        }
    }

    //Effects: Display the new purchase item in the bank info panel
    private void addPurchaseInPanel() {
        int lastItem = uiBank.getBank().getMySpendingList().size() - 1;
        bankInfo.add(new JLabel(uiBank.getBank().getMySpendingList().get(lastItem).itemName()));
        bankInfo.add(new JLabel(String.valueOf(uiBank.getBank().getMySpendingList().get(lastItem).value())));
        bankInfo.add(new JLabel("   "));
        updateBankInfo();
    }


    //Requires: non-negative double
    //Modifies: this.bank
    //Effects: deposit and update bank information based on text field input
    private void deposit(Double amount) {
        if (amount >= 0.0) {
            uiBank.getBank().deposit(amount);
            uiBank.getBank().updateNetBalance(amount);
        } else {
            System.out.println("Cannot deposit negative amount...\n");
        }
        updateBankInfo();
    }

    //Requires: non-negative double
    //Modifies: this.bank
    //Effects: withdraw and update bank formation based on the text field input
    private void withdraw(Double amount) {
        if (amount < 0.0) {
            System.out.println("Cannot withdraw negative amount...\n");
        } else if (uiBank.getBank().getBalance() < amount) {
            System.out.println("Insufficient balance on account...\n");
        } else {
            uiBank.getBank().withdraw(amount);
            uiBank.getBank().updateNetBalance(-1 * amount);
        }
        updateBankInfo();
    }

    //Requires: savingAmount greater than bank's current balance
    //Modifies: this.bank
    //Effects:  set saving amount, adjust net balance and update bank info based the text field input
    private void save(Double savingsAmount) {
        boolean validSavings = uiBank.getBank().getBalance() > savingsAmount;

        if (validSavings && uiBank.getBank().getBalance() != 0) {
            uiBank.getBank().getMyFinGoals().setSavingAmount(savingsAmount);
            uiBank.getBank().setNetBalance(
                    uiBank.getBank().getBalance() - uiBank.getBank().getMyFinGoals().getSavingAmount()
            );
            uiBank.getBank().getMyFinGoals().setIsSaving(true);
        } else {
            System.out.println("Invalid saving input! Please try again");
        }
        updateBankInfo();
    }

    //Effects: update the bank info panel based on new information from the BankAccount object
    public void updateBankInfo() {
        heading.setText(uiBank.getBank().getAccName() + "'s Bank Info:");
        bankBalance.setText("Current Balance: $" + uiBank.getBank().getBalance());
        netBalance.setText("Spendable Balance: $" + uiBank.getBank().getNetBalance());
        savingGoals.setText("Current Saving Goal: $" + uiBank.getBank().getMyFinGoals().getSavingAmount());
        spendTracker.setText("Total spending: $ " + uiBank.getBank().getMySpendingTracker().getTotalSpending());
    }

    @Override
    //Requires: select option from side menu
    //Modifies: this
    //Effects:  execute the respective action based on user selection
    public void valueChanged(ListSelectionEvent e) {
        // While loop condition to ensure it does not get called twice
        if (!e.getValueIsAdjusting()) {
            refresh();
            doTask((String) bankMenuList.getSelectedValue());
        }
    }

    // Effects: Act as the selector for the menu options
    public void doTask(String options) {
        switch (options) {
            case "Deposit" :
                deDeposit();
                break;
            case "Withdraw" :
                doWithdrawal();
                break;
            case "Make Purchase" :
                makePurchase();
                break;
            case "Set saving goals" :
                setSavingAmt();
                break;
            case "Save bank account to file" :
                saveFile();
                break;
            case "Load bank account from file" :
                loadFile();
                break;
            case "Quit" :
                printAllLog();
                this.dispose();
        }
    }

    // Effects: Print all event logs
    private void printAllLog() {
        System.out.println("\n----------------------------------");
        System.out.println("Printing all event logs...");
        for (Event next: EventLog.getInstance()) {
            System.out.println("\n" + next.toString());
        }
        System.out.println("----------------------------------");
    }

    //Effects: Refresh the panel so past panel's attributes don't persist
    public void refresh() {
        currPanel.removeAll();
        currPanel.repaint();
    }

    //Effects:  Construct the prompt asking for the amount to save
    private void setSavingAmt() {
        field = new JTextField(15);
        JLabel askName = new JLabel("How much do you intend to save?", SwingConstants.CENTER);
        JButton button = new JButton("Done");

        currPanel.add(askName);
        currPanel.add(field);
        currPanel.add(button);
        currPanel.setBorder(BorderFactory.createEmptyBorder(80,80,80,80));

        revalidate();
        button.addActionListener(savingListener);
    }

    //Effects: Construct the prompt asking user for the amount to withdraw
    private void doWithdrawal() {
        field = new JTextField(15);
        JLabel askName = new JLabel("How much do you want to withdraw?", SwingConstants.CENTER);
        JButton button = new JButton("Done");

        currPanel.add(askName);
        currPanel.add(field);
        currPanel.add(button);
        currPanel.setBorder(BorderFactory.createEmptyBorder(80,80,80,80));

        revalidate();
        button.addActionListener(withdrawListener);
    }

    //Effects: Construct the prompt asking user for the amount to deposit
    private void deDeposit() {

        field = new JTextField(15);
        JLabel askName = new JLabel("How much do you want to deposit?", SwingConstants.CENTER);
        JButton button = new JButton("Done");

        currPanel.add(askName);
        currPanel.add(field);
        currPanel.add(button);
        currPanel.setBorder(BorderFactory.createEmptyBorder(80,80,80,80));

        revalidate();
        button.addActionListener(depositListener);
    }

    //Modifies: this.bank
    // Effects: Get user input from JText and display on bank info panel
    private void makePurchase() {
        field = new JTextField(15);
        fieldPrice =  new JTextField(15);
        JLabel askItemName = new JLabel("What is the name of your purchase?", SwingConstants.CENTER);
        JLabel askItemPrice = new JLabel("What is the price of your purchase?", SwingConstants.CENTER);
        JButton button = new JButton("Done");


        currPanel.add(askItemName);
        currPanel.add(askItemPrice);
        currPanel.add(field);
        currPanel.add(fieldPrice);
        currPanel.add(button);
        currPanel.setBorder(BorderFactory.createEmptyBorder(80,80,80,80));

        revalidate();
        button.addActionListener(purchaseListener);
    }

    //Modifies: this.bank's JSON file
    //Effects: save current BankAccount's status and refresh the bank info panel
    private void saveFile() {
        try {
            uiBank.saveBankAccount();

            BufferedImage myPicture = ImageIO.read(new File("./data/success.png"));
            Image image = myPicture.getScaledInstance(50, 50, Image.SCALE_DEFAULT);
            JLabel picLabel = new JLabel(new ImageIcon(image));

            currPanel.add(picLabel);
            currPanel.setBackground(Color.decode("#7DEC96"));

            revalidate();
        } catch (IOException ex) {
            currPanel.add(new JLabel("Sorry file could not be open"));
            currPanel.setBackground(Color.RED);
            revalidate();
        }
    }

    //Requires: this.bank's JSON file
    //Modifies: this.bank
    //Effects:  populate this.bank based on saved JSON file and refresh the bank info panel
    private void loadFile() {
        try {
            uiBank.loadBankAccount();
            updateBankInfo();
            populateSpendingList();

            BufferedImage myPicture = ImageIO.read(new File("./data/success.png"));
            Image image = myPicture.getScaledInstance(50, 50, Image.SCALE_DEFAULT);
            JLabel picLabel = new JLabel(new ImageIcon(image));
            currPanel.add(picLabel);
            currPanel.setBackground(Color.decode("#7DEC96"));

            revalidate();
        } catch (IOException ex) {
            currPanel.add(new JLabel("Sorry file could not be open"));
            currPanel.setBackground(Color.RED);
            revalidate();
        }
    }

    // Modifies; this (GUI)
    // Effects: Populate the Bank Info panel with the items in this,bank's spending list
    private void populateSpendingList() {
        for (Purchase item: uiBank.getBank().getMySpendingList()) {
            bankInfo.add(new JLabel(item.itemName()));
            bankInfo.add(new JLabel(String.valueOf(item.value())));
            bankInfo.add(new JLabel("   "));
        }
    }
}
