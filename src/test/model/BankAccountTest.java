package model;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.JsonTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


class BankAccountTest extends JsonTest {
    private BankAccount testAccount;

    @BeforeEach
    void runBefore() {
        testAccount = new BankAccount("Alex",1000);
        testAccount.getMySpendingList().add(new Purchase("Amazon",300));
    }

    @Test
    void testConstructor() {
        assertEquals("Alex",testAccount.getAccName());
        testAccount.setAccName("Lore");
        assertEquals("Lore",testAccount.getAccName());
        assertEquals(1000,testAccount.getBalance());
        assertEquals(1000,testAccount.getNetBalance());
    }

    @Test
    void testConstructorNegBalance() {
        testAccount = new BankAccount("Harry", -25.0);
        assertEquals("Harry", testAccount.getAccName());
        assertEquals(0, testAccount.getBalance());
        assertEquals(0,testAccount.getNetBalance());
    }

    @Test
    void testDeposit() {
        testAccount.deposit(12.23);
        assertEquals(1000 + 12.23, testAccount.getBalance());
    }

    @Test
    void testWithdraw() {
        testAccount.withdraw(170.50);
        assertEquals(1000 - 170.50, testAccount.getBalance());
    }

    @Test
    void testMultipleDeposits() {
        testAccount.deposit(300);
        testAccount.deposit(232.2);
        assertEquals(1000 +300 + 232.2, testAccount.getBalance());
    }

    @Test
    void testMultipleWithdrawals() {
        testAccount.withdraw(23.23);
        testAccount.withdraw(22);
        assertEquals(1000 - 23.23 - 22, testAccount.getBalance());
    }

    @Test
    void testDisplayBalance() {
        assertTrue(testAccount.displayBalance().contains("Alex" + "'s Balance: $" + "1000"));
    }

    @Test
    void testSearchItem() {
        testAccount.setBalance(200);
        assertEquals(200,testAccount.getBalance());
        assertEquals(0,testAccount.getMySpendingTracker().searchItem("Amazon"));
        assertEquals(-1,testAccount.getMySpendingTracker().searchItem("Walmart"));
    }

    @Test
    void testNetBalanceMethods() {
        testAccount = new BankAccount();
        testAccount.setNetBalance(200);
        assertEquals(200,testAccount.getNetBalance());
        testAccount.updateNetBalance(100);
        assertEquals(300,testAccount.getNetBalance());
        assertEquals(0,testAccount.getMyFinGoals().getSavingAmount());
    }

    @Test
    void testToJsonMethod() {
        BankAccount bankAcc = new BankAccount("GeneralBank",400, 301);

        //Bank Account
        JSONObject bankAccJson = bankAcc.toJson();
        assertEquals("GeneralBank", bankAcc.getAccName());
        assertEquals("GeneralBank", bankAccJson.get("accName"));
        assertEquals(400.0,bankAccJson.get("balance"));
        assertEquals(301.0,bankAccJson.get("netBalance"));


        //Spending Tracker
        bankAcc.getMySpendingTracker().setTotalSpending(600);
        bankAcc.getMySpendingList().add(new Purchase("Amazon",100));
        bankAcc.getMySpendingList().add(new Purchase("Uber",200));
        bankAcc.getMySpendingList().add(new Purchase("PS5",300));
        bankAcc.getMySpendingTracker().setTotalSpending(600);
        bankAcc.getMySpendingTracker().calculateMaxSpending();

        JSONObject mySpendingListJson = bankAcc.getMySpendingTracker().toJson();

        assertEquals(2, mySpendingListJson.get("maxPurchaseIndex"));
        assertEquals(600.0,mySpendingListJson.get("totalSpending"));
        }
}