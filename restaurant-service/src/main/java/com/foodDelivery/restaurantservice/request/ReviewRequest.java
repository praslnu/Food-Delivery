package com.foodDelivery.restaurantservice.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewRequest{
    @NotBlank(message = "Review must not be empty")
    private String review;
    @NotNull(message = "Rating must not be empty")
    @DecimalMax(value = "5.0", message = "Rating cannot be more than 5.0", inclusive = true)
    @DecimalMin(value = "1.0", message = "Rating cannot be less than 1.0", inclusive = true)
    private Double rating;
}