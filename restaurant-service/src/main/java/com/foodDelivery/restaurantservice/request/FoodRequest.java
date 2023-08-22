package com.foodDelivery.restaurantservice.request;

import com.foodDelivery.restaurantservice.model.FoodType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FoodRequest{
    @NotBlank(message = "Food name must not be empty")
    private String name;
    private FoodType foodType;
    @NotNull(message = "Price must not be empty")
    private Double price;
}
