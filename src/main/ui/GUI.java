package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;



public class GUI extends JFrame implements ActionListener {
    private BankApp uiBank;

    //All the components this welcome page frame uses
    private JPanel panel;
    private JTextField field;
    private JLabel askName;
    private JLabel welcome;
    private JButton button;

    //Effects: Initialize GUI class with access to Bank App data
    public GUI(BankApp uiBank) {
        super("Finance Manager");
        this.uiBank = uiBank;
    }

    // Main tasks
    public void run() {
        field = new JTextField(15);
        askName = new JLabel("What is your name?", SwingConstants.CENTER);
        welcome = new JLabel("Welcome to the Finance Manager", SwingConstants.CENTER);
        button = new JButton("Done");

        button.addActionListener(this);
        welcome.setFont(new Font("Arial", Font.PLAIN, 19));
        askName.setFont(new Font("Arial", Font.PLAIN, 18));

        panel =  new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(150, 150, 150, 150));
        panel.setLayout(new GridLayout(0, 1));
        panel.add(welcome);
        panel.add(askName);
        panel.add(field);
        panel.add(button);

        openFrame(panel);
    }

    // Construct all the GUI components
    public void openFrame(JPanel panel) {
        // set up the frame and display it
        add(panel, BorderLayout.CENTER);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        uiBank.getBank().setAccName(field.getText());
        welcome.setText(uiBank.getBank().getAccName());
        this.setVisible(false);

        new MenuGUI(this.uiBank);
    }
}
