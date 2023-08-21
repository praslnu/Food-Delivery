package com.foodDelivery.userservice.controller;

import com.foodDelivery.userservice.entity.CartItems;
import com.foodDelivery.userservice.model.CartDetails;
import com.foodDelivery.userservice.request.PaymentDetailsRequest;
import com.foodDelivery.userservice.service.UserService;
import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.JWTParser;
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
    public String addFoodToCart(Authentication authentication, @RequestBody CartDetails cartDetails) throws Exception
    {
        CartItems cartItems = userService.addFoodToCart(cartDetails.getFoodId(), cartDetails.getRestaurantId(), cartDetails.getQuantity(), cartDetails.getPrice(), authentication.getName());
        if (cartItems == null){
            log.error("add items to cart failed");
            throw new Exception("add items to cart failed");
        }
        return "Items added to the cart successfully";
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
        return new ResponseEntity<>(userService.getCartItems(authentication.getName()), HttpStatus.ACCEPTED);
    }

    @PostMapping("/cart/checkout")
    public void checkout(Authentication authentication, @RequestBody PaymentDetailsRequest paymentDetailsRequest){
        userService.checkOutItems(authentication.getName(), paymentDetailsRequest);
    }
}