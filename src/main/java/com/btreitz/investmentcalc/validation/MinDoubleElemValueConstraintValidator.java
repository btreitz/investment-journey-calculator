package com.btreitz.investmentcalc.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class MinDoubleElemValueConstraintValidator implements ConstraintValidator<MinDoubleElemValueConstraint, List<Double>> {

    private double minValue;

    @Override
    public void initialize(MinDoubleElemValueConstraint constraintAnnotation) {
        this.minValue = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(List<Double> elements, ConstraintValidatorContext context) {
        for (double element : elements) {
            if (element < minValue) {
                return false;
            }
        }
        return true;
    }
}
