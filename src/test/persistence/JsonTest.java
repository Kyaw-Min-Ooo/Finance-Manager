package persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import model.Purchase;

public class JsonTest {
    protected void checkPurchase(String itemName, Double itemValue, Purchase item) {
        assertEquals(itemName,item.itemName());
        assertEquals(itemValue,item.value());
    }
}
