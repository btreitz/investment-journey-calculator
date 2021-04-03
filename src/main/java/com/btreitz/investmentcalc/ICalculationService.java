package com.btreitz.investmentcalc;

public interface ICalculationService {

    /**
     * Calculate Results in total and for every single year determined by the given duration
     * Therefore create the classes CalculationResult and AnnualResult and save the results in those classes
     *
     * @param initialInvestment     Initial start Balance
     * @param periodicContribution  further contribution amount
     * @param contributionFrequency further contribution frequency as every month/quarter/half a year/year
     * @param annualGrowth          expected annual return of all investments
     * @param duration              Amount of years to calculate returns for
     * @return the whole result in an object called CalculationResult
     */
    CalculationResult calculatePhase(
            double initialInvestment,
            double periodicContribution,
            int contributionFrequency,
            double annualGrowth,
            int duration
    );

    /**
     * If no contribution is given, then contribution defaults to "0" for this month
     *
     * @param beforeTotal  Balance at start of the month
     * @param annualGrowth expected annual Growth
     * @return Balance at the end of the month
     */
    double calcMonth(double beforeTotal, double annualGrowth);

    /**
     * Based on formula for compound interest
     *
     * @param beforeTotal Balance at start of the month
     * @param contribution invested amount in this month
     * @param annualGrowth expected annual Growth
     * @return Balance at the end of the month
     */
    double calcMonth(double beforeTotal, double contribution, double annualGrowth);

}
