package com.foodDelivery.orderservice.request;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewRequest{
    @NotBlank(message = "review cannot be null")
    private String review;
    @NotNull(message = "Rating must not be empty")
    @DecimalMax(value = "5.0", message = "Rating cannot be more than 5.0", inclusive = true)
    @DecimalMin(value = "1.0", message = "Rating cannot be less than 1.0", inclusive = true)
    private Double rating;
}