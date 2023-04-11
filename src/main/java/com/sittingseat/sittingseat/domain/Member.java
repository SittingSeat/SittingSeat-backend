package com.sittingseat.sittingseat.domain;

import com.sittingseat.sittingseat.enums.FoodCategoryEnum;
import com.sittingseat.sittingseat.enums.VeganEnum;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String name;
    private String email;
    private String password;
    private String phone;
    private String nickname;
    private String provider;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    @Builder
    public Member(String name, String email, String password, String phone, String nickname, String provider) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.nickname = nickname;
        this.provider = provider;
    }

    public void updateMember(String name, String email, String password, String phone, String nickname, String provider){
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.nickname = nickname;
        this.provider = provider;
    }

    public void createReservation(Reservation reservation){
        this.reservation = reservation;
    }

}
