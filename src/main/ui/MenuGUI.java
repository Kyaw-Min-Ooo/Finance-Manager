package ui;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MenuGUI extends JFrame implements ListSelectionListener, ActionListener {
    private BankApp uiBank;

    // Listeners for button actions
    ActionListener depositListener = null;
    ActionListener withdrawListener = null;
    ActionListener savingListener =  null;

    // All the components for Bank information panel
    private JLabel heading;
    private JLabel bankBalance;
    private JLabel finGoals;
    private JLabel netBalance;
    private JLabel spendTracker;
    private JLabel savingGoals;

    // All the major Swing Components used for the whole menu
    private JSplitPane menuSplitPane;
    private JTextField field;
    private JSplitPane bankInfoAndCurPanel;
    private JList bankMenuList;
    private JPanel currPanel;
    private JPanel bankInfo;
    private String[] menuOptions = {
            "Deposit",
            "Withdraw",
            "Make Purchase",
            "Set saving goals",
            "Print the statistics of all your spending",
            "Load bank account from file",
            "Save bank account to file",
            "Quit"
    };

    public MenuGUI(BankApp uiBank) {
        super(uiBank.getBank().getAccName() + "'s " + "Finance Manager");
        this.uiBank = uiBank;
        run();
    }

    public void run() {
        //Design the panels
        bankMenuList = new JList(menuOptions);
        bankMenuList.addListSelectionListener(this);

        currPanel =  new JPanel();
        currPanel.setBackground(Color.WHITE);
        currPanel.setSize(new Dimension(200,200));
        currPanel.setLayout(new GridLayout(0, 1));

        bankInfo = new JPanel();
        buildBankInfo();

        bankInfoAndCurPanel = new JSplitPane(JSplitPane.VERTICAL_SPLIT,currPanel,bankInfo);
        bankInfoAndCurPanel.setDividerLocation(250);

        menuSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                new JScrollPane(bankMenuList),
                new JScrollPane(bankInfoAndCurPanel)
        );
        menuSplitPane.setDividerLocation(300);

        // Add and set up the frame
        add(menuSplitPane);
        setUpListeners();
        setMinimumSize(new Dimension(800,500));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    public void setUpListeners() {
        depositListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double amount = Double.parseDouble(field.getText());

                if (amount >= 0.0) {
                    uiBank.getBank().deposit(amount);
                    uiBank.getBank().updateNetBalance(amount);
                } else {
                    System.out.println("Cannot deposit negative amount...\n");
                }

                updateBankInfo();
            }
        };
        withdrawListener =  new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double amount = Double.parseDouble(field.getText());

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
        };
        savingListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double savingsAmount = Double.parseDouble(field.getText());
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
        };
    }


    private void buildBankInfo() {
        heading = new JLabel(uiBank.getBank().getAccName() + "'s Bank Info:");
        bankBalance = new JLabel("Current Balance: $" + uiBank.getBank().getBalance());
        netBalance = new JLabel("Spendable Balance: $" + uiBank.getBank().getBalance());
        savingGoals = new JLabel("Current Saving Goal: $" + uiBank.getBank().getMyFinGoals().getSavingAmount());
        spendTracker = new JLabel("Total spending: $ " + uiBank.getBank().getMySpendingTracker().getTotalSpending());

        GridLayout layout = new GridLayout(0,1);
        bankInfo.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

        bankInfo.setLayout(layout);
        bankInfo.add(heading, BorderLayout.CENTER);
        bankInfo.add(bankBalance);
        bankInfo.add(netBalance);
        bankInfo.add(savingGoals,BorderLayout.AFTER_LAST_LINE);
        bankInfo.add(spendTracker);
    }

    public void updateBankInfo() {
        heading.setText(uiBank.getBank().getAccName() + "'s Bank Info:");
        bankBalance.setText("Current Balance: $" + uiBank.getBank().getBalance());
        netBalance.setText("Spendable Balance: $" + uiBank.getBank().getNetBalance());
        savingGoals.setText("Current Saving Goal: $" + uiBank.getBank().getMyFinGoals().getSavingAmount());
        spendTracker.setText("Total spending: $ " + uiBank.getBank().getMySpendingTracker().getTotalSpending());
    }


    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    @Override
    public void valueChanged(ListSelectionEvent e) {
        String options = (String) bankMenuList.getSelectedValue();

        switch (options) {
            case "Deposit" :
                refresh();
                deDeposit();
                break;
            case "Withdraw" :
                refresh();
                doWithdrawal();
                break;
            case "Make Purchase" :
            case "Print the statistics of all your spending" :
                refresh();
                // TODO: Future changes
                currPanel.setBackground(Color.white);
                break;
            case "Set saving goals" :
                refresh();
                setSavingAmt();
                break;
            case "Save bank account to file" :
                refresh();
                saveFile();
                break;
            case "Load bank account from file" :
                refresh();
                loadFile();
                break;
            case "Quit" :
                this.dispose();
                break;
        }
    }

    public void refresh() {
        currPanel.removeAll();
        currPanel.repaint();
    }

    private void setSavingAmt() {
        field = new JTextField(15);
        JLabel askName = new JLabel("How much do you intend to save?", SwingConstants.CENTER);
        JButton button = new JButton("Done");

        currPanel.add(askName);
        currPanel.add(field);
        currPanel.add(button);
        revalidate();

        button.addActionListener(savingListener);
    }


    private void doWithdrawal() {
        field = new JTextField(15);
        JLabel askName = new JLabel("How much do you want to withdraw?", SwingConstants.CENTER);
        JButton button = new JButton("Done");

        currPanel.add(askName);
        currPanel.add(field);
        currPanel.add(button);
        revalidate();

        button.addActionListener(withdrawListener);
    }

    private void deDeposit() {

        field = new JTextField(15);
        JLabel askName = new JLabel("How much do you want to deposit?", SwingConstants.CENTER);
        JButton button = new JButton("Done");

        currPanel.add(askName);
        currPanel.add(field);
        currPanel.add(button);
        revalidate();

        button.addActionListener(depositListener);

    }

    private void saveFile() {
        try {
            uiBank.saveBankAccount();

            BufferedImage myPicture = ImageIO.read(new File("./data/success.png"));
            Image image = myPicture.getScaledInstance(50, 50, Image.SCALE_DEFAULT);
            JLabel picLabel = new JLabel(new ImageIcon(image));

            currPanel.add(picLabel);
            currPanel.setBackground(Color.GREEN);

            revalidate();
        } catch (IOException ex) {
            JLabel sorry = new JLabel("Sorry file could not be open");
            currPanel.add(sorry);
            currPanel.setBackground(Color.RED);

            revalidate();
        }
    }

    private void loadFile() {
        try {
            uiBank.loadBankAccount();
            updateBankInfo();

            BufferedImage myPicture = ImageIO.read(new File("./data/success.png"));
            Image image = myPicture.getScaledInstance(50, 50, Image.SCALE_DEFAULT);
            JLabel picLabel = new JLabel(new ImageIcon(image));
            currPanel.add(picLabel);
            currPanel.setBackground(Color.GREEN);

            revalidate();
        } catch (IOException ex) {
            JLabel sorry = new JLabel("Sorry file could not be open");
            currPanel.add(sorry);
            currPanel.setBackground(Color.RED);

            revalidate();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        uiBank.getBank().deposit(Double.parseDouble(field.getText()));
        updateBankInfo();
    }
}
