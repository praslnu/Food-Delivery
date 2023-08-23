package com.foodDelivery.paymentservice.request;

import com.foodDelivery.paymentservice.dto.PaymentMode;
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
    private long amount;
    private PaymentMode paymentMode;
}
