package com.foodDelivery.paymentservice.service;

import com.foodDelivery.paymentservice.dto.PaymentDto;
import com.foodDelivery.paymentservice.entity.PaymentDetails;
import com.foodDelivery.paymentservice.repository.PaymentRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class PaymentService{
    @Autowired
    private PaymentRepository paymentRepository;
    public long doPayment(PaymentDto paymentDto) {
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
}
