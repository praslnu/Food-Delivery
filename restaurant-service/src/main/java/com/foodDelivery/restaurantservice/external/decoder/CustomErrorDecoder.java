package com.foodDelivery.restaurantservice.external.decoder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.foodDelivery.restaurantservice.exception.ApiExceptionResponse;
import com.foodDelivery.restaurantservice.exception.CustException;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.log4j.Log4j2;
import org.apache.hc.core5.http.HttpStatus;
import java.io.IOException;

@Log4j2
public class CustomErrorDecoder implements ErrorDecoder{
    @Override
    public Exception decode(String s, Response response) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            ApiExceptionResponse apiExceptionResponse = objectMapper.readValue(response.body().asInputStream(), ApiExceptionResponse.class);
            return new CustException(apiExceptionResponse.getError(), apiExceptionResponse.getStatusCode());
        } catch (IOException e) {
            throw new CustException("Internal Server Error", HttpStatus.SC_INTERNAL_SERVER_ERROR);
        }
    }
}