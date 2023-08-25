package com.foodDelivery.restaurantservice.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewRestaurantResponse{
    private long restaurantId;
    private String name;
}