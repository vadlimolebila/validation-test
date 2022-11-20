package com.nurvadli.test.dto;

import com.nurvadli.test.validator.Name;
import com.nurvadli.test.validator.DateOfBirth;
import com.nurvadli.test.validator.Ssn;
import lombok.Data;

@Data
public class UserCreateDto {

    @Name
    private String name;

    @Ssn
    private String socialSecurityNumber;

    @DateOfBirth
    private String dateOfBirth;

}
