package com.foodDelivery.userservice.service;

import com.foodDelivery.userservice.entity.Cart;
import com.foodDelivery.userservice.entity.CartItems;
import com.foodDelivery.userservice.exception.BadRequestException;
import com.foodDelivery.userservice.exception.NotFoundException;
import com.foodDelivery.userservice.external.client.OrderClient;
import com.foodDelivery.userservice.external.request.OrderRequest;
import com.foodDelivery.userservice.external.response.OrderResponse;
import com.foodDelivery.userservice.repository.CartItemsRepository;
import com.foodDelivery.userservice.repository.CartRepository;
import com.foodDelivery.userservice.request.PaymentDetailsRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class UserService{
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartItemsRepository cartItemsRepository;
    @Autowired
    private OrderClient orderClient;

    public CartItems addFoodToCart(long foodId, long restaurantId, int quantity, double price, String email){
        Cart cart = cartRepository.findByEmail(email);
        if (cart == null) {
            cart = new Cart();
            cart.setEmail(email);
        }
        Long existingFoodItem = cartItemsRepository.findCartItemsByFoodIdAndRestaurantIdAndCartId(cart.getId(), restaurantId, foodId);
        CartItems cartItem;
        if (existingFoodItem != null){
            cartItem = cartItemsRepository.findById(existingFoodItem)
                    .orElseThrow(() -> new NotFoundException(String.format("Cart item with id:%s not found", existingFoodItem)));
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        }
        else
        {
            cartItem = CartItems.builder()
                    .cart(cart)
                    .foodId(foodId)
                    .restaurantId(restaurantId)
                    .quantity(quantity)
                    .price(price)
                    .build();
        }
        return cartItemsRepository.save(cartItem);
    }

    public CartItems adjustFoodQuantityInCart(String email, long cartItemId, String type){
        verifyCartItem(email, cartItemId);
        CartItems cartItem = cartItemsRepository.findById(cartItemId)
                .orElseThrow(() -> new NotFoundException(String.format("Cart item with id:%s not found", cartItemId)));
        if (type.equalsIgnoreCase("increment")){
            cartItem.setQuantity(cartItem.getQuantity() + 1);
            cartItemsRepository.save(cartItem);
        }
        else {
            if (cartItem.getQuantity() == 1){
                removeFromCart(email, cartItemId);
            }
            else{
                cartItem.setQuantity(cartItem.getQuantity() - 1);
                cartItemsRepository.save(cartItem);
            }
        }
        return cartItem;
    }

    public void verifyCartItem(String email, long cartItemId){
        Cart cart = cartRepository.findByEmail(email);
        if (cart == null) {
            throw new NotFoundException(String.format("Cart not found for user email: %s", email));
        }
        List<CartItems> cartItems = cartItemsRepository.findAllByCart(cart);
        Optional<CartItems> cartItemById = cartItems.stream().filter(cartItem -> cartItem.getId() == cartItemId).findAny();
        if (!cartItemById.isPresent()){
            throw new NotFoundException(String.format("Cart doesn't have an item with id : %s", cartItemId));
        }
    }

    public boolean removeCartItemsOfRestaurant(String email, long restaurantId){
        Cart cart = cartRepository.findByEmail(email);
        if (cart == null) {
            throw new NotFoundException(String.format("Cart not found for user email: %s", email));
        }
        List<CartItems> cartItems = cartItemsRepository.findAllByRestaurantIdAndCartId(restaurantId, cart.getId());
        cartItems.forEach(cartItem -> {
            removeFromCart(email, cartItem.getId());
        });
        return cartItems.size() == 0;
    }

    public String removeFromCart(String email, long cartItemId){
        verifyCartItem(email, cartItemId);
        cartItemsRepository.findById(cartItemId)
                .orElseThrow(() -> new NotFoundException(String.format("Cart item with id:%s not found", cartItemId)));
        cartItemsRepository.deleteById(cartItemId);
        return "Successfully removed item from the cart";
    }

    public List<CartItems> getCartItems(String email){
        Cart cart = cartRepository.findByEmail(email);
        if (cart == null) {
            throw new NotFoundException(String.format("Cart not found for user email: %s", email));
        }
        return cartItemsRepository.findAllByCart(cart);
    }

    public void checkOutItems(String email, PaymentDetailsRequest paymentDetailsRequest){
        Cart cart = cartRepository.findByEmail(email);
        if (cart == null) {
            throw new NotFoundException(String.format("Cart not found for user email: %s", email));
        }
        List<Long> userCartRestaurants = cartItemsRepository.findAllRestaurantsOfUser(cart.getId());
        if (userCartRestaurants.size() == 0){
            throw new BadRequestException("Cart is empty!");
        }
        userCartRestaurants.forEach(restaurantId -> {
            List<CartItems> restaurantItem = cartItemsRepository.findAllByRestaurantIdAndCartId(restaurantId , cart.getId());
            OrderRequest orderRequest = OrderRequest.builder()
                    .restaurantId(restaurantId)
                    .foods(new ArrayList<>())
                    .build();
            double[] totalPrice = {0.0};
            restaurantItem.forEach(item -> {
                System.out.println(item.getRestaurantId() + " " + item.getId());
                orderRequest.getFoods().add(item.getFoodId());
                totalPrice[0] += item.getPrice();
            });
            orderRequest.setTotalPrice(totalPrice[0]);
            orderRequest.setPaymentMode(paymentDetailsRequest.getPaymentMode());
            System.out.println(orderRequest.getRestaurantId() + " " + orderRequest.getTotalPrice() + " " + orderRequest.getFoods().toString() + " " + orderRequest.getPaymentMode());
            log.info("Placing the order now");
            orderClient.placeOrder(orderRequest);
        });
    }

    public List<OrderResponse> getPastOrdered(){
        return orderClient.getPastOrders();
    }
}