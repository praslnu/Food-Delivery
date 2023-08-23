package com.foodDelivery.userservice.repository;

import com.foodDelivery.userservice.entity.Cart;
import com.foodDelivery.userservice.entity.CartItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CartItemsRepository extends JpaRepository<CartItems, Long>{
    List<CartItems> findAllByCart(Cart cart);

    @Query("select ci.id from CartItems ci join Cart c on c.id = ci.cart.id where c.id = :cartId and ci.restaurantId = :restaurantId and ci.foodId = :foodId")
    Long findCartItemsByFoodIdAndRestaurantIdAndCartId(@Param("cartId") long cartId, @Param("restaurantId") long restaurantId, @Param("foodId") long foodId);
}