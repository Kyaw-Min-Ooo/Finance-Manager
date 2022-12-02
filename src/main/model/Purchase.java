package model;

import org.json.JSONObject;
import persistence.Writable;

// Purchase helps the user track purchases made. It stores the name of the item or service bought and the price.
// It is used in SpendingTracker class to store a list of all purchases made and analyze user spending pattern
public class Purchase implements Writable {

    private double value;
    private String itemName;

    //Requires: non-zero length itemName, non-negative value of the item
    //Modifies: this
    //Effects: store name and price of the item purchased
    public Purchase(String itemName, double value) {
        this.itemName = itemName;
        this.value = value;
        EventLog.getInstance().logEvent(new Event("A new purchase was made and added to list of spending!"));
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
    // Effects: Take class fields and store into JSON objects
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("itemName", this.itemName);
        json.put("itemValue", this.value);
        return json;
    }
}
