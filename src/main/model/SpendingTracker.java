package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

// SpendingTracker is a class that holds a list of purchases made within a month's time. It can be used to
// perform statistical analysis to help user understand their spending pattern.
public class SpendingTracker implements Writable {
    private ArrayList<Purchase> spendingList;
    private double totalSpending;
    private int maxPurchaseIndex;

    //Modifies: this
    SpendingTracker() {
        this.spendingList =  new ArrayList<>();
        totalSpending = 0;
        maxPurchaseIndex = -1;
    }

    public int getMaxPurchaseIndex() {
        return this.maxPurchaseIndex;
    }

    public void setMaxPurchaseIndex(int maxPurchaseIndex) {
        this.maxPurchaseIndex = maxPurchaseIndex;
    }

    public void setTotalSpending(double totalSpending) {
        this.totalSpending = totalSpending;
    }

    public ArrayList<Purchase> getSpendingList() {
        return this.spendingList;
    }

    //Modifies: this
    //Effects: Calculate, return the total spending of the spending list and assign that total to class field
    public double getTotalSpending() {
        double currTotal = 0;
        for (Purchase purchase: this.spendingList) {
            currTotal += purchase.value();
        }
        this.totalSpending = currTotal;
        return this.totalSpending;
    }

    //Modifies: this
    //Effects Return the highest spending item's index from the spending list and assign it to class field
    public double calculateMaxSpending() {
        int currMaxPurchase = 0;
        for (Purchase purchase: this.spendingList) {
            if (this.spendingList.get(currMaxPurchase).value() < purchase.value()) {
                currMaxPurchase = this.spendingList.indexOf(purchase);
            }
        }
        this.maxPurchaseIndex = currMaxPurchase;
        return this.spendingList.get(maxPurchaseIndex).value();
    }

    //Effects: Takes an input String and search through this.mySpendingTracker for an item with matching name.
    // returns the first stance of the item. Does not account for repeating name.
    public int searchItem(String inputName) {
        for (Purchase itemInList: this.spendingList) {
            if (itemInList.itemName().equals(inputName)) {
                return this.spendingList.indexOf(itemInList);
            }
        }
        return -1;
    }

    @Override
    // Effects: Take class fields and store into JSON objects
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("maxPurchaseIndex", this.maxPurchaseIndex);
        json.put("totalSpending", this.totalSpending);
        json.put("spendingList", spendingListToJson());
        return json;
    }

    // EFFECTS: returns list of purchases in the spending list as a JSON array
    private JSONArray spendingListToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Purchase item: this.spendingList) {
            jsonArray.put(item.toJson());
        }
        return jsonArray;
    }

}
