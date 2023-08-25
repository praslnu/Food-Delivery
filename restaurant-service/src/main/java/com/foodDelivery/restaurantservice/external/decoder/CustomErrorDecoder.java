package com.foodDelivery.restaurantservice.external.decoder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.foodDelivery.restaurantservice.response.ApiExceptionResponse;
import com.foodDelivery.restaurantservice.exception.CustomException;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.apache.hc.core5.http.HttpStatus;
import java.io.IOException;

public class CustomErrorDecoder implements ErrorDecoder{
    @Override
    public Exception decode(String s, Response response) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            ApiExceptionResponse apiExceptionResponse = objectMapper.readValue(response.body().asInputStream(), ApiExceptionResponse.class);
            return new CustomException(apiExceptionResponse.getError(), apiExceptionResponse.getStatusCode());
        } catch (IOException e) {
            throw new CustomException("Internal Server Error", HttpStatus.SC_INTERNAL_SERVER_ERROR);
        }
    }
}