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

@Controller
@Validated
public class CalculationController {

    private final ICalculationService calculationService;

    public CalculationController(ICalculationService calculationService) {
        this.calculationService = calculationService;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException e) {
        return new ResponseEntity<>("not valid due to validation error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
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
        model.addAttribute("query", new Query(initialInvestment, periodicContribution, contributionFrequency, annualGrowth, duration));
        model.addAttribute("calculationResult", calculationResult);
        return "index";
    }

}
