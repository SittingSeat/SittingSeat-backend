package com.sittingseat.sittingseat.domain;

import com.sittingseat.sittingseat.dto.FoodType;
import com.sittingseat.sittingseat.enums.FoodCategoryEnum;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Food {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Enumerated(EnumType.STRING)
    private FoodCategoryEnum food;

    @Enumerated(EnumType.STRING)
    private FoodType foodType;


    @Builder
    public Food(Member member, FoodCategoryEnum food, FoodType foodType) {
        this.member = member;
        this.food = food;
        this.foodType = foodType;
    }
}
