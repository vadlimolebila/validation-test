package com.nurvadli.test.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {DateOfBirthValidator.class})
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface DateOfBirth {

    String message() default "Invalid value for 'dateOfBirth', rejected value: '${validatedValue}'";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
