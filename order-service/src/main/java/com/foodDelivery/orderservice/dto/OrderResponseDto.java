package com.foodDelivery.orderservice.dto;

import com.foodDelivery.orderservice.response.PaymentResponse;
import com.foodDelivery.orderservice.response.RestaurantResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponseDto{
    private long orderId;
    private String orderStatus;
    private double amount;
    private RestaurantResponse restaurantResponse;
    private PaymentResponse paymentResponse;
}