package com.btreitz.investmentcalc.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Constraint(validatedBy = MinDoubleElemValueConstraintValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface MinDoubleElemValueConstraint {
    String message() default "Value must be of value {value} or higher";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    double value();
}
