package com.foodDelivery.paymentservice.controller;

import com.foodDelivery.paymentservice.dto.PaymentDto;
import com.foodDelivery.paymentservice.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
public class PaymentController{
    @Autowired
    private PaymentService paymentService;
    @PostMapping
    public ResponseEntity<Long> doPayment(@RequestBody PaymentDto paymentDto) {
        return new ResponseEntity<>(
                paymentService.doPayment(paymentDto),
                HttpStatus.OK
        );
    }
}