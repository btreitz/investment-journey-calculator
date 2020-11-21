package com.btreitz.investmentcalc;

import lombok.Getter;
import lombok.Setter;

@Getter
public class CalculationResult {

    private final AnnualResult[] annualResultList;
    @Setter
    private double totalValue;
    @Setter
    private double totalContributions;
    @Setter
    private double totalInterest;

    public CalculationResult(int duration) {
        this.annualResultList = new AnnualResult[duration];
    }

}