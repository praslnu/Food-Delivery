package com.foodDelivery.paymentservice.exception;

import lombok.Data;

@Data
public class CustomException extends RuntimeException {
    private int StatusCode;
    public CustomException(String message, int status) {
        super(message);
        this.StatusCode = status;
    }
}