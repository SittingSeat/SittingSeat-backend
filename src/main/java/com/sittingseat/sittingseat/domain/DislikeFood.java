package com.sittingseat.sittingseat.domain;

import com.sittingseat.sittingseat.enums.FoodCategoryEnum;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DislikeFood extends BaseEntity{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dislike_food_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private FoodCategoryEnum dislikeFood;

    @Builder
    public DislikeFood(Member member, FoodCategoryEnum dislikeFood) {
        this.member = member;
        this.dislikeFood = dislikeFood;
    }
}
