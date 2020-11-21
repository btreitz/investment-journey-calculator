package com.btreitz.investmentcalc;

import lombok.Getter;
import lombok.Setter;

@Getter
public class AnnualResult {

    private final int year;
    private final double startBalance;
    @Setter
    private double endBalance;
    @Setter
    private double annualContribution;
    @Setter
    private double annualInterest;

    public AnnualResult(int year, double startBalance) {
        this.year = year;
        this.startBalance = startBalance;
    }

}
