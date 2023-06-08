package com.sittingseat.sittingseat.shopkeeper.dtos;

import com.querydsl.core.annotations.QueryProjection;
import com.sittingseat.sittingseat.domain.ImageFile;
import com.sittingseat.sittingseat.enums.FoodCategoryEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class RestaurantResponse {

    private Long id;
    private String name;
    private String greeting;
    private LocalDateTime openTime;
    private LocalDateTime closeTime;
    private Integer reservationTimeUnit;
    private String location;
    private String phone;
    private String snsLink;
    private String blogLink;
    private Integer totalTableCount;
    private FoodCategoryEnum category;
    private List<ImageFileDto> imagePaths;
    private double reviewScore;
    private int reviewCount;

    public RestaurantResponse(Long id, String name, String greeting, LocalDateTime openTime, LocalDateTime closeTime, Integer reservationTimeUnit, String location, String phone, String snsLink, String blogLink, Integer totalTableCount, FoodCategoryEnum category, List<ImageFileDto> imagePaths, double reviewScore, int reviewCount) {
        this.id = id;
        this.name = name;
        this.greeting = greeting;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.reservationTimeUnit = reservationTimeUnit;
        this.location = location;
        this.phone = phone;
        this.snsLink = snsLink;
        this.blogLink = blogLink;
        this.totalTableCount = totalTableCount;
        this.category = category;
        this.imagePaths = imagePaths;
        this.reviewScore = reviewScore;
        this.reviewCount = reviewCount;
    }
}
