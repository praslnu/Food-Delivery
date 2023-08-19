package com.foodDelivery.authservice.external.client;

import com.foodDelivery.authservice.request.UserRequest;
import com.foodDelivery.authservice.response.UserCredentials;
import com.foodDelivery.authservice.response.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@FeignClient(name = "USER-SERVICE/user")
public interface UserClient{
    @PostMapping("/register")
    public UserResponse registerUser(@RequestBody UserRequest userRequest);

    @GetMapping("/username/{username}")
    public UserCredentials getUserDetails(@RequestParam String username);

    @GetMapping
    public List<UserResponse> getAllUsers(@RequestHeader("Authorization") String authorizationHeader);
}