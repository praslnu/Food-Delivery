package com.foodDelivery.restaurantservice.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiExceptionResponse {
    private String error;
    private int StatusCode;
}
