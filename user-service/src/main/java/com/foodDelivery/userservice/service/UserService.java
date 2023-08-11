package com.foodDelivery.userservice.service;

import com.foodDelivery.userservice.entity.Cart;
import com.foodDelivery.userservice.entity.CartItems;
import com.foodDelivery.userservice.entity.Role;
import com.foodDelivery.userservice.entity.Users;
import com.foodDelivery.userservice.exception.NotFoundException;
import com.foodDelivery.userservice.mapper.UserMapper;
import com.foodDelivery.userservice.repository.CartItemsRepository;
import com.foodDelivery.userservice.repository.RoleRepository;
import com.foodDelivery.userservice.repository.UserRepository;
import com.foodDelivery.userservice.request.UserLoginRequest;
import com.foodDelivery.userservice.request.UserRequest;
import com.foodDelivery.userservice.response.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private CartItemsRepository cartItemsRepository;
    @Autowired
    private UserMapper userMapper;

    public UserResponse registerAdmins(UserLoginRequest userLoginRequest, Boolean isAdmin)
    {
        Users user =  userMapper.getUser(userLoginRequest);
        Role role;
        if (isAdmin){
            role = roleRepository.getByRole("admin");
        }
        else {
            role = roleRepository.getByRole("staff");
        }
        if (role == null){
            throw new NotFoundException("Role not found");
        }
        user.setRole(role);
        return userMapper.getUserResponse(userRepository.save(user));
    }

    public UserResponse registerUser(UserRequest userRequest)
    {
        Users user =  userMapper.getUser(userRequest);
        Role role = roleRepository.getByRole("user");
        if (role == null){
            throw new NotFoundException("Role not found");
        }
        user.setRole(role);
        return userMapper.getUserResponse(userRepository.save(user));
    }

    public List<UserResponse> getUsers(){
        Role role = roleRepository.getByRole("user");
        return userRepository.findAllByRole(role).stream()
                .map(user -> userMapper.getUserResponse(user))
                .toList();
    }

    public UserResponse getUser(long id){
        Users user = userRepository.getById(id);
        return userMapper.getUserResponse(user);
    }

    public CartItems addFoodToCart(long userId, long foodId, long restaurantId, int quantity){
        Cart cart = userRepository.findById(userId).orElseThrow(() -> new NotFoundException(String.format("User with id:%s not found", userId))).getCart();
        if (cart == null) {
            throw new NotFoundException(String.format("Cart not found for user id: %s", userId));
        }
        CartItems cartItems = CartItems.builder()
                .cart(cart)
                .foodId(foodId)
                .restaurantId(restaurantId)
                .quantity(quantity)
                .build();
        return cartItemsRepository.save(cartItems);
    }

    public void incrementFoodQuantityInCart(){

    }

    public void decrementFoodQuantityInCart(){

    }

    public void checkOutCart(){

    }
}