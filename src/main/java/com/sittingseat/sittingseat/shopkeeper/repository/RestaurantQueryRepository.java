package com.sittingseat.sittingseat.shopkeeper.repository;

import com.sittingseat.sittingseat.enums.FoodCategoryEnum;
import com.sittingseat.sittingseat.shopkeeper.dtos.RestaurantDto;

import java.util.List;

public interface RestaurantQueryRepository {
    List<RestaurantDto> searchByName(String name);

    List<RestaurantDto> searchByCategories(List<FoodCategoryEnum> foodCategories);
}
