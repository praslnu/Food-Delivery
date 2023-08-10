package com.foodDelivery.paymentservice.response;

import com.foodDelivery.paymentservice.dto.PaymentMode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponse{
    private long paymentId;
    private long orderId;
    private PaymentMode paymentMode;
    private String paymentStatus;
    private long amount;
}