package com.foodDelivery.userservice.service;

import com.foodDelivery.userservice.entity.*;
import com.foodDelivery.userservice.exception.BadRequestException;
import com.foodDelivery.userservice.exception.NotFoundException;
import com.foodDelivery.userservice.external.client.OrderClient;
import com.foodDelivery.userservice.external.client.RestaurantClient;
import com.foodDelivery.userservice.external.request.OrderRequest;
import com.foodDelivery.userservice.external.response.OrderResponse;
import com.foodDelivery.userservice.mapper.AddressMapper;
import com.foodDelivery.userservice.repository.*;
import com.foodDelivery.userservice.request.AddressRequest;
import com.foodDelivery.userservice.request.PaymentDetailsRequest;
import com.foodDelivery.userservice.response.AddressResponse;
import com.foodDelivery.userservice.response.FavouritesResponse;
import com.foodDelivery.userservice.response.RestaurantResponse;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Log4j2
@AllArgsConstructor
public class UserService{
    private CartRepository cartRepository;
    private CartItemsRepository cartItemsRepository;
    private UserDetailsRepository userDetailsRepository;
    private LocationRepository locationRepository;
    private FavouritesRepository favouritesRepository;
    private OrderClient orderClient;
    private RestaurantClient restaurantClient;
    private AddressMapper addressMapper;

    public void addFoodToCart(long foodId, long restaurantId, int quantity, double price, String email){
        log.info("Adding food to cart");
        Cart cart = cartRepository.findByEmail(email);
        if (Objects.isNull(cart)) {
            log.info("creating a cart for user");
            cart = new Cart();
            cart.setEmail(email);
        }
        Long existingFoodItem = cartItemsRepository.findCartItemsByFoodIdAndRestaurantIdAndCartId(cart.getId(), restaurantId, foodId);
        CartItems cartItem;
        if (!Objects.isNull(existingFoodItem)){
            log.info("Updating the existing cart item");
            cartItem = cartItemsRepository.findById(existingFoodItem)
                    .orElseThrow(() -> new NotFoundException(String.format("Cart item with id:%s not found", existingFoodItem)));
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        }
        else
        {
            log.info("Adding item to the cart");
            cartItem = CartItems.builder()
                    .cart(cart)
                    .foodId(foodId)
                    .restaurantId(restaurantId)
                    .quantity(quantity)
                    .price(price)
                    .build();
        }
        cartItemsRepository.save(cartItem);
    }

