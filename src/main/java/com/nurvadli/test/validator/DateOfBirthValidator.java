package com.nurvadli.test.validator;

import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateOfBirthValidator implements ConstraintValidator<DateOfBirth, String> {

    private static final String YYYY_MM_DD = "yyyy-MM-dd";

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return StringUtils.hasLength(s) && isValidDate(s);
    }

    private static boolean isValidDate(String input) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(YYYY_MM_DD);
        try {
            LocalDate.parse(input, formatter);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
