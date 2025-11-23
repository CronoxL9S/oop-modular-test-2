package rebrin.ad241.mkr2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {

    @Test
    void testBrzyckiFormula() {
        double result = RMCalculator.brzycki(100, 5);
        assertEquals(112.5, result, 0.5);
    }

    @Test
    void testEpleyFormula() {
        double result = RMCalculator.epley(100, 5);
        assertEquals(116.67, result, 0.5);
    }

    @Test
    void testLanderFormula() {
        double result = RMCalculator.lander(100, 5);
        assertEquals(113.5, result, 1.0);
    }

    @Test
    void testNegativeWeightValidation() {
        // Змінено з isValidWeight на isValidWeight (String)
        assertFalse(InputValidator.isValidWeight("-50"));
    }

    @Test
    void testInvalidRepsValidation() {
        // Змінено з isValidReps на isValidReps (String)
        assertFalse(InputValidator.isValidReps("0"));
        assertFalse(InputValidator.isValidReps("20"));
        assertTrue(InputValidator.isValidReps("5"));
    }

    @Test
    void testTextInputValidation() {
        assertFalse(InputValidator.isValidWeight("abc"));
        assertFalse(InputValidator.isValidReps("xyz"));
    }

    @Test
    void testZeroWeightValidation() {
        assertFalse(InputValidator.isValidWeight("0"));
    }

    @Test
    void testOneRepMax() {
        // При 1 повторенні 1RM дорівнює вазі
        assertEquals(100, RMCalculator.brzycki(100, 1));
        assertEquals(100, RMCalculator.epley(100, 1));
    }
}
