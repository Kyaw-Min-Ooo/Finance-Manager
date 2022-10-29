package persistence;

import model.BankAccount;
import model.Purchase;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

// Represents a reader that reads BankAccount from JSON data stored in file
// Code credit to thingy project example
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file path
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads BankAccount from file and returns it;
    // throws IOException if an error occurs reading data from file
    public BankAccount read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseBankAccount(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source),StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses BankAccount from JSON object and construct BankAccount from the data
    private BankAccount parseBankAccount(JSONObject jsonObject) {
        String accName = jsonObject.getString("accName");
        Double balance = jsonObject.getDouble("balance");
        Double netBalance = jsonObject.getDouble("netBalance");
        BankAccount bankAcc = new BankAccount(accName,balance,netBalance);
        addFinGoals(bankAcc,jsonObject);
        addSpendingTracker(bankAcc,jsonObject);
        return bankAcc;
    }

    // MODIFIES: bankAcc's finGoals field
    // Effects: parses finGoal from JSON object and adds it to BankAccount
    private void addFinGoals(BankAccount bankAcc, JSONObject jsonObject) {
        JSONObject finGoals = jsonObject.getJSONObject("financeGoals");
        Boolean isSaving = finGoals.getBoolean("isSaving");
        Double savingAmount = finGoals.getDouble("savingAmount");

        bankAcc.getMyFinGoals().setIsSaving(isSaving);
        bankAcc.getMyFinGoals().setSavingAmount(savingAmount);
    }

    // MODIFIES: bankAcc
    // EFFECTS: parses spendingTracker from JSON object and adds them to BankAccount
    private void addSpendingTracker(BankAccount bankAcc, JSONObject jsonObject) {
        JSONObject spendingTracker = jsonObject.getJSONObject("spendingTracker");
        Double totalSpending = spendingTracker.getDouble("totalSpending");
        int maxPurchaseIndex = spendingTracker.getInt("maxPurchaseIndex");

        bankAcc.getMySpendingTracker().setTotalSpending(totalSpending);
        bankAcc.getMySpendingTracker().setMaxPurchaseIndex(maxPurchaseIndex);

        addSpendingList(bankAcc,spendingTracker);
    }

    // MODIFIES: bankAcc
    // EFFECTS: parses purchases from JSON object and adds them into BankAccount
    private void addSpendingList(BankAccount bankAcc, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("spendingList");
        for (Object json: jsonArray) {
            JSONObject nextPurchase = (JSONObject) json;
            addPurchase(bankAcc,nextPurchase);
        }
    }

    // MODIFIES: bankAcc
    // EFFECTS: parses a purchase from JSON array and adds it to spendingList in bankAcc
    private void addPurchase(BankAccount bankAcc, JSONObject jsonObject) {
        String itemName = jsonObject.getString("itemName");
        Double itemValue = jsonObject.getDouble("itemValue");
        bankAcc.getMySpendingList().add(new Purchase(itemName,itemValue));
    }

}
