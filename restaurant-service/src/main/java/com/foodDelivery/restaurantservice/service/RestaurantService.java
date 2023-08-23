package com.foodDelivery.restaurantservice.service;

import com.foodDelivery.restaurantservice.entity.Food;
import com.foodDelivery.restaurantservice.entity.Review;
import com.foodDelivery.restaurantservice.external.client.UserClient;
import com.foodDelivery.restaurantservice.request.CartDetailsRequest;
import com.foodDelivery.restaurantservice.mapper.FoodMapper;
import com.foodDelivery.restaurantservice.mapper.RestaurantMapper;
import com.foodDelivery.restaurantservice.mapper.ReviewMapper;
import com.foodDelivery.restaurantservice.repository.ReviewRepo;
import com.foodDelivery.restaurantservice.request.RestaurantRequest;
import com.foodDelivery.restaurantservice.entity.Restaurant;
import com.foodDelivery.restaurantservice.exception.NotFoundException;
import com.foodDelivery.restaurantservice.repository.FoodRepository;
import com.foodDelivery.restaurantservice.repository.RestaurantRepo;
import com.foodDelivery.restaurantservice.request.ReviewRequest;
import com.foodDelivery.restaurantservice.response.FoodResponse;
import com.foodDelivery.restaurantservice.response.RestaurantResponse;
import com.foodDelivery.restaurantservice.response.ReviewResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Log4j2
public class RestaurantService{
    @Autowired
    private RestaurantRepo restaurantRepo;
    @Autowired
    private FoodRepository foodRepository;
    @Autowired
    private ReviewRepo reviewRepo;
    @Autowired
    private RestaurantMapper restaurantMapper;
    @Autowired
    private FoodMapper foodMapper;
    @Autowired
    private ReviewMapper reviewMapper;
    @Autowired
    private UserClient userClient;

    public List<RestaurantResponse> getAllRestaurants(Pageable pageable){
        return restaurantRepo.findAll(pageable).stream().map(restaurant -> restaurantMapper.getRestaurant(restaurant)).collect(Collectors.toList());
    }

    public List<RestaurantResponse> filterRestaurantsByNameAndFood(Pageable pageable, String name, String foodName){
        return restaurantRepo.findByRestaurantNameAndFoodName(name.toLowerCase(), foodName.toLowerCase(), pageable).stream().map(restaurant -> restaurantMapper.getRestaurant(restaurant)).collect(Collectors.toList());
    }

    public List<RestaurantResponse> filterRestaurantsByName(Pageable pageable, String name){
        return restaurantRepo.findByRestaurantName(name.toLowerCase(), pageable).stream().map(restaurant -> restaurantMapper.getRestaurant(restaurant)).collect(Collectors.toList());
    }

    public List<RestaurantResponse> filterRestaurantsByFood(Pageable pageable, String foodName){
        return restaurantRepo.findByFoodName(foodName.toLowerCase(), pageable).stream().map(restaurant -> restaurantMapper.getRestaurant(restaurant)).collect(Collectors.toList());
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

    public FoodResponse getFoodById(long foodId) {
        log.info("Get the Food for id: {}", foodId);
        Food food = foodRepository.findById(foodId)
                .orElseThrow(() -> new NotFoundException(String.format("Food with id:%s not found", foodId)));
        return foodMapper.getFood(food);
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
        if (Objects.isNull(restaurant)){
            throw new NotFoundException(String.format("Food with id:%s Not available in the restaurant", cartDetailsRequest.getFoodId()));
        }
        cartDetailsRequest.setPrice(food.getPrice());
        return userClient.addFoodToCart(cartDetailsRequest);
    }

    public ReviewResponse addReview(String email, long restaurantId, ReviewRequest reviewRequest){
        Restaurant restaurant = restaurantRepo.findById(restaurantId)
                .orElseThrow(() -> new NotFoundException(String.format("Restaurant with id:%s not found", restaurantId)));
        Review review = Review.builder()
                .email(email)
                .review(reviewRequest.getReview())
                .rating(reviewRequest.getRating())
                .restaurant(restaurant)
                .build();
        return reviewMapper.getReview(reviewRepo.save(review));
    }

    public String addToFavourites(long restaurantId){
        restaurantRepo.findById(restaurantId)
                .orElseThrow(() -> new NotFoundException(String.format("Restaurant with id:%s not found", restaurantId)));
        return userClient.addToFavourites(restaurantId);
    }
}