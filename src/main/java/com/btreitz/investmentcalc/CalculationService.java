package com.btreitz.investmentcalc;

import org.springframework.stereotype.Service;

import java.time.Year;

@Service
public class CalculationService implements ICalculationService {

    @Override
    public CalculationResult calculateResults(
            double initialInvestment,
            double periodicContribution,
            int contributionFrequency,
            double annualGrowth,
            int duration) {
        CalculationResult calculationResult = new CalculationResult(duration, initialInvestment);

        // initialize currentBalance with initialInvestment for first annual iteration
        // update currentBalance after first iteration
        double currentBalance = initialInvestment;

        // For every year (Amount of years = duration) create an AnnualResult
        for (int currentYear = 0; currentYear < duration; currentYear++) {
            AnnualResult annualResult = new AnnualResult(Year.now().getValue() + currentYear, currentBalance);
            // call calcMonth for every single month in a year (12 times)
            for (int currentMonth = 0; currentMonth < 12; currentMonth++) {
                if (currentMonth % contributionFrequency == 0) {
                    currentBalance = calcMonth(currentBalance, periodicContribution, annualGrowth);
                } else {
                    currentBalance = calcMonth(currentBalance, annualGrowth);
                }
            }
            annualResult.setEndBalance(currentBalance);
            double annualContribution = (12.0 / contributionFrequency) * periodicContribution;
            annualResult.setAnnualContribution(annualContribution);
            annualResult.setAnnualInterest(currentBalance - annualContribution - annualResult.getStartBalance());
            calculationResult.getAnnualResultList()[currentYear] = annualResult;
        }
        calculationResult.setTotalValue(currentBalance);
        double totalContribution = ((12.0 / contributionFrequency) * periodicContribution) * duration;
        calculationResult.setTotalContributions(totalContribution);
        calculationResult.setTotalInterest(currentBalance - totalContribution - initialInvestment);
        return calculationResult;
    }

    @Override
    public double calcMonth(double beforeTotal, double annualGrowth) {
        return calcMonth(beforeTotal, 0, annualGrowth);
    }

    /**
     * Based on formula for compound interest
     *
     * @param beforeTotal  Balance at start of the month
     * @param contribution invested amount in this month
     * @param annualGrowth expected annual Growth
     * @return Balance at the end of the month
     */
    @Override
    public double calcMonth(double beforeTotal, double contribution, double annualGrowth) {
        return (beforeTotal + contribution) * (1 + ((Math.pow(((annualGrowth / 100) + 1), (1.0 / 12.0)) - 1)));
    }
}
