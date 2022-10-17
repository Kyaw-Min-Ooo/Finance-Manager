package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class BankAccountTest {
    private BankAccount testAccount;

    @BeforeEach
    void runBefore() {
        testAccount = new BankAccount("Alex",1000);
    }

    @Test
    void testConstructor() {
        assertEquals("Alex",testAccount.getAccName());
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
}