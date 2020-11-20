package com.btreitz.investmentcalc;

import org.springframework.stereotype.Service;

@Service
public class CalculationService implements ICalculationService {

    @Override
    public CalculationResult calculateResults(
            double initialInvestment,
            double periodicContribution,
            String contributionFrequency,
            double annualGrowth,
            int duration) {
        return null;
    }
}
