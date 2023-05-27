package com.sittingseat.sittingseat.shopkeeper.repository;

import com.sittingseat.sittingseat.shopkeeper.dtos.RestaurantDto;

import java.util.List;

public interface RestaurantQueryRepository {
    List<RestaurantDto> searchByName(String name);
}
