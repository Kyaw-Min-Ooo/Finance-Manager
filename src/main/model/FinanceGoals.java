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

    public double getSavingAmount() {
        return savingAmount;
    }

    public void setSavingAmount(double savingAmount) {
        this.savingAmount = savingAmount;
        EventLog.getInstance().logEvent(new Event("Saving goal updated to: $" + savingAmount));
    }

    public void setIsSaving(Boolean isUserSaving) {
        if (isUserSaving) {
            EventLog.getInstance().logEvent(new Event("User has started saving!"));
        } else {
            EventLog.getInstance().logEvent(new Event("User has stopped saving!"));
        }
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
