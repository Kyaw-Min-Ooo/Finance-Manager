package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class EventLogTest {
    BankAccount testAccount;
    List<String> l; // Used to check description of events

    @BeforeEach
    void runBefore() {
        l = new ArrayList<String>();
        testAccount = new BankAccount();
    }

    @Test
    void testDeposit() {
        EventLog el = EventLog.getInstance();
        testAccount.deposit(100);
        for (Event event: el) {
            l.add(event.getDescription());
        }
        assertTrue(l.contains("Deposited $100.0 into bank balance"));

        //Total operations check
        assertTrue(l.size() == 1);
    }

    @Test
    void testWithdraw() {
        EventLog el = EventLog.getInstance();
        testAccount.deposit(100);
        testAccount.withdraw(50);
        for (Event event: el) {
            l.add(event.getDescription());
        }
        assertTrue(l.contains("Deposited $100.0 into bank balance"));
        assertTrue(l.contains("Withdrawn $" + "50.0" + " from bank balance"));

        //Total operations check
        assertTrue(l.size() == 2);
    }

    @Test
    void testSetSavingAmountTrue() {
        EventLog el = EventLog.getInstance();
        testAccount.deposit(100);
        testAccount.getMyFinGoals().setSavingAmount(50);
        for (Event event: el) {
            l.add(event.getDescription());
        }
        //Deposit check
        assertTrue(l.contains("Deposited $100.0 into bank balance"));

        // Savings true check
        assertTrue(l.contains("Saving goal updated to: $" + "50.0"));

        //Total operations check
        assertTrue(l.size() == 2);
    }

    @Test
    void testSetSavingAmountFalse() {
        EventLog el = EventLog.getInstance();
        testAccount.deposit(100);
        testAccount.getMyFinGoals().setSavingAmount(50);
        testAccount.getMyFinGoals().setSavingAmount(0);
        for (Event event: el) {
            l.add(event.getDescription());
        }
        //Deposit check
        assertTrue(l.contains("Deposited $100.0 into bank balance"));

        // Savings true check
        assertTrue(l.contains("Saving goal updated to: $" + "50.0"));

        //Deactivate saving goals
        assertTrue(l.contains("Saving goal updated to: $" + "0.0"));

        //Total operations check
        assertTrue(l.size() == 3);
    }

    @Test
    void testMakePurchase() {
        EventLog el = EventLog.getInstance();
        testAccount.deposit(100);
        testAccount.getMySpendingList().add(new Purchase("Amazon",50));
        for (Event event: el) {
            l.add(event.getDescription());
        }
        //Deposit check
        assertTrue(l.contains("Deposited $100.0 into bank balance"));

        //Check if purchase added to spending list
        l.contains("A new purchase was made and added to list of spending!");

        //Total operations check
        assertTrue(l.size() == 2);
    }

}
