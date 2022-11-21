package ui;

import java.io.FileNotFoundException;

// Driver Class
public class Main {
    public static void main(String[] args) {
        try {
            new BankApp();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to run application: file not found");
        }
    }
}