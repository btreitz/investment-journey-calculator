package com.btreitz.investmentcalc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@WebMvcTest(CalculationService.class)
class CalculationServiceTest {

    @Autowired
    private CalculationService calculationService;

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

    @Test
    void calculatePhaseResults() {
        PhaseResult phaseResult = calculationService.calculatePhase(2021,2500, 500, 1, 10.0, 1);
        assertTrue(9070 <= phaseResult.getAnnualResultList()[0].getEndBalance() && phaseResult.getAnnualResultList()[0].getEndBalance() <= 9071);

        phaseResult = calculationService.calculatePhase(2021,2500, 500, 3, 10.0, 10);
        assertTrue(40327 <= phaseResult.getTotalPhaseValue() && phaseResult.getTotalPhaseValue() <= 40328);
    }

    @Test
    void calculateTotalResults() {
        TotalResult totalResult = calculationService.calculateTotal(500, Arrays.asList(200.0, 400.0, 600.0, 800.0), Arrays.asList(3,3,1,1), Arrays.asList(12.0, 10.0, 8.0, 8.0), Arrays.asList(3, 3, 3, 3));
        assertTrue(79735 <= totalResult.getTotalValue() && totalResult.getTotalValue() <= 79736);
        assertTrue(57600 <= totalResult.getTotalContributions() && totalResult.getTotalContributions() <= 57601);
        assertTrue(21635 <= totalResult.getTotalInterest() && totalResult.getTotalInterest() <= 21636);
    }

}