package com.btreitz.investmentcalc;

import java.util.List;

public interface ICalculationService {

    /**
     * For every phase in the investment journey calculate the annual results.
     * When all phases are calculated, then return the total results through the class TotalResult
     *
     * @param initialInvestment Initial start of balance
     * @param periodicContributionList List of all periodic contributions
     * @param contributionFrequencyList List of all contribution frequencies
     * @param annualGrowthList List of all annual growth values
     * @param durationList List of the duration for each phase
     * @return The calculated result in class TotalResult
     */
    TotalResult calculateTotal(
            double initialInvestment,
            List<Double> periodicContributionList,
            List<Integer> contributionFrequencyList,
            List<Double> annualGrowthList,
            List<Integer> durationList
    );

    /**
     * Calculate Results for the specific phase and for every single year determined by the given duration
     * Therefore create the classes PhaseResult and AnnualResult and save the results in those classes
     *
     * @param phaseStartBalance     Initial start balance from this phase
     * @param periodicContribution  further contribution amount
     * @param contributionFrequency further contribution frequency as every month/quarter/half a year/year
     * @param annualGrowth          expected annual return of all investments
     * @param duration              Amount of years to calculate returns for
     * @return the whole result in an object called CalculationResult
     */
    PhaseResult calculatePhase(
            int phaseStartYear,
            double phaseStartBalance,
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
