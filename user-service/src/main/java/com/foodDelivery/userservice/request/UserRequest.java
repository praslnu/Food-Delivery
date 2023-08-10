package com.foodDelivery.userservice.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest{
    private long id;
    private String userName;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private long phoneNumber;
}