package com.foodDelivery.orderservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler{
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiExceptionResponse> handleBadRequestException(BadRequestException e) {
        return buildResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiExceptionResponse> handleNotFoundException(NotFoundException e) {
        return buildResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CustException.class)
    public ResponseEntity<ApiExceptionResponse> handleCustomException(CustException exception) {
        return buildResponseEntity(exception.getMessage(), HttpStatus.valueOf(exception.getStatusCode()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiExceptionResponse> handleGenericException(Exception e) {
        return buildResponseEntity(e.getMessage(), HttpStatus.CONFLICT);
    }

    private ResponseEntity<ApiExceptionResponse> buildResponseEntity(String message, HttpStatus status) {
        ApiExceptionResponse apiExceptionResponse = new ApiExceptionResponse(message, status.value());
        return new ResponseEntity<>(apiExceptionResponse, status);
    }
}