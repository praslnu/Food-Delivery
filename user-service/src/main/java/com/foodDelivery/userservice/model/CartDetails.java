package com.foodDelivery.userservice.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartDetails{
    @NotNull(message = "Food id must not be empty")
    private Long foodId;
    @NotNull(message = "Restaurant Id not be empty")
    private Long restaurantId;
    @NotNull(message = "Quantity must not be empty")
    private Integer quantity;
    @NotNull(message = "Price must not be empty")
    private Double price;
}
