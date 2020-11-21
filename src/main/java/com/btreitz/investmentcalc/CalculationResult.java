package com.btreitz.investmentcalc;

import lombok.Getter;
import lombok.Setter;

@Getter
public class CalculationResult {

    private final AnnualResult[] annualResultList;
    @Setter
    private double startBalance;
    @Setter
    private double totalValue;
    @Setter
    private double totalContributions;
    @Setter
    private double totalInterest;

    public CalculationResult(int duration, double startBalance) {
        this.annualResultList = new AnnualResult[duration];
        this.startBalance = startBalance;
    }

}