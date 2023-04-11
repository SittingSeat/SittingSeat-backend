package com.sittingseat.sittingseat.domain;

import com.sittingseat.sittingseat.enums.TableStateEnum;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Seat extends BaseEntity{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seat_id")
    private Long id;

    private Integer tableNumber;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;
    private TableStateEnum tableState;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    @Builder
    public Seat(Integer tableNumber, Restaurant restaurant, TableStateEnum tableState, Reservation reservation) {
        this.tableNumber = tableNumber;
        this.restaurant = restaurant;
        this.tableState = tableState;
        this.reservation = reservation;
    }
}
