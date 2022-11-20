package com.nurvadli.test.validator;


import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NameValidator implements ConstraintValidator<Name, String> {
    private static final String ALPHANUMERIC_ONLY_REGEX = "^[a-zA-Z0-9]+$";

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {

        if (!StringUtils.hasLength(s)) {
            return false;
        }

        if (s.length() < 2 || s.length() > 50) {
            return false;
        }

        return isAlphanumeric(s, ALPHANUMERIC_ONLY_REGEX);
    }

    private boolean isAlphanumeric(String input, String regex) {
        Pattern mobilePhonePattern = Pattern.compile(regex);
        Matcher matcher = mobilePhonePattern.matcher(input);
        return matcher.matches();
    }
}
