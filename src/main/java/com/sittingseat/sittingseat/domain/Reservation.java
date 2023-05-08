package com.sittingseat.sittingseat.domain;

import com.sittingseat.sittingseat.shopkeeper.domain.Restaurant;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reservation extends BaseEntity{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;
    private LocalDateTime time;
    private Integer numberOfPeople;

    @Builder
    public Reservation(Restaurant restaurant, LocalDateTime time, Integer numberOfPeople) {
        this.restaurant = restaurant;
        this.time = time;
        this.numberOfPeople = numberOfPeople;
    }
}
