package com.sittingseat.sittingseat.shopkeeper.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SeatRequest {
    private int total;
    private int floor;

}
