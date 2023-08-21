package com.foodDelivery.userservice.request;

import com.foodDelivery.userservice.model.PaymentMode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDetailsRequest{
    private PaymentMode paymentMode;
}
