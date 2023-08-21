package com.foodDelivery.orderservice.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponse{
    private long orderId;
    private String orderStatus;
    private double amount;
    private RestaurantResponse restaurantResponse;
    private List<FoodResponse> foods;
    private PaymentResponse paymentResponse;
}