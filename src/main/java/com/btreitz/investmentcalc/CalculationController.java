package com.btreitz.investmentcalc;

import com.btreitz.investmentcalc.validation.MaxDoubleElemValueConstraint;
import com.btreitz.investmentcalc.validation.MaxIntElemValueConstraint;
import com.btreitz.investmentcalc.validation.MinDoubleElemValueConstraint;
import com.btreitz.investmentcalc.validation.MinIntElemValueConstraint;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        ArrayList<String> errorMessages = new ArrayList<>();

        for (ConstraintViolation<?> violation : violations) {
            String propPath = violation.getPropertyPath().toString();
            String field =  propPath.substring(propPath.lastIndexOf(".") + 1);

            if (field.contains("initialInvestment")) {
                errorMessages.add("Input must be of value 0.0 or higher" + " for field 'Initial Investment'");
            }
            if (field.contains("periodicContribution")) {
                errorMessages.add(violation.getMessage() + " for field(s) 'Periodic Contribution'");
            }
            if (field.contains("contributionFrequency")) {
                errorMessages.add("Invalid selection for field(s) 'Contribution Frequency'");
            }
            if (field.contains("annualGrowth")) {
                errorMessages.add(violation.getMessage() + " for field(s) 'Annual Growth'");
            }
            if (field.contains("duration")) {
                errorMessages.add(violation.getMessage() + " for field(s) 'Duration'");
            }
        }
        model.addAttribute("errors", errorMessages);
        model.addAttribute("query", this.mostRecentQuery);
        return "index";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/")
    public String index() {
        return "index";
    }

    /**
     * For multiple phases in a whole journey, receive arrays of values instead of single values in multiple requests
     * -> 1 Request -> Each value in an array -> calculate results for each index-position in the arrays
     */
    @RequestMapping(method = RequestMethod.GET, value = "/calculate")
    public String calculateResults(
            Model model,
            @RequestParam(name = "initialInvestment") @Min(0) double initialInvestment,
            @RequestParam(name = "periodicContribution") @MinDoubleElemValueConstraint(0) List<Double> periodicContribution,
            @RequestParam(name = "contributionFrequency") @MinIntElemValueConstraint(1) @MaxIntElemValueConstraint(12) List<Integer> contributionFrequency,
            @RequestParam(name = "annualGrowth") @MinDoubleElemValueConstraint(-100) @MaxDoubleElemValueConstraint(100) List<Double> annualGrowth,
            @RequestParam(name = "duration") @MinIntElemValueConstraint(1) @MaxIntElemValueConstraint(100) List<Integer> duration) {
        for (double perContr : periodicContribution) {
            System.out.println(perContr);
        }
        CalculationResult calculationResult = calculationService.calculateResults(initialInvestment, periodicContribution.get(0), contributionFrequency.get(0), annualGrowth.get(0), duration.get(0));
        this.mostRecentQuery = new Query(initialInvestment, periodicContribution.get(0), contributionFrequency.get(0), annualGrowth.get(0), duration.get(0));
        model.addAttribute("query", this.mostRecentQuery);
        model.addAttribute("calculationResult", calculationResult);
        return "index";
    }

}
