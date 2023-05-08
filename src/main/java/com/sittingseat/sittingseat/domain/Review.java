package com.sittingseat.sittingseat.domain;

import com.sittingseat.sittingseat.shopkeeper.domain.Restaurant;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review extends BaseEntity{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    private Double score;
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public Review(Double score, String content, Restaurant restaurant, Member member) {
        this.score = score;
        this.content = content;
        this.restaurant = restaurant;
        this.member = member;
    }

    public void updateReview(Double score, String content){
        this.score = score;
        this.content = content;
    }
}
