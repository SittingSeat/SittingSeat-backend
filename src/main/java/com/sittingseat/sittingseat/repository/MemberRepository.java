package com.sittingseat.sittingseat.repository;

import com.sittingseat.sittingseat.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);

    @Query(value = "select m.nickname from Member m where m.nickname=:nickname and m.provider= 'KAKAO'")
    Optional<Member> findByKakaoNickname(String nickname);

}
