package com.btreitz.investmentcalc;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String calculateResults(
            Model model,
            @RequestParam(name = "initialInvestment") double initialInvestment,
            @RequestParam(name = "periodicContribution") double periodicContribution,
            @RequestParam(name = "contributionFrequency") int contributionFrequency,
            @RequestParam(name = "annualGrowth") double annualGrowth,
            @RequestParam(name = "duration") int duration) {
        CalculationResult calculationResult = calculationService.calculateResults(initialInvestment, periodicContribution, contributionFrequency, annualGrowth, duration);
        model.addAttribute("query", new Query(initialInvestment, periodicContribution, contributionFrequency, annualGrowth, duration));
        model.addAttribute("calculationResult", calculationResult);
        return "index";
    }

}
