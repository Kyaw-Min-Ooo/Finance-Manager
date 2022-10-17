package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class PurchaseTest {
    private Purchase testItem;

    @BeforeEach
    void runBefore() {
        testItem =  new Purchase("Amazon",45);
    }

    @Test
    void testPurchaseConstructor() {
        assertEquals("Amazon",testItem.itemName());
        assertEquals(45,testItem.value());
    }

    @Test
    void testDisplayTransaction() {
        assertTrue(testItem.displayTransaction().contains("Amazon" + " | $" + "45"));
    }


}
