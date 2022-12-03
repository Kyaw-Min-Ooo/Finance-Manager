package persistence;

import model.BankAccount;
import model.Purchase;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

//Code credit to JsonWriter test approaches example from Thingy example
public class JsonWriterTest extends JsonTest{
    //NOTE TO CPSC 210 STUDENTS: the strategy in designing tests for the JsonWriter is to
    //write data to a file and then use the reader to read it back in and check that we
    //read in a copy of what was written out.

    @Test
    void testWriterInvalidFile() {
        try {
            BankAccount bankAccount = new BankAccount("InvalidBank",0);
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyBankAccount() {
        try {
            BankAccount bankAcc = new BankAccount("EmptyBank", 0);
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyBankAccount.json");
            writer.open();
            writer.write(bankAcc);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyBankAccount.json");
            bankAcc = reader.read();

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
            fail("Exception should not have been thrown");
        }
    }


    @Test
    void testWriterGeneralBankAccount() {
        try {
            BankAccount bankAcc = new BankAccount("GeneralBank",400, 301);
            bankAcc.getMySpendingTracker().setTotalSpending(600);
            bankAcc.getMySpendingList().add(new Purchase("Amazon",100));
            bankAcc.getMySpendingList().add(new Purchase("Uber",200));
            bankAcc.getMySpendingList().add(new Purchase("PS5",300));
            bankAcc.getMyFinGoals().setSavingAmount(99);
            bankAcc.getMyFinGoals().setIsSaving(true);
            bankAcc.getMySpendingTracker().setTotalSpending(600);
            bankAcc.getMySpendingTracker().calculateMaxSpending();

            JsonWriter writer = new JsonWriter("./data/testWriterGeneralBankAccount.json");
            writer.open();
            writer.write(bankAcc);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralBankAccount.json");
            bankAcc = reader.read();

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
            fail("Exception should not have been thrown");
        }
    }



}
