package com.sittingseat.sittingseat.repository;

import com.sittingseat.sittingseat.domain.Food;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodRepository extends JpaRepository<Food, Long> {
}
