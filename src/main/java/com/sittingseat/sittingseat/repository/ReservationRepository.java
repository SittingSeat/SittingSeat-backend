package com.sittingseat.sittingseat.repository;

import com.sittingseat.sittingseat.domain.Member;
import com.sittingseat.sittingseat.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByMember(Member member);
}
