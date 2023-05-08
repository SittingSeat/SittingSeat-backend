package com.sittingseat.sittingseat.shopkeeper.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReviewRequest {
    private Long restaurantId;
    private double score;
    private String content;
}
