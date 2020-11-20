package com.btreitz.investmentcalc;

public interface ICalculationService {

    CalculationResult calculateResults(
            double initialInvestment,
            double periodicContribution,
            String contributionFrequency,
            double annualGrowth,
            int duration
    );

}
