package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

//This class is used to track financial goals such as saving goals (can include more features later)
// Right now it only contains saving goals field.
public class FinanceGoals implements Writable {
    private double savingAmount;
    private Boolean isSaving;

    //Modifies: this
    //Effects: Initialize isSaving and savingAmount
    public FinanceGoals() {
        this.savingAmount = 0;
        this.isSaving = false;
    }

    //Requires: non-negative target saving amount; less than balance
    //Modifies: this
    public FinanceGoals(double savingAmount) {
        this.savingAmount = savingAmount;
        this.isSaving = true;
    }

    // Two parameter constructor mainly used for JSON file parsing
    //Modifies: this
    public FinanceGoals(double savingAmount, Boolean isSaving) {
        this.savingAmount = savingAmount;
        this.isSaving = isSaving;
    }

    public double getSavingAmount() {
        return savingAmount;
    }

    public void setSavingAmount(double savingAmount) {
        this.savingAmount = savingAmount;
    }

    public void setIsSaving(Boolean isUserSaving) {
        this.isSaving = isUserSaving;
    }

    public Boolean getIsSaving() {
        return this.isSaving;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("isSaving", this.isSaving);
        json.put("savingAmount", this.savingAmount);
        return json;
    }
}
