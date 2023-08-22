package com.foodDelivery.userservice.exception;

import lombok.Data;

@Data
public class CustException extends RuntimeException {
    private int StatusCode;
    public CustException(String message, int status) {
        super(message);
        this.StatusCode = status;
    }
}