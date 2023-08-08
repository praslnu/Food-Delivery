package com.foodDelivery.restaurantservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiExceptionResponse> handleBadRequestException(BadRequestException e) {
        return buildResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiExceptionResponse> handleNotFoundException(NotFoundException e) {
        return buildResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiExceptionResponse> handleGenericException(Exception e) {
        return buildResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<ApiExceptionResponse> buildResponseEntity(String message, HttpStatus status) {
        ApiExceptionResponse apiExceptionResponse = new ApiExceptionResponse(
                message,
                status.value()
        );
        return new ResponseEntity<>(apiExceptionResponse, status);
    }
}