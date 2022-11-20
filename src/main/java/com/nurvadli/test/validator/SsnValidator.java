package com.nurvadli.test.validator;

import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SsnValidator implements ConstraintValidator<Ssn, String> {

    private static final String NUMERIC_REGEX = "\\d+";
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return StringUtils.hasText(s) && isNumeric(s, NUMERIC_REGEX);
    }

    private boolean isNumeric(String input, String regex) {
        Pattern mobilePhonePattern = Pattern.compile(regex);
        Matcher matcher = mobilePhonePattern.matcher(input);
        return matcher.matches();
    }
}
