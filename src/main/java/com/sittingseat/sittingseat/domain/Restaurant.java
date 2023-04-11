package com.sittingseat.sittingseat.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Restaurant extends BaseEntity{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "restaurant_id")
    private Long id;

    private String name;
    private String greeting;
    private LocalDateTime openTime;
    private LocalDateTime closeTime;
    private Integer reservationUnit;
    private String location;
    private String phone;
    private String SnsLink;
    private String blogLink;
    private Integer totalTableCount;

    @Builder
    public Restaurant(
            String name,
            String greeting,
            LocalDateTime openTime,
            LocalDateTime closeTime,
            Integer reservationUnit,
            String location,
            String phone,
            String snsLink,
            String blogLink,
            Integer totalTableCount
    ) {
        this.name = name;
        this.greeting = greeting;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.reservationUnit = reservationUnit;
        this.location = location;
        this.phone = phone;
        SnsLink = snsLink;
        this.blogLink = blogLink;
        this.totalTableCount = totalTableCount;
    }
}
