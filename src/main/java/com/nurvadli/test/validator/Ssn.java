package com.nurvadli.test.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {SsnValidator.class})
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Ssn {
    String message() default "Invalid value for 'SSN', rejected value: '${validatedValue}'";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
