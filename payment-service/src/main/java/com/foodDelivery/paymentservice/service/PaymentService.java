package com.foodDelivery.paymentservice.service;

import com.foodDelivery.paymentservice.dto.PaymentMode;
import com.foodDelivery.paymentservice.request.PaymentRequest;
import com.foodDelivery.paymentservice.entity.PaymentDetails;
import com.foodDelivery.paymentservice.repository.PaymentRepository;
import com.foodDelivery.paymentservice.response.PaymentResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class PaymentService{
    @Autowired
    private PaymentRepository paymentRepository;
    public long doPayment(PaymentRequest paymentDto) {
        log.info("Recording Payment Details: {}", paymentDto);
        PaymentDetails paymentDetails
                = PaymentDetails.builder()
                .paymentMode(paymentDto.getPaymentMode().name())
                .paymentStatus("SUCCESS")
                .orderId(paymentDto.getOrderId())
                .amount(paymentDto.getAmount())
                .build();
        paymentRepository.save(paymentDetails);
        log.info("Payment Completed with Id: {}", paymentDetails.getId());
        return paymentDetails.getId();
    }

    public PaymentResponse getPaymentDetailsByOrderId(String orderId) {
        log.info("Getting payment details for the Order Id: {}", orderId);

        PaymentDetails paymentDetails
                = paymentRepository.findByOrderId(Long.valueOf(orderId));

        PaymentResponse paymentResponse
                = PaymentResponse.builder()
                .paymentId(paymentDetails.getId())
                .paymentMode(PaymentMode.valueOf(paymentDetails.getPaymentMode()))
                .orderId(paymentDetails.getOrderId())
                .paymentStatus(paymentDetails.getPaymentStatus())
                .amount(paymentDetails.getAmount())
                .build();
        return paymentResponse;
    }
}