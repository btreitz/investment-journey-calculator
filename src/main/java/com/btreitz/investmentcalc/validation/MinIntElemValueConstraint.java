package com.btreitz.investmentcalc.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Constraint(validatedBy = MinIntElemValueConstraintValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface MinIntElemValueConstraint {
    String message() default "Input must be of value {value} or higher";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    int value();
}
