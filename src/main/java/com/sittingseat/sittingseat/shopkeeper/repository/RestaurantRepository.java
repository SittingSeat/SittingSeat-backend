package com.sittingseat.sittingseat.shopkeeper.repository;

import com.sittingseat.sittingseat.shopkeeper.domain.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
}
