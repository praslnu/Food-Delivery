package com.foodDelivery.orderservice.external.request;

import com.foodDelivery.orderservice.dto.PaymentMode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentRequest{
    private long orderId;
    private double amount;
    private String referenceNumber;
    private PaymentMode paymentMode;
}
