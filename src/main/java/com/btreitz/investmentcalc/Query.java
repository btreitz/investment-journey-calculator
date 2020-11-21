package com.btreitz.investmentcalc;

import lombok.Getter;

public class Query {

    @Getter
    private final double initialInvestment;
    @Getter
    private final double periodicContribution;
    @Getter
    private final int contributionFrequency;
    @Getter
    private final double annualGrowth;
    @Getter
    private final int duration;

    public Query(double initialInvestment, double periodicContribution, int contributionFrequency, double annualGrowth, int duration) {
        this.initialInvestment = initialInvestment;
        this.periodicContribution = periodicContribution;
        this.contributionFrequency = contributionFrequency;
        this.annualGrowth = annualGrowth;
        this.duration = duration;
    }
}
