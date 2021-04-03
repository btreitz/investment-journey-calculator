package com.btreitz.investmentcalc;

import lombok.Getter;
import lombok.Setter;

@Getter
public class PhaseResult {

    private final AnnualResult[] annualResultList;
    @Setter
    private double phaseStartBalance;
    @Setter
    private double totalPhaseValue;
    @Setter
    private double totalPhaseContributions;
    @Setter
    private double totalPhaseInterest;

    public PhaseResult(int duration, double startBalance) {
        this.annualResultList = new AnnualResult[duration];
        this.phaseStartBalance = startBalance;
    }

}
