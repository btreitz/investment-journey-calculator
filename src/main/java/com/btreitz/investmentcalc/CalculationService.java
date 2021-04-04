package com.btreitz.investmentcalc;

import com.btreitz.investmentcalc.utils.CalculationUtils;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.List;

@Service
public class CalculationService implements ICalculationService {

    @Override
    public TotalResult calculateTotal(
            double initialInvestment,
            List<Double> periodicContributionList,
            List<Integer> contributionFrequencyList,
            List<Double> annualGrowthList,
            List<Integer> durationList) {
        int phasesCount = periodicContributionList.size();
        TotalResult totalResult = new TotalResult(phasesCount, initialInvestment);
        double currPhaseStartBalance = initialInvestment;
        int currPhaseStartYear = Year.now().getValue();
        for (int currPhaseIdx = 0; currPhaseIdx < phasesCount; currPhaseIdx++) {
            PhaseResult phaseResult = calculatePhase(
                    currPhaseStartYear,
                    currPhaseStartBalance,
                    periodicContributionList.get(currPhaseIdx),
                    contributionFrequencyList.get(currPhaseIdx),
                    annualGrowthList.get(currPhaseIdx),
                    durationList.get(currPhaseIdx));
            totalResult.getPhaseResultList()[currPhaseIdx] =  phaseResult;
            totalResult.addNewValues(phaseResult.getTotalPhaseContributions(), phaseResult.getTotalPhaseInterest());
            currPhaseStartBalance = phaseResult.getTotalPhaseValue();
            currPhaseStartYear += durationList.get(currPhaseIdx);
        }
        totalResult.setTotalValue(totalResult.getPhaseResultList()[totalResult.getPhaseResultList().length - 1].getTotalPhaseValue());
        return totalResult;
    }

    @Override
    public PhaseResult calculatePhase(
            int phaseStartYear,
            double phaseStartBalance,
            double periodicContribution,
            int contributionFrequency,
            double annualGrowth,
            int duration) {
        PhaseResult phaseResult = new PhaseResult(duration, phaseStartBalance);

        // initialize currentBalance with initialInvestment for first annual iteration
        // update currentBalance after first iteration
        double currentBalance = phaseStartBalance;

        // For every year (Amount of years = duration) create an AnnualResult
        for (int currentYear = 0; currentYear < duration; currentYear++) {
            AnnualResult annualResult = new AnnualResult(phaseStartYear + currentYear, CalculationUtils.trimDoubleToDecimalPrecision(2, currentBalance));
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
            phaseResult.getAnnualResultList()[currentYear] = annualResult;
        }
        phaseResult.setTotalPhaseValue(CalculationUtils.trimDoubleToDecimalPrecision(2, currentBalance));
        double totalContribution = ((12.0 / contributionFrequency) * periodicContribution) * duration;
        phaseResult.setTotalPhaseContributions(CalculationUtils.trimDoubleToDecimalPrecision(2, totalContribution));
        phaseResult.setTotalPhaseInterest(CalculationUtils.trimDoubleToDecimalPrecision(2, currentBalance - totalContribution - phaseStartBalance));
        return phaseResult;
    }

    @Override
    public double calcMonth(double beforeTotal, double annualGrowth) {
        return calcMonth(beforeTotal, 0, annualGrowth);
    }

    @Override
    public double calcMonth(double beforeTotal, double contribution, double annualGrowth) {
        return (beforeTotal + contribution) * (1 + ((Math.pow(((annualGrowth / 100) + 1), (1.0 / 12.0)) - 1)));
    }
}
