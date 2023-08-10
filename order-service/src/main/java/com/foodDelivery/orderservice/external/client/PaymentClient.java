package com.foodDelivery.orderservice.external.client;

import com.foodDelivery.orderservice.external.request.PaymentRequest;
import com.foodDelivery.orderservice.response.PaymentResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "PAYMENT-SERVICE/payment")
public interface PaymentClient{
    @PostMapping
    public ResponseEntity<Long> doPayment(@RequestBody PaymentRequest paymentDto);
    @GetMapping("/order/{orderId}")
    public PaymentResponse getPaymentDetailsByOrderId(@PathVariable long orderId);
}