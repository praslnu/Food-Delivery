package com.foodDelivery.orderservice.request;

import com.foodDelivery.orderservice.dto.PaymentMode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderRequest{
    private long cartId;
    private double totalAmount;
    private long quantity;
    private PaymentMode paymentMode;
}