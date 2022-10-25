package model;

import java.util.ArrayList;

// SpendingTracker is a class that holds a list of purchases made within a month's time. It can later be used to
// perform statistical analysis to help user understand their spending pattern.
public class SpendingTracker {
    private ArrayList<Purchase> spendingList;
    private double totalSpending;
    private int maxPurchaseIndex;

    SpendingTracker() {
        spendingList =  new ArrayList<>();
    }

    public int getMaxPurchaseIndex() {
        return this.maxPurchaseIndex;
    }

    public ArrayList<Purchase> getSpendingList() {
        return this.spendingList;
    }

    //Effects: Returns the total spending of the spending list
    public double getTotalSpending() {
        double totalSpending = 0;
        for (Purchase purchase: this.spendingList) {
            totalSpending += purchase.value();
        }
        this.totalSpending = totalSpending;
        return totalSpending;
    }

    //Modifies: this
    //Effects Return the highest spending item from the spending list and note the index of it
    public double calculateMaxSpending() {
        int maxPurchase = 0;
        for (Purchase purchase: this.spendingList) {
            if (this.spendingList.get(maxPurchase).value() < purchase.value()) {
                maxPurchase = this.spendingList.indexOf(purchase);
            }
        }

        this.maxPurchaseIndex = maxPurchase;
        return this.spendingList.get(maxPurchase).value();
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



}
