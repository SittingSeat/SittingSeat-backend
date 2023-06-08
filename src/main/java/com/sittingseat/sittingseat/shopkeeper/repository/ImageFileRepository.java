package com.sittingseat.sittingseat.shopkeeper.repository;

import com.sittingseat.sittingseat.domain.ImageFile;
import com.sittingseat.sittingseat.shopkeeper.domain.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageFileRepository extends JpaRepository<ImageFile, Long> {
    List<ImageFile> findByRestaurantId(Long restaurantId);
}
