package ui;

import javax.swing.*;
import javax.swing.border.Border;
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

        panel =  new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(250, 250, 250, 250));
        panel.setLayout(new GridLayout(0, 1));

        panel.add(welcome);
        panel.add(askName);
        panel.add(field);
        panel.add(button);

//        Border border =  BorderFactory.createCompoundBorder();
//        panel.setBorder(border);

        openFrame(panel);
    }

    // Construct all the GUI components
    public void openFrame(JPanel panel) {
        // set up the frame and display it
        add(panel, BorderLayout.CENTER);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        uiBank.getBank().setAccName(field.getText());
        welcome.setText(uiBank.getBank().getAccName());
        this.setVisible(false);

        new MenuGUI(this.uiBank);
    }
}
