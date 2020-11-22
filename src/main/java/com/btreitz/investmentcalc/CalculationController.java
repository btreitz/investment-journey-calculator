package com.btreitz.investmentcalc;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.ArrayList;

@Controller
@Validated
public class CalculationController {

    private final ICalculationService calculationService;
    private Query mostRecentQuery;

    public CalculationController(ICalculationService calculationService) {
        this.calculationService = calculationService;
        this.mostRecentQuery = new Query(0, 0, 1, 0, 1);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String handleConstraintViolationException(Model model, ConstraintViolationException e) {
        new ResponseEntity<>("not valid due to validation error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        ArrayList<String> errorMessages = new ArrayList<>();
        if (e.getMessage().contains("initialInvestment")) {
            errorMessages.add("Initial Investment must be of value 0 or above");
        }
        if (e.getMessage().contains("periodicContribution")) {
            errorMessages.add("Periodic Contribution must be of value 0 or above");
        }
        if (e.getMessage().contains("contributionFrequency")) {
            errorMessages.add("No valid Contribution Frequency was selected");
        }
        if (e.getMessage().contains("annualGrowth")) {
            errorMessages.add("Annual Growth must be in percentage between the values -100.0 and 100.0");
        }
        if (e.getMessage().contains("duration")) {
            errorMessages.add("Duration must be of value 0 and below 100");
        }
        model.addAttribute("errors", errorMessages);
        model.addAttribute("query", this.mostRecentQuery);
        return "index";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/")
    public String index() {
        return "index";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/calculate")
    public String calculateResults(
            Model model,
            @RequestParam(name = "initialInvestment") @Min(0) double initialInvestment,
            @RequestParam(name = "periodicContribution") @Min(0) double periodicContribution,
            @RequestParam(name = "contributionFrequency") @Min(1) @Max(12) int contributionFrequency,
            @RequestParam(name = "annualGrowth") @Min(-100) @Max(100) double annualGrowth,
            @RequestParam(name = "duration") @Min(1) @Max(100) int duration) {
        CalculationResult calculationResult = calculationService.calculateResults(initialInvestment, periodicContribution, contributionFrequency, annualGrowth, duration);
        this.mostRecentQuery = new Query(initialInvestment, periodicContribution, contributionFrequency, annualGrowth, duration);
        model.addAttribute("query", this.mostRecentQuery);
        model.addAttribute("calculationResult", calculationResult);
        return "index";
    }

}
