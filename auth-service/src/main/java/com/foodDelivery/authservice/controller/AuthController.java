package com.foodDelivery.authservice.controller;

import com.foodDelivery.authservice.exception.NotFoundException;
import com.foodDelivery.authservice.external.client.UserClient;
import com.foodDelivery.authservice.request.UserRequest;
import com.foodDelivery.authservice.request.LoginRequest;
import com.foodDelivery.authservice.response.TokenResponse;
import com.foodDelivery.authservice.response.UserResponse;
import com.foodDelivery.authservice.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController{
    @Autowired
    private UserClient userClient;
    @Autowired
    private AuthService authService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> addNewUser(@RequestBody UserRequest userRequest) {
        userRequest.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        return new ResponseEntity<>(userClient.registerUser(userRequest), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> getToken(@RequestBody LoginRequest loginRequest) throws Exception {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        if (authenticate.isAuthenticated()) {
            return new ResponseEntity<>(TokenResponse.builder().token(authService.generateToken(loginRequest.getUsername())).build(), HttpStatus.OK);
        }
        else {
            throw new Exception("invalid access");
        }
    }
}
