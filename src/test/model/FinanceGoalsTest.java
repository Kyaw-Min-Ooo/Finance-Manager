package model;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class FinanceGoalsTest {

    @Test
    void testFinGoalHelpers() {
        FinanceGoals testGoal = new FinanceGoals();
        assertEquals(0,testGoal.getSavingAmount());
        assertFalse(testGoal.getIsSaving());
        testGoal.setSavingAmount(5000);
        assertEquals(5000, testGoal.getSavingAmount());
        testGoal.setSavingAmount(300);
        assertEquals(300, testGoal.getSavingAmount());
    }

    @Test
    void testToJsonMethod() {
        FinanceGoals testGoal = new FinanceGoals(45.99);
        JSONObject testFinGoalJson = testGoal.toJson();
        assertEquals(45.99,testFinGoalJson.get("savingAmount"));
        assertTrue((Boolean) testFinGoalJson.get("isSaving"));
    }
}
