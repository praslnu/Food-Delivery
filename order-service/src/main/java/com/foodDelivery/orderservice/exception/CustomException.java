package com.foodDelivery.orderservice.exception;

import lombok.Data;

@Data
public class CustomException extends RuntimeException {
    private int StatusCode;
    public CustomException(String message, int status) {
        super(message);
        this.StatusCode = status;
    }
}