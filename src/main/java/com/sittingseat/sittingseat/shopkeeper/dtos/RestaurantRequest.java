package com.sittingseat.sittingseat.shopkeeper.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class RestaurantRequest {
    private String name;
    private String greeting;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int reservationTimeUnit;
    private String location;
    private String telephoneNumber;
    private String instagram;
    private String blog;

    private SeatRequest seatRequest;

    // 메뉴 사진들
    // 음식 및 인테리어 사진들

}
