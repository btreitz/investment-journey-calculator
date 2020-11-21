package com.btreitz.investmentcalc;

import com.btreitz.investmentcalc.utils.CalculationUtils;
import org.springframework.stereotype.Service;

import java.time.Year;

@Service
public class CalculationService implements ICalculationService {

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
            AnnualResult annualResult = new AnnualResult(Year.now().getValue() + currentYear, CalculationUtils.trimDoubleToDecimalPrecision(2, currentBalance));
            // call calcMonth for every single month in a year (12 times)
            for (int currentMonth = 0; currentMonth < 12; currentMonth++) {
                if (currentMonth % contributionFrequency == 0) {
                    currentBalance = calcMonth(currentBalance, periodicContribution, annualGrowth);
                } else {
                    currentBalance = calcMonth(currentBalance, annualGrowth);
                }
            }
            annualResult.setEndBalance(CalculationUtils.trimDoubleToDecimalPrecision(2, currentBalance));
            double annualContribution = (12.0 / contributionFrequency) * periodicContribution;
            annualResult.setAnnualContribution(CalculationUtils.trimDoubleToDecimalPrecision(2, annualContribution));
            annualResult.setAnnualInterest(CalculationUtils.trimDoubleToDecimalPrecision(2, currentBalance - annualContribution - annualResult.getStartBalance()));
            calculationResult.getAnnualResultList()[currentYear] = annualResult;
        }
        calculationResult.setTotalValue(CalculationUtils.trimDoubleToDecimalPrecision(2, currentBalance));
        double totalContribution = ((12.0 / contributionFrequency) * periodicContribution) * duration;
        calculationResult.setTotalContributions(CalculationUtils.trimDoubleToDecimalPrecision(2, totalContribution));
        calculationResult.setTotalInterest(CalculationUtils.trimDoubleToDecimalPrecision(2, currentBalance - totalContribution - initialInvestment));
        return calculationResult;
    }

    /**
     * If no contribution is given, then contribution defaults to "0" for this month
     *
     * @param beforeTotal  Balance at start of the month
     * @param annualGrowth expected annual Growth
     * @return Balance at the end of the month
     */
    @Override
    public double calcMonth(double beforeTotal, double annualGrowth) {
        return calcMonth(beforeTotal, 0, annualGrowth);
    }

    /**
     * Based on formula for compound interest
     *
     * @param beforeTotal Balance at start of the month
     * @param contribution invested amount in this month
     * @param annualGrowth expected annual Growth
     * @return Balance at the end of the month
     */
    @Override
    public double calcMonth(double beforeTotal, double contribution, double annualGrowth) {
        return (beforeTotal + contribution) * (1 + ((Math.pow(((annualGrowth / 100) + 1), (1.0 / 12.0)) - 1)));
    }
}
