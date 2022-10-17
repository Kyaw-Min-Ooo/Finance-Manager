package model;

public class Purchase  {
    private double value;
    private String itemName;

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

    public String displayTransaction() {
        return (itemName + " | $" + value);
    }
}
