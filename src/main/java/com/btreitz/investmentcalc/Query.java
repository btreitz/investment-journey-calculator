package com.btreitz.investmentcalc;

import lombok.Getter;

@Getter
public class Query {

    private final double initialInvestment;
    private final double periodicContribution;
    private final int contributionFrequency;
    private final double annualGrowth;
    private final int duration;

    public Query(double initialInvestment, double periodicContribution, int contributionFrequency, double annualGrowth, int duration) {
        this.initialInvestment = initialInvestment;
        this.periodicContribution = periodicContribution;
        this.contributionFrequency = contributionFrequency;
        this.annualGrowth = annualGrowth;
        this.duration = duration;
    }
}
