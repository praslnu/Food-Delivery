package com.foodDelivery.userservice.controller;

import com.foodDelivery.userservice.entity.CartItems;
import com.foodDelivery.userservice.exception.BadRequestException;
import com.foodDelivery.userservice.model.CartDetails;
import com.foodDelivery.userservice.request.UserLoginRequest;
import com.foodDelivery.userservice.request.UserRequest;
import com.foodDelivery.userservice.response.UserCredentials;
import com.foodDelivery.userservice.response.UserResponse;
import com.foodDelivery.userservice.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/user")
@Log4j2
public class UserController{
    @Autowired
    private UserService userService;

    @PostMapping("/admin/register")
    public ResponseEntity<UserResponse> registerAdmin(@RequestBody UserLoginRequest userLoginRequest, @RequestParam Boolean isAdmin)
    {
        if (userLoginRequest == null)
        {
            log.error("user details are invalid");
            throw new BadRequestException("Invalid User input");
        }
        return ResponseEntity.ok(userService.registerAdmins(userLoginRequest, isAdmin));
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers(){
        return ResponseEntity.ok(userService.getUsers());
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

    @GetMapping("/{id}")
    public UserResponse getUser(@RequestParam long id){
        return userService.getUser(id);
    }

    @GetMapping("/username/{username}")
    public UserCredentials getUserDetails(@RequestParam String username){
        return userService.getUser(username);
    }

    @PostMapping("/cart")
    public String addFoodToCart(@RequestBody CartDetails cartDetails) throws Exception
    {
        CartItems cartItems = userService.addFoodToCart(cartDetails.getUserId(), cartDetails.getFoodId(), cartDetails.getRestaurantId(), cartDetails.getQuantity());
        if (cartItems == null){
            log.error("add items to cart failed");
            throw new Exception("add items to cart failed");
        }
        return "Items added to the cart successfully";
    }

    @PutMapping("/cart/{cartItemId}")
    public ResponseEntity<CartItems> adjustFoodInCart(@PathVariable long cartItemId, @RequestParam String type)
    {
        return new ResponseEntity<>(userService.adjustFoodQuantityInCart(1, cartItemId, type), HttpStatus.OK);
    }

    @DeleteMapping("/cart/{cartItemId}")
    public ResponseEntity<String> removeCartItem(@PathVariable long cartItemId){
        return new ResponseEntity<>(userService.removeFromCart(1, cartItemId), HttpStatus.OK);
    }

    @GetMapping("/cart")
    public ResponseEntity<List<CartItems>> getCartItems(){
        return new ResponseEntity<>(userService.getCartItems(1), HttpStatus.ACCEPTED);
    }

    @PostMapping("/cart/checkout")
    public void checkout(){

    }
}