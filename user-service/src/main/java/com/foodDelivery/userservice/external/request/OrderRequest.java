package com.foodDelivery.userservice.external.request;

import com.foodDelivery.userservice.model.PaymentMode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest{
    private long restaurantId;
    private List<Long> foods;
    private double totalPrice;
    private long addressId;
    private PaymentMode paymentMode;
}
