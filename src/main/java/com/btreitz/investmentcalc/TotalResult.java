package com.btreitz.investmentcalc;

import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;

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
        this.totalContributions += newContribution;
        this.totalInterest += newInterest;
    }

}