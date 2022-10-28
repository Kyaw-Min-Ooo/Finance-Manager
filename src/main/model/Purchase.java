package model;

import org.json.JSONObject;
import persistence.Writable;

// Purchase helps the user track purchases made. It stores the name of the item or service bought and the price.
// It is used in SpendingTracker class to store a list of all purchases made. It will later be analyzed to help user
// review spending pattern.
public class Purchase implements Writable {

    private double value;
    private String itemName;

    //Requires: non-zero length itemName, non-negative value of the item
    //Modifies: this
    //Effects: store name and price of the item purchased
    public Purchase(String itemName, double value) {
        this.itemName = itemName;
        this.value = value;
    }

    public double value() {
        return this.value;
    }

    public String itemName() {
        return this.itemName;
    }

    //Effects: Format the item purchase with price and name
    public String displayTransaction() {
        return (itemName + " | $" + value);
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("itemName", this.itemName);
        json.put("itemValue", this.value);
        return json;
    }
}
