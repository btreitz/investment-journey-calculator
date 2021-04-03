package com.btreitz.investmentcalc.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class MaxIntElemValueConstraintValidator implements ConstraintValidator<MaxIntElemValueConstraint, List<Integer>> {

    private int maxValue;

    @Override
    public void initialize(MaxIntElemValueConstraint constraintAnnotation) {
        this.maxValue = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(List<Integer> elements, ConstraintValidatorContext context) {
        for (int element : elements) {
            if (element > maxValue) {
                return false;
            }
        }
        return true;
    }
}