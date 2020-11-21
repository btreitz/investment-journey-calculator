package com.btreitz.investmentcalc;

import org.springframework.stereotype.Service;

@Service
public class CalculationService implements ICalculationService {

    @Override
    public CalculationResult calculateResults(
            double initialInvestment,
            double periodicContribution,
            int contributionFrequency,
            double annualGrowth,
            int duration) {
        CalculationResult calculationResult = new CalculationResult(duration);
        return null;
    }
}
