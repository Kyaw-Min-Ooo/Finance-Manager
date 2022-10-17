package model;

//This class is used to track financial goals such as saving goals or include bills due later or limit user spending
// Right now it only contains saving goals field.
public class FinanceGoals {
    private double savingAmount;

    //Requires: non negative target saving amount; less than balance
    //Modifies: this
    public FinanceGoals(double savingAmount) {
        this.savingAmount = savingAmount;
    }

    public double getSavingAmount() {
        return savingAmount;
    }

    public void setSavingAmount(double savingAmount) {
        this.savingAmount = savingAmount;
    }

}