    public CartItems adjustFoodQuantityInCart(String email, long cartItemId, String type){
        verifyCartItem(email, cartItemId);
        CartItems cartItem = cartItemsRepository.findById(cartItemId)
                .orElseThrow(() -> new NotFoundException(String.format("Cart item with id:%s not found", cartItemId)));
        if (type.equalsIgnoreCase("increment")){
            cartItem.setQuantity(cartItem.getQuantity() + 1);
            cartItemsRepository.save(cartItem);
        }
        else if (type.equalsIgnoreCase("decrement")){
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
        log.info("Verifying the cart details");
        Cart cart = cartRepository.findByEmail(email);
        if (Objects.isNull(cart)) {
            log.error(String.format("Cart not found for email : %s", email));
            throw new NotFoundException(String.format("Cart not found for user email: %s", email));
        }
        List<CartItems> cartItems = cartItemsRepository.findAllByCart(cart);
        Optional<CartItems> cartItemById = cartItems.stream().filter(cartItem -> cartItem.getId() == cartItemId).findAny();
        if (!cartItemById.isPresent()){
            log.info("Cart doesn't have an item");
            throw new NotFoundException(String.format("Cart doesn't have an item with id : %s", cartItemId));
        }
    }

    public boolean removeCartItemsOfRestaurant(String email, long restaurantId){
        Cart cart = cartRepository.findByEmail(email);
        if (Objects.isNull(cart)) {
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
        if (Objects.isNull(cart)) {
            throw new NotFoundException(String.format("Cart not found for user email: %s", email));
        }
        return cartItemsRepository.findAllByCart(cart);
    }

    public void checkOutItems(String email, PaymentDetailsRequest paymentDetailsRequest){
        UserDetails userDetails = userDetailsRepository.findByEmail(email);
        if (Objects.isNull(userDetails)){
            throw new NotFoundException("Address not found for the user");
        }
        List<Location> locations = locationRepository.findAllByUserDetails(userDetails);
        Optional<Location> userLocation = locations.stream().filter(location -> location.getId() == paymentDetailsRequest.getAddressId()).findFirst();
        if (!userLocation.isPresent()){
            throw new NotFoundException(String.format("User Location Not Found!"));
        }
        Cart cart = cartRepository.findByEmail(email);
        if (Objects.isNull(cart)) {
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
                    .addressId(paymentDetailsRequest.getAddressId())
                    .foods(new ArrayList<>())
                    .build();
            double[] totalPrice = {0.0};
            restaurantItem.forEach(item -> {
                orderRequest.getFoods().add(item.getFoodId());
                totalPrice[0] += (item.getPrice() * item.getQuantity());
            });
            orderRequest.setTotalPrice(totalPrice[0]);
            orderRequest.setPaymentMode(paymentDetailsRequest.getPaymentMode());
            log.info("Placing the order now");
            orderClient.placeOrder(orderRequest);
        });
    }

    public List<OrderResponse> getPastOrdered(){
        return orderClient.getPastOrders();
    }

    public UserDetails findUserDetails(String email){
        UserDetails userDetail = userDetailsRepository.findByEmail(email);
        if (Objects.isNull(userDetail)){
            userDetail = new UserDetails();
            userDetail.setEmail(email);
            userDetail = userDetailsRepository.save(userDetail);
        }
        return userDetail;
    }

    public AddressResponse addAddress(String email, AddressRequest addressRequest){
        UserDetails userDetail = findUserDetails(email);
        Location location = Location.builder()
                .streetLine1(addressRequest.getStreetLine1())
                .streetLine2(addressRequest.getStreetLine2())
                .city(addressRequest.getCity())
                .pinCode(addressRequest.getPinCode())
                .userDetails(userDetail)
                .build();
        return addressMapper.getAddress(locationRepository.save(location));
    }

    public void addToFavourites(String email, long restaurantId){
        UserDetails userDetail = findUserDetails(email);
        Favourites favourites = favouritesRepository.findByRestaurantId(restaurantId);
        if (!Objects.isNull(favourites)){
            throw new BadRequestException("Restaurant is already in favourites");
        }
        favourites = Favourites.builder()
                .userDetails(userDetail)
                .restaurantId(restaurantId)
                .build();
        favouritesRepository.save(favourites);
    }

    public FavouritesResponse getFavouriteRestaurants(String email){
        UserDetails userDetails = userDetailsRepository.findByEmail(email);
        List<RestaurantResponse> restaurantResponses = new ArrayList<>();
        userDetails.getFavourites().forEach(favourites -> {
            RestaurantResponse restaurantResponse = restaurantClient.getRestaurantById(favourites.getRestaurantId());
            restaurantResponses.add(restaurantResponse);
        });
        FavouritesResponse favouritesResponse = FavouritesResponse.builder()
                .favouriteRestaurants(restaurantResponses)
                .build();
        return favouritesResponse;
    }

    public List<AddressResponse> getUserAddress(String email){
        UserDetails userDetails = userDetailsRepository.findByEmail(email);
        if (Objects.isNull(userDetails)){
            throw new NotFoundException(String.format("Address not found for %s", email));
        }
        List<Location> addresses = locationRepository.findAllByUserDetails(userDetails);
        List<AddressResponse> addressResponses = new ArrayList<>();
        addresses.forEach(location -> {
            AddressResponse addressResponse = addressMapper.getAddress(location);
            addressResponses.add(addressResponse);
        });
        return addressResponses;
    }
}