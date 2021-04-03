package com.btreitz.investmentcalc.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class MinIntElemValueConstraintValidator implements ConstraintValidator<MinIntElemValueConstraint, List<Integer>> {

    private int minValue;

    @Override
    public void initialize(MinIntElemValueConstraint constraintAnnotation) {
        this.minValue = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(List<Integer> elements, ConstraintValidatorContext context) {
        for (int element : elements) {
            if (element < minValue) {
                return false;
            }
        }
        return true;
    }
}
