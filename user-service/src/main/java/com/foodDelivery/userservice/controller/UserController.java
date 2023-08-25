package com.foodDelivery.userservice.controller;

import com.foodDelivery.userservice.entity.CartItems;
import com.foodDelivery.userservice.external.response.OrderResponse;
import com.foodDelivery.userservice.model.CartDetails;
import com.foodDelivery.userservice.request.AddressRequest;
import com.foodDelivery.userservice.request.PaymentDetailsRequest;
import com.foodDelivery.userservice.response.AddressResponse;
import com.foodDelivery.userservice.response.FavouritesResponse;
import com.foodDelivery.userservice.response.SuccessResponse;
import com.foodDelivery.userservice.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/user")
@Log4j2
public class UserController{
    @Autowired
    private UserService userService;

    @PreAuthorize("hasAuthority('user')")
    @PutMapping("/cart")
    public ResponseEntity<String> addFoodToCart(Authentication authentication, @RequestBody @Valid CartDetails cartDetails)
    {
        userService.addFoodToCart(cartDetails.getFoodId(), cartDetails.getRestaurantId(), cartDetails.getQuantity(), cartDetails.getPrice(), authentication.getName());
        return new ResponseEntity<>("Successfully added food to cart", HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('user') || hasAuthority('admin')")
    @DeleteMapping("/cart/restaurant/{restaurantId}")
    public void removeCartItems(Authentication authentication, @PathVariable long restaurantId){
        userService.removeCartItemsOfRestaurant(authentication.getName(), restaurantId);
    }

    @PreAuthorize("hasAuthority('user') || hasAuthority('admin')")
    @PutMapping("/cart/{cartItemId}")
    public ResponseEntity<CartItems> adjustFoodInCart(Authentication authentication, @PathVariable long cartItemId, @RequestParam String type)
    {
        return new ResponseEntity<>(userService.adjustFoodQuantityInCart(authentication.getName(), cartItemId, type), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('user') || hasAuthority('admin')")
    @DeleteMapping("/cart/{cartItemId}")
    public ResponseEntity<SuccessResponse> removeCartItem(Authentication authentication, @PathVariable long cartItemId){
        SuccessResponse successResponse = SuccessResponse.builder()
                .message(userService.removeFromCart(authentication.getName(), cartItemId))
                .build();
        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('user') || hasAuthority('admin')")
    @GetMapping("/cart")
    public ResponseEntity<List<CartItems>> getCartItems(Authentication authentication){
        return new ResponseEntity<>(userService.getCartItems(authentication.getName()), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('user')")
    @PostMapping("/cart/checkout")
    public ResponseEntity<SuccessResponse> checkout(Authentication authentication, @RequestBody PaymentDetailsRequest paymentDetailsRequest){
        userService.checkOutItems(authentication.getName(), paymentDetailsRequest);
        SuccessResponse successResponse = SuccessResponse.builder()
                .message("Checked out items Successfully")
                .build();
        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('user')")
    @GetMapping("/orders")
    public ResponseEntity<List<OrderResponse>> getOrders(){
        return new ResponseEntity<>(userService.getPastOrdered(), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('user')")
    @PostMapping("/address")
    public ResponseEntity<AddressResponse> addAddress(Authentication authentication, @RequestBody @Valid AddressRequest addressRequest){
        return new ResponseEntity<>(userService.addAddress(authentication.getName(), addressRequest), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('user')")
    @PostMapping("/favourites/{restaurantId}")
    public ResponseEntity<SuccessResponse> addToFavourites(Authentication authentication, @PathVariable Long restaurantId){
        userService.addToFavourites(authentication.getName(), restaurantId);
        SuccessResponse successResponse = SuccessResponse.builder()
                .message("Added to favourites successfully")
                .build();
        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('user')")
    @GetMapping("/favourites")
    public ResponseEntity<FavouritesResponse> getFavouriteRestaurants(Authentication authentication){
        return new ResponseEntity<>(userService.getFavouriteRestaurants(authentication.getName()), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('user')")
    @GetMapping("/address")
    public ResponseEntity<List<AddressResponse>> getAddress(Authentication authentication){
        return new ResponseEntity<>(userService.getUserAddress(authentication.getName()), HttpStatus.OK);
    }
}