package com.btreitz.investmentcalc.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Constraint(validatedBy = MaxDoubleElemValueConstraintValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface MaxDoubleElemValueConstraint {
    String message() default "Value must be of value {value} or lower";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    double value();
}
