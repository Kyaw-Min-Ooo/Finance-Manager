package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class FinanceGoalsTest {

    @Test
    void testFinGoalConstructor() {
        FinanceGoals testGoal = new FinanceGoals(5000);
        assertEquals(5000, testGoal.getSavingAmount());
        testGoal.setSavingAmount(300);
        assertEquals(300, testGoal.getSavingAmount());
    }
}
