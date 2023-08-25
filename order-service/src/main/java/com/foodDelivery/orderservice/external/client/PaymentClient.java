package com.foodDelivery.orderservice.external.client;

import com.foodDelivery.orderservice.exception.CustomException;
import com.foodDelivery.orderservice.external.request.PaymentRequest;
import com.foodDelivery.orderservice.response.PaymentResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@CircuitBreaker(name = "external", fallbackMethod = "fallback")
@FeignClient(name = "PAYMENT-SERVICE/payment")
public interface PaymentClient{
    @PostMapping
    public ResponseEntity<Long> doPayment(@RequestBody PaymentRequest paymentDto);


    @GetMapping("/order/{orderId}")
    public PaymentResponse getPaymentDetailsByOrderId(@PathVariable long orderId);

    default ResponseEntity<Long> fallback(Exception e) {
        throw new CustomException("Payment Service is not available", 500);
    }
}