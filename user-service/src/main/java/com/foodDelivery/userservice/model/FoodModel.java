package com.foodDelivery.userservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FoodModel{
    private long id;
    private String name;
    private String foodType;
    private double price;
}
