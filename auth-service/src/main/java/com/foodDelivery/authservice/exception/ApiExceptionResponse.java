package com.foodDelivery.authservice.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiExceptionResponse {
    private String error;
    private int StatusCode;
}
