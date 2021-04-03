package com.btreitz.investmentcalc.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class MaxDoubleElemValueConstraintValidator implements ConstraintValidator<MaxDoubleElemValueConstraint, List<Double>> {

    private double maxValue;

    @Override
    public void initialize(MaxDoubleElemValueConstraint constraintAnnotation) {
        this.maxValue = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(List<Double> elements, ConstraintValidatorContext context) {
        for (double element : elements) {
            if (element > maxValue) {
                return false;
            }
        }
        return true;
    }
}