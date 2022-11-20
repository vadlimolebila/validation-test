package com.nurvadli.test.validator;

import com.nurvadli.test.dto.UserCreateDto;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ValidatorTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();


    @Test
    public void testValidInput() {
        UserCreateDto userCreateDto = new UserCreateDto();
        userCreateDto.setName("Nurvadli");
        userCreateDto.setDateOfBirth("1990-05-11");
        userCreateDto.setSocialSecurityNumber("10");
        List<ConstraintViolation<UserCreateDto>> violations = new ArrayList<>(validator.validate(userCreateDto));

        assertEquals(0, violations.size());
    }

    @Test
    public void testInvalidInputNameWithSpecialChar() {
        UserCreateDto userCreateDto = new UserCreateDto();
        userCreateDto.setName("Nurvadli@ds08");
        userCreateDto.setDateOfBirth("1990-05-11");
        userCreateDto.setSocialSecurityNumber("10");
        List<ConstraintViolation<UserCreateDto>> violations = new ArrayList<>(validator.validate(userCreateDto));

        assertEquals(1, violations.size());
        assertEquals("Invalid value for 'name', rejected value: 'Nurvadli@ds08'", violations.get(0).getMessage());
    }

    @Test
    public void testInvalidInputNameWithNameNotSet() {
        UserCreateDto userCreateDto = new UserCreateDto();
        userCreateDto.setDateOfBirth("1990-05-11");
        userCreateDto.setSocialSecurityNumber("10");
        List<ConstraintViolation<UserCreateDto>> violations = new ArrayList<>(validator.validate(userCreateDto));

        assertEquals(1, violations.size());
        assertEquals("Invalid value for 'name', rejected value: ''", violations.get(0).getMessage());
    }

    @Test
    public void testInvalidInputNameWithNameLengthLessThanTwo() {
        UserCreateDto userCreateDto = new UserCreateDto();
        userCreateDto.setName("a");
        userCreateDto.setDateOfBirth("1990-05-11");
        userCreateDto.setSocialSecurityNumber("10");
        List<ConstraintViolation<UserCreateDto>> violations = new ArrayList<>(validator.validate(userCreateDto));

        assertEquals(1, violations.size());
        assertEquals("Invalid value for 'name', rejected value: ''", violations.get(0).getMessage());
    }

    @Test
    public void testInvalidInputNameWithNameLengthGreaterThanFifty() {
        UserCreateDto userCreateDto = new UserCreateDto();
        userCreateDto.setName("absd0394kdjskeur ghdyeushd jtkstkgkd anskskwketksa");
        userCreateDto.setDateOfBirth("1990-05-11");
        userCreateDto.setSocialSecurityNumber("10");
        List<ConstraintViolation<UserCreateDto>> violations = new ArrayList<>(validator.validate(userCreateDto));

        assertEquals(1, violations.size());
        assertEquals("Invalid value for 'name', rejected value: 'absd0394kdjskeur ghdyeushd jtkstkgkd anskskwketksa'", violations.get(0).getMessage());
    }

    @Test
    public void testInvalidInputDateOfBirthWithUnFormattedStringDate() {
        UserCreateDto userCreateDto = new UserCreateDto();
        userCreateDto.setName("Nurvadli07");
        userCreateDto.setDateOfBirth("10-1990-11");
        userCreateDto.setSocialSecurityNumber("10");
        List<ConstraintViolation<UserCreateDto>> violations = new ArrayList<>(validator.validate(userCreateDto));

        assertEquals(1, violations.size());
        assertEquals("Invalid value for 'dateOfBirth', rejected value: '10-1990-11'", violations.get(0).getMessage());
    }

    @Test
    public void testInvalidInputSocialSecurityNumberWithInputAlphanumeric() {
        UserCreateDto userCreateDto = new UserCreateDto();
        userCreateDto.setName("Purantyo01");
        userCreateDto.setDateOfBirth("1990-05-07");
        userCreateDto.setSocialSecurityNumber("sdd10");
        List<ConstraintViolation<UserCreateDto>> violations = new ArrayList<>(validator.validate(userCreateDto));

        assertEquals(1, violations.size());
        assertEquals("Invalid value for 'SSN', rejected value: 'sdd10'", violations.get(0).getMessage());
    }
}
