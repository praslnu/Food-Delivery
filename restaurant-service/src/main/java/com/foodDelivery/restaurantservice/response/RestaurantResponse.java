package com.foodDelivery.restaurantservice.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantResponse{
    private long restaurantId;
    private String name;
    private List<FoodResponse> menu;
}
