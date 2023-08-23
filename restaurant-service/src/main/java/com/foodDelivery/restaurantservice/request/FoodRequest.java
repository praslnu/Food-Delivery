package com.foodDelivery.restaurantservice.request;

import com.foodDelivery.restaurantservice.model.FoodType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FoodRequest{
    private String name;
    private FoodType foodType;
    private double price;
}
