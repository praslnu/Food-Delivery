package com.foodDelivery.orderservice.request;

import com.foodDelivery.orderservice.dto.PaymentMode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderRequest{
    private long restaurantId;
    private List<Long> foods;
    private double totalPrice;
    private PaymentMode paymentMode;
}