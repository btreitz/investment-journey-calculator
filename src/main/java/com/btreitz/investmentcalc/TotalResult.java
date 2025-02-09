package com.btreitz.investmentcalc;

import com.btreitz.investmentcalc.utils.CalculationUtils;
import lombok.Getter;
import lombok.Setter;

@Getter
public class TotalResult {

    private final PhaseResult[] phaseResultList;
    @Setter
    private double startBalance;
    @Setter
    private double totalValue;
    @Setter
    private double totalContributions;
    @Setter
    private double totalInterest;

    public TotalResult(int phasesCount, double startBalance) {
        this.phaseResultList = new PhaseResult[phasesCount];
        this.startBalance = startBalance;
    }

    public void addNewValues(double newContribution, double newInterest) {
        this.totalContributions = CalculationUtils.trimDoubleToDecimalPrecision(2, this.totalContributions + newContribution);
        this.totalInterest = CalculationUtils.trimDoubleToDecimalPrecision(2, this.totalInterest + newInterest);
    }

}