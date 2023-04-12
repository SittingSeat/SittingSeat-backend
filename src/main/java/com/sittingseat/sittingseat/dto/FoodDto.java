package com.sittingseat.sittingseat.dto;

import com.sittingseat.sittingseat.enums.FoodCategoryEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FoodDto {
    private FoodCategoryEnum food;
}
