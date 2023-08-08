package com.foodDelivery.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDto{
    private long restaurantId;
    private double totalAmount;
    private long quantity;
    private PaymentMode paymentMode;
}