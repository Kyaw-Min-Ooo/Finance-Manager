package model;

import java.util.ArrayList;

// SpendingTracker is a class that holds a list of purchases made within a month's time. It can later be used to
// perform statistical analysis to help user understand their spending pattern.
public class SpendingTracker {
    private ArrayList<Purchase> spendingList;

    SpendingTracker() {
        spendingList =  new ArrayList<>();
    }

    public ArrayList<Purchase> getSpendingList() {
        return this.spendingList;
    }

}
