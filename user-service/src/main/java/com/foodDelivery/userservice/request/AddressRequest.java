package com.foodDelivery.userservice.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddressRequest{
    @Size(min = 3, max = 20, message = "Street Line1 length cannot be less than 3 and more than 20")
    @NotBlank(message = "Street Line1 must not be empty")
    private String streetLine1;

    @Size(min = 3, max = 20, message = "Street Line2 length cannot be less than 3 and more than 20")
    @NotBlank(message = "Street Line2 must not be empty")
    private String streetLine2;

    @NotBlank(message = "City must not be empty")
    private String city;

    @NotNull(message = "Pin-code must not be empty")
    private Long pinCode;
}
