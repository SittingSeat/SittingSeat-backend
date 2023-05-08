package com.sittingseat.sittingseat.shopkeeper.repository;

import com.sittingseat.sittingseat.domain.Member;
import com.sittingseat.sittingseat.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByMember(Member member);
}
