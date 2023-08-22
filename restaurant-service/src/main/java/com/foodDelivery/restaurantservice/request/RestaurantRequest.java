package com.foodDelivery.restaurantservice.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RestaurantRequest{
    @Size(min = 1, max = 20, message = "Restaurant name length should be between 1 to 20")
    @NotBlank(message = "Restaurant name must not be empty")
    private String name;
}
