package com.foodDelivery.userservice.controller;

import com.foodDelivery.userservice.entity.CartItems;
import com.foodDelivery.userservice.external.response.OrderResponse;
import com.foodDelivery.userservice.model.CartDetails;
import com.foodDelivery.userservice.request.PaymentDetailsRequest;
import com.foodDelivery.userservice.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/user")
@Log4j2
public class UserController{
    @Autowired
    private UserService userService;

    @PutMapping("/cart")
    public ResponseEntity<CartItems> addFoodToCart(Authentication authentication, @RequestBody CartDetails cartDetails)
    {
        CartItems cartItems = userService.addFoodToCart(cartDetails.getFoodId(), cartDetails.getRestaurantId(), cartDetails.getQuantity(), cartDetails.getPrice(), authentication.getName());
        return new ResponseEntity<>(cartItems, HttpStatus.CREATED);
    }

    @DeleteMapping("/cart/restaurant/{restaurantId}")
    public void removeCartItems(Authentication authentication, @PathVariable long restaurantId){
        userService.removeCartItemsOfRestaurant(authentication.getName(), restaurantId);
    }

    @PutMapping("/cart/{cartItemId}")
    public ResponseEntity<CartItems> adjustFoodInCart(Authentication authentication, @PathVariable long cartItemId, @RequestParam String type)
    {
        return new ResponseEntity<>(userService.adjustFoodQuantityInCart(authentication.getName(), cartItemId, type), HttpStatus.OK);
    }

    @DeleteMapping("/cart/{cartItemId}")
    public ResponseEntity<String> removeCartItem(Authentication authentication, @PathVariable long cartItemId){
        return new ResponseEntity<>(userService.removeFromCart(authentication.getName(), cartItemId), HttpStatus.OK);
    }

    @GetMapping("/cart")
    public ResponseEntity<List<CartItems>> getCartItems(Authentication authentication){
        return new ResponseEntity<>(userService.getCartItems(authentication.getName()), HttpStatus.OK);
    }

    @PostMapping("/cart/checkout")
    public ResponseEntity<String> checkout(Authentication authentication, @RequestBody PaymentDetailsRequest paymentDetailsRequest){
        userService.checkOutItems(authentication.getName(), paymentDetailsRequest);
        return new ResponseEntity<>("Checked out items Successfully", HttpStatus.OK);
    }

    @GetMapping("/orders")
    public ResponseEntity<List<OrderResponse>> getPastOrders(){
        return new ResponseEntity<>(userService.getPastOrdered(), HttpStatus.OK);
    }
}