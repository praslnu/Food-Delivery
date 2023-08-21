package com.foodDelivery.userservice.external.response;

import com.foodDelivery.userservice.response.FoodResponse;
import com.foodDelivery.userservice.response.PaymentResponse;
import com.foodDelivery.userservice.response.RestaurantResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse{
    private long orderId;
    private String orderStatus;
    private double amount;
    private RestaurantResponse restaurantResponse;
    private List<FoodResponse> foods;
    private PaymentResponse paymentResponse;
}
