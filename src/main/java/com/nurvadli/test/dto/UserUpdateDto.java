package com.nurvadli.test.dto;

import com.nurvadli.test.validator.DateOfBirth;
import com.nurvadli.test.validator.Name;
import lombok.Data;

@Data
public class UserUpdateDto {
    @Name
    private String name;
    @DateOfBirth
    private String dateOfBirth;
}
