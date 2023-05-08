package com.sittingseat.sittingseat.shopkeeper.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReviewResponse {
    private String restaurantName;
    private double score;
    private String content;
}
