package com.foodDelivery.restaurantservice.service;

import com.foodDelivery.restaurantservice.entity.Food;
import com.foodDelivery.restaurantservice.external.client.UserClient;
import com.foodDelivery.restaurantservice.external.request.CartDetailsRequest;
import com.foodDelivery.restaurantservice.mapper.RestaurantMapper;
import com.foodDelivery.restaurantservice.request.RestaurantRequest;
import com.foodDelivery.restaurantservice.entity.Restaurant;
import com.foodDelivery.restaurantservice.exception.NotFoundException;
import com.foodDelivery.restaurantservice.repository.FoodRepository;
import com.foodDelivery.restaurantservice.repository.RestaurantRepo;
import com.foodDelivery.restaurantservice.response.RestaurantResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
public class RestaurantService{
    @Autowired
    private RestaurantRepo restaurantRepo;
    @Autowired
    private FoodRepository foodRepository;
    @Autowired
    private RestaurantMapper restaurantMapper;
    @Autowired
    private UserClient userClient;

    public List<RestaurantResponse> getRestaurants(){
        return restaurantRepo.findAll().stream().map(restaurant -> restaurantMapper.getRestaurant(restaurant)).collect(Collectors.toList());
    }

    public Long addRestaurant(RestaurantRequest restaurantRequest) {
        log.info("Adding Restaurant..");
        Restaurant restaurant = restaurantRepo.save(restaurantMapper.getRestaurant(restaurantRequest));
        log.info("Restaurant Created");
        return restaurant.getId();
    }

    public RestaurantResponse getRestaurantById(long restaurantId) {
        log.info("Get the Restaurant for id: {}", restaurantId);
        Restaurant restaurant
                = restaurantRepo.findById(restaurantId)
                .orElseThrow(() -> new NotFoundException(String.format("Restaurant with id:%s not found", restaurantId)));

        return restaurantMapper.getRestaurant(restaurant);
    }

    public RestaurantResponse addFoodToRestaurant(long foodId, long restaurantId){
        Restaurant restaurant
                = restaurantRepo.findById(restaurantId)
                .orElseThrow(() -> new NotFoundException(String.format("Restaurant with id:%s not found", restaurantId)));

        Food food
                = foodRepository.findById(foodId)
                .orElseThrow(() -> new NotFoundException(String.format("Food with id:%s not found", foodId)));

        restaurant.getMenu().add(food);
        return restaurantMapper.getRestaurant(restaurantRepo.save(restaurant));
    }

    public String addFoodToCart(CartDetailsRequest cartDetailsRequest){
        Restaurant restaurant = restaurantRepo.findById(cartDetailsRequest.getRestaurantId())
                .orElseThrow(() -> new NotFoundException(String.format("Restaurant with id:%s not found", cartDetailsRequest.getRestaurantId())));
        Food food = foodRepository.findById(cartDetailsRequest.getFoodId())
                .orElseThrow(() -> new NotFoundException(String.format("Food with id:%s not found", cartDetailsRequest.getFoodId())));
        restaurant.getMenu().stream().filter(menu -> cartDetailsRequest.getFoodId() == menu.getId()).findAny().orElseThrow(() -> new NotFoundException(String.format("Food with id:%s is not available in the restaurant", cartDetailsRequest.getFoodId())));
        if (restaurant == null){
            throw new NotFoundException(String.format("Food with id:%s Not available in the restaurant", cartDetailsRequest.getFoodId()));
        }
        cartDetailsRequest.setPrice(food.getPrice());
        return userClient.addFoodToCart(cartDetailsRequest);
    }
}