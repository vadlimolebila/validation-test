package com.nurvadli.test.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {NameValidator.class})
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Name {

    String message() default "Invalid value for 'name', rejected value: '${validatedValue}'";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
