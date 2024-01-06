package com.mycompany.project3;

import com.mycompany.project3.RPNCalculator;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * This class tests the RPNCalculator.calculate() method
 * 
 * @author samdecook
 * @version 1.0
 *          File: RPNCaclulatorTest.java
 *          Created September 2021
 * 
 *          Description: Tests the calculate function, goes through the
 *          operators, then
 *          complex calculations and finally error handling
 */
public class RPNCalculatorTest {

    @Test
    public void testAddition() {
        System.out.println("Test Addition");
        String t1 = "3 2 +";
        String t2 = "314 212 +";
        String t3 = "3.56 2.04 +";
        String t4 = "3 -2 +";
        String t5 = "3.48 -2.24 +";

        double r1 = RPNCalculator.calculate(t1);
        double r2 = RPNCalculator.calculate(t2);
        double r3 = RPNCalculator.calculate(t3);
        double r4 = RPNCalculator.calculate(t4);
        double r5 = RPNCalculator.calculate(t5);

        assertEquals(5.0, r1, 0.00001);
        assertEquals(526.0, r2, 0.00001);
        assertEquals(5.6, r3, 0.00001);
        assertEquals(1.0, r4, 0.00001);
        assertEquals(1.24, r5, 0.00001);
    }

    @Test
    public void testSubtraction() {
        System.out.println("Test Subtraction");
        String t1 = "3 2 -";
        String t2 = "314 212 -";
        String t3 = "3.56 2.04 -";
        String t4 = "3 -2 -";
        String t5 = "3.48 -2.24 -";

        double r1 = RPNCalculator.calculate(t1);
        double r2 = RPNCalculator.calculate(t2);
        double r3 = RPNCalculator.calculate(t3);
        double r4 = RPNCalculator.calculate(t4);
        double r5 = RPNCalculator.calculate(t5);

        assertEquals(1.0, r1, 0.00001);
        assertEquals(102.0, r2, 0.00001);
        assertEquals(1.52, r3, 0.00001);
        assertEquals(5.0, r4, 0.00001);
        assertEquals(5.72, r5, 0.00001);
    }

    @Test
    public void testMultiplication() {
        System.out.println("Test Multiplication");
        String t1 = "3 2 *";
        String t2 = "20 30 *";
        String t3 = "3.2 2 *";
        String t4 = "3 -2 *";

        double r1 = RPNCalculator.calculate(t1);
        double r2 = RPNCalculator.calculate(t2);
        double r3 = RPNCalculator.calculate(t3);
        double r4 = RPNCalculator.calculate(t4);

        assertEquals(6.0, r1, 0.00001);
        assertEquals(600.0, r2, 0.00001);
        assertEquals(6.4, r3, 0.00001);
        assertEquals(-6.0, r4, 0.00001);
    }

    @Test
    public void testDivision() {
        System.out.println("Test Division");
        String t1 = "4 2 /";
        String t2 = "3 2 /";
        String t3 = "3.4 2 /";
        String t4 = "4 -2 /";
        String t5 = "0 2 /";

        double r1 = RPNCalculator.calculate(t1);
        double r2 = RPNCalculator.calculate(t2);
        double r3 = RPNCalculator.calculate(t3);
        double r4 = RPNCalculator.calculate(t4);
        double r5 = RPNCalculator.calculate(t5);

        assertEquals(2.0, r1, 0.00001);
        assertEquals(1.5, r2, 0.00001);
        assertEquals(1.7, r3, 0.00001);
        assertEquals(-2.0, r4, 0.00001);
        assertEquals(0.0, r5, 0.00001);
    }

    @Test
    public void testComplexCalculations() {
        System.out.println("Test Complex Calculations");
        String t1 = "3 2 + 5 *";
        String t2 = "5 7 2 - *";
        String t3 = "5 3 + 2 / 2 - 8 *";

        double r1 = RPNCalculator.calculate(t1);
        double r2 = RPNCalculator.calculate(t2);
        double r3 = RPNCalculator.calculate(t3);

        assertEquals(25.0, r1, 0.00001);
        assertEquals(25.0, r2, 0.00001);
        assertEquals(16.0, r3, 0.00001);
    }

    @Test
    public void testErrorThrows() {
        System.out.println("Test error throws");
        String t1 = "3 *";
        String t2 = "3 2 / s";
        String t3 = "3 2 1 +";
        String t4 = "3 0 /";

        Exception e1 = assertThrows(InvalidRPNStringException.class, () -> {
            double r1 = RPNCalculator.calculate(t1);
        });
        String msg1 = e1.getMessage();
        assertTrue(msg1.equals("Insufficient numbers for operation"));

        Exception e2 = assertThrows(InvalidRPNStringException.class, () -> {
            double r2 = RPNCalculator.calculate(t2);
        });
        String msg2 = e2.getMessage();
        assertTrue(msg2.equals("Unable to parse string"));

        Exception e3 = assertThrows(InvalidRPNStringException.class, () -> {
            double r3 = RPNCalculator.calculate(t3);
        });
        String msg3 = e3.getMessage();
        assertTrue(msg3.equals("Improper number of arguments"));

        Exception e4 = assertThrows(InvalidRPNStringException.class, () -> {
            double r4 = RPNCalculator.calculate(t4);
        });
        String msg4 = e4.getMessage();
        assertTrue(msg4.equals("Cannot divide by 0"));
    }
}
