package com.sittingseat.sittingseat.repository;

import com.sittingseat.sittingseat.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
