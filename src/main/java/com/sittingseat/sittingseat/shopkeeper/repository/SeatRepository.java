package com.sittingseat.sittingseat.shopkeeper.repository;

import com.sittingseat.sittingseat.shopkeeper.domain.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatRepository extends JpaRepository<Seat, Long> {
}
