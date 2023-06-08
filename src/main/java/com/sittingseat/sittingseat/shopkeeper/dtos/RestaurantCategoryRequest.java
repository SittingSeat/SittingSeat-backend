package com.sittingseat.sittingseat.shopkeeper.dtos;

import com.sittingseat.sittingseat.enums.FoodCategoryEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantCategoryRequest {
    List<FoodCategoryEnum> categories;
}
