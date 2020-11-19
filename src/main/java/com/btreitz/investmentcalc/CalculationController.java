package com.btreitz.investmentcalc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CalculationController {

    private final ICalculationService calculationService;

    public CalculationController(ICalculationService calculationService) {
        this.calculationService = calculationService;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/")
    public String index() {
        return "index";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/calculate")
    @ResponseBody
    public CalculationResult calculateResults(
            @RequestParam(name = "initialInvestment") double initialInvestment,
            @RequestParam(name = "periodicContribution") double periodicContribution,
            @RequestParam(name = "contributionFrequency") int contributionFrequency,
            @RequestParam(name = "annualGrowth") double annualGrowth,
            @RequestParam(name = "duration") int duration) {
        return calculationService.calculateResults(initialInvestment, periodicContribution, contributionFrequency, annualGrowth, duration);
    }

}
