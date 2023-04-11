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
public class PreferenceFood extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "preference_food_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private FoodCategoryEnum preferenceFood;

    @Builder
    public PreferenceFood(Member member, FoodCategoryEnum preferenceFood) {
        this.member = member;
        this.preferenceFood = preferenceFood;
    }

}
