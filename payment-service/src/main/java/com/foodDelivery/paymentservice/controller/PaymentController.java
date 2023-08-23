package com.foodDelivery.paymentservice.controller;

import com.foodDelivery.paymentservice.request.PaymentRequest;
import com.foodDelivery.paymentservice.response.PaymentResponse;
import com.foodDelivery.paymentservice.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
public class PaymentController{
    @Autowired
    private PaymentService paymentService;

    @PreAuthorize("hasAuthority('user') || hasAuthority('admin')")
    @PostMapping
    public ResponseEntity<Long> doPayment(@RequestBody PaymentRequest paymentDto) {
        return new ResponseEntity<>(paymentService.doPayment(paymentDto), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('user') || hasAuthority('admin')")
    @GetMapping("/order/{orderId}")
    public ResponseEntity<PaymentResponse> getPaymentDetailsByOrderId(@PathVariable String orderId) {
        return new ResponseEntity<>(paymentService.getPaymentDetailsByOrderId(orderId), HttpStatus.OK);
    }
}