package com.foodDelivery.authservice.external.client;

import com.foodDelivery.authservice.request.UserRequest;
import com.foodDelivery.authservice.response.UserCredentials;
import com.foodDelivery.authservice.response.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "USER-SERVICE/user")
public interface UserClient{
    @PostMapping("/register")
    public UserResponse registerUser(@RequestBody UserRequest userRequest);

    @GetMapping("/username/{username}")
    public UserCredentials getUserDetails(@RequestParam String username);
}
