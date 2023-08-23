package com.foodDelivery.userservice.external.decoder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.foodDelivery.userservice.response.ApiExceptionResponse;
import com.foodDelivery.userservice.exception.CustomException;
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
            return new CustomException(apiExceptionResponse.getError(), apiExceptionResponse.getStatusCode());
        } catch (IOException e) {
            throw new CustomException("Internal Server Error", HttpStatus.SC_INTERNAL_SERVER_ERROR);
        }
    }
}