package com.btreitz.investmentcalc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@WebMvcTest(CalculationService.class)
class CalculationServiceTest {

    @Autowired
    private CalculationService calculationService;

    @Test
    void calculateResults() {
        CalculationResult calculationResult = calculationService.calculateResults(2500, 500, 1, 10.0, 1);
        assertTrue(9070 <= calculationResult.getAnnualResultList()[0].getEndBalance() && calculationResult.getAnnualResultList()[0].getEndBalance() <= 9071);

        calculationResult = calculationService.calculateResults(2500, 500, 3, 10.0, 10);
        assertTrue(40327 <= calculationResult.getTotalValue() && calculationResult.getTotalValue() <= 40328);
    }

    @Test
    void calcMonth() {
        double result = calculationService.calcMonth(4031.89656172, 10.0);
        assertTrue(4064 <= result && result <= 4065);
    }

    @Test
    void testCalcMonth() {
        double result = calculationService.calcMonth(2500, 500, 10.0);
        assertTrue(3023 <= result && result <= 3024);

        result = calculationService.calcMonth(2500, 500, 0.0);
        assertEquals(result, 3000);
    }
}