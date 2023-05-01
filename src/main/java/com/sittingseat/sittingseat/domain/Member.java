package com.sittingseat.sittingseat.domain;

import com.sittingseat.sittingseat.enums.FoodCategoryEnum;
import com.sittingseat.sittingseat.enums.ProviderEnum;
import com.sittingseat.sittingseat.enums.RoleEnum;
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
    @Enumerated(value = EnumType.STRING)
    private ProviderEnum provider;

    @Enumerated(value = EnumType.STRING)
    private RoleEnum authority;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    @Builder
    public Member(String name, String email, String password, String phone, String nickname, ProviderEnum provider) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.nickname = nickname;
        this.provider = provider;
        this.authority = RoleEnum.USER;
    }

    public void updateMember(String name, String email, String password, String phone, String nickname, ProviderEnum provider){
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

    public static Member toEntity(Long id, String name, String email, String password, String phone, String nickname, ProviderEnum provider){
        Member member = new Member();
        member.id = id;
        member.name = name;
        member.email = email;
        member.password = password;
        member.phone = phone;
        member.nickname = nickname;
        member.provider = provider;
        member.authority = RoleEnum.USER;
        return member;
    }

}
