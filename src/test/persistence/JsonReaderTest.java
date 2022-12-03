package persistence;

import model.BankAccount;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class JsonReaderTest extends JsonTest{
    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            BankAccount bankAcc = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyBankAccount() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyBankAccount.json");
        try {
            BankAccount bankAcc = reader.read();

            //Bank Account
            assertEquals("EmptyBank", bankAcc.getAccName());
            assertEquals(0,bankAcc.getBalance());
            assertEquals(0,bankAcc.getNetBalance());

            //Financial Goals
            assertEquals(0,bankAcc.getMyFinGoals().getSavingAmount());
            assertFalse(bankAcc.getMyFinGoals().getIsSaving());

            //Spending Tracker
            assertTrue(bankAcc.getMySpendingList().isEmpty());
            assertEquals(0,bankAcc.getMySpendingTracker().getTotalSpending());
            assertEquals(-1, bankAcc.getMySpendingTracker().getMaxPurchaseIndex());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralBankAccount() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralBankAccount.json");
        try {
            BankAccount bankAcc = reader.read();

            //Bank Account
            assertEquals("GeneralBank", bankAcc.getAccName());
            assertEquals(400,bankAcc.getBalance());
            assertEquals(301,bankAcc.getNetBalance());

            //Financial Goals
            assertEquals(99,bankAcc.getMyFinGoals().getSavingAmount());
            assertTrue(bankAcc.getMyFinGoals().getIsSaving());

            //Spending Tracker
            assertFalse(bankAcc.getMySpendingList().isEmpty());
            assertEquals(3,bankAcc.getMySpendingList().size());
            checkPurchase("Amazon", 100.0, bankAcc.getMySpendingList().get(0));
            checkPurchase("Uber", 200.0, bankAcc.getMySpendingList().get(1));
            checkPurchase("PS5", 300.0, bankAcc.getMySpendingList().get(2));
            assertEquals(600,bankAcc.getMySpendingTracker().getTotalSpending());
            assertEquals(2, bankAcc.getMySpendingTracker().getMaxPurchaseIndex());

        } catch (IOException e) {
            fail("Couldn't read from file");
        }

    }
}
