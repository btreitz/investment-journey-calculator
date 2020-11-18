package com.btreitz.investmentcalc;

public interface ICalculationService {

    CalculationResult calculateResults(
            double initialInvestment,
            double periodicContribution,
            int contributionFrequency,
            double annualGrowth,
            int duration
    );

}
