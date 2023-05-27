package com.sittingseat.sittingseat.shopkeeper.dtos;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;

@Getter
public class RestaurantDto {
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

    @QueryProjection
    public RestaurantDto(Long id, String name, String greeting, LocalDateTime openTime, LocalDateTime closeTime, Integer reservationTimeUnit, String location, String phone, String snsLink, String blogLink, Integer totalTableCount) {
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
    }
}
