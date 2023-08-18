package com.foodDelivery.apigateway.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;

@Component
@Order(-2)
public class GlobalExceptionHandler implements WebExceptionHandler{

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        HttpStatus httpStatus;
        String errorMessage;

        if (ex instanceof AccessDeniedException) {
            httpStatus = HttpStatus.UNAUTHORIZED;
            errorMessage = ex.getMessage();
        } else if (ex instanceof BadRequestException) {
            httpStatus = HttpStatus.BAD_REQUEST;
            errorMessage = ex.getMessage();
        } else {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            errorMessage = ex.getMessage();
        }

        ApiExceptionResponse errorResponse = new ApiExceptionResponse(errorMessage, httpStatus.value());

        exchange.getResponse().setStatusCode(httpStatus);
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);

        byte[] responseBytes = new byte[0];
        try {
            responseBytes = objectMapper.writeValueAsBytes(errorResponse);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(responseBytes);

        return exchange.getResponse().writeWith(Mono.just(buffer));
    }
}
