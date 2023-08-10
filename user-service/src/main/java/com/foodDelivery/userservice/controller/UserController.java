package com.foodDelivery.userservice.controller;

import com.foodDelivery.userservice.exception.BadRequestException;
import com.foodDelivery.userservice.request.UserLoginRequest;
import com.foodDelivery.userservice.request.UserRequest;
import com.foodDelivery.userservice.response.UserResponse;
import com.foodDelivery.userservice.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Log4j2
public class UserController{
    @Autowired
    private UserService userService;

    @PostMapping("/admin/register")
    public ResponseEntity<UserResponse> registerUser(@RequestBody UserRequest userRequest, @RequestParam Boolean isAdmin)
    {
        if (userRequest == null)
        {
            log.error("user details are invalid");
            throw new BadRequestException("Invalid User input");
        }
        return ResponseEntity.ok(userService.registerAdmins(userRequest, isAdmin));
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser(@RequestBody UserRequest userRequest)
    {
        if (userRequest == null)
        {
            log.error("user details are invalid");
            throw new BadRequestException("Invalid User input");
        }
        return ResponseEntity.ok(userService.registerUser(userRequest));
    }

    @PostMapping("/login")
    public void authenticateAndGetToken(@RequestBody UserLoginRequest userLoginRequest)
    {
        // to verify user credentials and generate token
    }
}