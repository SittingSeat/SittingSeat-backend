package com.sittingseat.sittingseat.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReservationDto {
    private Long id;
    private String memberName;
    private String restaurantName;
    private int numberOfPeople;
}
