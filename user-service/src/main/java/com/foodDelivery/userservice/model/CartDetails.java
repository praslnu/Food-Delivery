package com.foodDelivery.userservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartDetails{
    private long foodId;
    private long restaurantId;
    private int quantity;
    private double price;
}
