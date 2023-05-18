package com.sittingseat.sittingseat.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class ReservationRequest {
    private Long restaurantId;
    private String subscriberName;
    private LocalDateTime reservationTime;
    private int reservationNumber;
    private List<Integer> seats;
    private String phone;
}
