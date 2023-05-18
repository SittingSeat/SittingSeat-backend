package com.sittingseat.sittingseat.shopkeeper.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
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

}
