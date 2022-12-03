package persistence;

import model.Purchase;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkPurchase(String itemName, Double itemValue, Purchase item) {
        assertEquals(itemName,item.itemName());
        assertEquals(itemValue,item.value());
    }
}
