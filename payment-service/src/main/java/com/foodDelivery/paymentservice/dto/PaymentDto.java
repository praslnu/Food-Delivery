package com.foodDelivery.paymentservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentDto{
    private long orderId;
    private long amount;
    private String referenceNumber;
    private PaymentMode paymentMode;
}
