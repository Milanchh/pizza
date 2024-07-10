package com.example.test3pizza;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TotalBillCalculationTest {

    private static final double DELTA = 0.001; // Delta for double comparisons

    // Test cases for calculateTotalBill method

    @Test
    public void testCalculateTotalBillXL() {
        double totalBill = HelloController.calculateTotalBill("XL", 0);
        assertEquals(15.00 * 1.15, totalBill, DELTA);
    }

    @Test
    public void testCalculateTotalBillLWithToppings() {
        double totalBill = HelloController.calculateTotalBill("L", 2);
        assertEquals((12.00 + 2 * 1.50) * 1.15, totalBill, DELTA);
    }

    @Test
    public void testCalculateTotalBillMWithToppings() {
        double totalBill = HelloController.calculateTotalBill("M", 4);
        assertEquals((10.00 + 4 * 1.50) * 1.15, totalBill, DELTA);
    }

    @Test
    public void testCalculateTotalBillSWithToppings() {
        double totalBill = HelloController.calculateTotalBill("S", 1);
        assertEquals((8.00 + 1 * 1.50) * 1.15, totalBill, DELTA);
    }

    @Test
    public void testCalculateTotalBillInvalidSize() {
        double totalBill = HelloController.calculateTotalBill("S", 1);
        assertEquals((8.00 + 1 * 1.50) * 1.15, totalBill, DELTA);
    }
}
