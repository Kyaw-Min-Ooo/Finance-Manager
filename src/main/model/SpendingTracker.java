package model;

import java.util.ArrayList;

public class SpendingTracker {
    private ArrayList<Purchase> spendingList;

    SpendingTracker() {
        spendingList =  new ArrayList<>();
    }

    public ArrayList<Purchase> getSpendingList() {
        return this.spendingList;
    }

}
