package com.foodDelivery.orderservice.external.client;

import com.foodDelivery.orderservice.dto.PaymentDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "PAYMENT-SERVICE/payment")
public interface PaymentClient{
    @PostMapping
    public ResponseEntity<Long> doPayment(@RequestBody PaymentDto paymentDto);
}