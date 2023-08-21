package com.foodDelivery.restaurantservice.mapper;

import com.foodDelivery.restaurantservice.entity.Food;
import com.foodDelivery.restaurantservice.entity.Review;
import com.foodDelivery.restaurantservice.request.FoodRequest;
import com.foodDelivery.restaurantservice.response.FoodResponse;
import com.foodDelivery.restaurantservice.response.ReviewResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReviewMapper{
    @Autowired
    private ModelMapper modelMapper;

    public ReviewResponse getReview(Review review) {
        ReviewResponse reviewResponse = modelMapper.map(review, ReviewResponse.class);
        return reviewResponse;
    }
}
