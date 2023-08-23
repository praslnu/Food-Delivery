package com.foodDelivery.restaurantservice.external.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartDetailsRequest{
    private long userId;
    private long foodId;
    private long restaurantId;
    private int quantity;
}
