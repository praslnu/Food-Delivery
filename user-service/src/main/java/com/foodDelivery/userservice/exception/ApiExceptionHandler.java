package com.foodDelivery.userservice.exception;

import com.foodDelivery.userservice.response.ApiExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.util.List;
import java.util.stream.Collectors;

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

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiExceptionResponse> handleCustomException(CustomException exception) {
        return buildResponseEntity(exception.getMessage(), HttpStatus.valueOf(exception.getStatusCode()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiExceptionResponse> handleGenericException(Exception e) {
        System.out.println(e);
        return buildResponseEntity(e.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiExceptionResponse> handleValidationErrors(MethodArgumentNotValidException ex)
    {
        List<String> errors = ex.getBindingResult().getFieldErrors()
                .stream().map(FieldError::getDefaultMessage).collect(Collectors.toList());
        String errorLine = "";
        for (int errorIndex = 0; errorIndex < errors.size(); errorIndex++) {
            if (errorIndex != errors.size() - 1) {
                errorLine += errors.get(errorIndex) + ", ";
            }
            else {
                errorLine += errors.get(errorIndex);
            }
        }
        return buildResponseEntity(errorLine, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<ApiExceptionResponse> buildResponseEntity(String message, HttpStatus status) {
        ApiExceptionResponse apiExceptionResponse = new ApiExceptionResponse(message, status.value());
        return new ResponseEntity<>(apiExceptionResponse, status);
    }
}