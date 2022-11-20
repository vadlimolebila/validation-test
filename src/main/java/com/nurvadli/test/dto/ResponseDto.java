package com.nurvadli.test.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseDto {

    private String status;
    private int code;
    private String message;
}
